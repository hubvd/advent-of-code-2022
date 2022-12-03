private enum class RPC(val a: Char, val b: Char) {
    Rock('A', 'X'), Paper('B', 'Y'), Scissors('C', 'Z');

    companion object {
        operator fun invoke(char: Char) = values().find { it.a == char || it.b == char }!!
    }

}

private object RpcComparator : Comparator<RPC> {
    override fun compare(left: RPC, right: RPC): Int = when (left) {
        RPC.Rock -> when (right) {
            RPC.Rock -> 0
            RPC.Paper -> -1
            RPC.Scissors -> 1
        }

        RPC.Paper -> when (right) {
            RPC.Rock -> 1
            RPC.Paper -> 0
            RPC.Scissors -> -1
        }

        RPC.Scissors -> when (right) {
            RPC.Rock -> -1
            RPC.Paper -> 1
            RPC.Scissors -> 0
        }
    }
}

val day2solver = solver(2) {
    fun Pair<RPC, RPC>.score(): Int = ((RpcComparator.compare(second, first) + 1) * 3) + RPC.values().indexOf(second) + 1
    lineSequence().map { RPC(it[0]) to RPC(it[2]) }.toList().run {
        sumOf { it.score() }.yield()
        map { (theirs, ours) ->
            theirs to when (ours) {
                RPC.Rock -> RPC.values().find { RpcComparator.compare(it, theirs) < 0 }!!
                RPC.Paper -> RPC.values().find { RpcComparator.compare(it, theirs) == 0 }!!
                RPC.Scissors -> RPC.values().find { RpcComparator.compare(it, theirs) > 0 }!!
            }
        }.sumOf { it.score() }.yield()
    }
}
