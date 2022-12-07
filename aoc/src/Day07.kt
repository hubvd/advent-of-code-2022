private sealed class File {
    class Dir(val name: String, val parent: Dir? = null, val files: MutableList<File> = mutableListOf()) : File() {
        override val size: Long get() = files.sumOf { it.size }
    }

    class Data(override val size: Long) : File()

    abstract val size: Long
}


val day7solver = solver(7) {
    lateinit var currentDir: File.Dir

    lineSequence().forEach { line ->
        when {
            line.startsWith("$ cd") -> currentDir = when (val path = line.substringAfter("$ cd ")) {
                ".." -> currentDir.parent!!
                "/" -> File.Dir("/")
                else -> currentDir.files.filterIsInstance<File.Dir>().find { it.name == path }!!
            }

            line == "$ ls" -> {}

            line.startsWith("dir ") ->
                currentDir.files += File.Dir(line.removePrefix("dir "), parent = currentDir)

            else -> currentDir.files += File.Data(line.substringBefore(' ').toLong())
        }
    }

    while (currentDir.parent != null) currentDir = currentDir.parent!!
    fun dirs() = generateSequence(listOf(currentDir)) {
        it.flatMap { it.files }.filterIsInstance<File.Dir>().takeIf { it.isNotEmpty() }
    }.flatten()

    dirs().filter { it.size <= 100000 }.sumOf { it.size }.yield()
    val unusedSpace = 70000000 - currentDir.size
    dirs().sortedBy { it.size }.first { unusedSpace + it.size > 30000000 }.size.yield()
}
