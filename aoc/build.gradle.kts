plugins {
    id("kotlin-convention")
    application
}

application {
    mainClass.set("Day01Kt")
}

dependencies {
    testImplementation("io.kotest:kotest-runner-junit5:5.5.4")
}
