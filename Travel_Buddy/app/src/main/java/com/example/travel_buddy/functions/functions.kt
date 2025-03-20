package com.example.travel_buddy.functions


import android.util.Log


import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import com.example.travel_buddy.classes_res.Travel_point
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


fun exportToPdf(context: Context, fileName: String, travelPoints: Array<Travel_point>): File? {
    val pdfDocument = PdfDocument()
    val paint = Paint()
    val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create()
    val page = pdfDocument.startPage(pageInfo)
    val canvas = page.canvas

    var yPosition = 100f
    val maxWidth = 480f
    val lineHeight = 20f

    val titlePaint = Paint().apply {
        textSize = 18f
        textAlign = Paint.Align.CENTER
    }

    canvas.drawText("List of travel points", pageInfo.pageWidth / 2f, 50F, titlePaint)

    paint.textSize = 15F
    paint.color = Color.BLACK
    paint.textAlign = Paint.Align.LEFT

    travelPoints.forEach { paragraph ->
        val point = paragraph.name + " " + paragraph.date.toString()
        val words = point.split(" ")
        var line = ""

        for (word in words) {
            val testLine = if (line.isEmpty()) word else "$line $word"
            val textWidth = paint.measureText(testLine)

            if (textWidth > maxWidth) {
                canvas.drawText(line, 50f, yPosition, paint)
                yPosition += lineHeight
                line = word
            } else {
                line = testLine
            }
        }
        canvas.drawCircle(50f,yPosition-5,3F,paint)
        canvas.drawText(line, 70f, yPosition, paint)
        yPosition += lineHeight * 2  // Space between paragraphs
    }

    pdfDocument.finishPage(page)

    val pdfFile = File(context.getExternalFilesDir(null), "$fileName.pdf")
    try {
        pdfDocument.writeTo(FileOutputStream(pdfFile))
        Log.d("SUCCESS","Succesfully created pdf file in $pdfFile.path")
    } catch (e: IOException) {
        e.printStackTrace()
        Log.d("ERROR","error occured")
        return null
    } finally {
        pdfDocument.close()
    }

    return pdfFile
}
