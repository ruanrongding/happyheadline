package com.run.common.utils

import android.content.Context
import android.content.SharedPreferences
import com.run.common.utils.USharePreference.FILE_NAME

import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.util.HashSet

/**
 * SharePreference工具类
 */

object USharePreference {
    val TAG: String = USharePreference.javaClass.name
    /**
     * 保存在手机里面的文件名
     */
    const val FILE_NAME = "share_data"

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     */
    fun put(context: Context, key: String, `object`: Any) {
        val sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE)
        val editor = sp.edit()
        when (`object`) {
            is String -> editor.putString(key, `object`)
            is Int -> editor.putInt(key, `object`)
            is Boolean -> editor.putBoolean(key, `object`)
            is Float -> editor.putFloat(key, `object`)
            is Long -> editor.putLong(key, `object`)
            else -> {
                editor.putString(key, `object`.toString())
            }
        }
        SharedPreferencesCompat.apply(editor)


    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     */
    operator fun get(context: Context, key: String, defaultObject: Any): Any? {
        val sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
        when (defaultObject) {
            is String -> return sp.getString(key, defaultObject)
            is Int -> return sp.getInt(key, defaultObject)
            is Boolean -> return sp.getBoolean(key, defaultObject)
            is Float -> return sp.getFloat(key, defaultObject)
            is Long -> return sp.getLong(key, defaultObject)
        }
        return null
    }

    /**
     * 移除某个key值已经对应的值
     */
    fun remove(context: Context, key: String) {
        val sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.remove(key)
        SharedPreferencesCompat.apply(editor)
    }

    /**
     * 清除所有数据
     */
    fun clear(context: Context) {
        val sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.clear()
        SharedPreferencesCompat.apply(editor)
    }

    /**
     * 查询某个key是否已经存在
     */
    fun contains(context: Context, key: String): Boolean {
        val sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE)
        return sp.contains(key)
    }

    /**
     * 返回所有的键值对
     */
    fun getAll(context: Context): Map<String, *> {
        val sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE)
        return sp.all
    }


    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     */
    private object SharedPreferencesCompat {
        private val sApplyMethod = findApplyMethod()

        /**
         * 反射查找apply的方法
         */
        private fun findApplyMethod(): Method? {
            try {
                val clz = SharedPreferences.Editor::class.java
                return clz.getMethod("apply")
            } catch (e: NoSuchMethodException) {
                ULog.d(TAG, e)
            }
            return null
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         */
        fun apply(editor: SharedPreferences.Editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor)
                    return
                }
            } catch (e: IllegalArgumentException) {
                ULog.d(TAG, e)
            } catch (e: IllegalAccessException) {
                ULog.d(TAG, e)
            } catch (e: InvocationTargetException) {
                ULog.d(TAG, e)
            }
            editor.commit()
        }
    }
}