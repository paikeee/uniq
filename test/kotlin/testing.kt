package paikeee

import org.junit.Test
import org.junit.Assert.*
import java.lang.Exception
import java.lang.NumberFormatException
import java.io.File

class Testing {

    private fun assertFileContent(name: String, expectedContent: String) {
        val file = File(name)
        val content = file.readLines().joinToString("\n")
        assertEquals(expectedContent, content)
    }

    @Test
    fun main() {

        main(arrayOf("-c", "-o", "forTests/result.txt", "forTests/1.txt"))
        assertFileContent("forTests/result.txt",
                "2zzzz\n" +
                "1wwww\n" +
                "3eeee")
        main(arrayOf("-s", "3", "-o", "forTests/result.txt", "forTests/2.txt"))
        assertFileContent("forTests/result.txt","zzzz\n" +
                "ZZZZ\n" +
                "baaaa\n" +
                "ZzZz")
        main(arrayOf("-i", "-u", "-o", "forTests/result.txt", "forTests/2.txt"))
        assertFileContent("forTests/result.txt",
                "ZzZz")
    }
}