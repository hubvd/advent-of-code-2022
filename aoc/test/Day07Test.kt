class Day07Test : SolverSpec(7, {
    example = """
    $ cd /
    $ ls
    dir a
    14848514 b.txt
    8504156 c.dat
    dir d
    $ cd a
    $ ls
    dir e
    29116 f
    2557 g
    62596 h.lst
    $ cd e
    $ ls
    584 i
    $ cd ..
    $ cd ..
    $ cd d
    $ ls
    4060174 j
    8033020 d.log
    5626152 d.ext
    7214296 k
    """.trimIndent()

    part1Example = 95437L
    part1 = 1743217
    part2Example = 24933642L
    part2 = 8319096
})