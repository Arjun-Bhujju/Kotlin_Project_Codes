fun hideMessage(message: String): String {
    return message.map { it.code.toString(2).replace("0", "\u200B").replace("1", "\u200C") }.joinToString("\u200D")
}

fun revealMessage(hidden: String): String {
    return hidden.split("\u200D").map { it.replace("\u200B", "0").replace("\u200C", "1").toInt(2).toChar() }.joinToString("")
}

fun main() {
    val secret = "Hello"
    val hidden = hideMessage(secret)
    println("Hidden message: \"$hidden\" (Looks empty!)")
    println("Revealed: ${revealMessage(hidden)}")
}
