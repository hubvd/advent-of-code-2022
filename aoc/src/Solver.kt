interface SolverScope {
    fun lineSequence(): Sequence<String>

    fun lines() = lineSequence().toList()

    fun text() = lineSequence().joinToString("\n")

    fun groups() = text().split("\n\n").map { it.lines() }
    fun Any?.yield(text: String) {
        println("$text: $this")
    }

    fun Any?.yield()
}

open class SolverScopeImpl(day: Int) : SolverScope {

    private val input = Input(day).readText().trimEnd()
    protected var part = 1

    init {
        println("day $day:")
    }

    override fun lineSequence() = input.lineSequence()

    override fun Any?.yield() {
        println("part ${part++}: $this")
    }
}

data class Solver(val day: Int, val block: SolverScope.() -> Any)

object Solvers {
    private val solvers = Array<Solver?>(25) { null }

    operator fun plusAssign(solver: Solver) {
        solvers[solver.day -1] = solver
    }

    operator fun get(day: Int) = solvers[day -1]
}

fun solver(day: Int, block: SolverScope.() -> Unit) = Solver(day, block).also { Solvers += it }

fun Solver.solve() {
    with(SolverScopeImpl(day)) {
        block()
    }
}
