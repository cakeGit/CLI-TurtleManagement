package `interface`

import com.googlecode.lanterna.graphics.BasicTextImage
import com.googlecode.lanterna.graphics.TextImage


abstract class ScreenComponent(val componentDisplay: ComponentDisplay) {

    fun draw() : TextImage {
        val image = BasicTextImage(componentDisplay.width, componentDisplay.height);
        draw(image);
        return image;
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
