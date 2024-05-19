package networking.turtle.status

enum class TurtleNetworkStatus(var display: String, val fail: Boolean = false) {
    HTTP_FAIL("", true),
    AWAITING_HTTP(""),
    WS_FAIL("", true),
    AWAITING_WS(""),
    INIT("初始化"),
    NONE("");
}
