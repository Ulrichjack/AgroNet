# 🗺️ AgroNet V1 — Roadmap Complet et Définitif
**8 Mars → 18 Avril 2026 — 41 jours**
**1 développeur · 2 à 4h/jour · Zéro expérience Android**

---

## 🎯 CE QU'ON LIVRE LE 18 AVRIL

### Application Android fonctionnelle
- Détection maladie manioc offline en moins de 200ms
- Indicateur stabilité image avant capture (anti-flou)
- Résultat : maladie + sévérité estimée + recommandation IRAD
- Sauvegarde position GPS de chaque détection
- Alerte Firebase aux agriculteurs dans rayon 10 km
- Historique des détections par utilisateur

### Rapport scientifique complet
- Métriques réelles TensorFlow : accuracy, F1, matrice de confusion
- Comparaison AgroNet vs YOLO-AgriNet vs Aboh (2025) baseline
- Diagrammes UML complets : classes, séquence, cas d'utilisation, déploiement
- Sources IRAD, WAVE, MINADER vérifiées et citées

### Architecture ML validée
- EfficientNet-Lite0 fine-tuné sur Cassava Leaf Disease 2020
- Cible : accuracy supérieure à 87% — dépasser Aboh 2025 (84.33%)
- Modèle .tflite quantifié INT8 — taille inférieure à 10MB

---

## 🛠️ STACK TECHNIQUE COMPLET

| Couche | Technologie | Rôle |
|---|---|---|
| ML entraînement | TensorFlow + Keras 2.15 | Fine-tuning sur Colab |
| ML mobile | TFLite INT8 | Inférence Android offline |
| Architecture ML | EfficientNet-Lite0 | Classification 5 classes manioc |
| Prétraitement | Roboflow | Augmentation + formatage |
| Mobile | Android Kotlin API 28 | App native |
| Caméra | CameraX 1.3.0 | Capture + détection flou |
| Auth | Firebase Auth SMS | Connexion par numéro téléphone |
| Base de données locale | Room | Stockage offline détections |
| Base de données cloud | Firebase Firestore | Sync détections + GPS |
| Notifications | Firebase Cloud Messaging | Alertes voisins |
| Carte | Google Maps SDK Android | Visualisation zones à risque |
| IDE ML | VS Code + Extension Colab | Développement notebooks |
| IDE Android | Android Studio Hedgehog | Développement app |
| Versioning | GitHub | Sauvegarde et partage code |

---

## 📊 VUE D'ENSEMBLE 6 SEMAINES

| Semaine | Dates | Focus | Livrable clé |
|---|---|---|---|
| S1 | 8–14 Mars | Recherches + Setup + Dataset | Environnement prêt + données préparées |
| S2 | 15–21 Mars | Entraînement ML + Métriques | Modèle .tflite validé + chiffres rapport |
| S3 | 22–28 Mars | Android bases + Caméra + TFLite | Détection fonctionnelle sur téléphone |
| S4 | 29M–4Avr | GPS + Firebase + Alertes | App complète communautaire |
| S5 | 5–11 Avril | UML + Interface + Tests | Application V1 finalisée |
| S6 | 12–18 Avril | Rapport + PPTX + Soutenance | Présentation le 18 Avril |

---

## 🔬 RECHERCHES OBLIGATOIRES
**À faire en parallèle — priorité maximale dès la semaine 1**

### Institutions camerounaises

**IRAD — irad-cameroun.org**
- Rapport annuel surveillance phytosanitaire manioc 2023-2024
- Caractéristiques variété 8034 : rendement + résistance CMD
- Recommandations officielles traitement CMD, CBSD, CBB, CGM
- Estimation pertes manioc par maladies zones forestière et savane
- Règle absolue : ne citer aucun chiffre IRAD sans avoir lu le document original

**WAVE Center — wave-center.org**
- Rapport WAVE Cameroun 2023-2024
- Cartographie CMD et CBSD par région camerounaise
- Outils de diagnostic mobile déjà déployés
- Positionner AgroNet comme outil complémentaire WAVE accessible au grand public

**MINADER — minader.cm**
- Statistiques superficie moyenne exploitation agricole Cameroun
- Nombre d'agriculteurs manioc par région
- Rapport pertes post-récolte manioc
- NE PAS utiliser 92% sans avoir trouvé la source officielle MINADER

**FAO FAOSTAT — fao.org/faostat**
- Navigation : Production → Crops → Cassava → Cameroon
- Production manioc Cameroun 2020-2023 en tonnes
- Rendement moyen hectare Cameroun vs moyenne Afrique subsaharienne
- Valider ou corriger la statistique 50M/31M/19M citée précédemment

**CGIAR AI Lab — cgiar.org**
- Dataset manioc Afrique subsaharienne disponible
- iCassava 2019 Fine-Grained Challenge : évaluer et documenter les raisons d'écarter
- Rapport maladies manioc Afrique centrale 2023

### Articles à lire avant de coder

**Déjà lu — Aboh (2025) — résultats à retenir**
- EfficientNet-Lite0 : 84.33% accuracy — 123 FPS — 3.38M params
- MobileNetV3-Large : 83.55% accuracy — 108 FPS — 4.21M params
- Limite principale : seulement 20 epochs — augmentation basique
- Notre cible : dépasser 84.33% avec 50+ epochs et meilleure augmentation

**Déjà lu — Kumar et al. (2023) — résultats à retenir**
- CropNet sur MobileNetV3 seul : 88.01%
- Ensemble 5 modèles : 90.75% — non déployable sur mobile
- Argument : modèle unique léger approche l'ensemble grâce à fine-tuning soigné

**À lire — Ramcharan et al. (2017)**
- Titre : Deep learning for image-based cassava disease detection
- Journal : Frontiers in Plant Science — accès gratuit
- Raison : article fondateur sur manioc — cité dans Kumar 2023 — crédibilise la revue

**À lire — Calma et al. (2023)**
- Titre : Cassava disease detection using MobileNetV3 through augmented stem and leaf images
- Chercher sur ResearchGate et IEEE Xplore
- Raison : MobileNetV3 sur manioc — noter ses limites à surpasser

**Déjà lu — Nkonjoh et al. (2025) — YOLO-AgriNet IUC**
- Relire pour extraire exactement les limites déclarées par les auteurs eux-mêmes
- Page 202 : "future work — cassava" — citation directe utilisable

---

## 📅 SEMAINE 1 — SETUP COMPLET + DATASET
**8 au 14 Mars**

---

### Jour 1 — Installation environnement complet
*3 à 4h*

**VS Code + Colab :**
- Installer VS Code : code.visualstudio.com
- Chercher extension "Google Colab" dans le marketplace VS Code
- Installer et configurer
- Créer un fichier test.ipynb dans VS Code
- Connecter au runtime Colab → sélectionner GPU T4
- Créer dossier Google Drive "AgroNet" avec sous-dossiers : dataset, checkpoints, exports

**Android Studio :**
- Télécharger Android Studio Hedgehog : developer.android.com/studio
- Lancer l'installation — 8 Go d'espace requis
- Pendant l'installation : créer compte GitHub sur github.com
- Créer repo GitHub public "AgroNet-App"

**Firebase :**
- Aller sur console.firebase.google.com avec compte Google
- Créer projet "AgroNet"
- Activer Firestore Database en mode test (pour commencer)
- Activer Authentication → activer la méthode Téléphone
- Activer Cloud Messaging
- Garder précieusement le fichier google-services.json — on en aura besoin en semaine 3

---

### Jour 2 — Prise en main Android Studio
*3 à 4h*

**Créer le projet Android :**
- New Project → Empty Activity
- Language : Kotlin
- Minimum SDK : API 28 (Android 9.0)
- Package name : com.agronet.app

**Comprendre la structure — lire sans coder :**

AndroidManifest.xml — le passeport de l'app :
Déclare les permissions, les activités, le nom de l'app
Toute permission non déclarée ici = crash immédiat

MainActivity.kt — le point d'entrée :
C'est la première chose qui s'ouvre quand on lance l'app
Hérite de AppCompatActivity
Méthode onCreate() = tout ce qui se passe au démarrage

activity_main.xml — l'interface visuelle :
Le fichier XML décrit ce que l'utilisateur voit
Chaque élément a un id pour le référencer dans le code Kotlin
ConstraintLayout = positionnement relatif des éléments

build.gradle (Module) — les dépendances :
Chaque bibliothèque qu'on veut utiliser s'ajoute ici
Format : implementation "groupe:nom:version"

**Premier test :**
- Lancer l'app sur émulateur
- Si "Hello World" s'affiche = environnement fonctionnel

**Ressource à lire ce soir :** developer.android.com/codelabs/build-your-first-android-app-kotlin

---

### Jour 3 — Téléchargement et analyse du dataset
*3 à 4h*

**Créer compte Kaggle :**
- kaggle.com → Sign Up
- Aller sur : kaggle.com/competitions/cassava-leaf-disease-classification
- Lire la description complète — comprendre les 5 classes et leur origine

**Télécharger le dataset :**
- Download All — fichier zip d'environ 6 Go
- Extraire et noter la distribution exacte des images par classe
- Compter le nombre réel d'images pour valider avec les chiffres d'Aboh 2025

**Distribution attendue (à vérifier) :**
- CMD Cassava Mosaic Disease : environ 13 000 images
- CBSD Cassava Brown Streak Disease : environ 2 189 images
- CGM Cassava Green Mottle : environ 2 386 images
- CBB Bacterial Blight : environ 1 087 images
- Healthy : environ 2 577 images
- Total attendu : environ 26 000 images

**Évaluer les autres datasets pour le rapport :**

iCassava 2019 (Mwebaze et al.) :
- Chercher sur Kaggle
- Noter : nombre d'images, classes, licence, conditions de capture
- Conclusion à documenter : pourquoi retenu ou écarté

PlantVillage cassava subset :
- Confirmer la quasi-absence de manioc
- Confirmer les images en conditions de laboratoire (fond blanc)
- Conclusion à documenter : écarté car biais laboratoire et sous-représentation manioc

---

### Jour 4 — Roboflow : prétraitement complet
*4 à 5h*

**Créer compte Roboflow :**
- roboflow.com → créer compte gratuit
- Créer workspace "AgroNet"
- Créer projet "cassava-classification" → type : Image Classification

**Upload du dataset :**
- Uploader les images avec leurs étiquettes de classe
- Vérifier la distribution dans Roboflow après upload
- S'assurer que les 5 classes sont bien reconnues

**Configuration split stratifié :**
- 70% train — 20% validation — 10% test
- Activer l'option "stratified" pour garantir la représentation de chaque classe
- Vérifier que CBB (classe minoritaire) est bien représenté dans chaque split

**Augmentation — paramètres exacts à configurer :**
- Rotation aléatoire : plage -20° à +20°
- Flip horizontal : 50% de probabilité
- Flip vertical : 30% de probabilité
- Luminosité : ajustement -20% à +20%
- Contraste : ajustement -15% à +15%
- Saturation : ajustement -10% à +10%
- Bruit gaussien : léger niveau 1 — simule capteur camera bas de gamme
- Zoom aléatoire : -10% à +10%
- Ne PAS activer le blur — on entraîne sur des images nettes

**Export :**
- Format : TFRecord pour TensorFlow
- Résolution cible : 224×224 pixels
- Télécharger sur Google Drive AgroNet/dataset/

---

### Jour 5-6 — Entraînement Phase 1 sur Colab
*6 à 8h dont environ 3h d'attente GPU*

**Préparer le notebook AgroNet_Training.ipynb dans VS Code :**

Le notebook doit contenir ces sections dans l'ordre :

Section 1 — Configuration et imports :
- Monter Google Drive
- Vérifier disponibilité GPU T4
- Importer TensorFlow, Keras, NumPy, Matplotlib, scikit-learn

Section 2 — Chargement du dataset TFRecord :
- Lire les fichiers TFRecord depuis Drive
- Créer les pipelines tf.data avec AUTOTUNE
- Afficher quelques images pour vérifier que tout est correct

Section 3 — Définition du modèle EfficientNet-Lite0 :
- Source : TensorFlow Hub tfhub.dev/google/efficientnet/lite0/feature-vector/2
- Charger comme couche KerasLayer non entraînable (phase 1)
- Ajouter : GlobalAveragePooling2D + Dropout(0.3) + Dense(5, activation='softmax')
- Compiler avec Adam lr=1e-3 et SparseCategoricalCrossentropy

Section 4 — Gestion du déséquilibre de classes :
- Calculer class_weights : weight = total / (5 × count_class)
- CBB aura le poids le plus élevé car c'est la classe la plus rare

Section 5 — Entraînement Phase 1 :
- 25 epochs maximum
- Callbacks : ModelCheckpoint sauvegarde meilleur modèle + EarlyStopping patience=5 + ReduceLROnPlateau
- Passer class_weights au fit()

Section 6 — Évaluation Phase 1 :
- Accuracy sur jeu de test
- Rapport de classification scikit-learn : précision, rappel, F1 par classe
- Matrice de confusion matplotlib
- Sauvegarder tous les chiffres pour le rapport

**Résultats à noter absolument :**
- Accuracy Phase 1 : ____%
- Macro F1 Phase 1 : ____
- F1 par classe (CMD, CBSD, CBB, CGM, Healthy)
- Nombre d'epochs avant convergence

---

### Jour 7 — Entraînement Phase 2 + Conversion TFLite
*5 à 6h dont 2h GPU*

**Phase 2 — Fine-tuning progressif :**
- Dégeler les 50 dernières couches du feature extractor EfficientNet-Lite0
- Réduire lr à 1e-4
- Utiliser cosine annealing scheduler
- Entraîner 35 epochs supplémentaires
- EarlyStopping patience=7

**Comparaison avec Aboh (2025) :**
Aboh a utilisé seulement 20 epochs au total et AdamW avec lr=3e-4
Nous utilisons 60 epochs (25 + 35) avec fine-tuning progressif
C'est notre avantage méthodologique pour dépasser ses 84.33%

**Métriques finales à capturer et noter dans le rapport :**
- Accuracy test Phase 2 : ____% — cible supérieure à 87%
- Macro F1 Phase 2 : ____ — cible supérieure à 0.80
- Amélioration vs Aboh 2025 : +X.X points
- Comparaison avec toutes les baselines

**Conversion TFLite :**
- Convertir checkpoint .keras → .tflite
- Appliquer quantification INT8 représentative
- Vérifier taille : cible inférieure à 10 MB
- Tester vitesse inférence CPU Colab : cible inférieure à 200ms
- Sauvegarder agronet_efficientlite0.tflite sur Drive

**Grad-CAM — visualisations pour rapport :**
- Générer 5 images Grad-CAM par classe = 25 images total
- Vérifier visuellement que le modèle regarde les zones de lésions
- Ces images vont dans le rapport chapitre Résultats

---

## 📅 SEMAINE 2 — ANDROID : CAMÉRA + TFLITE
**15 au 21 Mars**

---

### Jour 8-9 — Architecture Android et navigation
*6 à 8h*

**Comprendre MVVM avant de coder :**

MVVM signifie Model-View-ViewModel.
C'est l'architecture officielle recommandée par Google pour Android.

Model = les données de l'application
View = ce que l'utilisateur voit, les fichiers XML et les Fragments
ViewModel = la logique qui fait le lien, survit aux rotations d'écran

Sans MVVM : si l'utilisateur tourne son téléphone pendant une analyse → l'app recharge tout et perd le résultat.
Avec MVVM : le ViewModel survit à la rotation → résultat préservé.

**Créer la structure de l'app :**

Dossier ui/ :
- SplashFragment.kt — écran de démarrage
- CameraFragment.kt — capture de photo
- ResultFragment.kt — affichage résultat
- HistoryFragment.kt — liste des détections

Dossier ml/ :
- CassavaClassifier.kt — interface avec TFLite

Dossier data/ :
- DetectionResult.kt — data class résultat
- DetectionEntity.kt — entité Room base de données
- DetectionDao.kt — requêtes Room
- DetectionDatabase.kt — configuration Room

Dossier viewmodel/ :
- DetectionViewModel.kt — logique principale

Dossier repository/ :
- DetectionRepository.kt — accès données locales et Firebase

**Navigation entre écrans :**
- Installer Navigation Component dans build.gradle
- Créer nav_graph.xml avec les 4 fragments
- Définir les transitions : Splash → Camera → Result → History (depuis menu)

**Déclaration des permissions dans AndroidManifest.xml :**
- CAMERA
- ACCESS_FINE_LOCATION
- ACCESS_COARSE_LOCATION
- INTERNET
- POST_NOTIFICATIONS (uniquement Android 13+)

---

### Jour 10-11 — Caméra CameraX + détection flou
*6 à 8h*

**Installer CameraX dans build.gradle :**
- androidx.camera:camera-core:1.3.0
- androidx.camera:camera-camera2:1.3.0
- androidx.camera:camera-lifecycle:1.3.0
- androidx.camera:camera-view:1.3.0

**Layout CameraFragment :**
- PreviewView plein écran pour le viewfinder
- Indicateur de stabilité : cercle coloré en bas au centre
- Bouton capture : désactivé par défaut
- Texte d'aide : "Pointez vers une feuille de manioc"

**Implémentation CameraX :**
- Démarrer la caméra en liant au cycle de vie du Fragment
- Afficher la prévisualisation dans le PreviewView
- Demander la permission CAMERA au premier lancement
- Gérer le cas de refus de permission avec message explicatif

**Système de détection de stabilité — sans ML :**

Le principe : toutes les 2 secondes on mesure la netteté de l'image.
Netteté = variance du filtre Laplacien appliqué à l'image en niveaux de gris.
Image nette = variance élevée (beaucoup de contours nets).
Image floue = variance faible (contours flous).

Implémentation :
- Créer un Handler qui se déclenche toutes les 2000ms
- À chaque déclenchement : prendre une frame du viewfinder
- Convertir en bitmap → niveaux de gris → appliquer Laplacien
- Calculer la variance du résultat
- Si variance supérieure à 150 (seuil à calibrer) : image nette
- Mettre à jour l'indicateur visuel : vert si net, rouge si flou
- Activer ou désactiver le bouton capture selon le résultat

**Capture photo :**
- Au tap du bouton : capturer en haute qualité via ImageCapture
- Convertir en bitmap 224×224 pour TFLite
- Passer immédiatement à CassavaClassifier

**Ressource :** developer.android.com/codelabs/camerax-getting-started

---

### Jour 12 — Intégration TFLite
*4 à 5h*

**Copier agronet_efficientlite0.tflite dans src/main/assets/**

**Ajouter dans build.gradle (android block) :**
aaptOptions → noCompress "tflite"

**Dépendances TFLite :**
- org.tensorflow:tensorflow-lite:2.14.0
- org.tensorflow:tensorflow-lite-support:0.4.4

**Implémenter CassavaClassifier.kt :**

Le constructeur charge le modèle depuis assets/ au démarrage de l'app.
Charger une seule fois au démarrage — pas à chaque analyse.

Méthode classify(bitmap: Bitmap) retourne DetectionResult :
Étape 1 — Redimensionner le bitmap à 224×224 pixels
Étape 2 — Normaliser les pixels : chaque valeur divisée par 255.0
Étape 3 — Créer le tenseur d'entrée TFLite
Étape 4 — Exécuter l'inférence : interpreter.run()
Étape 5 — Lire le tableau de sorties : probabilité pour chacune des 5 classes
Étape 6 — Trouver l'index avec la probabilité maximale
Étape 7 — Construire et retourner DetectionResult

**Mapping index → maladie en français :**
```
Index 0 CMD  → "Mosaïque du Manioc"
Index 1 CBSD → "Maladie des Stries Brunes"
Index 2 CGM  → "Marbrure Verte"
Index 3 CBB  → "Bactériose"
Index 4      → "Plant Sain"
```

**Calcul de sévérité :**
```
Score > 0.90 → SÉVÈRE   → rouge
Score > 0.70 → MODÉRÉE  → orange
Score > 0.50 → FAIBLE   → jaune
Classe saine  → SAIN    → vert
```

**Test immédiat :**
Téléphone en main → pointer vers n'importe quelle feuille → appuyer → vérifier que quelque chose s'affiche en moins de 200ms

---

### Jour 13-14 — Écran résultat + recommandations IRAD
*5 à 7h*

**Layout ResultFragment :**
- Miniature photo en haut à droite
- Grande carte colorée : nom maladie + badge sévérité
- Barre de progression : score de confiance en %
- Section dépliable "Que faire ?" avec recommandations
- Bouton "Signaler à la communauté" avec icône localisation
- Bouton "Nouvelle analyse"

**Recommandations IRAD — texte statique par maladie :**

CMD — Mosaïque du Manioc :
Recommandation 1 : Arracher et brûler immédiatement les plants infectés
Recommandation 2 : Ne jamais réutiliser des boutures issues de plants malades
Recommandation 3 : Planter la variété résistante IRAD 8034 — contacter le bureau IRAD le plus proche

CBSD — Stries Brunes :
Recommandation 1 : Aucun traitement curatif connu — destruction obligatoire des plants
Recommandation 2 : Utiliser exclusivement des boutures certifiées saines IRAD
Recommandation 3 : Cette maladie est à déclaration obligatoire — contacter le bureau IRAD

CBB — Bactériose :
Recommandation 1 : Traitement cuivre — bouillie bordelaise à 1% sur feuilles et tiges
Recommandation 2 : Désinfecter les outils de coupe entre chaque plant (eau de javel 5%)
Recommandation 3 : Éviter les blessures mécaniques lors des travaux culturaux

CGM — Marbrure Verte :
Recommandation 1 : Surveiller l'évolution — cette maladie est moins destructrice que CMD
Recommandation 2 : Contrôler les cochenilles farineuses vecteurs sur les tiges
Recommandation 3 : Maintenir une bonne fertilisation pour renforcer la résistance

Plant Sain :
Message 1 : Votre plant est en bonne santé — aucune action requise
Message 2 : Continuez la surveillance hebdomadaire de vos champs
Message 3 : Prochaine inspection recommandée dans 7 jours

---

## 📅 SEMAINE 3 — GPS + FIREBASE + ALERTES COMMUNAUTAIRES
**22 au 28 Mars**

---

### Jour 15-16 — GPS et base de données locale Room
*5 à 6h*

**Récupération GPS :**
- Ajouter dépendance : com.google.android.gms:play-services-location:21.0.1
- Créer LocationHelper.kt qui utilise FusedLocationProviderClient
- Méthode getCurrentLocation() retourne Pair(latitude, longitude) ou null si indisponible
- Demander permission ACCESS_FINE_LOCATION au premier lancement
- Gérer gracieusement le cas GPS désactivé : enregistrer quand même sans coordonnées

**Base de données Room :**

DetectionEntity.kt — une ligne de la base de données :
- id : Long auto-généré
- disease : String nom de la maladie
- confidence : Float score de confiance 0.0 à 1.0
- severity : String SEVERE/MODERATE/LOW/HEALTHY
- latitude : Double — nullable si GPS indisponible
- longitude : Double — nullable si GPS indisponible
- timestamp : Long millisecondes depuis epoch
- isSyncedToFirebase : Boolean false par défaut

DetectionDao.kt — les requêtes SQL :
- insertDetection() : insérer une nouvelle détection
- getAllDetections() : récupérer toutes triées par date décroissante
- getPendingSync() : récupérer celles non encore envoyées à Firebase
- markAsSynced() : mettre isSyncedToFirebase à true

**Pourquoi Room en plus de Firebase :**
L'app garantit un fonctionnement 100% offline.
Les données sont d'abord toujours sauvegardées localement.
Firebase reçoit les données uniquement quand internet est disponible.
Si internet n'est jamais disponible → les données restent accessibles en local.

**Écran Historique :**
- RecyclerView avec les 50 dernières détections
- Chaque item : date formatée + nom maladie + badge sévérité coloré
- Tap sur un item : détail avec recommandations

---

### Jour 17-18 — Firebase Authentication et Firestore
*6 à 8h*

**Connecter l'app Firebase :**
- Copier google-services.json dans le dossier app/
- Ajouter les plugins Firebase dans build.gradle
- Synchroniser le projet
- Initialiser FirebaseApp dans Application.kt

**Écran de connexion par SMS :**
- Un seul champ : numéro de téléphone au format camerounais (+237XXXXXXXXX)
- Bouton "Recevoir le code"
- Firebase envoie un SMS avec code à 6 chiffres
- Champ de saisie du code
- Vérification → utilisateur connecté et userId stocké en local
- L'utilisateur ne voit cet écran qu'une seule fois

**Structure Firestore :**

Collection "detections" :
Chaque document représente une détection
Document ID = userId + underscore + timestamp
Champs : userId, disease, confidence, severity, latitude, longitude, timestamp, region

Collection "users" :
Chaque document représente un utilisateur
Document ID = userId
Champs : phoneNumber, region, totalDetections, createdAt

**Logique de synchronisation :**
Au moment de chaque détection :
- Sauvegarder immédiatement dans Room (offline garanti)
- Vérifier si internet disponible
- Si oui : envoyer vers Firestore + marquer isSyncedToFirebase = true dans Room
- Si non : laisser en attente — WorkManager s'en chargera

**WorkManager pour sync en arrière-plan :**
- Créer SyncWorker.kt
- Se déclenche quand le réseau redevient disponible
- Récupère toutes les détections avec isSyncedToFirebase = false
- Les envoie à Firestore une par une
- Marque chacune comme synchronisée

---

### Jour 19-20 — Alertes communautaires GPS
*5 à 7h*

**C'est la fonctionnalité originale d'AgroNet — aucun autre système existant ne la propose**

**Côté envoi d'une alerte :**
Quand l'agriculteur appuie sur "Signaler à la communauté" :
- L'app vérifie que les coordonnées GPS sont disponibles
- Si oui : écrire dans Firestore collection "alerts" :
  - disease, latitude, longitude, timestamp, severity, userId
- Firebase Cloud Messaging envoie une notification via règle Firestore

**Côté réception d'une alerte :**
Au démarrage de l'app et toutes les 30 minutes :
- Récupérer position GPS actuelle de l'utilisateur
- Interroger Firestore : alertes dans les dernières 24h
- Calculer distance entre chaque alerte et position utilisateur
- Si distance inférieure à 10 km : afficher notification et badge
- Format notification : "⚠️ Mosaïque du Manioc détectée à 7 km de vous"

**Calcul de distance entre deux points GPS :**
Utiliser la formule de Haversine
Entrées : latitude1, longitude1, latitude2, longitude2
Sortie : distance en kilomètres
Cette formule est incluse dans les dépendances Android — pas besoin de librairie externe

**Carte Google Maps :**
- Ajouter dépendance Google Maps SDK Android
- Obtenir une API Key Google Cloud (gratuit pour usage limité)
- MapFragment avec marqueurs colorés par maladie
- Rouge pour CMD, orange pour CBSD, jaune pour CBB, gris pour CGM
- Cercle de rayon 2 km autour de chaque marqueur = zone à risque
- Filtre temporel : 24h / 7 jours / 30 jours

---

### Jour 21 — Tests d'intégration complets
*3 à 4h*

**Test 1 — Mode avion complet :**
- Activer le mode avion
- Ouvrir AgroNet
- Prendre une photo
- Vérifier : détection fonctionne + résultat affiché + sauvegardé dans Room
- Vérifier : pas d'erreur ni de crash

**Test 2 — Reconnexion internet :**
- Désactiver le mode avion avec des détections en attente
- Observer la synchronisation automatique vers Firestore
- Vérifier dans la console Firebase que les données arrivent

**Test 3 — Alerte communautaire :**
- Simuler une alerte via la console Firebase en ajoutant manuellement un document
- Vérifier que la notification arrive sur le téléphone

**Test 4 — Indicateur de flou :**
- Tenir le téléphone en mouvement rapide → vérifier indicateur rouge
- Stabiliser → vérifier indicateur vert et activation du bouton
- Prendre une photo très proche d'une surface → vérifier flou détecté

**Test 5 — Performance :**
- Mesurer le temps exact entre tap et affichage résultat
- Cible : moins de 200ms
- Si supérieur : investiguer goulot d'étranglement (prétraitement ou inférence)

---

## 📅 SEMAINE 4 — UML + RAPPORT RÉSULTATS
**29 Mars au 4 Avril**

---

### Jour 22-23 — Diagrammes UML complets
*6 à 8h*

**Outil : draw.io — diagrams.net — gratuit en ligne, pas d'installation**

**Diagramme 1 — Cas d'utilisation (Use Case Diagram) :**

Acteurs à représenter :
- Agriculteur (utilisateur principal)
- Firebase (système externe)
- Modèle TFLite (système interne)

Cas d'utilisation :
- Capturer une image de feuille de manioc
- Analyser la maladie (inclut : vérifier stabilité image)
- Consulter le résultat et la sévérité
- Lire les recommandations de traitement IRAD
- Signaler position GPS à la communauté
- Recevoir alerte maladie dans le voisinage
- Consulter la carte des zones à risque
- Voir l'historique de ses détections
- Se connecter par numéro de téléphone

**Diagramme 2 — Diagramme de classes :**

MainActivity :
- Attributs : navController
- Méthodes : onCreate(), setupNavigation()

CameraFragment :
- Attributs : cameraX (CameraX), blurChecker (Handler), captureButton (Button)
- Méthodes : startCamera(), checkBlur(), capturePhoto(), requestPermissions()

CassavaClassifier :
- Attributs : interpreter (TFLite), labels (List String)
- Méthodes : loadModel(), classify(bitmap Bitmap), preprocess(bitmap Bitmap), dispose()

DetectionResult :
- Attributs : disease (String), confidence (Float), severity (String), latitude (Double), longitude (Double), timestamp (Long)
- C'est une data class — pas de méthodes

DetectionViewModel :
- Attributs : classifier (CassavaClassifier), repository (DetectionRepository), currentResult (LiveData)
- Méthodes : analyzeImage(bitmap Bitmap), saveDetection(), sendCommunityAlert()

DetectionRepository :
- Attributs : localDb (Room), firebaseManager (FirebaseManager)
- Méthodes : saveDetection(result), syncPending(), getHistory(), sendAlert(result)

DetectionEntity :
- Attributs : id, disease, confidence, severity, latitude, longitude, timestamp, isSyncedToFirebase
- C'est une entité Room

FirebaseManager :
- Attributs : firestore (Firestore), fcm (FCM), auth (Auth)
- Méthodes : saveDetection(result), listenForAlerts(lat, lon, radius), sendNotification()

LocationHelper :
- Attributs : fusedClient (FusedLocationProvider)
- Méthodes : getCurrentLocation() retourne Pair Double, calculateDistance(lat1, lon1, lat2, lon2) retourne Double

**Relations entre classes :**
- MainActivity contient CameraFragment et ResultFragment (composition)
- CameraFragment utilise DetectionViewModel (dépendance)
- DetectionViewModel utilise CassavaClassifier (composition)
- DetectionViewModel utilise DetectionRepository (composition)
- DetectionRepository utilise DetectionEntity via Room (dépendance)
- DetectionRepository utilise FirebaseManager (dépendance)
- DetectionViewModel utilise LocationHelper (dépendance)

**Diagramme 3 — Séquence : Analyser une maladie**

Participants : Agriculteur — CameraFragment — CassavaClassifier — DetectionViewModel — Room — Firestore

Séquence dans l'ordre :
1. Agriculteur appuie sur bouton capture
2. CameraFragment capture le bitmap
3. CameraFragment appelle DetectionViewModel.analyzeImage(bitmap)
4. DetectionViewModel appelle CassavaClassifier.classify(bitmap)
5. CassavaClassifier prétraite le bitmap (resize + normalize)
6. CassavaClassifier exécute TFLite
7. CassavaClassifier retourne DetectionResult à DetectionViewModel
8. DetectionViewModel sauvegarde dans Room (synchrone — immédiat)
9. DetectionViewModel envoie le résultat à CameraFragment via LiveData
10. CameraFragment navigue vers ResultFragment avec le résultat
11. ResultFragment affiche maladie + sévérité + recommandations (asynchrone)
12. Si internet disponible : DetectionViewModel envoie vers Firestore

**Diagramme 4 — Séquence : Alerte communautaire**

Participants : Agriculteur — ResultFragment — DetectionViewModel — Firestore — FCM — App voisin

Séquence :
1. Agriculteur appuie sur "Signaler à la communauté"
2. ResultFragment appelle DetectionViewModel.sendCommunityAlert()
3. DetectionViewModel récupère position GPS actuelle
4. DetectionViewModel écrit dans Firestore collection alerts
5. Firestore déclenche Cloud Function ou règle
6. FCM envoie notification à tous les appareils dans le rayon
7. App voisin reçoit notification push
8. Si app voisin ouverte : carte mise à jour avec nouveau marqueur

**Diagramme 5 — Architecture de déploiement :**

Nœud 1 — Smartphone Agriculteur :
Contient : App AgroNet + TFLite Model + Room Database
Flèche sortante vers Nœud 2 quand réseau disponible

Nœud 2 — Firebase Cloud :
Contient : Firestore + FCM + Authentication
Flèche sortante vers Nœud 3

Nœud 3 — Smartphones voisins :
Reçoivent les notifications FCM

Indiquer clairement sur le diagramme :
- Ce qui fonctionne offline (TFLite + Room)
- Ce qui nécessite internet (Firestore + FCM)

---

### Jour 24-25 — Métriques ML et rédaction résultats
*5 à 6h*

**Remplir ce tableau comparatif dans le rapport :**

| Modèle | Dataset | Accuracy | Macro F1 | Params | FPS | Offline | Alerte GPS |
|---|---|---|---|---|---|---|---|
| YOLO-AgriNet (Nkonjoh 2025) | PlantDoc | 84.5% mAP | 83.1% | 14.2MB | 45 | Partiel | ❌ |
| EfficientNet-Lite0 (Aboh 2025) | Cassava 2020 | 84.33% | 74.35% | 3.38M | 123 | Non testé | ❌ |
| MobileNetV3 (Aboh 2025) | Cassava 2020 | 83.55% | 73.51% | 4.21M | 108 | Non testé | ❌ |
| CropNet/MobileNetV3 (Kumar 2023) | Cassava | 88.01% | — | — | — | ❌ | ❌ |
| AgroNet EfficientNet-Lite0 | Cassava 2020 + terrain | À remplir | À remplir | <10MB | <200ms | ✅ 100% | ✅ |

**Graphiques à générer sur Colab et intégrer dans rapport :**
- Courbe accuracy train vs validation Phase 1 (25 epochs)
- Courbe accuracy train vs validation Phase 2 (35 epochs supplémentaires)
- Courbe loss train vs validation Phase 1 et 2
- Matrice de confusion 5×5 normalisée avec pourcentages
- Barplot F1-score par classe avec couleurs par maladie
- 5 exemples Grad-CAM par classe (25 images au total)

**Section à rédiger dans le rapport : Analyse des résultats**

Pour chaque classe analyser :
- CMD : performance attendue élevée car 50% du dataset
- CBSD : performance à surveiller — confusion possible avec CGM (Aboh 2025 le confirme)
- CBB : performance à surveiller — classe la plus rare (1 087 images)
- CGM : confusion possible avec CBSD selon Aboh 2025
- Healthy : performance attendue très élevée — classe bien définie

---

### Jour 26-27 — Rédaction rapport : sections clés
*6 à 8h*

**Section à rédiger : Comparaison avec YOLO-AgriNet IUC**

Argument 1 — Tâche différente :
YOLO-AgriNet réalise de la détection d'objets — il localise la zone malade sur l'image avec une bounding box. AgroNet réalise de la classification — il identifie la maladie sur toute l'image. La détection nécessite des annotations bounding box (300 heures de travail humain selon Nkonjoh 2025). La classification nécessite seulement une étiquette par image — beaucoup plus scalable.

Argument 2 — Matériel cible différent :
YOLO-AgriNet est évalué sur GPU GTX 1660 Ti et nécessite un drone institutionnel. AgroNet cible les smartphones Android ordinaires à 2 Go de RAM — le matériel disponible pour 92% des agriculteurs camerounais selon [à vérifier MINADER].

Argument 3 — Contribution originale d'AgroNet :
Nkonjoh et al. (2025) déclarent explicitement en conclusion que leur future work se concentrera sur "extending the system to other critical crops such as cocoa and cassava". AgroNet adresse précisément cette lacune identifiée par l'équipe IUC elle-même.

**Section à rédiger : Positionnement vs Aboh (2025)**

Aboh (2025) réalise un benchmark sur le même dataset Cassava Leaf Disease 2020.
AgroNet part de ce benchmark et l'améliore sur trois dimensions :
Premièrement : stratégie d'entraînement plus approfondie — 60 epochs avec fine-tuning progressif vs 20 epochs Aboh.
Deuxièmement : déploiement réel dans une application Android complète — Aboh ne déploie pas.
Troisièmement : système d'alerte communautaire GPS — absent de tous les travaux analysés.

---

### Jour 28 — Cohérence code et UML
*3 à 4h*

- Comparer chaque diagramme de classe avec le code réel
- Corriger les classes dont les attributs ou méthodes ont changé
- Vérifier que tous les diagrammes de séquence correspondent aux flux réels de l'app
- Mettre à jour les diagrammes si nécessaire
- Tester l'app après les corrections éventuelles du code

---

## 📅 SEMAINE 5 — INTERFACE FINALE + TESTS COMPLETS
**5 au 11 Avril**

---

### Jour 29-30 — Interface utilisateur finale
*6 à 8h*

**Palette de couleurs AgroNet (cohérence avec PPTX soutenance) :**
- Vert forêt #1B4332 : headers, toolbar, boutons principaux
- Vert sage #40916C : éléments secondaires, boutons secondaires
- Ambre #F59E0B : badge sévérité modérée
- Rouge #DC2626 : badge sévérité élevée, alertes danger
- Crème #F8F9F0 : fond principal de l'app
- Blanc #FFFFFF : cartes, panels, éléments flottants

**Typographie :**
- Titres : Roboto Bold
- Corps : Roboto Regular
- Résultat maladie : taille 28sp bold — lisible de loin

**Écran Accueil :**
- Logo AgroNet en haut (créer un logo simple avec feuille verte + loupe)
- Slogan : "Diagnostic manioc en 1 tap"
- Bouton principal vert : "Analyser une feuille"
- Badge rouge si alertes actives dans la zone : "2 alertes dans votre zone"
- Lien discret : "Historique de mes analyses"

**Écran Caméra :**
- Viewfinder plein écran — pas de bordures inutiles
- En bas : cercle indicateur de stabilité (vert = net, rouge = flou)
- Texte sous l'indicateur : "Stabilisez l'appareil" ou "Prêt ✓"
- Bouton capture centré — grand — facile à tapper
- Grisé et non-cliquable quand image floue

**Écran Résultat :**
- Bannière de couleur en haut selon sévérité (rouge/orange/jaune/vert)
- Nom de la maladie en grand : taille 28sp
- Sous-titre : pourcentage de confiance
- Badge sévérité avec icône
- Card "Recommandations IRAD" avec accordéon — 3 recommandations
- Bouton "📍 Signaler à ma communauté" — avec confirmation avant envoi
- Bouton "Analyser une autre feuille"

**Écran Carte :**
- Carte Google Maps centrée sur position actuelle
- Marqueurs colorés par maladie
- Rayon orange semi-transparent autour de chaque marqueur
- Barre de filtre en bas : 24h / 7 jours / 30 jours
- Légende des couleurs

---

### Jour 31-32 — Gestion offline robuste + WorkManager
*4 à 5h*

**Moniteur de connectivité réseau :**
Créer NetworkMonitor.kt utilisant ConnectivityManager.
Observer en temps réel l'état de la connexion.
Exposer un StateFlow<Boolean> isConnected.
Afficher un indicateur discret dans l'app (bande de 4dp en haut) :
- Verte : en ligne
- Grise : hors ligne

**File d'attente de synchronisation :**
Room stocke isSyncedToFirebase = false pour chaque détection non envoyée.
Quand isConnected passe à true :
- WorkManager lance SyncWorker
- SyncWorker récupère toutes les détections non synchronisées
- Envoie chaque détection vers Firestore
- Met à jour isSyncedToFirebase = true

**Contraintes WorkManager :**
- Nécessite réseau disponible
- Ne lance pas si batterie faible (moins de 15%)
- S'exécute en background — l'utilisateur ne voit rien

---

### Jour 33-34 — Tests documentés
*5 à 6h*

**Plan de tests à exécuter et documenter dans le rapport :**

Catégorie 1 — Tests de détection ML :
- 10 images de feuilles saines → compter les faux positifs → cible : 0 sur 10
- 10 images CMD claires → compter les bonnes détections → cible : 9 sur 10 minimum
- 5 images CBSD → compter les bonnes détections → noter honnêtement
- 5 images prises avec téléphone bas de gamme (simuler mauvaise caméra) → noter la précision

Catégorie 2 — Tests de performance mobile :
- Latence moyenne sur 20 captures consécutives → cible : inférieure à 200ms
- RAM utilisée après 10 minutes d'usage → cible : inférieure à 150MB
- Batterie consommée sur 20 analyses → noter le pourcentage

Catégorie 3 — Tests offline/online :
- Mode avion → 5 détections → reactivation réseau → vérifier sync Firebase
- GPS désactivé → vérifier que l'app continue sans coordonnées
- Connexion très lente → vérifier timeout gracieux

Catégorie 4 — Tests utilisabilité :
- Donner le téléphone à une personne non technique
- Observer si elle comprend comment utiliser l'app sans aide
- Noter les points de confusion pour amélioration future

**Documenter tous les résultats dans une table du rapport :**
| Test | Résultat attendu | Résultat obtenu | Statut |
| chaque test | | | ✅ ou ❌ |

---

### Jour 35 — Optimisations et préparation démo
*3 à 4h*

**Optimisations finales :**
- Supprimer les logs de débogage (Log.d) avant la démo
- Vérifier qu'il n'y a pas de fuite mémoire avec l'outil Memory Profiler Android Studio
- Vérifier la taille finale de l'APK : inférieure à 50MB idéalement
- S'assurer que l'app ne crash pas sur rotation d'écran

**Préparer le scénario de démo pour la soutenance :**

Scénario en 3 minutes :
Minute 1 : "Je lance AgroNet. L'interface est simple. Je pointe vers cette feuille de manioc."
Minute 2 : "L'indicateur vert s'allume — l'image est nette. J'appuie sur Analyser."
[Résultat en moins de 200ms]
"En moins de 200 millisecondes : Mosaïque du Manioc détectée — sévérité élevée."
"Les recommandations IRAD s'affichent : arracher le plant, ne pas réutiliser les boutures."
Minute 3 : "Je signale ma position à la communauté. Sur la carte, les agriculteurs voisins dans 10 km seront alertés."
[Montrer la carte]
"Voilà ce que YOLO-AgriNet ne peut pas faire sans drone et sans serveur."

**Préparer des feuilles pour la démo :**
- Imprimer des photos de feuilles CMD, CBSD, et saine en couleur A4
- Tester que l'app les reconnaît correctement (les images imprimées ont parfois des performances réduites)

---

## 📅 SEMAINE 6 — RAPPORT FINAL ET SOUTENANCE
**12 au 18 Avril**

---

### Jour 36-37 — Rapport final
*8 à 10h*

**Chapitre 1 — Introduction :**
Contexte camerounais avec sources vérifiées IRAD + FAO FAOSTAT + WAVE
Problématique avec 3 verrous techniques
Objectifs OG + OS1 + OS2 + OS3

**Chapitre 2 — Revue de littérature :**
Tableau comparatif 7 articles
Synthèse certitudes + lacunes + réponses AgroNet
Positionnement clair vs YOLO-AgriNet IUC

**Chapitre 3 — Concepts clés :**
CNN + EfficientNet-Lite0 justification vs alternatives
Transfer Learning + fine-tuning
Edge AI + TFLite INT8
Social IoT + alertes communautaires

**Chapitre 4 — Méthodologie :**
Dataset avec comparaison des alternatives écartées
Prétraitement Roboflow avec paramètres exacts
Architecture EfficientNet-Lite0 justification chiffrée
Pipeline entraînement Phase 1 + Phase 2
Hyperparamètres complets

**Chapitre 5 — Résultats ML :**
Tableaux métriques Phase 1 et Phase 2
Matrice de confusion commentée
Comparaison avec baselines
Analyse Grad-CAM
Tests de performance mobile

**Chapitre 6 — Application AgroNet :**
Architecture avec diagrammes UML
Description fonctionnalités V1
Résultats des tests documentés
Limites reconnues honnêtement

**Chapitre 7 — Conclusion :**
4 contributions originales
Limites
Perspectives court terme et long terme

---

### Jour 38 — Mise à jour PPTX
*4 à 5h*

**Slides à modifier avec les vrais résultats :**

Slide Architecture CNN :
- Changer MobileNetV3-Large → EfficientNet-Lite0
- Mettre à jour le tableau comparatif avec chiffres Aboh 2025 réels

Slide Résultats :
- Remplacer objectifs par résultats réels obtenus
- Ajouter matrice de confusion réelle
- Ajouter comparaison chiffrée avec Aboh 2025

Slide Revue Littérature :
- Remplacer Gülmez par Aboh (2025) dans le tableau

Slide Démo :
- Ajouter captures d'écran de l'application réelle

---

### Jour 39 — Répétition complète
*4h*

- Soutenance complète chronométrée : cible 17 minutes
- Test démo application sur téléphone
- Répétition réponses aux 30 questions du guide de révision
- Mémoriser les chiffres réels obtenus

---

### Jour 40 — Dernier jour de préparation
*3h*

- Correction dernières coquilles dans le rapport
- Vérifier que tous les UML sont dans le rapport
- Préparer clé USB avec PPTX + rapport + APK
- Charger le téléphone à 100%
- Installer l'APK final et tester une dernière fois

---

### Jour 41 — 18 Avril — SOUTENANCE 🎯

---

## ⚠️ RISQUES ET PLANS B

| Risque | Plan B |
|---|---|
| Accuracy inférieure à 85% | Honnêteté scientifique — expliquer pourquoi et ce qui l'améliorerait |
| Pas de téléphone Android | Louer un appareil une semaine pour les tests finaux |
| Firebase quota dépassé | Mode purement offline pour la démo |
| Manque de temps semaine 5 | Retirer carte Google Maps — garder alertes texte seulement |
| CBSD mal classifié | Attendu selon Aboh 2025 — documenter et expliquer |

---

## 📋 CHECKLIST FINALE — 18 AVRIL

- [ ] Application installée et testée sur téléphone
- [ ] Modèle TFLite latence vérifiée inférieure à 200ms
- [ ] Firebase Firestore reçoit les données
- [ ] Notifications d'alerte fonctionnelles
- [ ] Tous les diagrammes UML dans le rapport
- [ ] Tableau métriques complet avec vraies valeurs
- [ ] Matrice de confusion dans le rapport
- [ ] Comparaison avec Aboh 2025 et YOLO-AgriNet
- [ ] PPTX mis à jour avec vrais résultats
- [ ] Guide révision 30 questions maîtrisé
- [ ] Démo de 3 minutes répétée

---

*Roadmap définitif — AgroNet V1 — Créé le 8 Mars 2026*
*Architecture ML : EfficientNet-Lite0 — Cible : accuracy supérieure à 87%*
*Présentation soutenance : 18 Avril 2026*
