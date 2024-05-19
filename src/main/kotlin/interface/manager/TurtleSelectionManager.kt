package `interface`.manager

import com.googlecode.lanterna.input.KeyStroke
import com.googlecode.lanterna.input.KeyType
import `interface`.InputConsumer
import networking.turtle.ConnectedTurtle
import networking.turtle.status.TurtleNetwork
import java.lang.Integer.max
import java.lang.Integer.min

class TurtleSelectionManager {

    companion object Selection {

        val INPUT_CONSUMER: InputConsumer = InputConsumer { event ->
            if (event.isShiftDown) {
                TURTLE_SELECTION_SIZE = min(max(1,
                    TURTLE_SELECTION_SIZE - InputConsumer.getUpDownArrowOrdinal(event)),
                    getMaxSelectionHeight()
                );
            } else {
                TURTLE_SELECTION_POS = min(max(0,
                    TURTLE_SELECTION_POS - InputConsumer.getUpDownArrowOrdinal(event)),
                    TurtleNetwork.TURTLES.size - TURTLE_SELECTION_SIZE
                );
            }

            if (event.keyType == KeyType.Enter) {
                setCursorSelected(!isTurtleSelected(TurtleNetwork.TURTLES[TURTLE_SELECTION_POS]))
            }
        };

        fun isTurtleSelected(turtle: ConnectedTurtle): Boolean {
            return SELECTED_TURTLES.contains(turtle);
        }

        private fun keyPressed(event: KeyStroke) {
        }

        private fun setCursorSelected(select: Boolean) {
            for (i in TURTLE_SELECTION_POS until TURTLE_SELECTION_POS + TURTLE_SELECTION_SIZE) {
                val turtle = TurtleNetwork.TURTLES[i];
                if (select)
                    SELECTED_TURTLES.add(turtle);
                else
                    SELECTED_TURTLES.remove(turtle);
            }
        }

        private fun getMaxSelectionHeight(): Int {
            return TurtleNetwork.TURTLES.size - TURTLE_SELECTION_POS;
        }

        val SELECTED_TURTLES: HashSet<ConnectedTurtle> = HashSet();

        var TURTLE_SELECTION_POS = 0;
        var TURTLE_SELECTION_SIZE = 1;
        var TURTLE_SELECTION_ACTIVE = true;

    }

}