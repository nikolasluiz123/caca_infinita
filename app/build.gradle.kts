import com.google.firebase.crashlytics.buildtools.gradle.CrashlyticsExtension
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlinAndroidKsp)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.hiltAndroid)
    alias(libs.plugins.google.services)
    alias(libs.plugins.crashlytics)
}

android {
    namespace = "br.com.schmittsolucoes.cacainfinita"
    compileSdk {
        version = release(37)
    }

    val versionPropsFile = rootProject.file("version.properties")
    val versionProps = Properties()

    if (versionPropsFile.exists()) {
        versionProps.load(versionPropsFile.inputStream())
    }

    val localProps = Properties().apply {
        val localPropsFile = rootProject.file("local.properties")

        if (localPropsFile.exists()) {
            load(localPropsFile.inputStream())
        }
    }

    signingConfigs {
        create("release") {
            storeFile = file(
                localProps.getProperty("KEYSTORE_PATH")
                    ?: project.findProperty("KEYSTORE_PATH")?.toString()
                    ?: System.getenv("KEYSTORE_PATH")
                    ?: "keystore.jks"
            )

            storePassword = localProps.getProperty("KEYSTORE_PASSWORD")
                ?: project.findProperty("KEYSTORE_PASSWORD")?.toString()
                ?: System.getenv("KEYSTORE_PASSWORD")

            keyAlias = localProps.getProperty("KEY_ALIAS")
                ?: project.findProperty("KEY_ALIAS")?.toString()
                ?: System.getenv("KEY_ALIAS")

            keyPassword = localProps.getProperty("KEY_PASSWORD")
                ?: project.findProperty("KEY_PASSWORD")?.toString()
                ?: System.getenv("KEY_PASSWORD")
        }
    }

    defaultConfig {
        applicationId = "br.com.schmittsolucoes.cacainfinita"
        minSdk = 29
        targetSdk = 37
        versionCode = versionProps.getProperty("versionCode")?.toInt()!!
        versionName = versionProps.getProperty("versionName")!!

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    flavorDimensions += "environment"

    productFlavors {
        create("dev") {
            dimension = "environment"
        }
        create("prod") {
            dimension = "environment"
        }
    }

    buildTypes {
        debug {

        }

        release {
            isMinifyEnabled = true
            isShrinkResources = true
            ndk.debugSymbolLevel = "FULL"
            signingConfig = signingConfigs.getByName("release")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            configure<CrashlyticsExtension> {
                mappingFileUploadEnabled = true
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    buildFeatures {
        compose = true
    }
    packaging {
        resources {
            merges += "META-INF/services/org.apache.lucene.codecs.Codec"
            merges += "META-INF/services/org.apache.lucene.codecs.PostingsFormat"
            merges += "META-INF/services/org.apache.lucene.codecs.DocValuesFormat"
            merges += "META-INF/services/org.apache.lucene.analysis.Analyzer"
        }
    }
}

ksp {
    arg("room.schemaLocation", "${project.projectDir}/schemas")
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material3.window.size)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.paging.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.material)

    implementation(libs.room.ktx)
    implementation(libs.room.runtime)
    implementation(libs.room.paging)
    ksp(libs.room.compiler)

    implementation(libs.mlkit.text.recognition)
    implementation(libs.mlkit.language.id)

    implementation(libs.androidx.camera.core)
    implementation(libs.androidx.camera.camera2)
    implementation(libs.androidx.camera.lifecycle)
    implementation(libs.androidx.camera.view)

    implementation(libs.hilt.navigation.compose)
    implementation(libs.hilt.android)
    implementation(libs.kotlinx.serialization.json)
    ksp(libs.hilt.android.compiler)

    implementation(libs.pdf.box.android)
    implementation(libs.lucene.analysis.common)

    implementation(libs.androidx.concurrent.futures.ktx)
    implementation(libs.kotlinx.coroutines.play.services)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)
    implementation(libs.play.services.games.v2)
    implementation(libs.review.ktx)

    debugImplementation(libs.androidx.compose.ui.test.manifest)
}