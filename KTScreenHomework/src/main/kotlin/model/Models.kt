package model

data class Archive(val name: String) {

    override fun toString(): String {
        return name
    }

}

data class Notes(val name: String) {

    override fun toString(): String {
        return this.name
    }

}