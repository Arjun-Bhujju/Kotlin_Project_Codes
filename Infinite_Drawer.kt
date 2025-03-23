fun drawTree(levels: Int, prefix: String = "") {
    if (levels == 0) return
    println("$prefix|")
    println("$prefix+-")
    drawTree(levels - 1, "$prefix  ")
    println("$prefix+-")
    drawTree(levels - 1, "$prefix  ")
}

fun main() {
    drawTree(5)
}
