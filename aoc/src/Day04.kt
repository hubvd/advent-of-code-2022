val day4solver = solver(4) {
    Regex("\\d+").findAll(text()).map { it.value.toInt() }
        .chunked(2).map { it[0]..it[1] }
        .chunked(2).run {
            count { (a, b) -> a.all { it in b } || b.all { it in a } }.yield()
            count { (a, b) -> a.any { it in b } || b.any { it in a } }.yield()
        }
}