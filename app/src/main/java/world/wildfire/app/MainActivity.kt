package world.wildfire.app

import android.os.Bundle
import android.view.KeyEvent
import android.webkit.CookieManager
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.onesignal.OneSignal

const val ONESIGNAL_APP_ID = "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx"
var versionCode = BuildConfig.VERSION_CODE
var versionName = BuildConfig.VERSION_NAME

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        val myWebView: WebView = findViewById(R.id.webview)

        // Cookies
        CookieManager.getInstance().setAcceptThirdPartyCookies(myWebView, true)
        CookieManager.getInstance().setAcceptCookie(true);

        // OneSignal Initialization
        OneSignal.initWithContext(this)
        OneSignal.setAppId(ONESIGNAL_APP_ID)
        OneSignal.promptForPushNotifications()
        val device = OneSignal.getDeviceState()
        val playerId = device!!.userId

        myWebView.settings.javaScriptEnabled = true
        myWebView.settings.javaScriptCanOpenWindowsAutomatically = true

        myWebView.loadUrl("https://tribe.wildfire.world/?os=android&player_id=$playerId&version_name=$versionName&version_code=$versionCode")

        myWebView.webViewClient = WebViewClient()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        val myWebView: WebView = findViewById(R.id.webview)

        // Check if the key event was the Back button and if there's history
        if (keyCode == KeyEvent.KEYCODE_BACK && myWebView.canGoBack()) {
            myWebView.goBack()
            return true
        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event)
    }
}