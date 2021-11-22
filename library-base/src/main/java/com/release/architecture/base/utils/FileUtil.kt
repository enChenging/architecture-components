package com.release.architecture.base.utils

import android.os.Environment
import android.util.Log
import com.release.architecture.base.utils.EventBusUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileWriter
import java.io.IOException

object FileUtil {
    /**
     * 将压缩笔迹的String转为文件
     * @param string String 字符串
     * @param fileName String 文件名
     * @return String 文件本地路径
     */
    suspend fun string2File(string: String, fileName: String): String {
        var filePath = ""
        withContext(Dispatchers.IO) {
            try {
                if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
                    val path = Environment.getExternalStorageDirectory().toString() + "/classroom"
                    val folder = File(path)
                    if (!folder.exists()) {
                        folder.mkdir()
                    }
                    val file = File(path, fileName)
                    filePath = "$path/$fileName"
                    Log.d("qqq", "string2File: $filePath")
                    // 判断当前文件是否存在 存在就删除
                    if (file.exists()) {
                        file.delete()
                    } else {
                        file.createNewFile()
                    }
                    val fileWriter = FileWriter(file, true)
                    fileWriter.write(string)
                    fileWriter.close()
                }
            } catch (e: Exception) {
                EventBusUtils.postEvent("创建文件失败 创建文件失败 ${e.message}")
            }
        }
        return filePath
    }

    /**
     * 删除指定文件地址的文件
     * @param filePathList MutableList<String> 文件地址列表
     * @throws IOException
     */
    @Throws(IOException::class)
    suspend fun deleteCacheFiles(filePathList: MutableList<String>) {
        withContext(Dispatchers.IO) {
            filePathList.forEach {
                try {
                    val file = File(it)
                    if (file.exists()) {
                        file.delete()
                    }
                } catch (e: Exception) {
                    Log.d("qqq", e.printStackTrace().toString())
                }
            }
        }
        filePathList.clear()
    }
}