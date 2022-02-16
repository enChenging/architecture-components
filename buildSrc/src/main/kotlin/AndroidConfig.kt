/**
 * 项目构建配置
 *
 * @author yancheng
 * @since 2021/11/15
 */
object AndroidConfig {

    const val PACKAGE_NAME = "com.release.architecture"
    const val COMPILE_SDK_VERSION = 30
    const val MIN_SDK_VERSION = 21
    const val TARGET_SDK_VERSION = 30
    const val BUILD_TOOLS_VERSION = "30.0.3"
    const val VERSION_CODE = 1
    const val VERSION_NAME = "0.0.1"

    /**
     * 是否模块为 App
     */
    const val MODULE_IS_APP = true
}

/**
 * 项目当前的版本状态
 * 该状态直接反映当前App是测试版 还是正式版 或者预览版
 * 打包前记得修改该状态
 * 开发环境:DEBUG、公开测试版:BETA、正式版:RELEASE
 */
object AppVersion {

    const val DEBUG = "VERSION_STATUS_DEBUG"

    const val BETA = "VERSION_STATUS_BETA"

    const val RELEASE = "VERSION_STATUS_RELEASE"
}