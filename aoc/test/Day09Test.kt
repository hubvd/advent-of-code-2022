import io.kotest.matchers.shouldBe

class Day09Test : SolverSpec(9, {
    example = """
    R 4
    U 4
    L 3
    D 1
    R 4
    D 1
    L 5
    R 2
    """.trimIndent()

    part1Example = 13
    part1 = 6018
    part2Example = 1
    part2 = 2619


    "follow" {
        Point(0, 0).follow(Point(-2, 3)) shouldBe Point(-1, 1)
    }

    "doesn't follow" {
        Point(0, 0).follow(Point(1, 0)) shouldBe Point(0, 0)
    }

    "follow diagonally" {
        Point(1, 1).follow(Point(2, 3)) shouldBe Point(2, 2)
    }

    "doesn't follow diagonally" {
        Point(1, 1).follow(Point(2, 2)) shouldBe Point(1, 1)
    }


})
