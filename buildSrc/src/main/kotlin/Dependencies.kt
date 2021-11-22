/**
 * 依赖库版本
 *
 * @author yancheng
 * @since 2021/11/15
 */
object Versions {

    //Plugin----------------------------------------------------------------
    const val AGP = "7.0.2"
    const val AROUTER_REGISTER = "1.0.2"

    // Android--------------------------------------------------------------
    const val AppCompat = "1.3.1"
    const val CoreKtx = "1.6.0"
    const val ActivityKtx = "1.1.0"
    const val FragmentKtx = "1.2.5"
    const val MultiDex = "2.0.1"
    const val ConstraintLayout = "2.0.1"              // 约束布局
    const val MaterialDesign = "1.3.0"                // 材料设计UI套件

    // Test-----------------------------------------------------------------
    const val Junit = "4.13.2"
    const val TestExtJunit = "1.1.2"
    const val TestEspresso = "3.3.0"

    // Kotlin---------------------------------------------------------------
    const val Kotlin = "1.5.31"                       // Kotlin
    const val Coroutines = "1.5.0"                    // 协程

    // JetPack--------------------------------------------------------------
    const val Lifecycle = "2.3.1"                     // 生命周期系列
    const val Hilt = "2.39.1"                         // DI框架-Hilt
    const val HiltAndroidx = "1.0.0"                  // DI框架-Hilt
    const val Palette = "1.0.0"                       // 调色板
    const val Room = "2.3.0"                          // 数据库

    // Network--------------------------------------------------------------
    const val OkHttp = "4.9.0"                        // OkHttp
    const val OkHttpInterceptorLogging = "4.9.1"      // OkHttp 请求日志拦截器
    const val Retrofit = "2.9.0"                      // Retrofit
    const val Gson = "2.8.7"                          // Gson
    const val RetrofitConverterGson = "2.9.0"         // Retrofit Gson 转换器

    // UI-------------------------------------------------------------------
    const val AutoSize = "v1.2.1"                     // 屏幕适配框架
    const val RecyclerViewAdapter = "3.0.4"           // RecyclerView 适配器框架
    const val Glide = "4.11.0"                        // Glide图片加载框架
    const val PictureSelector = "v2.7.2"              // 照片选择器
    const val CZXing = "1.0.17"                       // 扫码器
    const val RefreshClassics = "2.0.3"               // 经典刷新头/经典加载

    // 其他-----------------------------------------------------------------
    const val MMKV = "1.2.9"                          // 腾讯 MMKV 持久化存储
    const val ARoute = "1.5.2"                        // 阿里路由
    const val EventBus = "3.2.0"                      // 事件总线
    const val PermissionX = "1.6.1"                   // 权限申请
    const val AutoService = "1.0"                     // 自动生成SPI暴露服务文件
    const val Logger = "2.2.0"                        // 日志框架

    // 第三方SDK-------------------------------------------------------------
    const val TencentBugly = "3.4.4"                  // 腾讯 Bugly 异常上报
    const val TencentTBSX5 = "44085"                  // 腾讯 TBS X5内核
    const val JiPush = "4.3.0"                        // 极光推送
    const val JiCore = "2.9.0"                        // 极光推送
    const val Fastjson = "1.2.66"                     // Fastjson
}

/***************************************依赖库地址***************************************************/

/**
 * Android 核心相关
 */
object AndroidLibs {
    const val MaterialDesign =
        "com.google.android.material:material:${Versions.MaterialDesign}"
    const val AppCompat = "androidx.appcompat:appcompat:${Versions.AppCompat}"
    const val CoreKtx = "androidx.core:core-ktx:${Versions.CoreKtx}"
    const val ConstraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.ConstraintLayout}"
    const val ActivityKtx = "androidx.activity:activity-ktx:${Versions.ActivityKtx}"
    const val FragmentKtx = "androidx.fragment:fragment-ktx:${Versions.FragmentKtx}"
    const val MultiDex = "androidx.multidex:multidex:${Versions.MultiDex}"
}

/**
 * 测试相关
 */
object TestLibs {
    const val Junit = "junit:junit:${Versions.Junit}"
    const val AndroidJUnitRunner = "androidx.test.runner.AndroidJUnitRunner"
    const val TestExtJunit = "androidx.test.ext:junit:${Versions.TestExtJunit}"
    const val TestEspresso = "androidx.test.espresso:espresso-core:${Versions.TestEspresso}"
}

/**
 * Jetpack 组件
 */
object JetPackLibs {
    const val ViewModel =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.Lifecycle}"
    const val ViewModelSavedState =
        "androidx.lifecycle:lifecycle-viewmodel-savedstate:${Versions.Lifecycle}"
    const val LiveData =
        "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.Lifecycle}"
    const val Lifecycle =
        "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.Lifecycle}"
    const val LifecycleService =
        "androidx.lifecycle:lifecycle-service:${Versions.Lifecycle}"
    const val LifecycleCompilerAPT =
        "androidx.lifecycle:lifecycle-compiler:${Versions.Lifecycle}"
    const val HiltCore = "com.google.dagger:hilt-android:${Versions.Hilt}"
    const val HiltApt = "com.google.dagger:hilt-compiler:${Versions.Hilt}"
    const val HiltAndroidx = "androidx.hilt:hilt-compiler:${Versions.HiltAndroidx}"
    const val Palette = "androidx.palette:palette:${Versions.Palette}"
    const val Room = "androidx.room:room-runtime:${Versions.Room}"
    const val RoomCompiler = "androidx.room:room-compiler:${Versions.Room}"
    const val RoomCoroutineKtx = "androidx.room:room-ktx:${Versions.Room}"
}

/**
 * Kotlin 语言相关
 */
object KotlinLibs {
    const val Kotlin = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.Kotlin}"
    const val CoroutinesCore =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.Coroutines}"
    const val CoroutinesAndroid =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.Coroutines}"
}

/**
 * 网络相关
 */
object NetworkLibs {
    const val OkHttp = "com.squareup.okhttp3:okhttp:${Versions.OkHttp}"
    const val OkHttpInterceptorLogging =
        "com.squareup.okhttp3:logging-interceptor:${Versions.OkHttpInterceptorLogging}"
    const val Retrofit = "com.squareup.retrofit2:retrofit:${Versions.Retrofit}"
    const val RetrofitConverterGson =
        "com.squareup.retrofit2:converter-gson:${Versions.RetrofitConverterGson}"
    const val Gson = "com.google.code.gson:gson:${Versions.Gson}"
}

/**
 * UI 界面相关
 */
object UILibs {
    const val AutoSize =
        "com.github.JessYanCoding:AndroidAutoSize:${Versions.AutoSize}"
    const val RecyclerViewAdapter =
        "com.github.CymChad:BaseRecyclerViewAdapterHelper:${Versions.RecyclerViewAdapter}"
    const val Glide = "com.github.bumptech.glide:glide:${Versions.Glide}"
    const val GlideCompiler = "com.github.bumptech.glide:compiler:${Versions.Glide}"
    const val CZXing = "me.devilsen:czxing:${Versions.CZXing}"
    const val PictureSelector =
        "io.github.lucksiege:pictureselector:${Versions.PictureSelector}"
    const val RefreshKernel = "com.scwang.smart:refresh-layout-kernel:${Versions.RefreshClassics}"
    const val RefreshHeader = "com.scwang.smart:refresh-header-classics:${Versions.RefreshClassics}"
    const val RefreshFooter = "com.scwang.smart:refresh-footer-classics:${Versions.RefreshClassics}"
}

/**
 * 三方接入的 SDK
 */
object SDKLibs {
    const val TencentBugly = "com.tencent.bugly:crashreport:${Versions.TencentBugly}"
    const val TencentTBSX5 = "com.tencent.tbs:tbssdk:${Versions.TencentTBSX5}"
    const val JiCore = "cn.jiguang.sdk:jcore:${Versions.JiCore}"
    const val JiPush = "cn.jiguang.sdk:jpush:${Versions.JiPush}"
    const val Fastjson = "com.alibaba:fastjson:${Versions.Fastjson}"
}

/**
 * 其他
 */
object OtherLibs {
    const val MMKV = "com.tencent:mmkv-static:${Versions.MMKV}"
    const val ARoute = "com.alibaba:arouter-api:${Versions.ARoute}"
    const val ARouteCompiler = "com.alibaba:arouter-compiler:${Versions.ARoute}"
    const val EventBus = "org.greenrobot:eventbus:${Versions.EventBus}"
    const val EventBusAPT = "org.greenrobot:eventbus-annotation-processor:${Versions.EventBus}"
    const val PermissionX = "com.guolindev.permissionx:permissionx:${Versions.PermissionX}"
    const val AutoService = "com.google.auto.service:auto-service:${Versions.AutoService}"
    const val AutoServiceAnnotations =
        "com.google.auto.service:auto-service-annotations:${Versions.AutoService}"
    const val Logger = "com.orhanobut:logger:${Versions.Logger}"
}


