package cm.agronet.app

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnAnalyze = findViewById<MaterialButton>(R.id.btnAnalyze)
        val btnMap = findViewById<LinearLayout>(R.id.btnMap)

        btnAnalyze.setOnClickListener {
            val intent = Intent(this, CameraActivity::class.java)
            startActivity(intent)
        }

        btnMap.setOnClickListener {
            Toast.makeText(this, "Carte - bientot disponible", Toast.LENGTH_SHORT).show()
        }
    }
}