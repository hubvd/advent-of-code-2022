fun main() {
    val letters = listOf('#') + ('a'..'z') + ('A'..'Z')
    fun Iterable<String>.priority() = map { it.toHashSet() }.reduce<Set<Char>, _> { acc, s -> acc.intersect(s) }
        .let { letters.indexOf(it.first()) }
    Input(3).lineSequence()
        .map { listOf(it.substring(0..<it.length / 2), it.substring(it.length / 2..<it.length)) }
        .sumOf { it.priority() }
        .also { println(it) }
    Input(3).lineSequence().chunked(3)
        .sumOf { it.priority() }
        .also { println(it) }
}
