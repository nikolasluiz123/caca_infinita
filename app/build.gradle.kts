plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "br.com.schmittsolucoes.cacasobmedida"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "br.com.schmittsolucoes.cacasobmedida"
        minSdk = 29
        targetSdk = 37
        versionCode = 1
        versionName = "0.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}

dependencies {
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.material)
}