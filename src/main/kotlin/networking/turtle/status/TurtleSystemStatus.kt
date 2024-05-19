package networking.turtle.status

import com.googlecode.lanterna.TextColor
import networking.turtle.ConnectedTurtle

enum class TurtleSystemStatus(
    val foregroundColor: TextColor.ANSI, val backgroundColor: TextColor.ANSI,
    val statusTextSupplier: (TurtleSystemStatus, ConnectedTurtle) -> String = {
            self: TurtleSystemStatus, _: ConnectedTurtle -> self.name
    }
) {
    REBOOTING_TIMEOUT(TextColor.ANSI.WHITE_BRIGHT, TextColor.ANSI.RED, { self: TurtleSystemStatus, turtle: ConnectedTurtle -> "REBOOT_FAIL!" }),
    REBOOTING(TextColor.ANSI.WHITE_BRIGHT, TextColor.ANSI.RED_BRIGHT, { self: TurtleSystemStatus, turtle: ConnectedTurtle -> self.name + " (" + turtle.getRebootTime() + "s)" }),
    CONNECTED(TextColor.ANSI.GREEN_BRIGHT, TextColor.ANSI.BLACK),
    DISCONNECTED(TextColor.ANSI.BLACK_BRIGHT, TextColor.ANSI.BLACK)
}