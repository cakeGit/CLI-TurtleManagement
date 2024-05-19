package networking.turtle.status

import com.googlecode.lanterna.TextColor
import networking.turtle.ConnectedTurtle

enum class TurtleSystemStatus(
    val display: String,
    val foregroundColor: TextColor.ANSI, val backgroundColor: TextColor.ANSI,
    val statusTextSupplier: (TurtleSystemStatus, ConnectedTurtle) -> String = {
            self: TurtleSystemStatus, _: ConnectedTurtle -> self.display
    }
) {
    REBOOTING_TIMEOUT("重新启动超时", TextColor.ANSI.WHITE_BRIGHT, TextColor.ANSI.RED, { self: TurtleSystemStatus, turtle: ConnectedTurtle -> "重新启动超时!" }),
    REBOOTING("着重新启动", TextColor.ANSI.WHITE_BRIGHT, TextColor.ANSI.RED_BRIGHT, { self: TurtleSystemStatus, turtle: ConnectedTurtle -> self.display + " (" + turtle.getRebootTime() + "s)" }),
    CONNECTED("相通", TextColor.ANSI.GREEN_BRIGHT, TextColor.ANSI.BLACK),
    DISCONNECTED("不相通", TextColor.ANSI.BLACK_BRIGHT, TextColor.ANSI.BLACK)
}