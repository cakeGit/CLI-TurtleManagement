package action

import action.actions.EmptyAction

enum class SelectableActions(val action: TurtleAction = EmptyAction()) {
    RCON,
    REBOOT,
    FORCE_BUILDS,
    DEV_BUILD_DIST,
    STABLE_BUILD_DIST,
    SET_PROGRAM,
    GPS_LOCATE
}