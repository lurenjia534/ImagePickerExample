package com.lurenjia534.imagepickerexample

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.lurenjia534.imagepickerexample.ui.theme.ImagePickerExampleTheme

class MainActivity : ComponentActivity() {
    private val ss = ScreenCaptureCallback{

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ImagePickerExampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.primary
                ) {

                 //   registerScreenCaptureCallback(mainExecutor, screenCaptureCallback)
                    ImagePickerExample()
                }
            }
        }
    }
    // @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun onStart() {
        super.onStart()
        // 注册屏幕截图检测回调
        registerScreenCaptureCallback(mainExecutor, ss)
    }
    // @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun onStop() {
        super.onStop()
        // 注销屏幕截图检测回调
        unregisterScreenCaptureCallback(ss)
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
    val pickMedias = rememberLauncherForActivityResult(contract = ActivityResultContracts.PickMultipleVisualMedia(9)){}

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
            onClick = { pickMedias.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) },
        ) {
            Icon(imageVector = Icons.Default.Share, contentDescription = null, tint = Color.Black)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Pick Medias",
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                fontFamily = FontFamily.Serif
            )
        }
        RoundImage(painter = painterResource(id = R.drawable.ic_launcher_foreground), imageSize = 100)
    }
}

@Composable
fun RoundImage(painter: Painter, imageSize: Int) {
    Image(
        painter = painter,
        contentDescription = null, // 对于装饰性图片，可以将内容描述设置为null
        modifier = Modifier
            .size(imageSize.dp)
            .clip(CircleShape), // 应用圆形裁剪
        contentScale = ContentScale.Crop // 图片缩放以填充并保持其纵横比
    )
}