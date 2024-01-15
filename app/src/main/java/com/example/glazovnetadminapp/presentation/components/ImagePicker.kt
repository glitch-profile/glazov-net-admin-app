package com.example.glazovnetadminapp.presentation.components

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Size
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import java.io.File

const val CONTRACT = "image/*"

@Composable
fun ImagePicker(
    modifier: Modifier = Modifier,
    labelText: String,
    onNewImageSelected: (uri: Uri?) -> Unit,
    shape: Shape = MaterialTheme.shapes.medium,
    color: Color = Color.Transparent,
    contentColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    thumbnailSize: Size = Size(512, 512)
) {
    val context = LocalContext.current
    var bitmap by remember {
        mutableStateOf<Bitmap?>(null)
    }
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    var imageName by remember {
        mutableStateOf("")
    }

    fun getImageName(filePath: String): String {
        val fileNameTrimCount = if (filePath.contains('%')) filePath.reversed().indexOf("%")
        else filePath.reversed().indexOf("/")
        var fileName = filePath.takeLast(fileNameTrimCount)
        val fileExtension = File(fileName).extension
        if (fileExtension.isBlank()) fileName += ".jpg"
        return fileName
    }
    fun updateImageData(context: Context, uri: Uri?) {
        onNewImageSelected.invoke(uri)
        bitmap = if (uri != null) context.contentResolver.loadThumbnail(uri, thumbnailSize, null)
        else null
        imageUri = uri
        imageName = getImageName(uri.toString())

    }

    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) {
        updateImageData(context, it)
    }

    Surface(
        modifier = modifier,
        shape = shape,
        color = color,
        border = BorderStroke(1.dp, Color.LightGray),
        contentColor = contentColor
    ) {
        if (bitmap !== null) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.TopCenter
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { launcher.launch(CONTRACT) },
                    bitmap = bitmap!!.asImageBitmap(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    filterQuality = FilterQuality.Low
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.8f))
                        .padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier
                            .weight(1f),
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        text = imageName
                    )
                    TextButton(
                        onClick = { updateImageData(context, null) }
                    ) {
                        Text(text = "Clear")
                    }
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable { launcher.launch(CONTRACT) },
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        style = MaterialTheme.typography.labelMedium,
                        text = labelText
                    )
                }
            }
        }
    }


}