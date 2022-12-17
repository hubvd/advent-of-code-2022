import kotlin.math.abs
import kotlin.math.min

data class Rect(val left: Int, val right: Int, val bottom: Int, val top: Int, val center: Point) {

    inline fun range(y: Int): IntRange? {
        if (y !in top..bottom) return null
        val midX = center.x
        val distYEdge = min(y - top, bottom - y)
        return (midX - distYEdge)..(midX + distYEdge)
    }

    inline operator fun contains(point: Point) = range(point.y)?.let { point.x in it } ?: false

    fun outside(): Sequence<Point> = sequence {
        for (y in top - 1..bottom + 1) {
            val r = range(y)
            if (r == null) {
                yield(Point(center.x, y))
            } else {
                yield(Point(r.first - 1, y))
                yield(Point(r.last + 1, y))
            }
        }
    }
}

val day15solver = solver(15) {
    val re = Regex("""Sensor at x=([-+]?\d+), y=([-+]?\d+): closest beacon is at x=([-+]?\d+), y=([-+]?\d+)""")
    val data = lineSequence().map {
        re.find(it)!!.groupValues.drop(1).map { it.toInt() }.windowed(2, 2).map { Point(it[0], it[1]) }
    }.toList()

    val beacons = data.map { it[1] }.toHashSet()

    val rects = data.map { (sensor, beacon) ->
        val dist = abs(beacon.x - sensor.x) + abs(beacon.y - sensor.y)
        Rect(
            left = sensor.x - dist,
            right = sensor.x + dist,
            bottom = sensor.y + dist,
            top = sensor.y - dist,
            center = sensor
        )
    }

    val left = rects.minOf { it.left }
    val right = rects.maxOf { it.right }

    val bounds = 0..4000000
    fun Point.tuningFrequency() = x * 4000000L + y

    var count = 0L
    (left..right).map { Point(it, 2000000) }.forEach {
        if (it !in beacons && rects.any { rect -> it in rect }) count++
    }
    count.yield()

    for (rect in rects) {
        val res = rect.outside().filter { it.x in bounds && it.y in bounds }
            .find { point -> point !in beacons && rects.none { rect -> point in rect } }

        if (res != null) {
            println("Found $res")
            println(res.tuningFrequency().also { it.yield() })
            break
        }
    }

}


