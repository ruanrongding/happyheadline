package com.run.common.utils

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import java.io.*

object UBitmap {
    /**
     * 保存图片到图库
     *
     * @param context
     * @param bmp
     */
    fun saveImageToGallery(context: Context, bmp: Bitmap) {
        // 首先保存图片
        val appDir = File(Environment.getExternalStorageDirectory(), "image")
        if (!appDir.exists()) {
            appDir.mkdir()
        }
        val fileName = System.currentTimeMillis().toString() + ".jpg"
        val file = File(appDir, fileName)
        if (!file.exists()) {
            try {
                file.createNewFile()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
        try {
            val bos = BufferedOutputStream(FileOutputStream(file))
            bmp.compress(Bitmap.CompressFormat.JPEG, 80, bos)
            bos.flush()
            bos.close()
            Toast.makeText(context, "保存成功", Toast.LENGTH_SHORT).show()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.contentResolver,
                    file.absolutePath, fileName, null)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }

        // 最后通知图库更新
        context.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.absolutePath)))
    }
}