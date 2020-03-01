package tsvan.com.models

class Game {

    private var index = 0

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
    public fun clear() {
        instance.gamePackage = null
        instance.players = ArrayList<Player>()
        instance.questions = ArrayList<Question>()
    }

    private fun formatQuestionText(question : String) :String {
        var player1 = instance.players!!.random()
        var player2 = instance.players!!.random()
        while (instance.players!!.count() >= 2 && player1 == player2 ) {
            player1 = instance.players!!.random()
            player2 = instance.players!!.random()
        }

        var tmp = question
        tmp = tmp.replace("%PLAYER1%", instance.players!!.random().name)
        tmp = tmp.replace("%PLAYER2%", instance.players!!.random().name)
        return tmp
    }

    public fun getNextQuestion(): String {
        if(instance.index < instance.questions!!.count() ) {
            val text = formatQuestionText(instance.questions!!.get(instance.index).text)
            instance.index ++
            return text
        } else {
            instance.index = 0
            return formatQuestionText(instance.questions!!.first().text)
        }
    }
}