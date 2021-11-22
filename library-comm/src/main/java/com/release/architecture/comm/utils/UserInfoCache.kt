package com.release.architecture.comm.utils

import com.release.architecture.base.utils.SpUtils


/**
 * 用户信息缓存
 * 请任何修改用户信息的时候更新一下该缓存的信息
 *
 * @author yancheng
 * @since 2021/8/25
 */
object UserInfoCache {

    private const val USER_NAME = "user_name"
    private const val USER_ID = "user_id"
    private const val USER_SEX = "user_sex"
    private const val USER_AVATAR = "user_avatar"
    private const val USER_MOTTO = "user_motto"
    private const val USER_SCHOOL_NAME = "user_schoolName"
    private const val USER_YEAR = "user_year"
    private const val USER_CLASS = "user_class"


    /**
     * 获取用户姓名
     * @return String 用户姓名
     */
    fun getUserName(): String = SpUtils.getString(USER_NAME, "")

    /**
     * 保存用户姓名
     * @param name String 用户姓名
     */
    fun saveUserName(name: String): UserInfoCache {
        SpUtils.putString(USER_NAME, name)
        return this@UserInfoCache
    }

    /**
     * 获取用户id
     * @return String 用户id
     */
    fun getUserId(): String = SpUtils.getString(USER_ID, "")

    /**
     * 保存用户id
     * @param id String 用户id
     * @return Unit
     */
    fun saveUserId(id: String): UserInfoCache {
        SpUtils.putString(USER_ID, id)
        return this@UserInfoCache
    }

    /**
     * 获取用户性别
     * @return String 0:保密、1:男、2:女
     */
    fun getUserSex(): String = SpUtils.getString(USER_SEX, "")

    /**
     * 保存用户性别
     * @param sex String 用户性别
     * @return Unit
     */
    fun saveUserSex(sex: String): UserInfoCache {
        SpUtils.putString(USER_SEX, sex)
        return this@UserInfoCache
    }

    /**
     * 获取用户头像地址
     * @return String? 用户头像地址，如果没有则返回null
     */
    fun getUserAvatar(): String = SpUtils.getString(USER_AVATAR, "")

    /**
     * 保存用户头像地址
     * @param avatar String 用户头像地址
     * @return Unit
     */
    fun saveUserAvatar(avatar: String): UserInfoCache {
        SpUtils.putString(USER_AVATAR, avatar)
        return this@UserInfoCache
    }

    /**
     * 获取用户座右铭
     * @return String?
     */
    fun getUserMotto(): String = SpUtils.getString(USER_MOTTO, "")

    /**
     * 保存用户座右铭
     * @param motto String
     * @return UserInfoCache
     */
    fun saveUserMotto(motto: String): UserInfoCache {
        SpUtils.putString(USER_MOTTO, motto)
        return this@UserInfoCache
    }

    /**
     * 获取用户学校名
     * @return String?
     */
    fun getUserSchoolName(): String = SpUtils.getString(USER_SCHOOL_NAME, "")

    /**
     * 保存用户学校名
     * @param schoolName String
     * @return UserInfoCache
     */
    fun saveUserSchoolName(schoolName: String): UserInfoCache {
        SpUtils.putString(USER_SCHOOL_NAME, schoolName)
        return this@UserInfoCache
    }

    /**
     * 获取用户年级
     * @return String?
     */
    fun getUserYear(): String = SpUtils.getString(USER_YEAR, "")

    /**
     * 保存用户年级
     * @param year String
     * @return UserInfoCache
     */
    fun saveUserYear(year: String): UserInfoCache {
        SpUtils.putString(USER_YEAR, year)
        return this@UserInfoCache
    }

    /**
     * 获取用户班级
     * @return String
     */
    fun getUserClass(): String = SpUtils.getString(USER_CLASS, "")


    /**
     * 保存用户班级
     * @param userClass String
     */
    fun saveUserClass(userClass: String) {
        SpUtils.putString(USER_CLASS, userClass)
    }

    /**
     * 清除用户信息
     */
    fun clearUserInfo() {
        SpUtils.putString(USER_NAME, "")
        SpUtils.putString(USER_ID, "")
        SpUtils.putString(USER_SEX, "")
        SpUtils.putString(USER_AVATAR, "")
        SpUtils.putString(USER_MOTTO, "")
        SpUtils.putString(USER_SCHOOL_NAME, "")
        SpUtils.putString(USER_YEAR, "")
        SpUtils.putString(USER_CLASS, "")
    }
}