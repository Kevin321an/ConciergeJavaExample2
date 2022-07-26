package com.flybits.conciergejavaexample

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.View
import android.webkit.WebView
import android.widget.Button
import com.flybits.android.kernel.models.Content
import com.flybits.commons.library.api.results.callbacks.ObjectResultCallback
import com.flybits.commons.library.exceptions.FlybitsException
import com.flybits.commons.library.utils.Utilities
import java.io.UnsupportedEncodingException
import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import java.util.*

val rtfString = "%3Cp%3ETD%20%3Cstrong%3EGoalAssistTM%3C%2Fstrong%3E%20is%20%3Cem%3Eoffering%3C%2Fem%3E%20a%20single%20Initial%20%3Cspan%20style%3D%22text-decoration%3A%20underline%22%3ETransfer%20Award%20%3C%2Fspan%3Eand%20a%20single%20Ongoing%20Transfer%20Award%20(as%20described%20below)%20to%20any%20qualifying%20new%20or%20existing%20TD%20Direct%20Investing%20client.%3C%2Fp%3E%0A%3Col%3E%0A%3Cli%3Etest%3C%2Fli%3E%0A%3Cli%3Elist%3C%2Fli%3E%0A%3C%2Fol%3E%0A%3Cul%3E%0A%3Cli%3Etest%3C%2Fli%3E%0A%3Cli%3Eanother%20list%3C%2Fli%3E%0A%3C%2Ful%3E%0A%3Cp%3E%F0%9F%99%82%F0%9F%99%83%20%3Ca%20href%3D%22https%3A%2F%2Fgoogle.ca%22%3Elink%3C%2Fa%3E%3C%2Fp%3E"

class RTFScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rtfscreen)

        val webview = findViewById<WebView>(R.id.RTF_webview)
        val btn_load_rtf = findViewById<Button>(R.id.open_rtf)

        /***
         * Fetch the content, deserialize it then assign the String value to the `rtfString` property of the `FlybitsRTFComponent` instance.
         * The `rtfString` property ``will work with RTF and non RTF strings``. No need to check if the string is or not RTF.
         */
        btn_load_rtf.setOnClickListener {
            webview.apply {
                loadDataWithBaseURL(
                        null,
                        rtfString.toRTF(),
                        "text/html",
                        "UTF-8",
                        null
                )
            }
        }
    }

    /**
     * Decodes the String if the input String is RichTextFormat Encoded String
     * else returns a plains text.
     *
     * @param rtfString the string passed to be decoded if it in rich text format.
     * @return decoded String or plain text as given as input.
     */
    fun String.toRTF(): String {
        return try {
            URLDecoder.decode(this, StandardCharsets.UTF_8.name())
        } catch (e: UnsupportedEncodingException) {
            this
        } catch (e: IllegalArgumentException) {
            this
        }
    }
}