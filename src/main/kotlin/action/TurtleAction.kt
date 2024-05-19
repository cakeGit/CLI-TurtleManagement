package action

import networking.turtle.ConnectedTurtle

abstract class TurtleAction {

    abstract fun init(targets: HashSet<ConnectedTurtle>)
    abstract fun perform(target: ConnectedTurtle)

    companion object {
        fun info(text: String) {

        }
    }
}
