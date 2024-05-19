package `interface`

import java.util.function.Consumer
import com.googlecode.lanterna.input.KeyStroke
import com.googlecode.lanterna.input.KeyType

class InputConsumer(val handler: Consumer<KeyStroke>) {
    var hasFocus = false;

    fun disable() {
        hasFocus = false;
    }

    fun enable() {
        hasFocus = true;
    }

    companion object {
        fun getUpDownArrowOrdinal(event: KeyStroke) : Int {
            return when (event.keyType) {
                KeyType.ArrowUp -> 1
                KeyType.ArrowDown -> -1
                else -> 0
            }
        }
        fun getLeftRightArrowOrdinal(event: KeyStroke) : Int {
            return when (event.keyType) {
                KeyType.ArrowRight -> 1
                KeyType.ArrowLeft -> -1
                else -> 0
            }
        }
    }
}
