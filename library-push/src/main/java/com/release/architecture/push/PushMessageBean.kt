package com.release.architecture.push

/**
 * 推送来的消息
 * @property jumpType Int 跳转类型 0:首页 1:活动详情页 2:活动列表页 3:图鉴列表页
 * @property jumpTypeName String 跳转类型名称
 * @property activityId String 活动id
 * @property classId String 班级id
 * @property subject String 科目
 * @property activityName String 活动名称
 * @constructor
 * @author yancheng
 * @since 2021/9/18
 */
data class PushMessageBean(
    val jumpType: Int,
    val jumpTypeName: String,
    val activityId: String?,
    val classId: String?,
    val subject: String?,
    val activityName: String?
)

/**
 * 推送消息
 * @property name String 名称
 * @property type String 类型 0:活动 1:上课 2:下课
 * @property activityId String? 活动id
 * @property teacherId String? 老师id
 * @property classId String? 班级id
 * @constructor
 */
data class JPushBean(
    val name: String,
    val type: String,
    val activityId: String?,
    val teacherId: String?,
    val classId: String?
)