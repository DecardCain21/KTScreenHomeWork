package ui

import MenuItem
import datacache.DataCache
import model.Archive
import model.Notes
import navigation.*

class MenuOfArchives(navigation: Navigation) : Menu(navigation), IUserChoice {

    override val type: MenuItem
        get() = MenuItem.ArchiveMenu

    override fun start() {
        ShowMenu.start(MenuItem.ArchiveMenu)
        onMenuNavigate()
    }

    private fun onMenuNavigate() {
        when (userInputChoice()) {
            0 -> navigation.toScreen(ListMenu(navigation))
            1 -> navigation.toScreen(NewObjScreen(navigation))
            2 -> {
                navigation.onStop()
                navigation.toScreen(MenuOfArchives(navigation))
            }

            3 -> navigation.toScreen(EditMenu(navigation))
            else -> {
                println("Значение введено неверно")
                println(MenuImpl.showDataCollection())
            }
        }
    }

}

class MenuOfNotes(navigation: Navigation) : Menu(navigation), IUserChoice {

    override val type: MenuItem
        get() = MenuItem.NotesMenu

    override fun start() {
        ShowMenu.start(MenuItem.NotesMenu)
        onMenuNavigate()
    }

    private fun onMenuNavigate() {
        when (userInputChoice()) {
            0 -> navigation.toScreen(ListMenu(navigation))
            1 -> navigation.toScreen(NewObjScreen(navigation))
            2 -> {
                navigation.onStop()
                navigation.toScreen(MenuOfArchives(navigation))
            }

            3 -> navigation.toScreen(EditMenu(navigation))
            else -> {
                println("Значение введено неверно")
                println(MenuImpl.showDataCollection())
            }
        }
    }

}

class EditMenu(navigation: Navigation) : Menu(navigation), IUserChoice, IUserInput {


    override val type: MenuItem
        get() = MenuItem.EditMenu


    override fun start() {
        ShowMenu.start(MenuItem.EditMenu)
        when(MenuImpl.editDataType){
            EditType.TypeArchive -> println("EditObj:${MenuImpl.selectedArchive}")
            EditType.TypeNotes -> println("EditObj:${MenuImpl.selectedNote}")
        }
        onMenuNavigate()
    }

    private fun onMenuNavigate() {
        when (userInputChoice()) {
            0 -> {
                editing()
            }

            1 -> return
            else -> {
                println("Значение введено неверно")
                //println(MenuImpl.datacacheCollection.toString())
            }

        }
    }

    private fun editing() {
        val cache = DataCache.getInstance()
        when (MenuImpl.editDataType) {
            EditType.TypeArchive -> {
                //println("EditObj:${MenuImpl.tempArchive}")
                val temp = Archive(userInputCreate())
                cache.editFromCacheA(temp)
            }

            EditType.TypeNotes -> {
                //println("EditObj:${MenuImpl.tempNotes}")
                val temp = Notes(userInputCreate())
                cache.editFromCacheN(temp)
            }
        }
    }

}

class ListMenu(navigation: Navigation) : Menu(navigation), IUserChoice {

    override val type: MenuItem
        get() = MenuItem.ListMenu

    override fun start() {
        ShowMenu.start(MenuItem.ListMenu)
        onMenuNavigate()
    }

    private fun onMenuNavigate() {
        when (userInputChoice()) {
            0 -> {
                openDataItem()
                navigation.toScreen(MenuOfNotes(navigation))
            }

            1 -> navigation.toScreen(MenuOfArchives(navigation))
            else -> {
                println("Значение введено неверно")
                //println(datacacheCollection.toString())
            }
        }
    }

    private fun openDataItem() {
        val cache = DataCache.getInstance()
        when (MenuImpl.editDataType) {
            EditType.TypeArchive -> {
                val cacheArch = cache.archivesToList()
                val num = userInputChoice()
                MenuImpl.selectedArchive = cacheArch[num]
            }

            EditType.TypeNotes -> {
                val cacheNotes = cache.notesToList()
                val num = userInputChoice()
                MenuImpl.selectedNote = cacheNotes[num]
            }
        }
    }
}

class NewObjScreen(navigation: Navigation) : Menu(navigation), IUserInput, IUserChoice {

    override val type: MenuItem
        get() = MenuItem.NewObjScreen

    override fun start() {
        ShowMenu.start(MenuItem.NewObjScreen)
        onMenuNavigate()
    }

    private fun onMenuNavigate() {
        when (userInputChoice()) {
            0 -> createData()
            1 -> return
            else -> {
                println("Значение введено неверно")
                //println(datacacheCollection.toString())
            }
        }
    }

    private fun createData() {
        val cache = DataCache.getInstance()
        when (MenuImpl.editDataType) {
            EditType.TypeArchive -> {
                MenuImpl.selectedArchive = Archive(userInputCreate())
                cache.addToCache(MenuImpl.selectedArchive)
            }

            EditType.TypeNotes -> {
                MenuImpl.selectedNote = Notes(userInputCreate())
                cache.addNotes(MenuImpl.selectedNote)
            }
        }
    }

}