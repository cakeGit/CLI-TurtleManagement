package `interface`.components

import CLIApp
import `interface`.manager.TurtleSelectionManager
import com.googlecode.lanterna.TerminalPosition
import com.googlecode.lanterna.TerminalSize
import com.googlecode.lanterna.TextColor.ANSI
import com.googlecode.lanterna.graphics.TextImage
import `interface`.ScreenComponent
import `interface`.Text
import networking.turtle.ConnectedTurtle
import networking.turtle.status.TurtleNetwork

class TurtleOverviewList : ScreenComponent(ComponentDisplay(totalWidth, 67, 0, 0)) {

    companion object DisplayInfo {
        val entryWidth = 100;
        val selectColumnWidth = "SELECT".length;
        val totalWidth = entryWidth + selectColumnWidth;
        val columnWidth = entryWidth / 5;
    }

    override fun draw(image: TextImage) {
        val graphics = image.newTextGraphics();

        if (TurtleSelectionManager.INPUT_CONSUMER.hasFocus) {
            graphics.backgroundColor = ANSI.WHITE;
            graphics.foregroundColor = ANSI.BLACK;
        } else {
            graphics.backgroundColor = ANSI.BLACK;
            graphics.foregroundColor = ANSI.WHITE;
        }
        graphics.putString(0, 0, createColumnHeader());

        for (y in 0..66) {
            if (TurtleNetwork.TURTLES.size <= y) break;

            val currentTurtle = TurtleNetwork.TURTLES[y]

            graphics.backgroundColor = currentTurtle.status.backgroundColor;
            graphics.foregroundColor = currentTurtle.status.foregroundColor;

            graphics.putString(selectColumnWidth, y+1, buildOverviewEntry(currentTurtle));

            if (TurtleSelectionManager.isTurtleSelected(currentTurtle)) {
                if (CLIApp.getPassiveBlink()) {
                    graphics.backgroundColor = ANSI.BLUE_BRIGHT;
                    graphics.foregroundColor = ANSI.WHITE_BRIGHT;
                } else {
                    graphics.backgroundColor = ANSI.BLUE_BRIGHT;
                    graphics.foregroundColor = ANSI.WHITE;
                }
                graphics.setCharacter(selectColumnWidth/2, y+1, '#');
            }
        }

        if (TurtleSelectionManager.TURTLE_SELECTION_ACTIVE) {
            val cursorHighlight = if (!TurtleSelectionManager.INPUT_CONSUMER.hasFocus) ANSI.BLACK else
                if (CLIApp.getFocusBlink()) ANSI.BLACK_BRIGHT else ANSI.BLACK;

            graphics.backgroundColor = cursorHighlight;
            graphics.foregroundColor = ANSI.WHITE;

            graphics.setCharacter(
                (selectColumnWidth/2) -2, TurtleSelectionManager.TURTLE_SELECTION_POS +1, '-'
            );
            graphics.fillRectangle(
                TerminalPosition((selectColumnWidth/2) -1, TurtleSelectionManager.TURTLE_SELECTION_POS +1),
                TerminalSize(1, TurtleSelectionManager.TURTLE_SELECTION_SIZE),
                '>'
            );

        }
    }

    private fun buildOverviewEntry(currentTurtle: ConnectedTurtle): String {
        return Text.enforceWidth(currentTurtle.getName(), columnWidth) +
                Text.enforceWidth(currentTurtle.status.statusTextSupplier.invoke(currentTurtle.status, currentTurtle), columnWidth) +
                Text.enforceWidth(if (currentTurtle.devBuild) "DEV" else "STABLE", columnWidth) +
                Text.enforceWidth(currentTurtle.task?.name ?: "NONE", columnWidth) +
                Text.enforceWidth(currentTurtle.networkStatus.name, columnWidth);
    }

    private fun createColumnHeader() : String {
        return "SELECT" + Text.enforceWidth("NAME", columnWidth) +
                Text.enforceWidth("STATUS", columnWidth) +
                Text.enforceWidth("BUILDS", columnWidth) +
                Text.enforceWidth("TASK", columnWidth) +
                Text.enforceWidth("NET", columnWidth);
    }



}