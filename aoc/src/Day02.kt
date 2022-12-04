private enum class RPS(val a: Char, val b: Char) {
    Rock('A', 'X'), Paper('B', 'Y'), Scissors('C', 'Z');

    companion object {
        operator fun invoke(char: Char) = values().find { it.a == char || it.b == char }!!
    }

}

private object RpsComparator : Comparator<RPS> {
    override fun compare(left: RPS, right: RPS): Int = when (left) {
        RPS.Rock -> when (right) {
            RPS.Rock -> 0
            RPS.Paper -> -1
            RPS.Scissors -> 1
        }

        RPS.Paper -> when (right) {
            RPS.Rock -> 1
            RPS.Paper -> 0
            RPS.Scissors -> -1
        }

        RPS.Scissors -> when (right) {
            RPS.Rock -> -1
            RPS.Paper -> 1
            RPS.Scissors -> 0
        }
    }
}

val day2solver = solver(2) {
    fun Pair<RPS, RPS>.score(): Int = ((RpsComparator.compare(second, first) + 1) * 3) + RPS.values().indexOf(second) + 1
    lineSequence().map { RPS(it[0]) to RPS(it[2]) }.toList().run {
        sumOf { it.score() }.yield()
        map { (theirs, ours) ->
            theirs to when (ours) {
                RPS.Rock -> RPS.values().find { RpsComparator.compare(it, theirs) < 0 }!!
                RPS.Paper -> RPS.values().find { RpsComparator.compare(it, theirs) == 0 }!!
                RPS.Scissors -> RPS.values().find { RpsComparator.compare(it, theirs) > 0 }!!
            }
        }.sumOf { it.score() }.yield()
    }
}
