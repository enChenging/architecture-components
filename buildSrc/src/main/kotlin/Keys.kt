/**
 * 密钥相关
 * 尽量不要硬编码到代码里
 *
 * @author yancheng
 * @since 2021/11/15
 */
object Keys {

    /**
     * 渠道
     */
    const val CHANNEL = "***${Versions.AGP}"

    /****************************Bugly*************************************/
    /**
     * Bugly id 开发环境
     */
    const val BUGLY_APP_ID_DEV = "***"

    /**
     * Bugly id 测试环境
     */
    const val BUGLY_APP_ID_TEST = "***"

    /**
     * Bugly id 生产环境
     */
    const val BUGLY_APP_ID_PROD = "***"

    /****************************极光推送*************************************/
    /**
     * 极光推送 key 开发环境
     */
    const val J_PUSH_KEY_DEV = "***"

    /**
     * 极光推送 key 测试环境
     */
    const val J_PUSH_KEY_TEST = "***"

    /**
     * 极光推送 key 生产环境
     */
    const val J_PUSH_KEY_PROD = "***"

}