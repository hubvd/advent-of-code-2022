val day5solver = solver(5) {
    val (start, end) = groups()
    val stacks = start.run {
        Regex("\\d").findAll(last()).map { it.range.first }.map { i ->
            dropLast(1).mapNotNull { it.getOrNull(i)?.takeIf { it != ' ' } }
        }.map { it.reversed().toMutableList() }.toList()
    }
    val instructions = Regex("\\d+").run { end.map { findAll(it).map { it.value.toInt() }.toList() } }

    fun step(transform: (List<Char>) -> List<Char> = { it }) = stacks.map { it.toMutableList() }.apply {
        instructions.forEach { (count, from, to) ->
            val items = transform(this[from - 1].takeLast(count))
            repeat(count) { this[from - 1].removeLast() }
            this[to - 1].addAll(items)
        }
    }.joinToString("") { "${it.last()}" }.yield()
    step { it.reversed() }
    step()
}