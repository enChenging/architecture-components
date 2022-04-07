rootProject.name = "architecture-components"

// 壳工程
include(":shell")
// 基础模块,公共模块
include(":library-base", ":library-common")
// 独立能力组件
include(":library-push")
// 测试环境下模块单独运行需要登录时用到的组件
include(":library-debug")
// 业务功能组件(示例1),(示例2)
include(":module-one", ":module-two")
