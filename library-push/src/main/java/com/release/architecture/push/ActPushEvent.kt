package com.release.architecture.push

/**
 * 极光推送
 * @constructor
 * @author yancheng
 * @since 2021/9/23
 */
open class PushEvent

/**
 * 推送活动
 * @property activityId String? 活动id
 * @constructor
 */
data class ActPushEvent(val activityId: String?) : PushEvent()

/**
 * 上课
 */
data class ClassBeginPushEvent(val teacherId: String?, val activityId: String?, val classId :String?) :
    PushEvent()

/**
 * 下课
 */
data class ClassIsOverPushEvent(val teacherId: String?, val activityId: String?) : PushEvent()


