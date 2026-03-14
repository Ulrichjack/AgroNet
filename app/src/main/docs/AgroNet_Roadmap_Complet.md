# 🗺️ AgroNet V1 — Roadmap Complet
**8 Mars → 18 Avril 2026 — 41 jours**
**1 développeur · 2 à 4h/jour · Zéro expérience Android**

---

## 📊 VUE MACRO DES 6 SEMAINES

| Semaine | Dates | Thème | Livrable fin de semaine |
|---|---|---|---|
| S1 | 8–14 Mars | Recherches + Setup + Dataset | Environnement 100% prêt |
| S2 | 15–21 Mars | Entraînement ML Colab | Modèle .tflite validé + métriques |
| S3 | 22–28 Mars | Android bases + Caméra + UML | App caméra fonctionnelle + UML |
| S4 | 29 Mars–4 Avr | TFLite + Firebase + GPS | Détection complète offline |
| S5 | 5–11 Avr | Alertes + Interface + Tests | App V1 complète démontrable |
| S6 | 12–18 Avr | Rapport + Corrections + Répétition | Soutenance le 18 Avril |

---

## 🎯 CE QU'ON LIVRE LE 18 AVRIL

### Application Android démontrable
- Caméra avec indicateur de netteté avant capture
- Détection offline en moins de 200ms
- Résultat : maladie + sévérité + recommandation traitement IRAD
- Position GPS enregistrée à chaque détection
- Alerte Firebase aux agriculteurs dans rayon 10 km
- Historique des 10 dernières détections
- Fonctionne sans internet sur Tecno/Itel 4Go RAM

### Rapport scientifique complet
- 6 articles cités avec métriques précises
- Métriques réelles du modèle (accuracy, F1, matrice confusion)
- Comparaison directe avec YOLO-AgriNet IUC
- 5 diagrammes UML complets
- Sources IRAD, WAVE, MINADER vérifiées et citées

---

## 📋 RECHERCHES OBLIGATOIRES
**Ces recherches se font en parallèle — pas toutes la semaine 1**
**Chaque recherche a une deadline indiquée**

---

### BLOC A — Contexte camerounais (deadline : 14 Mars)

**IRAD — irad-cameroun.org**
- Rapport annuel surveillance phytosanitaire manioc 2023-2024
- Caractéristiques variété 8034 : rendement, résistance CMD
- Estimation officielle pertes manioc par maladies au Cameroun
- Recommandations traitement CMD, CBSD, CBB validées
- Si site indisponible : chercher "IRAD Cameroun manioc rapport" sur Google Scholar

**WAVE Center — wave-center.org**
- Rapport WAVE Cameroun 2023-2024
- Cartographie CMD et CBSD par région camerounaise
- Outils diagnostic mobile déjà déployés au Cameroun
- Note : positionner AgroNet comme outil grand public complémentaire à WAVE

**MINADER — minader.cm**
- Superficie moyenne exploitation agricole Cameroun
- Nombre agriculteurs manioc par région
- Rapport pertes post-récolte manioc officiel
- IMPORTANT : ne citer aucun chiffre sans URL officielle vérifiée

**FAO FAOSTAT — fao.org/faostat**
- Aller sur : Production → Crops → Cassava → Cameroon
- Production manioc Cameroun 2019-2023 en tonnes
- Rendement moyen hectare Cameroun vs moyenne Afrique
- Objectif : corriger ou valider la stat 50M/31M/19M

---

### BLOC B — Dataset et ML (deadline : 15 Mars)

**Kaggle — kaggle.com**
- Cassava Leaf Disease Classification (Sankalana, 2023) → source principale
- iCassava 2019 Fine-Grained Challenge → évaluer, justifier l'écart
- NE PAS utiliser le dataset combiné non officiel trouvé précédemment
- Télécharger et inspecter les 21 439 images : vérifier qualité, distribution classes

**CGIAR AI Lab — cgiarailab.org**
- Chercher cassava disease dataset Africa
- Chercher publications sur CMD et CBSD en Afrique centrale
- Objectif : trouver une référence scientifique sur la distribution CMD au Cameroun

**TensorFlow Hub — tfhub.dev**
- Trouver le modèle MobileNetV3-Large pré-entraîné ImageNet
- Vérifier version compatible TFLite
- Lire la documentation d'intégration

---

### BLOC C — Positionnement scientifique (deadline : 21 Mars)

**Article YOLO-AgriNet IUC (déjà lu)**
- Extraire tous leurs chiffres précis : mAP@0.5, FPS, taille modèle
- Lister leurs 3 limites avouées dans la conclusion
- Préparer tableau comparatif AgroNet vs YOLO-AgriNet

**Google Scholar — scholar.google.com**
- Chercher : "MobileNetV3 cassava disease classification"
- Chercher : "cassava leaf disease mobile android offline"
- Chercher : "federated learning plant disease detection"
- Objectif : trouver si quelqu'un a déjà fait exactement AgroNet

**Mohanty et al. (2016) — article fondateur**
- Lire le résumé sur Frontiers in Plant Science
- Extraire : méthodologie, résultat 99.35%, limite (pas de localisation)
- C'est la référence historique que tout le monde cite

---

## 📅 SEMAINE 1 — SETUP + RECHERCHES + DATASET
**8 au 14 Mars — 14 à 28 heures**

---

### Jour 1 (8 Mars) — Installation environnement
*2 à 4 heures*

**Android Studio :**
- Télécharger Android Studio Hedgehog sur developer.android.com/studio
- Installer : prévoir 8 Go d'espace disque
- Créer un premier projet vide "AgroNet" en Kotlin
- Lancer sur l'émulateur : vérifier que ça tourne
- Explorer la structure : MainActivity.kt, activity_main.xml, build.gradle

**Google Colab :**
- Aller sur colab.research.google.com
- Créer un nouveau notebook nommé "AgroNet_Training"
- Connecter Google Drive : pour sauvegarder les checkpoints automatiquement
- Vérifier GPU disponible : Runtime → Change runtime type → GPU T4
- Installer les bibliothèques de base dans le notebook : TensorFlow 2.x, NumPy, Matplotlib

**Firebase :**
- Créer un compte Google si pas encore fait
- Aller sur console.firebase.google.com
- Créer un projet nommé "agronet-v1"
- Activer : Firestore Database, Authentication, Cloud Messaging
- Garder la console ouverte — on reviendra en semaine 4

---

### Jour 2 (9 Mars) — Téléchargement dataset + inspection
*2 à 4 heures*

**Kaggle setup :**
- Créer un compte sur kaggle.com si pas encore fait
- Installer l'API Kaggle dans Colab
- Télécharger Cassava Leaf Disease Classification directement dans Colab

**Inspection du dataset :**
- Compter les images par classe et noter dans un tableau
- Regarder 20 images de chaque classe manuellement
- Identifier les images floues, mal cadrées, non représentatives
- Comparer la distribution des classes avec ce qu'on attendait

**Ce que tu dois noter dans ton rapport après cette inspection :**
- Distribution exacte des 5 classes avec pourcentages
- Qualité générale des images (conditions terrain ou laboratoire)
- Présence de variétés proches des variétés camerounaises

---

### Jour 3 (10 Mars) — Roboflow + Prétraitement
*2 à 4 heures*

**Créer un projet Roboflow :**
- Compte gratuit sur roboflow.com
- Créer un projet "AgroNet-Cassava" de type Classification
- Uploader les images Kaggle (peut prendre du temps selon connexion)

**Configurer le prétraitement :**
- Resize : 224×224 pixels (standard MobileNetV3)
- Augmentations à activer :
  - Rotation : ±15 degrés
  - Flip horizontal : 50% de probabilité
  - Luminosité : ±20%
  - Contraste : ±15%
  - Bruit gaussien léger : simuler photos smartphone bas de gamme
- Division : 70% train / 20% validation / 10% test
- Vérifier que chaque classe est bien représentée dans les 3 splits

**Exporter :**
- Format TFRecord pour Colab
- Garder aussi une version dossiers PNG pour inspection

---

### Jour 4 (11 Mars) — Recherches IRAD + WAVE + FAO
*2 à 4 heures*

**Effectuer toutes les recherches du BLOC A**
- Ouvrir chaque site mentionné
- Copier les chiffres exacts avec URL source dans un document texte
- Si un site ne répond pas, chercher sur Google Scholar le nom de l'institution
- Ne rien inventer — noter "donnée non trouvée" si c'est le cas

---

### Jour 5 (12 Mars) — Diagrammes UML : Cas d'utilisation + Activité
*2 à 4 heures*

**Outil recommandé : draw.io (app.diagrams.net) — gratuit, en ligne**

**Diagramme de cas d'utilisation :**

Acteurs :
- Agriculteur (acteur principal)
- Système AgroNet (app)
- Firebase (acteur secondaire)
- Agriculteur voisin (acteur secondaire)

Cas d'utilisation à modéliser :
- Photographier une feuille
- Détecter la maladie (inclut : vérifier netteté image)
- Afficher résultat et recommandation
- Enregistrer position GPS
- Consulter historique détections
- Recevoir alerte épidémique
- Envoyer alerte aux voisins (extend de Détecter maladie si maladie grave)

**Diagramme d'activité — flux principal :**
1. Agriculteur ouvre l'app
2. App demande permission caméra et GPS
3. Prévisualisation caméra s'affiche
4. App mesure netteté toutes les 2 secondes
5. Indicateur passe au vert si image nette
6. Agriculteur appuie sur le bouton capture
7. Photo prise → TFLite analyse → résultat affiché
8. App enregistre : maladie + sévérité + GPS + timestamp
9. Si réseau disponible → Firebase envoie alerte voisins
10. Fin

---

### Jour 6 (13 Mars) — Diagramme de séquence + Déploiement
*2 à 4 heures*

**Diagramme de séquence — Scénario détection complète :**

Participants : Agriculteur — App Android — TFLite — GPS — Firebase

Séquence :
1. Agriculteur → App : appuie sur "Analyser"
2. App → Caméra : capturer image
3. App → App : vérifier score flou (Laplacian)
4. Si flou trop élevé → App → Agriculteur : "Tenez l'appareil plus stable"
5. App → TFLite : envoyer image 224×224 normalisée
6. TFLite → App : retourner classe + score confiance
7. App → GPS : récupérer coordonnées actuelles
8. App → App : calculer niveau sévérité depuis score confiance
9. App → Agriculteur : afficher résultat + recommandation
10. App → Firebase : envoyer si réseau (maladie + GPS + timestamp)
11. Firebase → Agriculteurs voisins : envoyer notification alerte

**Diagramme de déploiement :**

Nœuds à représenter :
- Smartphone Android (TFLite + App Kotlin + GPS + Caméra)
- Google Firebase Cloud (Firestore + FCM + Auth)
- Google Colab (entraînement modèle — hors ligne de l'app)
- Roboflow (prétraitement — hors ligne de l'app)
- Téléphones voisins (réception notification)

Connexions :
- Smartphone → Firebase : HTTPS optionnel (offline possible)
- Firebase → Téléphones voisins : FCM push notification
- Colab → Smartphone : déploiement fichier .tflite via Android Studio

---

### Jour 7 (14 Mars) — Diagramme de classes + révision UML
*2 à 4 heures*

**Diagramme de classes AgroNet :**

Classes à modéliser :

```
MainActivity
- Attributs : cameraExecutor, imageCapture
- Méthodes : startCamera(), takePhoto(), navigateToResult()

ImageClassifier
- Attributs : model (TFLite), labels (List<String>), inputSize (224)
- Méthodes : loadModel(), classifyImage(bitmap) : ClassificationResult, close()

ClassificationResult
- Attributs : className (String), confidence (Float), severity (SeverityLevel)
- Méthodes : getSeverityColor(), getRecommendation()

SeverityLevel (Enumération)
- HEALTHY
- LOW (50-70% confiance)
- MODERATE (70-90% confiance)
- HIGH (>90% confiance)

Detection (entité Firebase)
- Attributs : id, userId, disease, confidence, latitude, longitude, timestamp
- Méthodes : toFirestoreMap(), fromFirestoreMap()

AlertManager
- Attributs : firestore, messagingService
- Méthodes : saveDetection(detection), sendAlertToNeighbors(detection, radiusKm)

UserPreferences
- Attributs : userId, phoneNumber, notificationsEnabled
- Méthodes : save(), load()

BlurDetector
- Attributs : threshold (Float)
- Méthodes : calculateLaplacianVariance(bitmap) : Float, isImageSharp(bitmap) : Boolean
```

Relations :
- MainActivity utilise ImageClassifier et BlurDetector
- MainActivity crée ClassificationResult
- ClassificationResult contient SeverityLevel
- AlertManager crée et sauvegarde Detection
- MainActivity appelle AlertManager après classification

**Fin semaine 1 — checkpoint :**
- Environnement Android Studio opérationnel
- Dataset Kaggle téléchargé et inspecté
- Roboflow configuré et prétraitement appliqué
- 5 diagrammes UML dessinés sur draw.io
- Recherches institutions camerounaises documentées

---

## 📅 SEMAINE 2 — ENTRAÎNEMENT ML + MÉTRIQUES
**15 au 21 Mars — 14 à 28 heures**

---

### Jour 8 (15 Mars) — Recherches ML + Préparation Colab
*2 à 4 heures*

**Effectuer les recherches du BLOC B et BLOC C**

**Préparer le notebook Colab :**
- Monter Google Drive dans Colab
- Charger le dataset TFRecord depuis Roboflow
- Vérifier le chargement : afficher 5 images de chaque classe
- Vérifier les dimensions : toutes les images en 224×224×3
- Calculer les statistiques : moyenne et écart-type des pixels

---

### Jour 9 (16 Mars) — Phase 1 : Transfer Learning couches gelées
*3 à 5 heures dont 1-2h d'attente GPU*

**Charger MobileNetV3-Large depuis TensorFlow Hub :**
- Modèle pré-entraîné sur ImageNet 1000 classes
- Retirer la couche de classification originale
- Ajouter une nouvelle tête pour 5 classes manioc

**Architecture finale du modèle :**
- Couches MobileNetV3-Large gelées (non entraînables)
- GlobalAveragePooling2D
- Dropout 0.3 (régularisation)
- Dense 5 classes avec activation Softmax

**Paramètres Phase 1 :**
- Optimiseur : Adam avec taux d'apprentissage 1e-3
- Fonction de perte : Sparse Categorical Crossentropy
- Métriques à surveiller : accuracy, val_accuracy
- Nombre d'epochs : 20
- Callbacks : EarlyStopping, ModelCheckpoint, TensorBoard

**Ce qui se passe pendant l'attente GPU :**
- Lire la documentation Android CameraX
- Regarder le tutoriel officiel TFLite Android Quickstart

---

### Jour 10 (17 Mars) — Phase 2 : Fine-tuning
*3 à 5 heures dont 2-3h d'attente GPU*

**Dégeler les 30 dernières couches de MobileNetV3**

**Paramètres Phase 2 :**
- Optimiseur : Adam avec taux d'apprentissage réduit à 1e-4
- Même fonction de perte
- Nombre d'epochs : 30 supplémentaires
- Continuer depuis le meilleur checkpoint Phase 1

**Surveiller attentivement :**
- Si val_accuracy arrête de progresser pendant 5 epochs → EarlyStopping déclenché
- Si val_loss augmente alors que train_loss descend → overfitting → augmenter Dropout

---

### Jour 11 (18 Mars) — Évaluation + Métriques complètes
*2 à 4 heures*

**Évaluer sur le jeu de test (jamais vu pendant l'entraînement) :**

**Métriques globales à capturer et noter dans le rapport :**
- Accuracy globale sur test set
- Loss sur test set

**Métriques par classe à capturer :**
- Précision par classe (Precision)
- Rappel par classe (Recall)
- F1-Score par classe
- Support (nombre d'images de test par classe)

**Matrice de confusion :**
- Matrice 5×5 avec les 5 classes
- Visualiser avec Seaborn heatmap
- Sauvegarder l'image en PNG pour le rapport

**Courbes d'apprentissage :**
- Courbe accuracy : train vs validation sur tous les epochs
- Courbe loss : train vs validation sur tous les epochs
- Ces courbes deviennent les Figures du rapport (Figure 5 et Figure 6)

**Comparaison à construire pour le rapport :**

| Métrique | YOLO-AgriNet (IUC) | AgroNet (toi) |
|---|---|---|
| Accuracy/mAP@0.5 | 84.5% | À remplir |
| F1-Score | 83.1% | À remplir |
| Taille modèle | 14.2 MB | À remplir |
| Latence Android | 32 FPS | À remplir |
| Dataset | PlantDoc 2569 img | Cassava 21439 img |
| Culture cible | 13 espèces | Manioc uniquement |
| Fonctionne offline | Partiel | 100% |
| Alerte communauté | Non | Oui |

---

### Jour 12 (19 Mars) — Conversion TFLite + Optimisation
*2 à 4 heures*

**Conversion du modèle Keras vers TFLite :**
- Convertir le meilleur modèle .keras → .tflite standard
- Appliquer la quantification INT8 (poids 32 bits → 8 bits)
- Sauvegarder les deux versions : standard et quantifié

**Tests de performance sur les deux versions :**
- Mesurer la taille en MB de chaque version
- Mesurer le temps d'inférence sur 100 images test
- Calculer la latence moyenne en millisecondes
- Vérifier que la précision ne chute pas de plus de 1%

**Valider les cibles :**
- Taille cible : moins de 15 MB ✓ ou ✗
- Latence cible : moins de 200ms ✓ ou ✗
- Accuracy post-quantification vs avant : différence acceptable

**Créer le fichier labels.txt :**
- Fichier texte avec les 5 noms de classes dans l'ordre exact
- Cassava Bacterial Blight
- Cassava Brown Streak Disease
- Cassava Green Mottle
- Cassava Healthy
- Cassava Mosaic Disease

---

### Jour 13 (20 Mars) — Ablation study + Analyse erreurs
*2 à 4 heures*

**Ablation study — tableau pour le rapport :**
Tester le modèle avec et sans augmentation pour montrer l'impact :

| Configuration | Accuracy | F1 CMD | F1 CBSD |
|---|---|---|---|
| Sans augmentation (baseline) | À mesurer | À mesurer | À mesurer |
| Avec augmentation Roboflow | À mesurer | À mesurer | À mesurer |
| Avec fine-tuning | À mesurer | À mesurer | À mesurer |

**Analyse des erreurs :**
- Quelles classes sont souvent confondues entre elles ?
- CMD est-elle confondue avec CGM (Green Mottle) ?
- CBSD est-elle confondue avec Healthy ?
- Regarder les images mal classifiées : sont-elles floues ? Mal cadrées ?
- Ces observations vont dans la section "Limites" du rapport

---

### Jour 14 (21 Mars) — Documentation ML + Préparation Android
*2 à 4 heures*

**Documenter toutes les métriques dans un fichier texte propre**

**Commencer à apprendre Android en parallèle :**
- Faire le cours Android Basics in Kotlin sur developer.android.com/courses
- Objectif du jour : comprendre Activity, Layout XML, Intent, ViewModel
- Créer une app simple avec 3 écrans et navigation entre eux
- Ne pas s'inquiéter du design

**Fin semaine 2 — checkpoint :**
- Modèle .tflite quantifié et validé
- Toutes les métriques documentées
- Courbes d'apprentissage sauvegardées
- Ablation study complète
- Bases Android en cours d'apprentissage

---

## 📅 SEMAINE 3 — ANDROID BASES + CAMÉRA
**22 au 28 Mars — 14 à 28 heures**

---

### Jour 15 (22 Mars) — Structure du projet Android
*2 à 4 heures*

**Comprendre la structure d'un projet Android Kotlin :**
- app/src/main/java : le code Kotlin
- app/src/main/res/layout : les fichiers XML d'interface
- app/src/main/assets : là où ira le fichier .tflite
- app/src/main/AndroidManifest.xml : permissions et configuration

**Créer la structure de l'app AgroNet :**
- MainActivity : écran principal avec prévisualisation caméra
- ResultActivity : écran affichant le résultat de la détection
- HistoryActivity : écran listant les 10 dernières détections
- SplashActivity : écran de démarrage avec logo AgroNet

**Configurer build.gradle avec les dépendances :**
- CameraX (camera-camera2, camera-lifecycle, camera-view)
- TFLite (tensorflow-lite, tensorflow-lite-support)
- Firebase (firebase-firestore, firebase-messaging, firebase-auth)
- Google Play Services Location (GPS)
- Glide (affichage images dans l'historique)

---

### Jour 16 (23 Mars) — Permissions Android
*2 à 4 heures*

**Déclarer les permissions dans AndroidManifest.xml :**
- CAMERA : accès à la caméra
- ACCESS_FINE_LOCATION : GPS précis
- ACCESS_COARSE_LOCATION : GPS approximatif fallback
- INTERNET : Firebase et alertes
- VIBRATE : retour haptique lors de la détection
- RECEIVE_BOOT_COMPLETED : recevoir notifications Firebase au démarrage

**Implémenter la demande de permissions à l'exécution :**
- Android 6+ nécessite de demander les permissions à l'utilisateur à l'usage
- Créer un flux de demande de permission caméra au premier lancement
- Créer un flux de demande de permission GPS au premier lancement
- Gérer le cas où l'utilisateur refuse : afficher explication

---

### Jour 17 (24 Mars) — CameraX : prévisualisation
*3 à 5 heures*

**Intégrer CameraX dans MainActivity :**
- Ajouter PreviewView dans le layout XML
- Configurer CameraProvider
- Lier le Preview au PreviewView
- Lancer la caméra sur onCreate

**BlurDetector — indicateur de netteté :**
- Toutes les 2 secondes, capturer une frame de la prévisualisation
- Calculer la variance du Laplacien de l'image (mesure de netteté)
- Si variance > seuil (à calibrer) → image nette → afficher cercle vert
- Si variance < seuil → image floue → afficher cercle rouge
- Calibrer le seuil en testant avec plusieurs images nettes et floues

**Interface de la prévisualisation :**
- Prévisualisation caméra plein écran
- Indicateur de netteté en haut à droite (cercle rouge/vert)
- Bouton capture centré en bas
- Texte d'aide : "Pointez sur une feuille et attendez le vert"

---

### Jour 18 (25 Mars) — CameraX : capture photo
*2 à 4 heures*

**Ajouter ImageCapture à CameraX :**
- Configurer la résolution de capture : 224×224 ou plus grand
- Implémenter la prise de photo au clic du bouton
- Sauvegarder la photo en mémoire temporaire (pas dans la galerie)
- Récupérer le Bitmap de la photo capturée

**Passage à ResultActivity :**
- Compresser le Bitmap en ByteArray pour le passer via Intent
- Lancer ResultActivity avec la photo en paramètre
- Afficher la photo capturée dans ResultActivity

---

### Jour 19 (26 Mars) — Tests caméra sur téléphone réel
*2 à 4 heures*

**Connecter le téléphone Android de Thayana en mode développeur :**
- Paramètres → À propos → Numéro de build → Appuyer 7 fois
- Activer le débogage USB
- Connecter via câble USB à l'ordinateur
- Lancer l'app depuis Android Studio sur le vrai téléphone

**Tests à effectuer :**
- La prévisualisation caméra s'affiche correctement
- L'indicateur de netteté change bien entre vert et rouge
- La capture photo fonctionne sans crash
- La navigation vers ResultActivity fonctionne
- Tester dans différentes conditions de lumière

---

### Jours 20-21 (27-28 Mars) — UML révision + rapport début
*4 à 8 heures*

**Réviser les 5 diagrammes UML avec ce qu'on a appris :**
- Mettre à jour le diagramme de classes avec les vraies classes Kotlin créées
- Vérifier la cohérence entre le diagramme de séquence et le code écrit
- Exporter tous les diagrammes en PNG haute résolution pour le rapport

**Commencer à rédiger les sections du rapport :**
- Section Méthodologie : décrire le pipeline ML avec les vraies métriques
- Section Architecture : décrire l'app Android avec référence aux UML
- Insérer les courbes d'apprentissage et la matrice de confusion

**Fin semaine 3 — checkpoint :**
- App Android avec caméra fonctionnelle sur téléphone réel
- Indicateur de netteté opérationnel
- 5 diagrammes UML finalisés et exportés
- Sections Méthodologie et Architecture du rapport rédigées

---

## 📅 SEMAINE 4 — TFLITE + FIREBASE + GPS
**29 Mars au 4 Avril — 14 à 28 heures**

---

### Jour 22 (29 Mars) — Intégration TFLite dans Android
*3 à 5 heures*

**Copier les fichiers modèle dans le projet :**
- Copier model_quantized.tflite dans app/src/main/assets/
- Copier labels.txt dans app/src/main/assets/

**Créer la classe ImageClassifier.kt :**
- Charger le modèle TFLite depuis assets au démarrage
- Accepter un Bitmap en entrée
- Redimensionner le Bitmap en 224×224 pixels
- Normaliser les valeurs pixels entre 0 et 1
- Lancer l'inférence TFLite
- Récupérer le tableau de scores de confiance (5 valeurs)
- Identifier la classe avec le score le plus élevé
- Retourner ClassificationResult avec classe + score + sévérité

---

### Jour 23 (30 Mars) — Affichage résultat + recommandations
*2 à 4 heures*

**ResultActivity — afficher le résultat complet :**

Éléments à afficher :
- Photo capturée en haut (en grand)
- Nom de la maladie en français en gras
- Badge de sévérité coloré (vert/jaune/orange/rouge)
- Pourcentage de confiance du modèle
- Section recommandation de traitement IRAD

**Recommandations statiques par maladie :**

CMD — Mosaïque du Manioc :
- Sévérité ÉLEVÉE (>90%) : Arracher et brûler les plants immédiatement. Traiter contre les mouches blanches avec Confidor. Replanter avec variété résistante IRAD 8034.
- Sévérité MODÉRÉE (70-90%) : Surveiller de près. Traiter préventivement contre les mouches blanches. Éviter de prélever des boutures sur ces plants.
- Sévérité FAIBLE (50-70%) : Continuer surveillance. Prendre note de la localisation.

CBSD — Stries Brunes :
- Tout niveau : Pas de traitement curatif connu. Destruction obligatoire des plants infectés. Replanter uniquement avec boutures certifiées saines. Contacter IRAD au numéro [à remplir].

CBB — Bactériose :
- Traitement au cuivre (Bouillie bordelaise 1%). Désinfecter les outils entre chaque plant. Éviter les blessures sur les tiges.

CGM — Green Mottle :
- Surveillance renforcée. Moins destructeur que CMD. Éviter le stress hydrique qui aggrave les symptômes.

HEALTHY — Plante Saine :
- Aucune action requise. Surveillance hebdomadaire recommandée.

**Calcul sévérité depuis score de confiance :**
- Score > 90% → ÉLEVÉE → rouge
- Score 70-90% → MODÉRÉE → orange
- Score 50-70% → FAIBLE → jaune
- Classe Healthy → SAIN → vert

---

### Jour 24 (31 Mars) — GPS + Sauvegarde locale
*2 à 4 heures*

**Récupérer la position GPS :**
- Utiliser FusedLocationProviderClient de Google Play Services
- Récupérer la dernière position connue (getLastLocation)
- Si position indisponible : demander une mise à jour unique
- Timeout de 10 secondes si GPS ne répond pas : continuer sans GPS

**Sauvegarde locale avec Room Database :**
- Créer une entité Detection avec : id, disease, confidence, severity, latitude, longitude, timestamp, photoPath
- Créer un DetectionDao avec : insertDetection, getLastTenDetections
- Sauvegarder chaque détection localement même sans internet
- L'historique local fonctionne 100% offline

---

### Jour 25 (1 Avril) — Firebase configuration + connexion
*3 à 5 heures*

**Connecter l'app Android à Firebase :**
- Télécharger google-services.json depuis la console Firebase
- Placer ce fichier dans app/
- Ajouter le plugin Google Services dans build.gradle

**Firebase Authentication — connexion par numéro de téléphone :**
- L'agriculteur entre son numéro de téléphone camerounais
- Firebase envoie un SMS avec un code de vérification
- L'utilisateur entre le code → il est connecté
- Son userId est généré et stocké localement

**Firebase Firestore — structure des données :**

Collection "detections" :
- Document avec userId, disease, confidence, severity, latitude, longitude, timestamp, regionCode

Collection "users" :
- Document avec userId, phone, latitude, longitude, lastSeen, notificationsEnabled

---

### Jour 26 (2 Avril) — Alertes Firebase aux voisins
*3 à 5 heures*

**Logique d'alerte géographique :**

Quand une détection est sauvegardée sur Firebase :
1. Lire tous les utilisateurs dans un rayon de 10 km
2. Calculer la distance avec la formule Haversine (distance entre deux points GPS)
3. Filtrer ceux qui ont notificationsEnabled = true
4. Envoyer une notification Firebase Cloud Messaging à chacun

**Format de la notification :**
- Titre : "⚠️ Alerte AgroNet — [Nom maladie]"
- Corps : "[Maladie] détectée à [distance] km de votre champ. Inspectez vos plants."
- Action au clic : ouvrir l'app sur un écran carte

**Gérer la réception de la notification :**
- Créer un FirebaseMessagingService
- Afficher la notification dans la barre de statut
- Stocker l'alerte localement pour consultation offline

---

### Jour 27-28 (3-4 Avril) — Tests intégration complète
*4 à 8 heures*

**Tests end-to-end sur téléphone réel :**

Scénario 1 — Détection offline complète :
- Désactiver le wifi et les données mobiles
- Ouvrir l'app → prendre photo feuille → vérifier résultat
- Vérifier que la détection est sauvegardée localement
- Vérifier que l'app ne plante pas sans internet

Scénario 2 — Alerte avec internet :
- Activer le wifi
- Détecter une maladie → vérifier que Firebase reçoit la détection
- Vérifier que la notification est envoyée (tester avec un second compte)

Scénario 3 — Image floue :
- Présenter une image floue à la caméra
- Vérifier que l'indicateur rouge s'affiche
- Vérifier que la précision est meilleure avec image nette

Scénario 4 — Mesures de performance :
- Chronométrer le temps entre la prise de photo et l'affichage du résultat
- Mesurer sur 20 photos différentes
- Calculer la latence moyenne — doit être sous 200ms

**Bugs à corriger immédiatement cette semaine :**
- Crash si GPS désactivé
- Crash si pas de connexion Firebase au démarrage
- Lenteur si image très haute résolution (ajouter compression)

**Fin semaine 4 — checkpoint :**
- Détection offline complète et fonctionnelle
- Firebase connecté et alerte voisins opérationnelle
- GPS et sauvegarde locale fonctionnels
- Latence mesurée et documentée

---

## 📅 SEMAINE 5 — INTERFACE + HISTORIQUE + TESTS COMPLETS
**5 au 11 Avril — 14 à 28 heures**

---

### Jour 29 (5 Avril) — Écran historique + carte simple
*2 à 4 heures*

**HistoryActivity :**
- Liste des 10 dernières détections sauvegardées localement
- Chaque élément affiche : date, maladie, sévérité (badge coloré), ville approximative
- Clic sur un élément → afficher le détail complet
- Fonctionne 100% offline

**Carte simplifiée des alertes :**
- Utiliser Google Maps SDK Android (gratuit jusqu'à 28 000 chargements/mois)
- Afficher les détections récentes dans un rayon de 20 km comme marqueurs colorés
- Marqueur rouge = maladie grave, orange = modérée, vert = saine
- Cette carte nécessite internet — indiquer "Carte indisponible offline"

---

### Jour 30 (6 Avril) — Interface utilisateur finale
*3 à 5 heures*

**Design de l'interface — couleurs AgroNet :**
- Couleur principale : vert forêt #1B4332
- Couleur accent : vert menthe #40916C
- Fond clair : #F8F9F0
- Danger : rouge #DC2626
- Alerte : orange #F59E0B

**Améliorer chaque écran :**

SplashActivity :
- Logo AgroNet centré
- Sous-titre : "Diagnostic manioc offline · Cameroun"
- Durée : 2 secondes puis navigation automatique

MainActivity (caméra) :
- Overlay de guide de cadrage sur la prévisualisation
- Indicateur de netteté animé
- Bouton capture avec feedback visuel au clic
- Barre de navigation en bas : Analyser / Historique / Paramètres

ResultActivity :
- Photo capturée en haut (40% de l'écran)
- Résultat clair avec icône maladie
- Badge sévérité avec animation d'apparition
- Recommandations dans un scroll expandable
- Bouton "Nouvelle analyse" et "Voir sur la carte"

---

### Jour 31 (7 Avril) — Paramètres + Gestion offline
*2 à 4 heures*

**Écran Paramètres :**
- Numéro de téléphone (authentification Firebase)
- Rayon d'alerte configurable : 5 km / 10 km / 20 km
- Activer/désactiver les notifications
- Langue : Français (seule langue pour V1)
- Version de l'app et crédits

**Gestion robuste offline :**
- File d'attente de synchronisation : si l'app est offline, stocker les détections à envoyer
- Quand le réseau revient, envoyer automatiquement la file d'attente
- Indicateur visuel du statut de connexion dans l'app

---

### Jours 32-33 (8-9 Avril) — Tests utilisateurs simulés
*4 à 8 heures*

**Faire tester l'app par Thayana et d'autres personnes disponibles :**

Questions à observer (sans aider l'utilisateur) :
- Est-ce qu'il comprend seul comment prendre une photo ?
- Est-ce qu'il comprend l'indicateur de netteté ?
- Est-ce qu'il comprend le résultat affiché ?
- Est-ce qu'il comprend la recommandation de traitement ?
- Est-ce qu'il sait où trouver son historique ?

**Bugs probables à cette étape :**
- L'app plante sur certains modèles de téléphone (fragmentation Android)
- La caméra arrière n'est pas sélectionnée par défaut
- Le texte est trop petit sur certains écrans

**Corrections prioritaires :**
- Corriger tous les crashs en premier
- Améliorer la lisibilité du texte de résultat
- S'assurer que le bouton capture est facilement accessible

---

### Jours 34-35 (10-11 Avril) — Mesures finales + Rapport technique
*4 à 8 heures*

**Mesures de performance à documenter pour le rapport :**

Sur le téléphone de test :
- Latence moyenne de détection sur 50 photos (ms)
- Taille APK de l'application installée (MB)
- Taille du modèle .tflite embarqué (MB)
- Consommation batterie sur 30 détections consécutives
- Consommation mémoire RAM pendant l'utilisation

**Tableau de comparaison final pour la soutenance :**

| Critère | YOLO-AgriNet IUC | AgroNet V1 |
|---|---|---|
| Culture principale | Multi (pas manioc) | Manioc uniquement |
| Dataset | PlantDoc 2 569 img | Cassava 21 439 img |
| Accuracy | mAP@0.5 84.5% | Accuracy [à remplir]% |
| Taille modèle | 14.2 MB | [à remplir] MB |
| Latence Android | 32 FPS | [à remplir] ms |
| Matériel requis | GPU GTX 1660 Ti + Drone | Téléphone 4Go RAM |
| Fonctionne offline | Partiel | 100% |
| Alerte communauté GPS | Non | Oui |
| Recommandation traitement | Non | Oui (IRAD) |
| Coût déploiement | Drone 500k+ FCFA | Téléphone 45k FCFA |

**Rédiger les sections manquantes du rapport :**
- Section Résultats et Discussion avec toutes les métriques
- Section Comparaison avec l'existant (tableau ci-dessus)
- Section Limites et perspectives
- Vérifier que chaque affirmation a une source citée

**Fin semaine 5 — checkpoint :**
- App V1 complète et fonctionnelle
- Tous les bugs critiques corrigés
- Rapport scientifique quasi-complet
- Tableau comparatif avec YOLO-AgriNet finalisé

---

## 📅 SEMAINE 6 — FINITIONS + SOUTENANCE
**12 au 18 Avril — 14 à 28 heures**

---

### Jour 36 (12 Avril) — Révision rapport complet
*3 à 5 heures*

**Checklist rapport final :**
- Chaque statistique camerounaise a une source officielle (IRAD, MINADER, FAO)
- Aucune affirmation sans référence
- Les métriques du modèle sont réelles et vérifiables
- Les 5 diagrammes UML sont dans le rapport en bonne résolution
- La matrice de confusion est incluse
- Les courbes d'apprentissage sont incluses
- La comparaison avec YOLO-AgriNet est honnête et précise
- Les limites sont clairement énoncées
- Les 6 articles sont correctement cités avec DOI

**Vérifier la justification des textes dans le rapport**
Word : sélectionner tout → Format → Paragraphe → Justifié

---

### Jour 37 (13 Avril) — Préparation démo application
*2 à 4 heures*

**Préparer la démonstration pour la soutenance :**

Scénario de démo en 3 minutes :
1. Ouvrir l'app : montrer l'écran d'accueil (30 secondes)
2. Pointer la caméra vers une feuille imprimée ou une vraie feuille : montrer l'indicateur de netteté (30 secondes)
3. Prendre la photo : montrer la détection en temps réel (30 secondes)
4. Commenter le résultat : maladie + sévérité + recommandation IRAD (30 secondes)
5. Montrer l'historique des détections (30 secondes)
6. Montrer la carte avec les alertes GPS (30 secondes)

**Préparer des photos de test :**
- Imprimer ou avoir sur un second téléphone des photos claires de feuilles malades
- CMD avec taches jaunes visibles
- CBSD avec lésions brunes
- Plante saine

**Important :** tester la démo complète 3 fois de suite sans erreur avant la soutenance

---

### Jour 38 (14 Avril) — Slides finales + notes
*2 à 4 heures*

**Mettre à jour les slides PPTX avec :**
- Métriques réelles du modèle entraîné
- Captures d'écran réelles de l'application
- Tableau comparatif final avec YOLO-AgriNet
- Diagrammes UML en haute résolution

**Compléter les speaker notes avec :**
- Les vrais chiffres obtenus
- Réponses aux nouvelles questions probables sur YOLO-AgriNet
- Réponse sur le drone (argument des 92% exploitations — à vérifier MINADER)

---

### Jours 39-40 (15-16 Avril) — Répétitions complètes
*4 à 8 heures*

**Répétition 1 (15 Avril) — seul :**
- Chronométrer chaque section
- Vérifier que la démo app fonctionne dans les conditions de la salle
- Identifier les passages qui semblent hésitants

**Répétition 2 (16 Avril) — avec Thayana :**
- Présentation complète devant elle
- Elle pose les questions du guide de révision
- Corriger les réponses imprécises

---

### Jour 41 (17 Avril) — Jour avant soutenance
*2 heures maximum — pas plus*

**Checklist finale :**
- APK de l'app installé et testé sur le téléphone de démo
- Slides sur clé USB ET en ligne (Google Drive)
- Rapport imprimé en 3 exemplaires si requis
- Photos de test imprimées ou disponibles sur second téléphone
- Chargeur téléphone prévu
- Câble USB prévu si connexion Android Studio nécessaire

**Ce qu'il ne faut PAS faire ce jour :**
- Modifier le code de l'app
- Réécrire des sections du rapport
- Apprendre de nouveaux concepts

**Ce qu'il faut faire :**
- Relire les notes de présentation une fois
- Dormir tôt

---

## 🔑 RÉSUMÉ DES DÉCISIONS TECHNIQUES

| Décision | Choix | Raison |
|---|---|---|
| ML framework | TFLite MobileNetV3-Large | Léger, validé Android 95%, 5.4M params |
| Dataset principal | Cassava Leaf Disease Kaggle | 21 439 images terrain Afrique, CC0 |
| Entraînement | Google Colab GPU T4 | Gratuit, GPU puissant, Python |
| Prétraitement | Roboflow | Augmentation automatique, export TFRecord |
| App mobile | Android natif Kotlin | Performance native, intégration TFLite |
| Backend cloud | Firebase uniquement | Gratuit, zéro serveur, suffisant V1 |
| Notifications | Firebase Cloud Messaging | Géographique, gratuit, fiable |
| Stockage local | Room Database | Offline-first, SQL Android natif |
| Caméra | CameraX | API officielle Google, simple |
| UML | draw.io | Gratuit, en ligne, export PNG |
| FastAPI | Reporté V2 | Pas nécessaire pour V1, gain de temps |
| Apprentissage fédéré | Reporté V2 | Requiert FastAPI, 2 mois supplémentaires |

---

## ⚠️ RISQUES ET SOLUTIONS

| Risque | Probabilité | Solution |
|---|---|---|
| Accuracy modèle < 90% | Moyenne | Augmenter epochs, ajuster fine-tuning |
| App plante sur téléphone test | Haute | Tester tôt en semaine 3 pour avoir du temps |
| Pas de téléphone Android | Haute | Organiser planning avec Thayana dès maintenant |
| Firebase quota dépassé | Faible | Plan gratuit = 1Go storage, 50k lectures/jour |
| Latence > 200ms | Faible | TFLite INT8 est largement sous 200ms sur 4Go RAM |
| Jury demande FastAPI | Possible | Répondre : "Architecture prévue V2, Firebase suffisant V1" |
| Jury compare avec YOLO-AgriNet | Certain | Tableau comparatif prêt avec nos avantages clairs |

---

*Roadmap créé le 8 Mars 2026 — Présentation le 18 Avril 2026*
*AgroNet V1 : IA accessible · Agriculteur autonome · Cameroun connecté*
