import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class ExampleSolverScopeImpl(private val input: String) : SolverScope {

    private var part = 1
    val results = Array<Any?>(2) { null }
    override fun lineSequence() = input.lineSequence()

    override fun Any?.yield() {
        results[-1 + part++] = this
    }
}

class InputSolverScopeImpl(day: Int) : SolverScopeImpl(day) {

    val results = Array<Any?>(2) { null }
    override fun Any?.yield() {
        results[-1 + part++] = this
    }
}


abstract class SolverSpec(day: Int, body: SolverSpec.() -> Unit = {}) : FreeSpec() {
    private val solver: Solver

    init {
        Class.forName("Day${day.toString().padStart(2, '0')}Kt")
        solver = Solvers[day] ?: error("day not found")
    }

    lateinit var example: String
    var part1Example: Any? = null
    var part2Example: Any? = null
    var part1: Any? = null
    var part2: Any? = null

    init {
        body()

        "examples" - {
            val solverScope = ExampleSolverScopeImpl(example)
            with(solverScope) {
                with(solver) {
                    block()
                }
            }

            "part 1".config(enabled = part1Example != null && solverScope.results[0] != null) {
                solverScope.results[0] shouldBe part1Example
            }

            "part 2".config(enabled = part2Example != null && solverScope.results[1] != null) {
                solverScope.results[1] shouldBe part2Example
            }
        }

        "input" - {
            val solverScope = InputSolverScopeImpl(day)
            with(solverScope) {
                with(solver) {
                    block()
                }
            }
            "part 1".config(enabled = part1 != null && solverScope.results[0] != null) {
                solverScope.results[0] shouldBe part1
            }

            "part 2".config(enabled = part2 != null && solverScope.results[1] != null) {
                solverScope.results[1] shouldBe part2
            }
        }
    }

}