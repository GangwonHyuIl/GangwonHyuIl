import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.devtools.ksp)
    alias(libs.plugins.google.dagger.hilt.android)
}

android {
    namespace = "com.gangwonhyuil.gangwonhyuil"
    compileSdk = 34

    lateinit var weatherApiKey: String
    lateinit var tourApiKey: String
    lateinit var kakoLocalApiKey: String
    lateinit var supabaseApiKey: String
    lateinit var geminiApiKey: String

    if (System.getenv("CI") == "true") {
        weatherApiKey = System.getenv("WEATHER_API_KEY")
            ?: throw GradleException("WEATHER_API_KEY is not set in CI environment")
        tourApiKey = System.getenv("TOUR_API_KEY")
            ?: throw GradleException("TOUR_API_KEY is not set in CI environment")
        kakoLocalApiKey = System.getenv("KAKAO_LOCAL_API_KEY")
            ?: throw GradleException("KAKAO_LOCAL_API_KEY is not set in CI environment")
        supabaseApiKey = System.getenv("SUPABASE_API_KEY")
            ?: throw GradleException("SUPABASE_API_KEY is not set in CI environment")
        geminiApiKey = System.getenv("GEMINI_API_KEY")
            ?: throw GradleException("GEMINI_API_KEY is not set in CI environment")

    } else {
        val properties = Properties()
        val localPropertiesFile = project.rootProject.file("local.properties")
        if (localPropertiesFile.exists()) {
            properties.load(localPropertiesFile.inputStream())

            weatherApiKey = properties.getProperty("WEATHER_API_KEY")
                ?: throw GradleException("WEATHER_API_KEY is not set in local.properties")
            tourApiKey = properties.getProperty("TOUR_API_KEY")
                ?: throw GradleException("TOUR_API_KEY is not set in local.properties")
            kakoLocalApiKey = properties.getProperty("KAKAO_LOCAL_API_KEY")
                ?: throw GradleException("KAKAO_LOCAL_API_KEY is not set in local.properties")
            supabaseApiKey = properties.getProperty("SUPABASE_API_KEY")
                ?: throw GradleException("SUPABASE_API_KEY is not set in local.properties")
            geminiApiKey = properties.getProperty("GEMINI_API_KEY")
                ?: throw GradleException("GEMINI_API_KEY is not set in local.properties")
        } else {
            throw GradleException("local.properties file not found")
        }
    }

    defaultConfig {
        applicationId = "com.gangwonhyuil.gangwonhyuil"
        minSdk = 27
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "WEATHER_API_KEY", "$weatherApiKey")
        buildConfigField("String", "TOUR_API_KEY", "$tourApiKey")
        buildConfigField("String", "KAKAO_LOCAL_API_KEY", "$kakoLocalApiKey")
        buildConfigField("String", "SUPABASE_API_KEY", "$supabaseApiKey")
        buildConfigField("String", "GEMINI_API_KEY", "$geminiApiKey")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.identity.credential.android)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    implementation(libs.timber)

    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    implementation(libs.coil)

    implementation(libs.gson)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)

    implementation(libs.v2.user)

    implementation(libs.generativeai)

    implementation(libs.material)

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}