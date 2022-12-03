fun main() {
    val letters = listOf('#') + ('a'..'z') + ('A'..'Z')
    Input(3).lineSequence()
        .map { it.substring(0..<it.length / 2) to it.substring(it.length / 2..<it.length) }
        .map { (left, right) -> left.toHashSet().intersect(right.toHashSet()) }
        .sumOf { letters.indexOf(it.first()) }
        .also { println(it) }
    Input(3).lineSequence().chunked(3)
        .map { lines -> lines.map { it.toHashSet() }.reduce<Set<Char>, _> { acc, s -> acc.intersect(s) } }
        .sumOf { letters.indexOf(it.first()) }
        .also { println(it) }
}