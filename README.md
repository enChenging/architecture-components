# Android 架构框架

## 一、介绍

这是一个 **MVVM** + **组件化架构**的 **Android 项目开发框架**，使用 **Kotlin** 语言进行开发，基于 **Google Android Teme 推荐架构**和 **Android Jetpack** 实现。

## 二、架构设计

**官方推荐应用代码分层架构图：**

![代码分层架构图](https://tva1.sinaimg.cn/large/008i3skNgy1gvjhh0a8xej60qo0k0t9t02.jpg)

## 三、技术栈

| 类别           | 技术                                                         |
| -------------- | ------------------------------------------------------------ |
| 数据库         | [Tencent-MMKV](https://github.com/Tencent/MMKV)、[Jetpack-Room](https://developer.android.com/jetpack/androidx/releases/room) |
| 序列化         | [Google-gson](https://github.com/google/gson)                |
| 屏幕适配       | [Jetpack-ConstraintLayout](https://developer.android.com/training/constraint-layout?hl=zh-cn)、[AndroidAutoSize](https://github.com/JessYanCoding/AndroidAutoSize) |
| 依赖注入（DI） | [Jetpack-Hilt](https://developer.android.com/training/dependency-injection/hilt-android?hl=zh_cn) |
| 网络           | [Square-OkHttp4](https://github.com/square/okhttp)、[Square-Retrofit2](https://github.com/square/retrofit) |
| 图片加载       | [Glide](https://github.com/bumptech/glide)            |
| RV适配器       | [BaseRecyclerViewAdapterHelper](https://github.com/CymChad/BaseRecyclerViewAdapterHelper) |
| 事件总线       | [greenrobot-EventBus](https://github.com/greenrobot/EventBus) |
| Crash 上报监控 | [Tencent-Bugly](https://bugly.qq.com/v2/index)               |
| 视图绑定       | [Jetpack-ViewBinding](https://developer.android.com/topic/libraries/view-binding?hl=zh-cn) |
| 异步编程       | [Kotlin-Coroutines](https://github.com/Kotlin/kotlinx.coroutines) |
| 路由           | [Alibaba-ARouter](https://github.com/alibaba/ARouter)        |
| WebView        | [Tencent-X5](https://x5.tencent.com/tbs/index.html)          |
| MVVM相关       | [Jetpack-ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel?hl=zh_cn)、[Jetpack-LiveData](https://developer.android.com/topic/libraries/architecture/livedata?hl=zh-cn) |


## 四、注意⚠️

1. 模块单独运行需要在 **buildSrc** 模块下的 `ProjectBuildConfig.MODULE_IS_APP` 值改为 `true`，然后 **sync** 工程后，就能够看到模块可以被单独运行了，但是还有几个需要注意的地方：
   - **module** 模块需要有自己的自定义 **Application** 但是由于使用了依赖注入框架 **Hilt**，所以需要在 自定义的**Application** 上面加上 `@ HiltAndroidApp` 注解，但是 **Hilt** 内部会检查运行的模块以及所依赖的模块只能有一个注解存在，所以当我们运行 **shell** 的时候，需要将其他 **module** 模块的 `@ HiltAndroidApp` 注解注释掉，当运行 **module** 的时候需要将该注解再打开。
