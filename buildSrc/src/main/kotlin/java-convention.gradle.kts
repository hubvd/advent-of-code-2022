plugins {
    java
}

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(19))
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

sourceSets["main"].resources.setSrcDirs(listOf("src"))
sourceSets["main"].java.setSrcDirs(emptyList<String>())
sourceSets["test"].resources.setSrcDirs(listOf("test"))
sourceSets["test"].java.setSrcDirs(emptyList<String>())
