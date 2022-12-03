plugins {
    id("kotlin-convention")
    id("aoc")
    application
}

application {
    mainClass.set("MainKt")
}

dependencies {
    testImplementation("io.kotest:kotest-runner-junit5:5.5.4")
}
