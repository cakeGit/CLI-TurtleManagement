package action.actions

import action.TurtleAction
import networking.turtle.ConnectedTurtle

class EmptyAction : TurtleAction() {

    override fun init(targets: HashSet<ConnectedTurtle>) {
        info("Ran empty task")
    }

    override fun perform(target: ConnectedTurtle) {
        info("Targeting " + target.getName())
    }

}