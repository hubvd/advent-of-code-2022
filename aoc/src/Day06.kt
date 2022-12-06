val day6solver = solver(6) {
    listOf(4, 14).forEach { size ->
        val match = text().windowed(size).find { it.toSet().size == size }!!
        (text().indexOf(match) + size).yield()
    }
}