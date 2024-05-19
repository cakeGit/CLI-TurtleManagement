package `interface`

import CLIApp
import `interface`.manager.TurtleActionManager
import `interface`.manager.TurtleRconDisplayManager
import `interface`.manager.TurtleSelectionManager
import com.googlecode.lanterna.input.KeyStroke
import com.googlecode.lanterna.input.KeyType

class InputHandler {

    companion object {

        var CURRENT_INPUT_CONSUMER_INDEX = 0;
        var INPUT_CONSUMERS: List<InputConsumer> = listOf(TurtleSelectionManager.INPUT_CONSUMER, TurtleActionManager.INPUT_CONSUMER, TurtleRconDisplayManager.INPUT_CONSUMER);
        var CURRENT_INPUT_CONSUMER: InputConsumer = TurtleSelectionManager.INPUT_CONSUMER;

        fun tick() {
            while (true) {
                val input: KeyStroke = CLIApp.TERMINAL.pollInput() ?: break;
                if (input.keyType == KeyType.Tab) {
                    CURRENT_INPUT_CONSUMER.disable();
                    CURRENT_INPUT_CONSUMER_INDEX++;
                    CURRENT_INPUT_CONSUMER_INDEX = CURRENT_INPUT_CONSUMER_INDEX % INPUT_CONSUMERS.size;
                    CURRENT_INPUT_CONSUMER = INPUT_CONSUMERS[CURRENT_INPUT_CONSUMER_INDEX];
                    CURRENT_INPUT_CONSUMER.enable();
                }

                CURRENT_INPUT_CONSUMER.handler.accept(input);
            }

        }
    }

}
