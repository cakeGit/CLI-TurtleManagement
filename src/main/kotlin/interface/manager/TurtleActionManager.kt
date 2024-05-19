package `interface`.manager

import action.SelectableActions
import com.googlecode.lanterna.input.KeyStroke
import com.googlecode.lanterna.input.KeyType
import `interface`.InputConsumer
import kotlin.math.ceil

class TurtleActionManager {

    companion object {

        val INPUT_CONSUMER: InputConsumer = InputConsumer { e -> keyPressed(e) };

        var CURSOR_COLUMN = 0;
        var CURSOR_ROW = 0;
        var VISUAL_CURSOR_ROW = 0;

        var AWAITING_CONFIRM = false;
        var CONFIRM_SELECTION = false;

        fun getColumnCount() : Int {
            return ceil(SelectableActions.values().size / 5.0).toInt();
        }
        fun getRowHeight(column: Int) : Int {
            return if (column < (getColumnCount() -1)) 5
            else if (column > (getColumnCount() -1)) 0
            else SelectableActions.values().size % 5;
        }

        private fun keyPressed(e: KeyStroke) {
            //Row changes even with a 0 ordinal, so check before changing

            if (!AWAITING_CONFIRM) {
                if (InputConsumer.getUpDownArrowOrdinal(e) != 0) {
                    CURSOR_ROW = (getRowHeight(CURSOR_COLUMN) + VISUAL_CURSOR_ROW - InputConsumer.getUpDownArrowOrdinal(e)) % getRowHeight(
                        CURSOR_COLUMN
                    );
                }
                CURSOR_COLUMN = (getColumnCount() + CURSOR_COLUMN + InputConsumer.getLeftRightArrowOrdinal(e)) % getColumnCount();

                if (CURSOR_ROW >= getRowHeight(CURSOR_COLUMN)) {
                    VISUAL_CURSOR_ROW = getRowHeight(CURSOR_COLUMN) -1;
                } else {
                    VISUAL_CURSOR_ROW = CURSOR_ROW;
                }
            } else {
                if (InputConsumer.getLeftRightArrowOrdinal(e) != 0)
                    CONFIRM_SELECTION = !CONFIRM_SELECTION;
            }

            if (e.keyType == KeyType.Enter) {
                if (AWAITING_CONFIRM) {
                    AWAITING_CONFIRM = false;
                    if (CONFIRM_SELECTION) {
                        val actionToPerform = SelectableActions.values()[CURSOR_COLUMN * 5 + CURSOR_ROW].action;
                        actionToPerform.init(TurtleSelectionManager.SELECTED_TURTLES);
                        for (turtle in TurtleSelectionManager.SELECTED_TURTLES) {
                            actionToPerform.perform(turtle)
                        }
                        CONFIRM_SELECTION = false;
                    }
                } else {
                    AWAITING_CONFIRM = true;
                    CONFIRM_SELECTION = false;
                }
            }
        }


    }
}
