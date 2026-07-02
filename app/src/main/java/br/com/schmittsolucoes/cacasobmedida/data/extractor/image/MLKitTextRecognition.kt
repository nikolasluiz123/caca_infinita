package br.com.schmittsolucoes.cacasobmedida.data.extractor.image

import android.content.Context
import android.graphics.Rect
import android.net.Uri
import android.util.Log
import br.com.schmittsolucoes.cacasobmedida.domain.model.BoundingBox
import br.com.schmittsolucoes.cacasobmedida.domain.model.result.text.TextBlock
import br.com.schmittsolucoes.cacasobmedida.domain.model.result.text.TextElement
import br.com.schmittsolucoes.cacasobmedida.domain.model.result.text.TextLine
import br.com.schmittsolucoes.cacasobmedida.domain.model.result.text.TextResult
import br.com.schmittsolucoes.cacasobmedida.domain.model.result.text.TextSymbol
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import java.io.File

import javax.inject.Inject

class MLKitTextRecognition @Inject constructor(
    @param:ApplicationContext private val context: Context
): ImageTextExtractor {

    private val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

    override suspend fun recognizeText(image: File): TextResult {
        val tag = this@MLKitTextRecognition::class.simpleName
        Log.d("DEBUG_PROCESS", "$tag: Iniciando extração de texto da imagem: ${image.name}")

        val imageUri = Uri.fromFile(image)
        val inputImage = InputImage.fromFilePath(context, imageUri)

        val mlKitText = recognizer.process(inputImage).await()

        return mlKitText.toDomain().also {
            Log.d("DEBUG_PROCESS", "$tag: Extração de texto da imagem concluída. Texto extraído: \n ${it.text}")
        }
    }
}

fun Rect?.toDomain(): BoundingBox? {
    this ?: return null

    return BoundingBox(
        left = left.toFloat(),
        top = top.toFloat(),
        right = right.toFloat(),
        bottom = bottom.toFloat()
    )
}

fun Text.toDomain(): TextResult {
    return TextResult(
        text = text,
        blocks = textBlocks.map { it.toDomain() }
    )
}

fun Text.TextBlock.toDomain(): TextBlock {
    return TextBlock(
        text = text,
        boundingBox = boundingBox.toDomain(),
        lines = lines.map { it.toDomain() }
    )
}

fun Text.Line.toDomain(): TextLine {
    return TextLine(
        text = text,
        boundingBox = boundingBox.toDomain(),
        confidence = confidence,
        elements = elements.map { it.toDomain() }
    )
}

fun Text.Element.toDomain(): TextElement {
    return TextElement(
        text = text,
        boundingBox = boundingBox.toDomain(),
        confidence = confidence,
        symbols = symbols.map { it.toDomain() }
    )
}

fun Text.Symbol.toDomain(): TextSymbol {
    return TextSymbol(
        text = text,
        boundingBox = boundingBox.toDomain(),
        confidence = confidence
    )
}