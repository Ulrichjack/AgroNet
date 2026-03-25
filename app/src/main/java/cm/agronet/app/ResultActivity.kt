package cm.agronet.app

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.channels.FileChannel
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.google.android.gms.location.LocationServices
import android.Manifest
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class ResultActivity : AppCompatActivity() {

    // ─── Éléments UI ────────────────────────────────────────
    private lateinit var imgPlant: ImageView
    private lateinit var tvDisease: TextView
    private lateinit var tvConfidence: TextView
    private lateinit var tvWarning: TextView
    private lateinit var tvRiskLevel: TextView
    private lateinit var tvActions: TextView
    private lateinit var tvNaturalSolution: TextView
    private lateinit var tvVarieties: TextView
    private lateinit var tvContacts: TextView
    private lateinit var tvDisclaimer: TextView
    private lateinit var btnRetake: Button
    private lateinit var btnAlert: Button
    private lateinit var loadingState: View
    private lateinit var resultContent: View

    // ─── TFLite ─────────────────────────────────────────────
    private var tflite: Interpreter? = null
    private val IMG_SIZE = 224
    private val NUM_CLASSES = 5

    // ─── Étiquettes des classes (ordre exact du modèle) ─────
    private val CLASS_LABELS = arrayOf(
        "Bactériose (CBB)",
        "Striure Brune (CBSD)",
        "Mosaïque Verte (CGM)",
        "Plant Sain",
        "Mosaïque du Manioc (CMD)"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        initViews()

        val imagePath = intent.getStringExtra("IMAGE_PATH")

        // Launch in background to prevent freezing the UI
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                loadTFLiteModel()
                if (imagePath != null) {
                    analyzeImage(imagePath)
                } else {
                    withContext(Dispatchers.Main) { showError("No image received") }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) { showError("Failed to initialize: ${e.message}") }
            }
        }

        btnRetake.setOnClickListener { finish() }
        btnAlert.setOnClickListener { sendGPSAlert() }
    }

    // ─── Initialisation des vues ─────────────────────────────
    private fun initViews() {
        imgPlant          = findViewById(R.id.imgPlant)
        tvDisease         = findViewById(R.id.tvDisease)
        tvConfidence      = findViewById(R.id.tvConfidence)
        tvWarning         = findViewById(R.id.tvWarning)
        tvRiskLevel       = findViewById(R.id.tvRiskLevel)
        tvActions         = findViewById(R.id.tvActions)
        tvNaturalSolution = findViewById(R.id.tvNaturalSolution)
        tvVarieties       = findViewById(R.id.tvVarieties)
        tvContacts        = findViewById(R.id.tvContacts)
        tvDisclaimer      = findViewById(R.id.tvDisclaimer)
        btnRetake         = findViewById(R.id.btnRetake)
        btnAlert          = findViewById(R.id.btnAlert)
        loadingState      = findViewById(R.id.loadingState)
        resultContent     = findViewById(R.id.resultContent)

        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        btnBack.setOnClickListener { finish() }

        resultContent.visibility  = View.GONE
        loadingState.visibility = View.VISIBLE
    }

    // ─── Chargement du modèle TFLite ─────────────────────────
    private fun loadTFLiteModel() {
        val afd = assets.openFd("agronet_model.tflite")
        val modelBuffer = FileInputStream(afd.fileDescriptor).channel.map(
            FileChannel.MapMode.READ_ONLY,
            afd.startOffset,
            afd.declaredLength
        )

        val options = Interpreter.Options().apply {
            numThreads = 4
            useNNAPI = true
        }

        tflite = Interpreter(modelBuffer, options)
        Log.d("AgroNet", "✅ Model loaded successfully")
    }

    // ─── Pipeline d'analyse principal ────────────────────────
    private suspend fun analyzeImage(imagePath: String) {
        try {
            // 1. Load bitmap
            val options = BitmapFactory.Options().apply { inSampleSize = 2 }
            val rawBitmap = BitmapFactory.decodeFile(imagePath, options)
                ?: throw Exception("Cannot decode image")

            // 2. Fix rotation
            val bitmap = correctImageRotation(rawBitmap, imagePath)
            if (rawBitmap != bitmap) rawBitmap.recycle() // Free memory!

            // 3. Blur detection (Variance)
            val gray = Bitmap.createScaledBitmap(bitmap, 100, 100, true)
            val blurScore = calculateBlurScore(gray)
            gray.recycle() // Free memory!
            val isBlurry  = blurScore < 100.0

            // 4. Preprocess -> 224x224 raw 0-255 pixels
            val inputBuffer  = preprocessImage(bitmap)
            val outputBuffer = Array(1) { FloatArray(NUM_CLASSES) }

            // 5. TFLite Inference
            val interpreter = tflite ?: throw Exception("TFLite not initialized")
            interpreter.run(inputBuffer, outputBuffer)

            // 6. Results
            val probabilities = outputBuffer[0]
            val maxIndex   = probabilities.indices.maxByOrNull { probabilities[it] } ?: 0
            val confidence = probabilities[maxIndex]

            // Switch back to Main Thread to update UI
            withContext(Dispatchers.Main) {
                displayResults(bitmap, maxIndex, confidence, probabilities, isBlurry)
                // ✅ Launch GPS and Weather logic
                loadWeather()
            }

        } catch (e: Exception) {
            Log.e("AgroNet", "Analysis error: ${e.message}")
            withContext(Dispatchers.Main) { showError("Analysis failed: ${e.message}") }
        }
    }

    // ─── Correction de la rotation EXIF ──────────────────────
    private fun correctImageRotation(bitmap: Bitmap, imagePath: String): Bitmap {
        return try {
            val exif = androidx.exifinterface.media.ExifInterface(imagePath)
            val orientation = exif.getAttributeInt(
                androidx.exifinterface.media.ExifInterface.TAG_ORIENTATION,
                androidx.exifinterface.media.ExifInterface.ORIENTATION_NORMAL
            )

            val matrix = android.graphics.Matrix()
            when (orientation) {
                androidx.exifinterface.media.ExifInterface.ORIENTATION_ROTATE_90  ->
                    matrix.postRotate(90f)
                androidx.exifinterface.media.ExifInterface.ORIENTATION_ROTATE_180 ->
                    matrix.postRotate(180f)
                androidx.exifinterface.media.ExifInterface.ORIENTATION_ROTATE_270 ->
                    matrix.postRotate(270f)
                androidx.exifinterface.media.ExifInterface.ORIENTATION_FLIP_HORIZONTAL ->
                    matrix.preScale(-1f, 1f)
                else -> return bitmap  // Pas de rotation nécessaire
            }

            val rotated = Bitmap.createBitmap(
                bitmap, 0, 0,
                bitmap.width, bitmap.height,
                matrix, true
            )
            Log.d("AgroNet", "✅ Rotation corrigée — orientation : $orientation")
            rotated

        } catch (e: Exception) {
            Log.w("AgroNet", "⚠️ Impossible de lire EXIF — image utilisée telle quelle")
            bitmap
        }
    }

    // ─── Détection de flou (Variance) ───────────────────────
    private fun calculateBlurScore(bitmap: Bitmap): Double {
        val gray = Bitmap.createScaledBitmap(bitmap, 100, 100, true)
        var sum = 0.0; var sumSq = 0.0
        val n = gray.width * gray.height
        for (x in 0 until gray.width) {
            for (y in 0 until gray.height) {
                val p = gray.getPixel(x, y)
                val lum = 0.299 * Color.red(p) + 0.587 * Color.green(p) + 0.114 * Color.blue(p)
                sum += lum; sumSq += lum * lum
            }
        }
        val mean = sum / n
        return (sumSq / n) - (mean * mean)
    }

    // ─── Prétraitement — CRITIQUE : pixels 0-255 BRUTS ───────
    private fun preprocessImage(bitmap: Bitmap): ByteBuffer {
        val scaled = Bitmap.createScaledBitmap(bitmap, IMG_SIZE, IMG_SIZE, true)
        val buffer = ByteBuffer.allocateDirect(4 * IMG_SIZE * IMG_SIZE * 3)
        buffer.order(ByteOrder.nativeOrder())
        for (y in 0 until IMG_SIZE) {
            for (x in 0 until IMG_SIZE) {
                val pixel = scaled.getPixel(x, y)
                buffer.putFloat(Color.red(pixel).toFloat())
                buffer.putFloat(Color.green(pixel).toFloat())
                buffer.putFloat(Color.blue(pixel).toFloat())
            }
        }
        buffer.rewind()
        return buffer
    }

    // ─── Affichage des résultats ──────────────────────────────
    private fun displayResults(
        bitmap: Bitmap, classIndex: Int,
        confidence: Float, allProbs: FloatArray, isBlurry: Boolean
    ) {
        loadingState.visibility = View.GONE
        resultContent.visibility  = View.VISIBLE
        imgPlant.setImageBitmap(bitmap)
        tvDisease.text = CLASS_LABELS[classIndex]

        val confidencePct = (confidence * 100).toInt()
        tvConfidence.text = "Confiance : $confidencePct% | Précision du modèle : 80,11%"

        if (isBlurry) {
            tvWarning.visibility = View.VISIBLE
            tvWarning.text = "L'image semble floue, le résultat peut être imprécis."
        } else {
            tvWarning.visibility = View.GONE
        }

        val recommendations = getRecommendations(classIndex, confidencePct)
        displayRecommendations(recommendations, confidencePct, classIndex)
    }

    // ─── Recommandations — Textes Professionnels ─────────────
    private fun getRecommendations(classIndex: Int, confidence: Int): DiseaseRecommendation {
        return when (classIndex) {

            0 -> DiseaseRecommendation(
                riskLevel = "🚨 RISQUE ÉLEVÉ",
                riskColor = Color.parseColor("#D32F2F"), // Red
                potentialLoss = "Perte estimée : 30% à 70% de la récolte",
                immediateActions = """
                    • Arrachez et brûlez les plants infectés immédiatement.
                    • Nettoyez tous les outils (machette, houe) avec du savon avant de toucher les plants sains.
                    • Ne prenez aucune bouture sur ce plant.
                    • Inspectez les champs voisins (la maladie se propage par la pluie).
                """.trimIndent(),
                naturalSolution = """
                    • Tithonia diversifolia (fleur jalousie) : Plantez en bordure de champ.
                    Action : Fertilisant naturel et répulsif insectes.
                    Source : WAVE/IRAD 2022.
                """.trimIndent(),
                varieties = """
                    Replantez avec des variétés certifiées (IRAD) :
                    • TMS 92/0326 (Résistante bactériose)
                    • TMS 96/0023 (Résistante bactériose)
                    • Manioc 8034 (Rendement 30-40 T/ha)
                """.trimIndent(),
                contacts = """
                    • Produits chimiques : Consultez l'agent MINADER de votre zone pour les dosages officiels.
                    • Boutures saines : Programme WAVE (IRAD Nkolbisson) ou réseau RENAMUSIM-CAM.
                """.trimIndent(),
                showAlertButton = true)

            1 -> DiseaseRecommendation(
                riskLevel = "🔴 RISQUE CRITIQUE",
                riskColor = Color.parseColor("#B71C1C"), // Dark Red
                potentialLoss = "Perte estimée : 90% à 100% de la récolte",
                immediateActions = """
                    • AUCUN TRAITEMENT CHIMIQUE EXISTANT.
                    • Arrêtez immédiatement toute prise de boutures.
                    • Arrachez et brûlez tous les plants infectés.
                    • Ne replantez pas de manioc sur cette parcelle pendant au moins 3 ans.
                    • Ne transportez pas les tiges vers d'autres villages.
                """.trimIndent(),
                naturalSolution = """
                    • Aucun remède naturel contre la striure brune.
                    Prévention : La seule protection est d'utiliser toujours des boutures certifiées saines et de n'accepter aucun matériel végétal de source inconnue.
                """.trimIndent(),
                varieties = """
                    Après 3 ans de rotation de culture, replantez avec :
                    • Manioc 8034 (IRAD)
                    • Approvisionnement UNIQUEMENT via l'IRAD ou un distributeur certifié.
                """.trimIndent(),
                contacts = """
                    • URGENT : Signalez cette maladie au Programme WAVE (IRAD Nkolbisson). Il s'agit d'une maladie sous surveillance nationale (IITA/IRAD).
                """.trimIndent(),
                showAlertButton = true)

            2 -> DiseaseRecommendation(
                riskLevel = "RISQUE MODÉRÉ",
                riskColor = Color.parseColor("#F57F17"), // Amber/Orange
                potentialLoss = "Perte estimée : 20% à 40% de la récolte",
                immediateActions = """
                    • Arrachez et brûlez les plants fortement infectés (plus de 50% du feuillage touché).
                    • Ne prenez pas de boutures sur ces plants.
                    • Maintenez une bonne hygiène du champ et inspectez les plants voisins.
                    • Une rotation des cultures est recommandée pour la saison prochaine.
                """.trimIndent(),
                naturalSolution = """
                    • Bonnes pratiques culturales : Plantation en début de saison des pluies (mars-avril).
                    • Espacement recommandé : 1m × 1m (10 000 plants/ha).
                    • Sarclage régulier : 3 fois (semaines 3-4, 7-9, 12-14).
                """.trimIndent(),
                varieties = """
                    Variétés tolérantes recommandées par l'IRAD Cameroun :
                    • Manioc 8034 (30-40 T/ha)
                    • Manioc 8061, 8017
                    • TMS 92/0326, TMS 96/0023
                """.trimIndent(),
                contacts = """
                    • Conseil technique : IRAD Nkolbisson (Dr. Oumar Doungous).
                    • Pour les produits phytosanitaires approuvés : Contactez l'agent MINADER de votre zone.
                """.trimIndent(),
                showAlertButton = false)

            3 -> DiseaseRecommendation(
                riskLevel = "PLANT SAIN",
                riskColor = Color.parseColor("#2E7D32"), // Green
                potentialLoss = "Aucune anomalie détectée",
                immediateActions = """
                    • Continuez l'inspection hebdomadaire de votre champ.
                    • Maintenez un sarclage régulier (semaines 3-4, 7-9, 12-14).
                    • Ne prélevez des boutures que sur des plants rigoureusement sains.
                    • Arrachez tout plant qui commencerait à présenter des symptômes.
                """.trimIndent(),
                naturalSolution = """
                    • Prévention : Tithonia diversifolia en bordure de champ.
                    • Traitement préventif des boutures : Immersion dans l'eau chaude (50°C) pendant 15 minutes avant plantation pour éliminer champignons et insectes (Source : IFDC).
                """.trimIndent(),
                varieties = """
                    Pour vos prochaines campagnes (Variétés IRAD) :
                    • Manioc 8034 (Cycle 9-12 mois, 30-40 T/ha)
                    • Recommandation de fertilisation (IFDC) : NPK 200-300 kg/ha en deux apports (semaines 2-4 puis 4-6 semaines après).
                """.trimIndent(),
                contacts = """
                    • Formation et prévention : IRAD Nkolbisson.
                    • Boutures certifiées saines : Réseau RENAMUSIM-CAM (présent dans 301 arrondissements).
                """.trimIndent(),
                showAlertButton = false)

            4 -> DiseaseRecommendation(
                riskLevel = "🚨 RISQUE ÉLEVÉ",
                riskColor = Color.parseColor("#E65100"), // Orange/Red
                potentialLoss = "Perte estimée : 40% à 90% de la récolte",
                immediateActions = """
                    • Arrêtez de prendre des boutures sur les plants suspects.
                    • Brûlez les plants dont plus de 60% des feuilles sont déformées ou jaunes.
                    • Évitez de planter des arbres fruitiers à proximité (ils attirent la mouche blanche, vecteur du virus).
                    • Les symptômes sont particulièrement sévères sur les plants de moins de 6 mois.
                """.trimIndent(),
                naturalSolution = """
                    • Tithonia diversifolia : Agit comme répulsif naturel contre la mouche blanche (Bemisia tabaci).
                    • Attention : Aucun traitement chimique n'est efficace contre le virus lui-même (Source : IITA / MINADER).
                """.trimIndent(),
                varieties = """
                    Variétés résistantes à la mosaïque (IRAD) :
                    • TMS 92/0326, TMS 96/0023
                    • Manioc 8061, 8017 (Résistantes depuis les années 1980)
                    • Manioc 8034 (Adapté à toutes les zones climatiques du Cameroun)
                """.trimIndent(),
                contacts = """
                    • Lutte contre le vecteur (mouche blanche) : Agent MINADER pour les insecticides approuvés.
                    • Formation et boutures : Programme WAVE (IRAD Nkolbisson) et réseau RENAMUSIM-CAM.
                """.trimIndent(),
                showAlertButton = true)

            else -> DiseaseRecommendation(
                riskLevel = "INCONNU",
                riskColor = Color.GRAY,
                potentialLoss = "Analyse impossible",
                immediateActions = "Reprenez la photo avec une meilleure luminosité ou un meilleur cadrage.",
                naturalSolution = "N/A",
                varieties = "N/A",
                contacts = "En cas de doute persistant, contactez un agent de l'IRAD pour un diagnostic visuel.",
                showAlertButton = false)
        }
    }

    // ─── Affichage des recommandations ───────────────────────
    // ─── Affichage des recommandations ───────────────────────
    private fun displayRecommendations(
        rec: DiseaseRecommendation,
        confidence: Int,
        classIndex: Int
    ) {
        tvRiskLevel.text = rec.riskLevel

        // ✅ CORRECTIF DESIGN : On utilise backgroundTintList pour garder les bords arrondis de ton XML !
        tvRiskLevel.backgroundTintList = android.content.res.ColorStateList.valueOf(rec.riskColor)

        val confidenceNote = when {
            classIndex == 0 && confidence < 80 ->
                "\n\nAttention : La bactériose peut parfois être confondue avec un plant sain par le modèle. Vérifiez visuellement."
            confidence < 60 ->
                "\n\nNiveau de confiance faible ($confidence%). Le résultat est incertain, veuillez reprendre une photo."
            confidence in 60..79 ->
                "\n\nNiveau de confiance moyen ($confidence%). Vérifiez visuellement les symptômes."
            else -> ""
        }

        val baseText = "Précision IA : $confidence%"
        tvConfidence.text = "$baseText\n${rec.potentialLoss}$confidenceNote"

        tvActions.text         = rec.immediateActions
        tvNaturalSolution.text = rec.naturalSolution
        tvVarieties.text       = rec.varieties
        tvContacts.text        = rec.contacts

        tvDisclaimer.text = """
            AVERTISSEMENT IMPORTANT
            Cet outil est une aide au diagnostic par Intelligence Artificielle (précision technique 80,11%). Il ne remplace en aucun cas l'expertise d'un agent agricole qualifié.
            
            Sources validées : IRAD/WAVE Cameroun (2022), IITA (2000), IFDC (2019), MINADER Cameroun (2023).
            Ne jamais appliquer de produits phytosanitaires sans les dosages officiels fournis par le MINADER.
        """.trimIndent()

        btnAlert.visibility = if (rec.showAlertButton) View.VISIBLE else View.GONE
    }

    // ─── Alerte GPS ───────────────────────────────────────────
    private fun sendGPSAlert() {
        Toast.makeText(
            this,
            "Alerte GPS en cours d'envoi aux autorités locales...",
            Toast.LENGTH_LONG
        ).show()
        // TODO Sprint 2 : Firebase Firestore
    }

    // ─── Gestion des erreurs ──────────────────────────────────
    private fun showError(message: String) {
        loadingState.visibility = View.GONE
        resultContent.visibility  = View.VISIBLE
        tvDisease.text = "Erreur lors de l'analyse"
        tvActions.text = message
        Log.e("AgroNet", "Erreur affichée : $message")
    }

    override fun onDestroy() {
        super.onDestroy()
        tflite?.close()
    }

    // ─── Classe de données ────────────────────────────────────
    data class DiseaseRecommendation(
        val riskLevel: String,
        val riskColor: Int,
        val potentialLoss: String,
        val immediateActions: String,
        val naturalSolution: String,
        val varieties: String,
        val contacts: String,
        val showAlertButton: Boolean
    )

    // ─── Weather API (Open-Meteo) ─────────────────────────────
    private fun fetchWeather(lat: Double, lon: Double): String {
        return try {
            val urlString = String.format(
                java.util.Locale.US,
                "https://api.open-meteo.com/v1/forecast?latitude=%.4f&longitude=%.4f&daily=precipitation_sum,temperature_2m_max&timezone=Africa/Douala&forecast_days=1",
                lat, lon
            )

            val response = java.net.URL(urlString).readText()
            val jsonObject = org.json.JSONObject(response)
            val daily = jsonObject.getJSONObject("daily")
            val rain = daily.getJSONArray("precipitation_sum").getDouble(0)
            val temp = daily.getJSONArray("temperature_2m_max").getDouble(0)

            " Conditions Météo Locales :🌡️ Max $temp°C | 🌧️ Précipitations : $rain mm"
        } catch (e: Exception) {
            Log.e("AgroNet", "Erreur Météo : ${e.message}")
            "🌤️ Données météorologiques locales indisponibles."
        }
    }



    // ─── GPS & Weather Logic ─────────────────────────────────
    private fun loadWeather() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    fetchAndDisplayWeather(location.latitude, location.longitude)
                } else {
                    // Fallback Yaoundé
                    fetchAndDisplayWeather(3.84, 11.50)
                }
            }.addOnFailureListener {
                fetchAndDisplayWeather(3.84, 11.50)
            }
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                200
            )
            fetchAndDisplayWeather(3.84, 11.50)
        }
    }

    private fun fetchAndDisplayWeather(lat: Double, lon: Double) {
        lifecycleScope.launch(Dispatchers.IO) {
            val weatherData = fetchWeather(lat, lon)
            withContext(Dispatchers.Main) {
                tvDisclaimer.text = "$weatherData\n\n${tvDisclaimer.text}"
            }
        }
    }
}