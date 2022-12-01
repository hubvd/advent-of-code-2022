fun main() = Input(1).readText().split("\n\n").map { it.lines().sumOf { it.toLong() } }.run {
    println(max())
    println(sorted().takeLast(3).sum())
}
