package `interface`.components

import CLIApp
import action.SelectableActions
import `interface`.manager.TurtleActionManager
import com.googlecode.lanterna.TextColor.ANSI
import com.googlecode.lanterna.graphics.TextImage
import `interface`.ScreenComponent
import `interface`.Text
import kotlin.math.ceil
import kotlin.math.floor

class TurtleActionList : ScreenComponent(ComponentDisplay(240 - TurtleOverviewList.totalWidth, 8, TurtleOverviewList.totalWidth, 0))  {

    override fun draw(image: TextImage) {
        val graphics = image.newTextGraphics();

        if (TurtleActionManager.INPUT_CONSUMER.hasFocus) {
            graphics.backgroundColor = ANSI.WHITE;
            graphics.foregroundColor = ANSI.BLACK;
        } else {
            graphics.backgroundColor = ANSI.BLACK;
            graphics.foregroundColor = ANSI.WHITE;
        }
        graphics.putString(0, 0, createColumnHeader());

        val columnCount = TurtleActionManager.getColumnCount();
        val columnWidth = getWidth() / columnCount;

        for ((i, action) in SelectableActions.values().withIndex()) {
            val column = i / 5;
            val row = i % 5;


            if (TurtleActionManager.VISUAL_CURSOR_ROW == row && TurtleActionManager.CURSOR_COLUMN == column) {
                if (TurtleActionManager.AWAITING_CONFIRM) {
                    if (CLIApp.getIndicatorBlink()) {
                        graphics.backgroundColor = ANSI.BLUE_BRIGHT;
                        graphics.foregroundColor = ANSI.BLACK;
                    } else {
                        graphics.backgroundColor = ANSI.BLUE_BRIGHT;
                        graphics.foregroundColor = ANSI.WHITE_BRIGHT;
                    }
                } else {
                    graphics.backgroundColor = ANSI.BLACK_BRIGHT;
                    graphics.foregroundColor = ANSI.WHITE_BRIGHT;
                }
            } else {
                graphics.backgroundColor = ANSI.BLACK;
                if (TurtleActionManager.AWAITING_CONFIRM) {
                    graphics.foregroundColor = ANSI.BLACK_BRIGHT;
                } else {
                    graphics.foregroundColor = ANSI.WHITE;
                }
            }

            graphics.putString(column * columnWidth, row + 1, Text.enforceWidth(action.toString(), columnWidth));
        }

        if (TurtleActionManager.AWAITING_CONFIRM) {
            graphics.foregroundColor = ANSI.BLACK;
            if (TurtleActionManager.CONFIRM_SELECTION) {
                graphics.backgroundColor = ANSI.GREEN_BRIGHT;
            } else {
                graphics.backgroundColor = ANSI.RED_BRIGHT;
            }

            graphics.putString(0, 6, Text.enforceWidth("CONFIRM ACTION SELECTION", getWidth()));

            graphics.backgroundColor = ANSI.GREEN_BRIGHT;
            graphics.putString(0, 7, Text.enforceWidth(
                (if (TurtleActionManager.CONFIRM_SELECTION) ">" else "") + "YES",
                ceil(getWidth()/2.0).toInt()));

            graphics.backgroundColor = ANSI.RED_BRIGHT;
            graphics.putString(ceil(getWidth()/2.0).toInt(), 7, Text.enforceWidth(
                (if (!TurtleActionManager.CONFIRM_SELECTION) ">" else "") + "NO",
                floor(getWidth()/2.0).toInt()));
        }
    }

    private fun createColumnHeader(): String {
        return Text.enforceWidth("ACTIONS",  getWidth());
    }

}