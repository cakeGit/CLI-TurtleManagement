package networking.turtle

import networking.turtle.status.TurtleTask
import networking.turtle.status.TurtleNetworkStatus
import networking.turtle.status.TurtleSystemStatus

class ConnectedTurtle(val id: Int, var status: TurtleSystemStatus, var networkStatus: TurtleNetworkStatus = TurtleNetworkStatus.INIT, var devBuild: Boolean = false, var task: TurtleTask? = null) {

    fun currentTimeSeconds(): Long {
        return System.currentTimeMillis() / 1000;
    }

    var rebootBeginTimestamp: Long = currentTimeSeconds();

    private var lastTickTimeSeconds = (currentTimeSeconds()) -1;
    private var currentTimeSeconds = currentTimeSeconds();

    fun tick() {
        currentTimeSeconds = currentTimeSeconds();
        if (currentTimeSeconds > lastTickTimeSeconds) {
            secondsTick();
            lastTickTimeSeconds = currentTimeSeconds;
        }
    }

    fun secondsTick() {
        if (isRebooting() && rebootBeginTimestamp + 30 < currentTimeSeconds)
            status = TurtleSystemStatus.REBOOTING_TIMEOUT;
    }

    fun getRebootTime(): String {
        val timeSeconds = System.currentTimeMillis() / 1000;

        return (timeSeconds - rebootBeginTimestamp).toString();
    }

    fun getName(): String {
        return "TURTLE_${"%03d".format(id)}";
    }

    private fun isRebooting(): Boolean {
        return status == TurtleSystemStatus.REBOOTING;
    }

}