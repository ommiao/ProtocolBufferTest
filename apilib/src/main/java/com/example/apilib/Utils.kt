package com.example.apilib

import java.io.File
import java.io.FileWriter

fun printToFile(fileName: String, content: ByteArray) {

    val fileWriter = FileWriter(File(fileName))
    fileWriter.write(String(content))
    fileWriter.flush()
    fileWriter.close()

}