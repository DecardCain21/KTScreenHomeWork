import navigation.MenuImpl
import navigation.Navigation
import ui.MenuOfArchives


fun main() {
    val navigation: Navigation = MenuImpl()
    (navigation as MenuImpl).stackAddMenu(MenuOfArchives(navigation))
    navigation.startProgramm()
}