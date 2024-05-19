package `interface`.components

import `interface`.manager.TurtleRconDisplayManager
import com.googlecode.lanterna.TextColor
import com.googlecode.lanterna.graphics.TextImage
import `interface`.ScreenComponent
import `interface`.Text

class TurtleRconDisplay : ScreenComponent(ComponentDisplay(240 - TurtleOverviewList.totalWidth, 50, TurtleOverviewList.totalWidth, 8)) {

    override fun draw(image: TextImage) {
        val graphics = image.newTextGraphics();

        if (TurtleRconDisplayManager.INPUT_CONSUMER.hasFocus) {
            graphics.backgroundColor = TextColor.ANSI.WHITE;
            graphics.foregroundColor = TextColor.ANSI.BLACK;
        } else {
            graphics.backgroundColor = TextColor.ANSI.BLACK;
            graphics.foregroundColor = TextColor.ANSI.WHITE;
        }
        graphics.putString(0, 0, createColumnHeader());
    }

    private fun createColumnHeader(): String {
        return Text.enforceWidth("TURTLE", 20) + Text.enforceWidth("CONSOLE", getWidth() -20);
    }


}