# Android 架构框架

## 一、介绍

这是一个 **MVVM** + **组件化架构**的 **Android 项目开发框架**，使用 **Kotlin** 语言进行开发，基于 **Google Android Teme 推荐架构**和 **Android Jetpack** 实现。

## 二、架构设计

**官方推荐应用代码分层架构图：**

![代码分层架构图](https://tva1.sinaimg.cn/large/008i3skNgy1gvjhh0a8xej60qo0k0t9t02.jpg)

**框架工程架构图：**

![框架工程架构图](https://tva1.sinaimg.cn/large/008i3skNly1gvjine237ej60s80hxgmy02.jpg)

### 组件化

​		项目架构采用组件化的方式进行开发，模块化/组件化是项目工程化标配，采用这种设计可以让项目各模块独立开发测试，可以更好的解耦，每个人只需要关心自己开发的业务组件模块，而不需要关心其他的业务模块，当需要抽离组件复用时，只要保证基础公共模块相同就可以被复用，前提是这个组件解耦的比较彻底，否者也有小的迁移成本。

​		项目底层封装为 library-base 模块，该模块应该是和业务没有任何关系的一个模块，基本上不需要调整该模块的代码，除非增加与业务无关的工具类及封装。

​		与项目业务有关系的公共组件下沉模块 library-comm，为各组件提供公共部分逻辑，该模块直接继承自基础模块 library-base，然后被其他业务组件所继承（依赖），内容主要是各组件的公共逻辑、或需要进行交互进行下沉的代码。该模块是主要耦合的地方，所以应该尽量保证各业务组件的内容足够内聚，降低公共模块与其他业务组件的耦合度。

​		作为单独能力提供的组件，例如推送模块，被封装为单独的组件，该组件应该与外界毫无关系，仅作为一个提供能力的组件，这样能够最大化减少组件复用的迁移成本，提高组件的内聚、降低组件与外界的耦合能够让这些组件单独存在，可以被打包、可以被开箱即用。

​		业务组件需要封装自身所有的功能，尽量不与外界产生直接联系，可以通过一些工具进行解耦，例如 ARouter 提供的服务提供者，EventBus 进行组件间通信，保证业务组件的高内聚性，能够更好的单独测试、发布、复用等。

​		组件间通过 Java SPI 机制进行组件注册，各组件间的界面跳转使用 ARouter 进行跳转，统一的依赖版本控制通过 buildSrc 进行统一版本。

### MVVM

​		代码分层设计使用官方推荐的 MVVM 架构模式，官方的文档地址：[应用架构指南](https://developer.android.com/jetpack/guide)。View（视图层）主要负责UI界面的更新，ViewModel（视图模型层）负责数据的保存和业务逻辑的处理，Model（模型层）主要为数据，官方推荐使用 Repository 模式来抹平数据来源不同的差异，让 Repository 专注于数据提供，外界无需知道其数据的来源或者内部逻辑。这种设计在多个数据来源时非常有用，例如有本地数据库和远程服务器两个数据来源时，Repository 把两种数据来源封装起来，外界只需要调用 Repository 暴露的方法即可获取到数据，这种设计的好处会在项目发展的越来越大和越来越复杂时突显出来。

​		官方文中提到了几个应用架构最佳做法的指导：

- **避免将应用的入口点（如 Activity、Service 和广播接收器）指定为数据源。**
- **在应用的各个模块之间设定明确定义的职责界限。**
- **尽量少公开每个模块中的代码。**
- **考虑如何使每个模块可独立测试。**
- **专注于应用的独特核心，以使其从其他应用中脱颖而出。**
- **保留尽可能多的相关数据和最新数据。**
- **将一个数据源指定为单一可信来源。**

只要我们按照上面的指导来进行编码，项目从长远角度来看是更容易维护和测试的，能够最大程度上减少后期的维护成本和新人接手项目的熟悉成本。

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

![推送设计](https://tva1.sinaimg.cn/large/008i3skNly1gvj98ytzlrj60ol0rpabc02.jpg)

## 五、注意⚠️

1. 模块单独运行需要在 **buildSrc** 模块下的 `ProjectBuildConfig.MODULE_IS_APP` 值改为 `true`，然后 **sync** 工程后，就能够看到模块可以被单独运行了，但是还有几个需要注意的地方：
   - **module** 模块需要有自己的自定义 **Application** 但是由于使用了依赖注入框架 **Hilt**，所以需要在 自定义的**Application** 上面加上 `@HiltAndroidApp` 注解，但是 **Hilt** 内部会检查运行的模块以及所依赖的模块只能有一个注解存在，所以当我们运行 **shell** 的时候，需要将其他 **module** 模块的 `@HiltAndroidApp` 注解注释掉，当运行 **module** 的时候需要将该注解再打开。
   - 由于极光推送内部会根据配置的包名生成 **DataProvider**，而同包名下只能存在一个，所以安装模块应用时，需要将其他模块卸载，否则会安装不成功。
