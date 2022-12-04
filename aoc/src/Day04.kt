val day4solver = solver(4) {
    val sectionRe = """^(\d+)-(\d+),(\d+)-(\d+)$""".toRegex()
    lineSequence().map { sectionRe.find(it)!!.destructured.toList().map { it.toInt() } }
        .map { (a, b, c, d) -> a..b to c..d }
        .toList()
        .run {
            count { (a, b: IntRange) -> (a.first in b && a.last in b) || (b.first in a && b.last in a) }.yield()
            count { (a, b: IntRange) -> a.any { it in b } || b.any { it in a } }.yield()
        }
}