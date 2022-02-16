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

## 四、功能封装

### **library-base**

#### 1.全局 Crash 上报和监控

**介绍：**

项目使用腾讯 **Bugly** 来进行全局的 **Crash** 上报，需要在 **buildSrc** 包下的 **Keys.BUGLY_APP_ID & Keys.BUGLY_APP_ID_DEV** 配置应用的正式环境id和测试环境id，为的是不让测试环境的数据污染线上的数据

#### 2.全局 Activity 生命周期监听

**具体实现类：**

`ActivityLifecycleCallbacksImpl`

#### 3.全局 Activity 管理栈

**具体实现类：**

`ActivityStackManager`

**介绍：**

该类是一个单例类，内部维护一个 `Stack` 用来存放 `Activity` 实例，与 `ActivityStackManager` 配合使用，没有内存泄露的问题。该类提供了一系列的操作栈的方法，可以供调用者进行使用。

#### 4.全局网络状态变化监听

**具体实现类：**

`NetworkStateClient`、`NetworkCallbackImpl`、`INetworkStateChangeListener`、`AutoRegisterNetListener`

**介绍：**

项目中使用了全新的方式实现网络状态变化的监听，摒弃了旧的监听广播的方式。项目中已经把该监听注册到 `Activity` 基类中，配合 **Jetpack** 的 **Lifecycle** 能够自主管理生命周期，无需调用者自己处理。

### **library-comm**

#### 1.X5 内核 - WebView

**介绍：**

该项目集成了 **腾讯 TBS** 的 浏览器 **X5 内核**用来替换掉原生的 **WebView**。并且封装了公共的Web页面，具体类是 `CommWebActivity`。

### **library-push**

#### 1.极光推送

**介绍：** 

该项目集成了极光推送，但是没有集成厂商通道配置，可以在官网创建应用时选择自行集成。如需测试推送是否可用，可以申请极光推送的 **key** 填入 **buildSrc** 包下的 **Keys.J_PUSH_KEY**，该项目已经经过测试，自定义推送消息是可用的。

自定义消息推送的设计大概是这样的：从极光云端下发到App的推送消息会先到达 `PushReceiver.onMessage()` ，然后进行数据解析，根据解析的数据再通过 **EventBus** 进行事件分发（也可以通过其他的方式来实现），这么做就是为了将这个模块独立出去。

## 五、注意⚠️

1. 模块单独运行需要在 **buildSrc** 模块下的 `ProjectBuildConfig.MODULE_IS_APP` 值改为 `true`，然后 **sync** 工程后，就能够看到模块可以被单独运行了，但是还有几个需要注意的地方：
   - **module** 模块需要有自己的自定义 **Application** 但是由于使用了依赖注入框架 **Hilt**，所以需要在 自定义的**Application** 上面加上 `@ HiltAndroidApp` 注解，但是 **Hilt** 内部会检查运行的模块以及所依赖的模块只能有一个注解存在，所以当我们运行 **shell** 的时候，需要将其他 **module** 模块的 `@ HiltAndroidApp` 注解注释掉，当运行 **module** 的时候需要将该注解再打开。
