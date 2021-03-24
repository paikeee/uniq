package paikeee

import java.io.File
import java.lang.Exception

fun combiner(flagI: Boolean, flagU: Boolean, flagC: Boolean,
             flagS: Boolean, input: List<String>, s: Int): List<String> = when {
    flagI && !flagS || flagI && flagS -> register(flagU, flagC, input, s)
    !flagI && flagS -> sub(flagU, flagC, input, s)
    else -> simple(flagU, flagC, input)
}


fun simple(flagU: Boolean, flagC: Boolean, input: List<String>): List<String> {

    val output = mutableListOf<String>()
    var last = input.first()
    var c = 1

    for (line in input.subList(1, input.lastIndex + 1)) {
        if (line != last) {

            if (flagC) {
                last = "$c" + last
                c = 1
            }
            if (flagU && last !in output) output.add(last)
            if (!flagU) output.add(last)

            last = line
        } else if (flagC) c++
    }
        if (flagC) last = "$c" + last
        if (flagU && last !in output) output.add(last)
        if (!flagU) output.add(last)
    return output
}

fun register(flagU: Boolean, flagC: Boolean, input: List<String>, s: Int): List<String> {

    val output = mutableListOf<String>()
    var last = input.first()
    var c = 1

    for (line in input.subList(1, input.lastIndex + 1)) {
        if (line.substring(s).toLowerCase() != last.substring(s).toLowerCase()) {

            if (flagC) {
                last = "$c" + last
                c = 1
            }
            if (flagU && last !in output) output.add(last)
            if (!flagU) output.add(last)

            last = line
        } else if (flagC) c++
    }
    if (flagC) last = "$c" + last
    if (flagU && last !in output) output.add(last)
    if (!flagU) output.add(last)
    return output
}

fun sub(flagU: Boolean, flagC: Boolean, input: List<String>, s: Int): List<String> {

    val output = mutableListOf<String>()
    var last = input.first()
    var c = 1

    for (line in input.subList(1, input.lastIndex + 1)) {
        if (line.substring(s) != last.substring(s)) {

            if (flagC) {
                last = "$c" + last
                c = 1
            }
            if (flagU && last !in output) output.add(last)
            if (!flagU) output.add(last)

            last = line
        } else if (flagC) c++
    }
    if (flagC) last = "$c" + last
    if (flagU && last !in output) output.add(last)
    if (!flagU) output.add(last)
    return output
}

fun main(args: String): List<String> {

    val input = args.split(" ")
    var flagO = false
    var outputName = String()
    var flagI = false
    var flagS = false
    var s = 0
    var flagU = false
    var flagC = false
    var inputName = String()

    var i = 1
    while (i < input.size && i <= 8) {
        when (input[i]) {
            "-c" -> flagC = true
            "-i" -> flagI = true
            "-u" -> flagU = true
            "-s" -> {
                flagS = true
                s = input[i + 1].toInt()
                i++
            }
            "-o" -> {
                flagO = true
                outputName = input[i + 1]
                i++
            }
            else -> if (File(input[i]).isFile) {
                inputName = input[i]
                break
            } else throw Exception("Invalid argument")
        }
        i++
    }

    if (inputName.isEmpty()) inputName = readLine() ?: throw Exception("Error")

    val strings =
            if (File(inputName).isFile) File(inputName).readLines() else inputName.split("/n")
    val output = combiner(flagI, flagU, flagC, flagS, strings, s)
    if (flagO)
        File(outputName).bufferedWriter().use { out ->
            output.forEach {
                out.write(it)
                out.newLine()
            }
        }
    else for (string in output) println(string)
    return output
}
