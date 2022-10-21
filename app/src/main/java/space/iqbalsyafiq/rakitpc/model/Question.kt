package space.iqbalsyafiq.rakitpc.model

data class Question(
    val question: String,
    val yesAnswer: Long,
    val noAnswer: Long,
    val yesNextQuestion: String,
    val noNextQuestion: String
)
