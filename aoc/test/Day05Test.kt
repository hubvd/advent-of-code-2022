class Day05Test : SolverSpec(5, {
    example = """
        [D]
    [N] [C]
    [Z] [M] [P]
     1   2   3

    move 1 from 2 to 1
    move 3 from 1 to 3
    move 2 from 2 to 1
    move 1 from 1 to 2
    """.trimIndent()

    part1Example = "CMZ"
    part1 = "TDCHVHJTG"
    part2Example = "MCD"
    part2 = "NGCMPJLHV"
})