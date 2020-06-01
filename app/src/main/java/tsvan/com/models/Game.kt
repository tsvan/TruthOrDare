package tsvan.com.models

class Game {

    var gamePackage: GamePackage? = null
    var players: ArrayList<Player>? = null
    var questions: ArrayList<Question>? = null

    private var stackPlayers: ArrayList<Player> = ArrayList()
    private var stackQuestions: ArrayList<Question> = ArrayList()

    companion object {
        val instance = Game()
    }

    fun clear() {
        instance.gamePackage = null
        instance.players = ArrayList()
        instance.questions = ArrayList()
    }

    fun setStack() {
        instance.stackPlayers.addAll(instance.players!!)
        instance.stackQuestions.addAll(instance.questions!!)
    }

    private fun getRandomPlayer(): Player {
        val player: Player
        if (instance.stackPlayers.count() > 0) {
            player = instance.stackPlayers.random()
            instance.stackPlayers.remove(player)
        } else {
            instance.stackPlayers.addAll(instance.players!!)
            player = instance.stackPlayers.random()
        }
        return player
    }

    private fun getRandomQuestion(): Question {
        val question: Question
        if (instance.stackQuestions.count() > 0) {
            question = instance.stackQuestions.random()
            instance.stackQuestions.remove(question)
        } else {
            instance.stackQuestions.addAll(instance.questions!!)
            question = instance.stackQuestions.random()
        }
        return question
    }

    fun getNextQuestion(): String {
        var player1: Player
        var player2: Player
        do {
            player1 = instance.getRandomPlayer()
            player2 = instance.getRandomPlayer()
        } while (player1 == player2)

        val question: Question = instance.getRandomQuestion()
        var questionText = question.text
        questionText = questionText.replace("%PLAYER1%", player1.name)
        questionText = questionText.replace("%PLAYER2%", player2.name)
        return questionText
    }
}