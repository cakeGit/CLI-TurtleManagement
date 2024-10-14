package `interface`

import com.googlecode.lanterna.TextColor
import com.googlecode.lanterna.graphics.BasicTextImage
import com.googlecode.lanterna.graphics.TextGraphics
import com.googlecode.lanterna.graphics.TextImage


abstract class ScreenComponent(val componentDisplay: ComponentDisplay) {

    fun draw() : TextImage {
        val image = BasicTextImage(componentDisplay.width, componentDisplay.height);
        draw(image);
        return image;
    }

    fun paintHeader(graphics: TextGraphics, focus: Boolean, createColumnHeader: String) {
        if (focus) {
            graphics.backgroundColor = TextColor.ANSI.WHITE;
            graphics.foregroundColor = TextColor.ANSI.BLACK;
        } else {
            graphics.backgroundColor = TextColor.ANSI.BLACK;
            graphics.foregroundColor = TextColor.ANSI.WHITE;
        }
        graphics.putString(0, 0, createColumnHeader);
    }

    abstract fun draw(image: TextImage)

    fun getWidth() : Int{
        return componentDisplay.width;
    }

    fun getHeight() : Int {
        return componentDisplay.height;
    }

    data class ComponentDisplay(val width: Int, val height: Int, val x: Int, val y: Int)

}
