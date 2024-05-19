package networking.turtle.status

import networking.turtle.ConnectedTurtle

class TurtleNetwork {
    companion object {
        val TURTLES: List<ConnectedTurtle> = listOf(
            ConnectedTurtle(23, TurtleSystemStatus.REBOOTING),
            ConnectedTurtle(12, TurtleSystemStatus.CONNECTED),
            ConnectedTurtle(15, TurtleSystemStatus.CONNECTED),
            ConnectedTurtle(16, TurtleSystemStatus.CONNECTED),
            ConnectedTurtle(20, TurtleSystemStatus.CONNECTED),
            ConnectedTurtle(21, TurtleSystemStatus.CONNECTED),
            ConnectedTurtle(32, TurtleSystemStatus.DISCONNECTED)
        );
    }
}