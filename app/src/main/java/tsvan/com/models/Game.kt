package tsvan.com.models

class Game {

    public var gamePackage : GamePackage? = null
        get() = field
        set(value) {
            field = value
        }
    public var players : ArrayList<Player>? = null
        get() = field
        set(value) {
            field = value
        }

    public var questions : ArrayList<Question>? = null
        get() = field
        set(value) {
            field = value
        }

    companion object {
        val instance = Game()
    }
}