package com.example

import android.annotation.SuppressLint
import android.os.Build
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun AdsterraBannerAd(
    modifier: Modifier = Modifier,
    adHtmlCode: String = """
        <script type="text/javascript">
          atOptions = {
            'key' : '6b88629b499f748cf71eb7b5f7f5818c',
            'format' : 'iframe',
            'height' : 50,
            'width' : 320,
            'params' : {}
          };
        </script>
        <script type="text/javascript" src="https://www.highperformanceformat.com/6b88629b499f748cf71eb7b5f7f5818c/invoke.js"></script>
    """.trimIndent()
) {
    val htmlContent = """
        <!DOCTYPE html>
        <html>
        <head>
            <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
            <style>
                body, html { 
                    margin: 0; padding: 0; 
                    width: 100%; height: 100%; 
                    display: flex; justify-content: center; align-items: center; 
                    overflow: hidden; background-color: transparent; 
                }
            </style>
        </head>
        <body>
            $adHtmlCode
        </body>
        </html>
    """.trimIndent()

    Box(modifier = modifier.fillMaxWidth().height(60.dp).background(Color(0xFF13131A))) {
        // Fallback or placeholder text behind the ad
        Text(
            text = "Advertisement", 
            color = Color.White.copy(alpha = 0.3f),
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Center)
        )
        
        AndroidView(
            modifier = Modifier.fillMaxWidth().height(60.dp),
            factory = { context ->
                val webContext = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    context.createAttributionContext("Adsterra")
                } else {
                    context
                }
                WebView(webContext).apply {
                    setBackgroundColor(android.graphics.Color.TRANSPARENT)
                    settings.javaScriptEnabled = true
                    settings.domStorageEnabled = true
                    settings.cacheMode = WebSettings.LOAD_DEFAULT
                    settings.useWideViewPort = true
                    settings.loadWithOverviewMode = true
                    settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                    webChromeClient = android.webkit.WebChromeClient()
                    
                    // Keep navigation contained inside the WebView
                    webViewClient = object : WebViewClient() {
                        @Deprecated("Deprecated in Java")
                        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                            try {
                                val intent = android.content.Intent(android.content.Intent.ACTION_VIEW, android.net.Uri.parse(url))
                                intent.addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK)
                                context.startActivity(intent)
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                            return true
                        }
                    }
                    
                    loadDataWithBaseURL("https://www.highperformanceformat.com/", htmlContent, "text/html", "UTF-8", null)
                }
            }
        )
    }
}
