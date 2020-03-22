package tsvan.com.models

class Game {

    private var index = 0

    var gamePackage : GamePackage? = null
    var players : ArrayList<Player>? = null

    var questions : ArrayList<Question>? = null

    companion object {
        val instance = Game()
    }
    fun clear() {
        instance.gamePackage = null
        instance.players = ArrayList()
        instance.questions = ArrayList()
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

    fun getNextQuestion(): String {
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