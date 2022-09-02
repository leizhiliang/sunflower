//整个项目的构建脚本
buildscript {

    //依赖的仓库
    repositories {
        google()
        mavenCentral()
    }

    //项目依赖的插件
    dependencies {
        classpath(libs.android.gradle.plugin)
        classpath(libs.kotlin.gradle.plugin)
        classpath(libs.androidx.navigation.safe.args.gradle.plugin)
        classpath(libs.hilt.android.gradle.plugin)
    }
}

plugins {
    id("com.diffplug.spotless") version "6.4.1"
}

spotless {
    kotlin {
        target("**/*.kt")
        ktlint(libs.versions.ktlint.get()).userData(mapOf("max_line_length" to "100"))
    }
}
