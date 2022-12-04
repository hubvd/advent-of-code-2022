import java.time.LocalDate

fun main(args: Array<String>) {
    val day = args.firstOrNull()?.toIntOrNull() ?: LocalDate.now().dayOfMonth
    Class.forName("Day${day.toString().padStart(2, '0')}Kt")
    Solvers[day]?.solve() ?: error("day not found")
}