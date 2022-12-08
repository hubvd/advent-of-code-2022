private data class Point(val x: Int, val y: Int)

val day8solver = solver(8) {
    val grid = lines().map { it.map { it.digitToInt() } }
    val points = buildMap {
        for (y in grid.indices) {
            for (x in grid.first().indices) {
                put(Point(x, y), grid[y][x])
            }
        }
    }

    fun Point.top() = points.entries.filter { it.key.x == x && it.key.y < y }.sortedByDescending { it.key.y }
    fun Point.left() = points.entries.filter { it.key.x < x && it.key.y == y }.sortedByDescending { it.key.x }
    fun Point.right() = points.entries.filter { it.key.x > x && it.key.y == y }.sortedBy { it.key.x }
    fun Point.bottom() = points.entries.filter { it.key.x == x && it.key.y > y }.sortedBy { it.key.y }
    fun Point.adjacents() = sequence {
        yield(left())
        yield(right())
        yield(top())
        yield(bottom())
    }

    points.entries.count { (point, value) ->
        point.adjacents().any { it.all { it.value < value } }
    }.yield()

    points.maxOf { (point, value) ->
        point.adjacents().map {
            var count = 0
            for (point in it) {
                if (point.value < value) {
                    count++
                } else {
                    count++
                    break
                }
            }
            count
        }.reduce { acc, cur -> acc * cur }
    }.yield()

}
