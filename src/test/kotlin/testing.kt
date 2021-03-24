package paikeee

import org.junit.Test
import kotlin.test.assertEquals


class Testing {
    @Test
    fun mainTest() {
        assertEquals(listOf("2zzzz", "1SFfdfs", "3eeee"), main("uniq -c -u forTests/1.txt"))
        assertEquals(listOf("zzzz", "baaaa", "ZzZz"), main("uniq -u -i forTests/2.txt"))
        assertEquals(listOf("zzzz",  "ZZZZ", "baaaa", "ZzZz"), main("uniq -u -s 3 forTests/2.txt"))
        assertEquals(listOf("zzzz", "SFfdfs", "eeee"), main("uniq -u forTests/1.txt"))
        assertEquals(listOf("2zzzz", "1SFfdfs", "3eeee"), main("uniq -c -u -o forTests/result.txt forTests/1.txt"))
        assertEquals(listOf("2zzzz", "1SFfdfs", "3eeee"), main("uniq -c -u"))
    }
}