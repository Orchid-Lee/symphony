plugins {
    alias(libs.plugins.android.app)
    alias(libs.plugins.android.kotlin)
    id("kotlin-parcelize")
    id("androidx.room")
}

android {
    namespace = "io.github.zyrouge.symphony"
    compileSdk = libs.versions.compile.sdk.get().toInt()

    defaultConfig {
        //唯一標識
        applicationId = "io.github.zyrouge.symphony"
        minSdk = libs.versions.min.sdk.get().toInt()
        targetSdk = libs.versions.target.sdk.get().toInt()

        versionCode = 110
        versionName = "2024.3.110"
        versionName = System.getenv("APP_VERSION_NAME") ?: versionName

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    signingConfigs {
        register("release") {
            val storeFileEnv = System.getenv("SIGNING_KEYSTORE_FILE")
            storeFile = if (null != storeFileEnv) rootProject.file(storeFileEnv) else null
            storePassword = System.getenv("SIGNING_KEYSTORE_PASSWORD")
            keyAlias = System.getenv("SIGNING_KEY_ALIAS")
            keyPassword = System.getenv("SIGNING_KEY_PASSWORD")
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.findByName("release")
        }
        getByName("debug") {
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
        freeCompilerArgs += "-opt-in=kotlin.RequiresOptIn"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
    packaging {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }

    room {
        schemaDirectory("$projectDir/schemas")
    }
}

dependencies {
    implementation(libs.activity.compose)
    implementation(libs.coil)
    implementation(libs.compose.material.icons.extended)
    implementation(libs.compose.material3)
    implementation(libs.compose.navigation)
    implementation(libs.core)
    implementation(libs.core.splashscreen)
    implementation(libs.jaudiotagger)
    implementation(libs.lifecycle.runtime)
    implementation(libs.media)
    implementation(libs.okhttp3)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling.preview)
    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.logging.interceptor)
    implementation(libs.converter.gson)
    implementation(libs.media3.common)
    debugImplementation(libs.compose.ui.tooling)
    implementation(libs.room.runtime)
    // Kotlin
    implementation(libs.preference.ktx)
    annotationProcessor(libs.room.compiler)
    debugImplementation(libs.compose.ui.test.manifest)
    //
    debugImplementation(libs.library)
    releaseImplementation(libs.library.no.op)
    testImplementation(libs.junit)
}
