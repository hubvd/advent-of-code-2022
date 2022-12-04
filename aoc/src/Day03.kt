val day3solver = solver(3) {
    val letters = listOf('#') + ('a'..'z') + ('A'..'Z')
    fun Iterable<String>.priority() = map { it.toHashSet() }.reduce<Set<Char>, _> { acc, s -> acc.intersect(s) }
        .let { letters.indexOf(it.first()) }
    lineSequence().map { it.chunked(it.length / 2) }
        .sumOf { it.priority() }
        .yield()
    lineSequence().chunked(3)
        .sumOf { it.priority() }
        .yield()
}
