val day1Solver = solver(1) {
    text().split("\n\n").map { it.lines().sumOf { it.toLong() } }.run {
        max().yield()
        sorted().takeLast(3).sum().yield()
    }
}
