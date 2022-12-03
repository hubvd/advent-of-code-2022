import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.nio.file.Path
import java.nio.file.Files
import java.time.LocalDate

class AocPlugin : Plugin<Project> {

    fun generateTest(day: Int): String {
        val paddedDay = day.toString().padStart(2, '0')
        return """
        class Day${paddedDay}Test : SolverSpec($day, {
            example = ""${"\""}
            ""${"\""}.trimIndent()

            part1Example = null
            part1 = null
            part2Example = null
            part2 = null
        })
        """.trimIndent()
    }

    fun generateSolver(day: Int) = """
        val day${day}solver = solver($day) {

        }
        """.trimIndent()

    fun downloadInput(day: Int, session: String): String {
        val request = HttpRequest.newBuilder()
            .uri(URI.create("https://adventofcode.com/2022/day/$day/input"))
            .header("Cookie", "session=$session")
            .header("User-Agent", "github.com/hubvd/advent-of-code-2022")
            .GET()
            .build()

        val response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString())
        if (response.statusCode() in 200 until 300) {
            return response.body()
        } else {
            error(response.toString())
        }
    }

    override fun apply(target: Project) {
        val sourceSets = target.extensions.getByType<KotlinProjectExtension>().sourceSets
        val testPath = sourceSets.getByName("test").kotlin.sourceDirectories.asPath
        val mainPath = sourceSets.getByName("main").kotlin.sourceDirectories.asPath
        val resourcePath = mainPath // FIXME

        target.tasks.register("prepare") {
            doLast {
                val day = LocalDate.now().dayOfMonth
                val paddedDay = day.toString().padStart(2, '0')
                val solverFile = Path.of(mainPath, "Day${paddedDay}.kt")
                if (!Files.exists(solverFile)) {
                    Files.writeString(solverFile, generateSolver(day))
                }
                val testFile = Path.of(testPath, "Day${paddedDay}Test.kt")
                if (!Files.exists(testFile)) {
                    Files.writeString(testFile, generateTest(day))
                }
                val inputFile = Path.of(resourcePath, "Day${paddedDay}.txt")
                if (!Files.exists(inputFile)) {
                    val session = Files.readString(Path.of(target.rootDir.path, ".session")).trim()
                    Files.writeString(inputFile, downloadInput(day, session))
                }
            }
        }

    }
}