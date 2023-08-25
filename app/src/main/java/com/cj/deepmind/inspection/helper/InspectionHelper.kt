package com.cj.deepmind.inspection.helper

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageFormat
import android.graphics.Matrix
import android.graphics.Rect
import android.graphics.YuvImage
import android.media.Image
import android.util.Log
import com.cj.deepmind.inspection.models.AnalysisResult
import com.cj.deepmind.inspection.models.InspectionTypeModel
import com.cj.deepmind.inspection.models.Result
import com.cj.deepmind.inspection.ui.getACode
import org.pytorch.IValue
import org.pytorch.Module
import org.pytorch.Tensor
import org.pytorch.torchvision.TensorImageUtils
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.nio.ByteBuffer


class InspectionHelper {
    private lateinit var mModule: Module
    private fun loadModel(type: InspectionTypeModel, context: Context) : String?{
        val fileName = when(type){
            InspectionTypeModel.HOUSE -> {
                "CL01_NMS.pth"
            }

            InspectionTypeModel.TREE -> {
                "CL02_NMS.pth"
            }

            InspectionTypeModel.PERSON_1, InspectionTypeModel.PERSON_2 -> {
                "CL03_NMS.pth"
            }
        }

        val file = File(context.filesDir, fileName)

        try{
            context.assets.open(fileName).use { `is` ->
                FileOutputStream(file).use { os ->
                    val buffer = ByteArray(4 * 1024)
                    var read: Int
                    while (`is`.read(buffer).also { read = it } != -1) {
                        os.write(buffer, 0, read)
                    }
                    os.flush()
                }
                return file.absolutePath
            }
        } catch(e: IOException){
            e.printStackTrace()
            return null
        }
    }

    fun predict(type: InspectionTypeModel, context: Context): AnalysisResult?{
        try{
            val path = loadModel(type, context)

            if(path == null){
                Log.d("InspectionHelper", "Module file not found.")
                return null
            }

            mModule = Module.load(loadModel(type, context))

            val contextWrapper = ContextWrapper(context)
            val directory: File = contextWrapper.getDir("HTP", Context.MODE_PRIVATE)

            var bitmap: Bitmap

            when(type){
                InspectionTypeModel.HOUSE -> {
                    val path = File(directory, "HOUSE.png")
                    if(path.exists()){
                        bitmap = BitmapFactory.decodeFile(path.path)
                        PrePostProcessor.nClasses = 15
                    } else{
                        Log.d("InspectionHelper", "File not found : HOUSE.png")
                        return null
                    }
                }

                InspectionTypeModel.TREE -> {
                    val path = File(directory, "TREE.png")
                    if(path.exists()){
                        bitmap = BitmapFactory.decodeFile(path.path)
                        PrePostProcessor.nClasses = 14
                    } else{
                        Log.d("InspectionHelper", "File not found : TREE.png")
                        return null

                    }
                }

                InspectionTypeModel.PERSON_1 -> {
                    val path = File(directory, "PERSON_1.png")
                    if(path.exists()){
                        bitmap = BitmapFactory.decodeFile(path.path)
                        PrePostProcessor.nClasses = 20
                    } else{
                        Log.d("InspectionHelper", "File not found : PERSON_1.png")
                        return null
                    }
                }

                InspectionTypeModel.PERSON_2 -> {
                    val path = File(directory, "PERSON_2.png")
                    if(path.exists()){
                        bitmap = BitmapFactory.decodeFile(path.path)
                        PrePostProcessor.nClasses = 20
                    } else{
                        Log.d("InspectionHelper", "File not found : PERSON_2.png")
                        return null
                    }
                }
            }

            bitmap = Bitmap.createScaledBitmap(bitmap, 1280, 1280, true)

            try{
                val inputTensor = TensorImageUtils.bitmapToFloat32Tensor(bitmap, PrePostProcessor.NO_MEAN_RGB, PrePostProcessor.NO_STD_RGB)
                val (x, boxes, scores) = mModule.forward(IValue.from(inputTensor)).toTuple()

                val results = PrePostProcessor.nms(x.toTensor(), 0.45f)

                return AnalysisResult(results)
            } catch(ex: Exception){
                ex.printStackTrace()
                return null
            } catch(eI: IllegalStateException){
                eI.printStackTrace()
                return null
            }
        } catch(e: Exception){
            e.printStackTrace()
        }

        return null
    }
}