package networking.turtle.status

enum class TurtleNetworkStatus(val fail: Boolean = false) {
    HTTP_FAIL(true),
    AWAITING_HTTP,
    WS_FAIL(true),
    AWAITING_WS,
    INIT,
    NONE;
}
