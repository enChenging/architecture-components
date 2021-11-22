/**
 * 插件管理
 *
 * @author yancheng
 * @since 2021/11/15
 */
object GradlePluginId {
    const val AGP = "com.android.tools.build:gradle:${Versions.AGP}"
    const val KOTLIN_PLUGIN = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.Kotlin}"
    const val HILT_PLUGIN = "com.google.dagger:hilt-android-gradle-plugin:${Versions.Hilt}"
    const val AROUTER_REGISTER = "com.alibaba:arouter-register:${Versions.AROUTER_REGISTER}"

    const val ANDROID_APPLICATION = "com.android.application"
    const val ANDROID_LIBRARY = "com.android.library"
    const val KOTLIN_ANDROID = "org.jetbrains.kotlin.android"
    const val KOTLIN_KAPT = "org.jetbrains.kotlin.kapt"
    const val AROUTER = "com.alibaba.arouter"
    const val HILT = "dagger.hilt.android.plugin"
}