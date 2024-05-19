package networking.turtle.status

import com.googlecode.lanterna.graphics.BasicTextImage

class TurtleRemoteConsole {

    fun drawContent(image: BasicTextImage) {
        val graphics = image.newTextGraphics();
        graphics.putString(0, 0, "TURTLE NOT CONNECTED");
    }

}
