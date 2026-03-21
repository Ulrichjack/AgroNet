package cm.agronet.app

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

class ResultActivity : AppCompatActivity() {

    private lateinit var interpreter: Interpreter
    private lateinit var resultTextView: TextView
    private lateinit var imageView: ImageView

    companion object {
        const val EXTRA_IMAGE_URI = "image_uri"
        private const val MODEL_FILE = "agronet_model.tflite"
        private const val BYTES_PER_FLOAT = 4
        private const val NUM_CHANNELS = 3
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        resultTextView = findViewById(R.id.resultTextView)
        imageView = findViewById(R.id.imageView)

        val backButton: Button = findViewById(R.id.backButton)
        backButton.setOnClickListener { finish() }

        loadTFLiteModel()

        val imageUriString = intent.getStringExtra(EXTRA_IMAGE_URI)
        if (!::interpreter.isInitialized) {
            return
        }
        if (imageUriString != null) {
            val bitmap = getBitmapFromUri(Uri.parse(imageUriString))
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap)
                runInference(bitmap)
            } else {
                resultTextView.text = getString(R.string.error_loading_image)
            }
        } else {
            resultTextView.text = getString(R.string.no_image_selected)
        }
    }

    private fun loadTFLiteModel() {
        try {
            val modelBuffer = loadModelFile()
            val options = Interpreter.Options()
            interpreter = Interpreter(modelBuffer, options)
        } catch (e: IOException) {
            resultTextView.text = getString(R.string.error_loading_model)
        } catch (e: IllegalArgumentException) {
            resultTextView.text = getString(R.string.error_loading_model)
        }
    }

    private fun loadModelFile(): MappedByteBuffer {
        val fileDescriptor = assets.openFd(MODEL_FILE)
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }

    private fun getBitmapFromUri(uri: Uri): Bitmap? {
        return try {
            contentResolver.openInputStream(uri)?.use { inputStream ->
                BitmapFactory.decodeStream(inputStream)
            }
        } catch (e: Exception) {
            null
        }
    }

    private fun runInference(bitmap: Bitmap) {
        val inputShape = interpreter.getInputTensor(0).shape()
        val inputHeight = inputShape[1]
        val inputWidth = inputShape[2]

        val scaledBitmap = Bitmap.createScaledBitmap(bitmap, inputWidth, inputHeight, true)
        val inputBuffer = convertBitmapToByteBuffer(scaledBitmap, inputWidth, inputHeight)

        val outputShape = interpreter.getOutputTensor(0).shape()
        val outputBuffer = Array(1) { FloatArray(outputShape[1]) }

        interpreter.run(inputBuffer, outputBuffer)

        val result = outputBuffer[0]
        val maxIndex = result.indices.maxByOrNull { result[it] } ?: 0
        val confidence = result[maxIndex] * 100f

        resultTextView.text = getString(R.string.result_format, maxIndex, confidence)
    }

    private fun convertBitmapToByteBuffer(bitmap: Bitmap, width: Int, height: Int): ByteBuffer {
        val byteBuffer = ByteBuffer.allocateDirect(width * height * NUM_CHANNELS * BYTES_PER_FLOAT)
        byteBuffer.order(ByteOrder.nativeOrder())

        val intValues = IntArray(width * height)
        bitmap.getPixels(intValues, 0, width, 0, 0, width, height)

        for (pixelValue in intValues) {
            byteBuffer.putFloat((pixelValue shr 16 and 0xFF) / 255.0f)
            byteBuffer.putFloat((pixelValue shr 8 and 0xFF) / 255.0f)
            byteBuffer.putFloat((pixelValue and 0xFF) / 255.0f)
        }

        return byteBuffer
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::interpreter.isInitialized) {
            interpreter.close()
        }
    }
}
