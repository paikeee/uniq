package paikeee


import java.io.File
import java.lang.Exception


fun combiner(flagI: Boolean, flagU: Boolean, flagS: Boolean,
             input: List<String>, s: Int): List<Pair<Int, String>> {

    val output = mutableListOf<Pair<Int, String>>()
    var last = input.first()
    var num = 1
    var last1 = if (flagI) last.toLowerCase() else last
    if (flagS) last1 = last1.substring(s)

    for (line in input.subList(1, input.lastIndex + 1)) {

        var line1 = if (flagI) line.toLowerCase() else line
        if (flagS) line1 = line1.substring(s)

        if (last1 != line1) {
            when {
                flagU && num == 1 -> output.add(Pair(num, last))
                !flagU -> output.add(Pair(num, last))
            }
            last = line
            num = 1
        } else {
            num++
        }
        last1 = line1
    }
    when {
        flagU && num == 1 -> output.add(Pair(num, last))
        !flagU -> output.add(Pair(num, last))
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
                if (i + 1 < args.size) s = args[i + 1].toInt()
                else throw Exception("No number for flag -s")
                i++
            }
            "-o" -> {
                flagO = true
                if (i + 1 < args.size) outputName = args[i + 1]
                else throw Exception("File name expected")
                i++
            }
            else -> if (File(args[i]).isFile) {
                input = File(args[i]).readLines()
                if (i != args.size - 1) throw Exception("Arguments are unacceptable after file name")
            } else throw Exception("Wrong file name")
        }
        i++
    }
    if (input.isEmpty())
        input = generateSequence { readLine() }.toList()

    val result = combiner(flagI, flagU, flagS, input, s)
    val output = if (flagC) result.map { it.first.toString() + it.second }
    else result.map { it.second }

    if (flagO)
        File(outputName).bufferedWriter().use { out ->
            output.forEach {
                out.write(it)
                out.newLine()
            }
        }
    else for (string in output) println(string)
}
