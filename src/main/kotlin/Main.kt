import CLIApp.Application.buildScreenComponents
import `interface`.InputHandler
import com.googlecode.lanterna.TerminalPosition
import com.googlecode.lanterna.TerminalSize
import com.googlecode.lanterna.terminal.DefaultTerminalFactory
import com.googlecode.lanterna.terminal.Terminal
import `interface`.ScreenComponent
import `interface`.components.TurtleActionList
import `interface`.components.TurtleOverviewList
import `interface`.components.TurtleRconDisplay
import networking.Networker
import networking.turtle.status.TurtleNetwork
import java.nio.charset.Charset
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit


class CLIApp {

    companion object Application {

        var TICK_HOLDER: Int = 0;
        val SCREEN_COMPONENTS: ArrayList<ScreenComponent> = ArrayList();

        val TERMINAL: Terminal = createTerminal();
        val SCHEDULER = Executors.newScheduledThreadPool(1);

        fun buildScreenComponents() {
            SCREEN_COMPONENTS.add(TurtleOverviewList());
            SCREEN_COMPONENTS.add(TurtleActionList());
            SCREEN_COMPONENTS.add(TurtleRconDisplay());
        }

        private fun createTerminal(): Terminal {
            return DefaultTerminalFactory(System.out, System.`in`, Charset.forName("UTF8"))
                .setTerminalEmulatorTitle("TURTLE CLI <3")
                .setInitialTerminalSize(getApplicationTerminalSize())
                .setForceAWTOverSwing(true)
                .createTerminal();
        }

        fun getApplicationTerminalSize(): TerminalSize {
            return TerminalSize(240, 67);
        }

        fun terminate() {
            SCHEDULER.shutdownNow();
            TERMINAL.exitPrivateMode();
        }

        fun redraw() {
            TICK_HOLDER++;
            for (screenComponent in SCREEN_COMPONENTS) {
                TERMINAL.newTextGraphics().drawImage(
                    TerminalPosition(screenComponent.componentDisplay.x, screenComponent.componentDisplay.y),
                    screenComponent.draw()
                );
                TERMINAL.flush()
            }
        }

        fun getFocusBlink(): Boolean {
            return TICK_HOLDER % 10 < 5;
        }

        fun getIndicatorBlink(): Boolean {
            return TICK_HOLDER % 20 < 5;
        }

        fun getPassiveBlink(): Boolean {
            return TICK_HOLDER % 60 < 10;
        }

        fun runProcess(process: Runnable, interval: Long, name: String) {
            CLIApp.SCHEDULER.schedule({
                try {
                    runProcess(process, interval, name);
                    process.run()
                } catch (e: Throwable) {
                    println("ERROR IN PROCESS $name")
                    e.printStackTrace();
                }
            }, interval, TimeUnit.MILLISECONDS);
        }

    }

}

fun main(args: Array<String>) {

    println("Good morning starshine, the earth says hello!");
    println("Program arguments: ${args.joinToString()}");

    CLIApp.TERMINAL.enterPrivateMode();
    CLIApp.TERMINAL.setCursorVisible(false);

    buildScreenComponents();
    InputHandler.CURRENT_INPUT_CONSUMER.enable();

    CLIApp.runProcess({
        CLIApp.redraw();
        InputHandler.tick();

        for (turtle in TurtleNetwork.TURTLES) {
            turtle.tick();
        }
    }, 1000 / 20, "BACKEND TICK");

    Networker.init();
    CLIApp.runProcess({
        Networker.tick();
    }, 1000 / 5, "NETWORK TICK");

}
