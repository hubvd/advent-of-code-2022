val day5solver = solver(5) {
    val (start, end) = text().split("\n\n")
    val stacks = start.lines().run {
        Regex("\\d+").findAll(last()).map { it.range }.map { range ->
            dropLast(1).map { it.padEnd(last().length).substring(range) }.filter { it.isNotBlank() }
        }.map { it.reversed().toMutableList() }.toList()
    }
    val instructions = Regex("\\d+").run { end.lines().map { findAll(it).map { it.value.toInt() }.toList() } }

    fun step(transform: (List<String>) -> List<String> = { it }) {
        val copy = stacks.map { it.toMutableList() }
        instructions.forEach { (count, from, to) ->
            val items = transform(copy[from - 1].takeLast(count))
            repeat(count) { copy[from - 1].removeLast() }
            copy[to - 1].addAll(items)
        }
        copy.joinToString("") { it.last() }.yield()
    }

    step { it.reversed() }
    step()
}