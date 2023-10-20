package datacache


import model.Archive
import model.Notes
import navigation.MenuImpl

class DataCache private constructor() {
    private val dataCacheCollection: MutableMap<Archive, MutableList<Notes>> = mutableMapOf()

    companion object {
        @Volatile
        private var instance: DataCache? = null

        fun getInstance(): DataCache {
            if (instance == null) {
                synchronized(DataCache::class) {
                    if (instance == null) {
                        instance = DataCache()
                    }
                }
            }
            return instance!!
        }
    }

    fun addToCache(archive: Archive) {
        if (!dataCacheCollection.containsKey(archive)) {
            dataCacheCollection[archive] = mutableListOf()
        }
        dataCacheCollection[archive] = mutableListOf()
    }

    fun addNotes(notes: Notes) {
        dataCacheCollection[MenuImpl.selectedArchive]?.add(notes)
    }

    fun editFromCacheA(archive: Archive) {
        val tempNotes = dataCacheCollection[MenuImpl.selectedArchive]
        dataCacheCollection.remove(MenuImpl.selectedArchive)?.let {
            if (tempNotes != null) {
                dataCacheCollection[archive] = tempNotes
            } else {
                dataCacheCollection[archive] = mutableListOf()
            }
            MenuImpl.selectedArchive = archive
        }
    }

    fun editFromCacheN(notes: Notes) {
        if (dataCacheCollection[MenuImpl.selectedArchive]?.contains(MenuImpl.selectedNote) == true) {
            dataCacheCollection[MenuImpl.selectedArchive]?.remove(MenuImpl.selectedNote)?.let {
                dataCacheCollection[MenuImpl.selectedArchive]?.add(notes)
                MenuImpl.selectedNote = notes
            }
        } else {
            println("Ошибка, возможно у вас выбрана заметка из другого архива")
        }
    }

    fun archivesToList(): List<Archive> {
        val values: List<Archive> = dataCacheCollection.keys.toList()
        for (i in values.indices) {
            println("\n$i)${values[i]}")
        }
        return values
    }

    fun notesToList(): MutableList<Notes> {
        val values: MutableList<Notes> = dataCacheCollection[MenuImpl.selectedArchive]!!.toMutableList()
        var ind = 0
        for (i in values) {
            println("\n$ind)${i}")
            ind++
        }
        return values
    }

    fun archivesIsEmpty(): Boolean {
        return dataCacheCollection.isEmpty()
    }

    fun notesIsEmpty(): Boolean {
        return dataCacheCollection[MenuImpl.selectedArchive]?.isEmpty() ?: false
    }

    fun removeFromCache(archive: Archive, notes: Notes) {
        if (archive in dataCacheCollection && notes in dataCacheCollection[archive]!!) {
            dataCacheCollection[archive]?.remove(notes)
            if (dataCacheCollection[archive]?.isEmpty() == true) {
                dataCacheCollection.remove(archive)
            }
        }
    }

    fun getNotesFromArchive(archive: Archive): List<Notes> {
        return dataCacheCollection[archive] ?: emptyList()
    }

    override fun toString(): String {
        return "datacache.DataCache(dataCacheCollection=$dataCacheCollection)"
    }
}