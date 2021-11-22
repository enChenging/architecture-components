// Top-level build file where you can add configuration options common to all sub-projects/modules.
/**
 * 项目顶级的构建脚本
 */
buildscript {

    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(GradlePluginId.AGP)
        classpath(GradlePluginId.KOTLIN_PLUGIN)
        classpath(GradlePluginId.HILT_PLUGIN)
        classpath(GradlePluginId.AROUTER_REGISTER)
    }
}

allprojects {
    repositories {
        jcenter()
        google()
        mavenCentral()
        maven(url = "https://www.jitpack.io")
    }
}


tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}