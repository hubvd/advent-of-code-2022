val day4solver = solver(4) {
    val sectionRe = """^(\d+)-(\d+),(\d+)-(\d+)$""".toRegex()
    lines().map { sectionRe.find(it)!!.destructured.toList().map { it.toInt() } }
        .map { (a, b, c, d) -> a..b to c..d }
        .run {
            count { (a, b) -> a.all { it in b } || b.all { it in a } }.yield()
            count { (a, b) -> a.any { it in b } || b.any { it in a } }.yield()
        }
}