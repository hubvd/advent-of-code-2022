import kotlin.math.max

val day14solver = solver(14) {
    var bottom = 0
    val ranges: List<Pair<IntRange, IntRange>> = lineSequence().flatMap {
        it.split(" -> ").map { it.split(',') }.zipWithNext()
    }.map { (start, end) ->
        val xx = listOf(start[0].toInt(), end[0].toInt()).sorted()
        val yy = listOf(start[1].toInt(), end[1].toInt()).sorted()
        bottom = max(yy[1], bottom)
        (xx[0]..xx[1]) to (yy[0]..yy[1])
    }.toList()

    operator fun Pair<IntRange, IntRange>.contains(point: Point): Boolean {
        val (x, y) = this
        return point.x in x && point.y in y
    }

    val source = Point(500, 0)

    fun Point.next() = sequence {
        yield(copy(y = y + 1))
        yield(copy(y = y + 1, x = x - 1))
        yield(copy(y = y + 1, x = x + 1))
    }

    fun part1() {
        val sands = HashSet<Point>()
        var sand = source.copy()
        while (true) {
            val nextSand = sand.next().find { next -> next !in sands && ranges.all { next !in it } }
            if (nextSand != null) {
                sand = nextSand
                if (nextSand.y > bottom) break
            } else {
                sands += sand
                sand = source.copy()
            }
        }
        sands.size.yield()
    }
    part1()

    fun part2() {
        val floor = bottom + 2
        val sands = HashSet<Point>()
        var sand = source.copy()
        var moved = false
        while (true) {
            val nextSand = sand.next().find { next ->
                next.y != floor && next !in sands && ranges.all { next !in it }
            }
            if (nextSand != null) {
                moved = true
                sand = nextSand
            } else {
                sands += sand
                sand = source.copy()
                if (!moved) break
                moved = false

            }
        }
        sands.size.yield()
    }
    part2()

}
