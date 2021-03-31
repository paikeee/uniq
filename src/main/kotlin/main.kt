package paikeee


import java.io.File
import java.lang.Exception
import java.lang.IndexOutOfBoundsException
import java.lang.NumberFormatException


fun combiner(flagI: Boolean, flagU: Boolean, flagS: Boolean,
             input: List<String>, s: Int): List<Pair<Int, String>> {

    val output = mutableListOf<Pair<Int, String>>()
    var last = Pair(1, input.first())
    var num = 1

    for (line in input.subList(1, input.lastIndex + 1)) {

        var last1 = if (flagI) last.second.toLowerCase() else last.second
        var line1 = if (flagI) line.toLowerCase() else line
        if (flagS) {
            last1 = last1.substring(s)
            line1 = line1.substring(s)
        }
        if (last1 != line1) {
            when {
                flagU && last.first == 1 -> output.add(last)
                !flagU -> output.add(last)
            }
            last = Pair(1, line)
            num = 1
        } else {
            num++
            last = Pair(num, last.second)
        }
    }
    when {
        flagU && last.first == 1 -> output.add(last)
        !flagU -> output.add(last)
    }
    return output
}


fun main(args: Array<String>) {

    var flagO = false
    var outputName = ""
    var flagI = false
    var flagS = false
    var s = 0
    var flagU = false
    var flagC = false
    var input = listOf<String>()

    var i = 0
    while (i < args.size) {
        when (args[i]) {
            "-c" -> flagC = true
            "-i" -> flagI = true
            "-u" -> flagU = true
            "-s" -> {
                flagS = true
                try {
                    if (Regex("""\d*""").matches(args[i + 1])) s = args[i + 1].toInt()
                    else throw NumberFormatException()
                } catch (e: IndexOutOfBoundsException)
                {
                    throw Exception ("No number for flag -s")
                }
                i++
            }
            "-o" -> {
                flagO = true
                try {
                    outputName = args[i + 1]
                } catch (e: IndexOutOfBoundsException)
                {
                    throw Exception ("File name expected")
                }
                i++
            }
            else -> if (File(args[i]).isFile) {
                input = File(args[i]).readLines()
                break
            } else throw Exception("Wrong file name")
        }
        i++
    }
    if (input.isEmpty())
        input = (generateSequence { readLine() }.takeWhile{ it != "" }).toList()

    val output = if (flagC) combiner(flagI, flagU, flagS, input, s).map { it.first.toString() + it.second }
    else combiner(flagI, flagU, flagS, input, s).map { it.second }

    if (flagO)
        File(outputName).bufferedWriter().use { out ->
            output.forEach {
                out.write(it)
                out.newLine()
            }
        }
    else for (string in output) println(string)
}
