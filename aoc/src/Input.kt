object Input {
    operator fun invoke(day: Int) = this.javaClass.getResourceAsStream("/Day${day.toString().padStart(2, '0')}.txt")?.bufferedReader()
        ?: error("Resource not found")
}
