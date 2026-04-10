package cm.agronet.app

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Wait 2.5 seconds (2500 milliseconds)
        Handler(Looper.getMainLooper()).postDelayed({
            // Launch the main dashboard
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

            // Finish this activity so the user cannot press "Back" to see the splash screen again
            finish()
        }, 1500)
    }
}