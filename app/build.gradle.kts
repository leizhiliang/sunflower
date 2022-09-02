plugins {
  id("com.android.application")
  id("kotlin-android")
  id("kotlin-parcelize")
  id("kotlin-kapt")
  id("dagger.hilt.android.plugin")
  id("androidx.navigation.safeargs.kotlin")
}

android {

  compileSdk = libs.versions.compileSdk.get().toInt()

  buildFeatures {
    dataBinding = true
  }

  defaultConfig {
    applicationId = "com.google.samples.apps.sunflower"
    minSdk = libs.versions.minSdk.get().toInt()
    targetSdk = libs.versions.targetSdk.get().toInt()
    testInstrumentationRunner = "com.google.samples.apps.sunflower.utilities.MainTestRunner"
    versionCode = 1
    versionName = "0.1.6"
    vectorDrawables.useSupportLibrary = true

    // Consult the README on instructions for setting up Unsplash API key
    //有关设置 Unsplash API 密钥的说明，请参阅自述文件
    buildConfigField("String", "UNSPLASH_ACCESS_KEY", "\"" + getUnsplashAccess() + "\"")

    javaCompileOptions {
      annotationProcessorOptions {
        arguments["dagger.hilt.disableModulesHaveInstallInCheck"] = "true"
      }
    }

  }

  buildTypes {

    release {
      isMinifyEnabled = true
      proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
    }

    create("benchmark") {
      initWith(getByName("release"))
      signingConfig = signingConfigs.getByName("debug")
      isDebuggable = false
      proguardFiles(
        getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules-benchmark.pro"
      )
    }

  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }


  kotlinOptions {
    // work-runtime-ktx 2.1.0 and above now requires Java 8
    jvmTarget = "1.8"

    // Enable Coroutines and Flow APIs
    freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi"
    freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlinx.coroutines.FlowPreview"
  }

  buildFeatures {
    compose = true
    dataBinding = true
  }

  composeOptions {
    kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
  }

  packagingOptions {
    // Multiple dependency bring these files in. Exclude them to enable
    // our test APK to build (has no effect on our AARs)
    //多个依赖项引入这些文件。排除它们以启用我们的测试 APK 构建（对我们的 AAR 没有影响）
    resources.excludes += "/META-INF/AL2.0"
    resources.excludes += "/META-INF/LGPL2.1"
  }

}

dependencies {

  kapt(libs.androidx.room.compiler)
  kapt(libs.hilt.android.compiler)

  implementation(libs.androidx.appcompat)
  implementation(libs.androidx.constraintlayout)
  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.fragment.ktx)
  implementation(libs.androidx.lifecycle.livedata.ktx)
  implementation(libs.androidx.lifecycle.viewmodel.ktx)
  implementation(libs.androidx.navigation.fragment.ktx)
  implementation(libs.androidx.navigation.ui.ktx)
  implementation(libs.androidx.paging.runtime.ktx)
  implementation(libs.androidx.room.ktx)
  implementation(libs.androidx.viewpager2)
  implementation(libs.androidx.work.runtime.ktx)
  implementation(libs.material)
  implementation(libs.gson)
  implementation(libs.okhttp3.logging.interceptor)
  implementation(libs.retrofit2.converter.gson)
  implementation(libs.retrofit2)
  implementation(libs.kotlinx.coroutines.android)
  implementation(libs.kotlinx.coroutines.core)
  implementation(libs.hilt.android)
  implementation(libs.androidx.profileinstaller)
  implementation(libs.androidx.tracing.ktx)

  // Compose
  implementation(libs.androidx.activity.compose)
  implementation(libs.androidx.constraintlayout.compose)
  implementation(libs.androidx.compose.runtime)
  implementation(libs.androidx.compose.ui)
  implementation(libs.androidx.compose.foundation)
  implementation(libs.androidx.compose.foundation.layout)
  implementation(libs.androidx.compose.material)
  implementation(libs.androidx.compose.ui.viewbinding)
  implementation(libs.androidx.compose.ui.tooling.preview)
  implementation(libs.androidx.compose.runtime.livedata)
  implementation(libs.androidx.lifecycle.viewmodel.compose)
  implementation(libs.material.compose.theme.adapter)
  implementation(libs.coil.compose)
  debugImplementation(libs.androidx.compose.ui.tooling)

  // Testing dependencies
  kaptAndroidTest(libs.hilt.android.compiler)
  androidTestImplementation(libs.androidx.arch.core.testing)
  androidTestImplementation(libs.androidx.espresso.contrib)
  androidTestImplementation(libs.androidx.espresso.core)
  androidTestImplementation(libs.androidx.espresso.intents)
  androidTestImplementation(libs.androidx.test.ext.junit)
  androidTestImplementation(libs.androidx.test.uiautomator)
  androidTestImplementation(libs.androidx.work.testing)
  androidTestImplementation(libs.truth)
  androidTestImplementation(libs.androidx.compose.ui.test.junit4)
  androidTestImplementation(libs.hilt.android.testing)
  androidTestImplementation(libs.accessibility.test.framework)
  androidTestImplementation(libs.kotlinx.coroutines.test)
  testImplementation(libs.junit)

  implementation("com.tencent:mmkv-static:1.2.10")

}

fun getUnsplashAccess(): String? {
  return project.findProperty("unsplash_access_key") as? String
}
