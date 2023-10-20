package navigation

import EditType
import MenuItem
import datacache.DataCache
import model.Archive
import model.Notes
import java.util.*


interface IUserInput {

    fun userInputCreate(): String {
        val scanner: Scanner = Scanner(System.`in`)
        println("Введите название:")
        val temp = scanner.nextLine()
        println("Архив добавлен")
        return temp
    }

}

interface IUserChoice {

    fun userInputChoice(): Int {
        val scanner: Scanner = Scanner(System.`in`)
        val temp = scanner.nextLine()
        if (temp.equals("")) {
            return userInputChoice()
        }
        for (i in temp) {
            if (!Character.isDigit(i) or temp.equals("")) {
                println(i + " не число")
                return userInputChoice()
            }
        }
        return temp.toInt()
    }

}

interface Navigation {

    fun toScreen(widget: Menu)

    fun onStop()

}

abstract class Menu(val navigation: Navigation) {

    abstract val type: MenuItem

    abstract fun start()

}

class MenuImpl() : IUserChoice, Navigation {

    fun startProgramm() {

        println(stack.toString())
        while (stack.isNotEmpty()) {
            stack.peek().start()
        }

    }

    override fun onStop() {
        stackPopMenu()
    }

    override fun toScreen(widget: Menu) {
        if (stack.isEmpty()) {
            println("Завершение программы...")
            return
        }

        when (widget.type) {

            MenuItem.EditMenu -> {
                if (editTypeIsNull()) {
                    println("Ошибка")
                    return
                }
                widget.start()
            }

            MenuItem.ArchiveMenu -> {
                editDataType = EditType.TypeArchive
                widget.start()
            }

            MenuItem.NotesMenu -> {
                editDataType = EditType.TypeNotes
                if (!stack.contains(widget)) {
                    stackAddMenu(widget)
                }
                widget.start()
            }

            MenuItem.ListMenu -> {
                if (editTypeIsNull()) {
                    println("Ошибка")
                    return
                }
                widget.start()
            }

            MenuItem.NewObjScreen -> {
                widget.start()
            }

        }
    }

    private fun editTypeIsNull(): Boolean {
        val cache = DataCache.getInstance()
        if (editDataType == EditType.TypeArchive && cache.archivesIsEmpty()) {
            println("editDataType ERROR")
            return true
        }
        if (editDataType == EditType.TypeNotes && cache.notesIsEmpty() == true) {
            println("editDataType ERROR2")
            return true
        }
        return false
    }

    fun stackAddMenu(menu: Menu) {
        stack.push(menu)
    }

    fun stackPopMenu() {
        stack.pop()
    }


    companion object {
        val stack: Stack<Menu> = Stack()
        val dataS = DataCache.getInstance()

        //TempData
        var selectedArchive: Archive = Archive("")
        var selectedNote: Notes = Notes("")

        //Тип для меню Edit
        var editDataType: EditType = EditType.TypeArchive

        fun showDataCollection() {
            println(DataCache.getInstance().toString())
        }
    }

}

class ShowMenu() : IUserChoice, IUserInput {
    companion object {
        fun start(menuitem: MenuItem) {

            when (menuitem) {
                MenuItem.EditMenu -> {
                    println("->EditMenu")
                    println(
                        "Меню Радиктирования:\n" + "0. Редактировать:\n" + "1. Выход\n"

                    )
                }

                MenuItem.ArchiveMenu -> {
                    println("->MainMenu")
                    println(
                        "Меню Архивов:\n" + "0. Это мой уже созданный архив\n" + "1. Создать архив\n" + "2. Выход \n" + "3. Редактировать\n"
                    )
                }

                MenuItem.NotesMenu -> {
                    println("->NotesMenu")
                    println(
                        "Меню Заметок:\n" + "0. Это моя уже созданная заметка\n" + "1. Создать заметку\n" + "2. Выход\n" + "3. Редактировать\n"
                    )
                }

                MenuItem.ListMenu -> {
                    println("->ListMenu")
                    println(
                        "Меню Список:\n" + "0. Выбрать из списка:\n" + "1. Выход\n"

                    )

                }

                MenuItem.NewObjScreen -> {
                    println("->NewObjScreen")
                    println(
                        "Меню Создания:\n" + "0. Введите текст:\n" + "1. Выход\n"

                    )
                }
            }

        }
    }
}