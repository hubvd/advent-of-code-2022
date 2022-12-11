private data class Monkey(
    val items: MutableList<Long>,
    val operation: String,
    val test: Int,
    val ok: Int,
    val notOk: Int
)

val day11solver = solver(11) {
    val monkeys = groups().map {
        Monkey(
            it[1].substringAfter(':').split(',').map { it.trim().toLong() }.toMutableList(),
            it[2].substringAfter("new = "),
            it[3].substringAfter("by ").toInt(),
            it[4].substringAfter("monkey ").toInt(),
            it[5].substringAfter("monkey ").toInt(),
        )
    }

    fun math(old: Long, operation: String): Long {
        val (a, op, b) = operation.replace("old", old.toString()).split(' ')
        return when (op) {
            "*" -> a.toLong() * b.toLong()
            "+" -> a.toLong() + b.toLong()
            else -> error("")
        }
    }

    fun part(rounds: Int, manage: (Long) -> Long) {
        val inspections = IntArray(monkeys.size)
        val monkeys = monkeys.map { it.copy(items = it.items.toMutableList()) } // deep copy
        fun round() {
            for ((index, monkey) in monkeys.withIndex()) {
                inspections[index] += monkey.items.size
                for (item in monkey.items) {
                    val level = manage(math(item, monkey.operation))
                    if (level % monkey.test == 0L) monkeys[monkey.ok].items += level
                    else monkeys[monkey.notOk].items += level
                }
                monkey.items.clear()
            }
        }
        repeat(rounds) { round() }
        inspections.sorted().takeLast(2).map { it.toLong() }.reduce { a, b -> a * b }.yield()
    }
    part(20) { it / 3 }
    val ppcm = monkeys.map { it.test }.reduce { acc, i -> acc * i }
    part(10000) { it % ppcm }

}
