package `interface`.manager

import com.googlecode.lanterna.input.KeyStroke
import `interface`.InputConsumer
import networking.turtle.status.TurtleRemoteConsole

class TurtleRconDisplayManager {

    companion object {

        val INPUT_CONSUMER = InputConsumer { e -> onKeyPressed(e) };

        var OPEN_CONSOLES: ArrayList<TurtleRemoteConsole> = ArrayList();
        var SELECTED_CONSOLE = 0;
        var CURRENT_CONSOLE = null;

        private fun onKeyPressed(e: KeyStroke) {
            if (OPEN_CONSOLES.size == 0) return;

            if (e.isShiftDown) {
                SELECTED_CONSOLE = (OPEN_CONSOLES.size + SELECTED_CONSOLE + InputConsumer.getLeftRightArrowOrdinal(e)) % OPEN_CONSOLES.size;
            } else {

            }
        }

    }

}