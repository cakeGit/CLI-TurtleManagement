package `interface`.manager

import com.googlecode.lanterna.graphics.BasicTextImage
import java.lang.Integer.max

class TurtleActionLog {

    companion object {

        val log: ArrayList<BasicTextImage> = ArrayList();

        fun addEntry(entry: BasicTextImage) {
            log.add(entry);
        }

        /**
         * Returns the last `height` entries in the log
         * */
        fun getLogContents(height: Int) : List<BasicTextImage> {

            return log.subList(max((log.size - height) - 1, 0), log.size - 1);

        }
    }

}