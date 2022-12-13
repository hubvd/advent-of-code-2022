import java.util.*

private data class Cost(val value: Int, val point: Point)


val day12solver = solver(12) {

    val letters = ('a'..'z').toList()
    val grid = lines().map {
        it.map { letter ->
            when (letter) {
                'S' -> 0
                'E' -> 25
                else -> letters.indexOf(letter)
            }
        }
    }

    operator fun List<List<Int>>.get(point: Point) = this[point.y][point.x]

    lateinit var start: Point
    lateinit var end: Point

    val starts = mutableListOf<Point>()

    lines().forEachIndexed { y, line ->
        line.forEachIndexed { x, c ->
            when (c) {
                'S' -> start = Point(x, y)
                'E' -> end = Point(x, y)
                'a' -> starts += Point(x, y)
            }
        }
    }

    fun Point.neighbours() = sequenceOf(
        copy(x = x - 1),
        copy(x = x + 1),
        copy(y = y - 1),
        copy(y = y + 1),
    ).filter { it.x in grid.first().indices && it.y in grid.indices }

    fun step(start: Point, end: Point): Int {
        val queue = PriorityQueue(compareBy<Cost> { it.value }).apply { add(Cost(0, start)) }
        val visited = HashSet<Point>().apply { add(start) }
        while (queue.isNotEmpty()) {
            val (cost, point) = queue.poll()
            val value = grid[point]
            if (point == end) return cost
            point.neighbours()
                .filter { grid[it] <= value + 1 }
                .filter { visited.add(it) }
                .map { Cost(cost + 1, it) }
                .forEach { queue += it }
        }
        return Int.MAX_VALUE
    }

    step(start, end).yield()
    starts.minOfOrNull { step(it, end) }.yield()

}