val day10solver = solver(10) {
    val program = lineSequence().map {
        val tokens = it.split(' ')
        val operator = Operator.values().find { it.name.lowercase() == (tokens[0].lowercase()) }!!
        val operands = tokens.drop(1)
        operator to operands
    }.toList()

    val cpu = Cpu(program)
    var total = 0
    cpu.run {
        if ((cycle - 20) % 40 == 0) {
            total += signalStrength
        }
    }
    total.yield()
    cpu.crt.toString().yield()

}

private typealias Program = List<Pair<Operator, List<String>>>

private class Cpu(private val program: Program) {
    var cycle = 1
    var programIndex = 0
    var operatorCycle = 0

    var x = 1

    val crt = StringBuilder()

    val signalStrength get() = cycle * x

    fun run(observe: Cpu.() -> Unit) {
        while (programIndex < program.size) {
            // TODO start: totally off, ??? -> used the output in vim to realign it
            if ((cycle + 1) % 40 == 0) crt.appendLine()
            var c = cycle % 40
            val range = (c - 2)..(c)
            val visible = x in range
            if (visible) crt.append('#') else crt.append('.')
            // TODO: end
            observe(this)
            tick()
        }
    }

    fun tick() {
        val (operator, operands) = program[programIndex]
        if (operatorCycle == operator.cycles - 1) {
            execute(operator, operands)
        }
        cycle++
        operatorCycle++
        if (operatorCycle == operator.cycles) {
            operatorCycle = 0

            if (programIndex < program.size) {
                programIndex++
            } else {
                error("")
            }
        }
    }

    fun execute(operator: Operator, operands: List<String>) {
        when (operator) {
            Operator.NoOP -> {}
            Operator.AddX -> x += operands.first().toInt()
        }
    }

}

private enum class Operator(val cycles: Int) {
    NoOP(1),
    AddX(2)
}
