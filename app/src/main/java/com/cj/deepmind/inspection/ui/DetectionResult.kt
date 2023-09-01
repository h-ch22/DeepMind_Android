package com.cj.deepmind.inspection.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.cj.deepmind.inspection.models.AnalysisResult

class DetectionResult: View {
    companion object{
        private val TEXT_X = 40
        private val TEXT_Y = 35
        private val TEXT_WIDTH = 260
        private val TEXT_HEIGHT = 50
    }

    private lateinit var mPaintRectangle: Paint
    private lateinit var mPaintText: Paint
    var results: AnalysisResult? = null

    constructor(context: Context) : super(context){
        setWillNotDraw(false)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs){
        mPaintRectangle = Paint()
        mPaintRectangle.setColor(Color.YELLOW)
        mPaintText = Paint()
        setWillNotDraw(false)
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        Log.d("DetectionResult", "onDraw() function called")
        if(results == null){
            Log.d("DetectionResult", "Given results object is null.")
            return
        }

        for(result in results!!.mResults){
            mPaintRectangle.strokeWidth = 5F
            mPaintRectangle.style = Paint.Style.STROKE
            canvas.drawRect(result.boundingBox, mPaintRectangle)

            val mPath = Path()
            val rectF = RectF(result.boundingBox.left, result.boundingBox.top, result.boundingBox.left + TEXT_WIDTH, result.boundingBox.top + TEXT_HEIGHT)
            mPath.addRect(rectF, Path.Direction.CW)
            mPaintText.color = Color.MAGENTA
            canvas.drawPath(mPath, mPaintText)

            mPaintText.color = Color.WHITE
            mPaintText.strokeWidth = 0F
            mPaintText.style = Paint.Style.FILL
            mPaintText.textSize = 32F

            canvas.drawText(String.format("%s %.2f", result.classId, result.score), result.boundingBox.left + TEXT_X, result.boundingBox.top + TEXT_Y, mPaintText)
        }
    }

    fun setResult(results: AnalysisResult?){
        this.results = results
    }
}