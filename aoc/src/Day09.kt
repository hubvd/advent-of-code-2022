import kotlin.math.abs
import kotlin.math.sign

val day9solver = solver(9) {
    val moves = lineSequence().map { it.split(' ').let { it[0].first() to it[1].toInt() } }
        .flatMap { (dir, count) -> List(count) { dir } }

    run {
        var tail = Point(0, 0)
        var head = tail.copy()
        val tailPath = hashSetOf(tail)

        moves.forEach { dir ->
            head = head.move(dir)
            tail = tail.follow(head)
            tailPath += tail
        }

        tailPath.size.yield()
    }

    run {
        var head = Point(0, 0)
        val knots = List(9) { Point(0, 0) }.toMutableList()
        val tailPath = hashSetOf(knots.last())

        moves.forEach { dir ->
            head = head.move(dir)

            knots.forEachIndexed { index, point ->
                val previous = if (index == 0) head else knots[index - 1]
                knots[index] = point.follow(previous)
            }

            tailPath += knots.last()
        }

        tailPath.size.yield()
    }


}

fun Point.move(direction: Char) = when (direction) {
    'R' -> copy(x = x + 1)
    'L' -> copy(x = x - 1)
    'D' -> copy(y = y + 1)
    'U' -> copy(y = y - 1)
    else -> error("!")
}

fun Point.follow(target: Point): Point {
    val diffY = target.y - y
    val signY = sign(diffY.toDouble()).toInt()

    val diffX = target.x - x
    val signX = sign(diffX.toDouble()).toInt()

    val away = abs(diffX) > 1 || abs(diffY) > 1
    val newX = if (away) x + signX else x
    val newY = if (away) y + signY else y
    return Point(newX, newY)
}
