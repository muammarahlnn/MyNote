package com.ardnn.mynote

import android.content.Context

internal object FileHelper {
    fun writeToFile(context: Context, file: FileModel) {
        context.openFileOutput(file.filename, Context.MODE_PRIVATE).use {
            it.write(file.data?.toByteArray())
        }
    }

    fun readFromFile(context: Context, filename: String): FileModel {
        val file = FileModel()
        file.filename = filename
        file.data = context.openFileInput(filename).bufferedReader().useLines { lines ->
            lines.fold("") { some, text ->
                "$some\n$text"
            }
        }
        return file
    }
}