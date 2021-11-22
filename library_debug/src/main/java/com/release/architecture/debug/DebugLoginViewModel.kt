package com.release.architecture.debug

import androidx.lifecycle.MutableLiveData
import com.release.architecture.base.ktx.launchIO
import com.release.architecture.base.mvvm.vm.BaseViewModel
import com.release.architecture.base.utils.logD
import com.release.architecture.comm.net.helper.ResponseCodeEnum
import com.release.architecture.comm.net.helper.ResponseException
import com.release.architecture.comm.repo.CommRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

/**
 * 测试环境下的登陆界面的VM层
 *
 * @author yancheng
 * @since 2021/8/6
 */
@HiltViewModel
class DebugLoginViewModel @Inject constructor(private val mRepository: CommRepository) :
    BaseViewModel() {

    /**
     * 登陆结果
     */
    val loginLD = MutableLiveData<Boolean>()

    /**
     * 登录接口
     * @param username String 账号
     * @param password String 密码
     * @param role String 角色0、老师1、学生
     * @param pushId String
     */
    fun login(username: String, password: String, role: String, pushId: String) {
        loginLD.postValue(true)
//        launchIO {
//            mRepository.login(username, password, role, pushId)
//                .catch {
//                    if (it is ResponseException && it.type == ResponseCodeEnum.ERROR) {
//                        loginLD.postValue(false)
//                    }
//                    logD(it.message ?: "")
//                }
//                .collect {
//                    loginLD.postValue(true)
//                }
//        }
    }
}