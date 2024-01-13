package com.lurenjia534.imagepickerexample

import android.graphics.drawable.Icon
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lurenjia534.imagepickerexample.ui.theme.ImagePickerExampleTheme
import java.time.format.TextStyle

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ImagePickerExampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.primary
                ) {
                    ImagePickerExample()
                }
            }
        }
    }
}

@Composable
fun ImagePickerExample() {
    val pickMedia =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                Log.d("PhotoPicker", "Selected URI: $uri")
            } else {
                Log.d("PhotoPicker", "No media selected")
            }
        }

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {
        // 按钮：选择图片和视频
        Button(
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surface),
            onClick = {
                pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))
            }) {
            Text("Pick Image or Video", color = MaterialTheme.colorScheme.onPrimaryContainer)
        }

        // 按钮：仅选择图片
        Button(
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surface),
            onClick = {
                pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }) {
            Text("Pick Image Only", color = MaterialTheme.colorScheme.onPrimaryContainer)
        }

        // 按钮：仅选择视频
        Button(
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surface),
            onClick = {
                pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.VideoOnly))
            }) {
            Text("Pick Video Only", color = MaterialTheme.colorScheme.onPrimaryContainer)
        }

        // 按钮：选择特定MIME类型（如GIF）
        val mimeType = "image/gif"
        Button(
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surface),
            onClick = {
                pickMedia.launch(
                    PickVisualMediaRequest(
                        ActivityResultContracts.PickVisualMedia.SingleMimeType(
                            mimeType
                        )
                    )
                )
            }) {
            Text("Pick GIF", color = MaterialTheme.colorScheme.onPrimaryContainer)
        }
        Button(
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surface),
            onClick = { pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) },
        ) {
            Icon(imageVector = Icons.Default.Share, contentDescription = null, tint = Color.Black)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Share image",
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                fontFamily = FontFamily.Serif
            )
        }
    }
}