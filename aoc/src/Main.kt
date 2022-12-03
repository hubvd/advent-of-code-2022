import java.time.LocalDate

fun main() {
    val day = LocalDate.now().dayOfMonth
    Class.forName("Day${day.toString().padStart(2, '0')}Kt")
    Solvers[day]?.solve() ?: error("day not found")
}