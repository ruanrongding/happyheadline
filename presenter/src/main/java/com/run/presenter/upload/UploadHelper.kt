package com.run.presenter.upload

import android.Manifest
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.content.ContentUris
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import android.text.TextUtils
import android.util.Log
import android.view.View
import com.run.common.R
import com.run.common.base.BaseObserver
import com.run.common.utils.UCompositeDisposable
import com.run.common.utils.ULog
import com.run.common.utils.URxBus
import com.run.common.view.MyBottomSheetDialog
import com.run.config.modle.BaseModle
import com.run.config.modle.BaseRxBean
import com.run.conifg.RxBusConfig
import com.run.login.api.LoginManager
import com.run.presenter.LoginHelper
import com.tbruyelle.rxpermissions2.RxPermissions
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.IOException

class UploadHelper private constructor() {

    private var mActivity: Activity? = null

    private var imageUri: Uri? = null//相机拍照图片保存地址


    private var outputUri: Uri? = null

    private var imagePath: String? = null//打开相册选择照片的路径
    private var type: Int = 0

    @JvmOverloads
    fun showUploadDialog(activity: Activity, type: Int = 0) {
        this.type = type
        this.mActivity = activity
        val dialog = MyBottomSheetDialog(activity)
        val view = View.inflate(activity, R.layout.dialog_upload_img_layout, null)
        dialog.setContentView(view)
        view.findViewById<View>(R.id.tv_cancle).setOnClickListener { dialog.cancel() }
        view.findViewById<View>(R.id.ll_share_wc).setOnClickListener {
            //拍照
            openCamera()//打开相机
            dialog.cancel()
        }
        view.findViewById<View>(R.id.ll_share_wc_friend).setOnClickListener {
            //动态权限
            dialog.cancel()
            openAlbum()
        }


        dialog.show()
    }

    @SuppressLint("CheckResult")
    private fun openCamera() {
        if (mActivity == null) return
        //权限检查
        RxPermissions(mActivity!!)
                .request(Manifest.permission.CAMERA)
                .subscribe { aBoolean ->
                    if (aBoolean!!) {//用户授权成功
                        // 创建File对象，用于存储拍照后的图片
                        val outputImage = File(mActivity!!.externalCacheDir, "output_image.jpg")
                        try {
                            if (outputImage.exists()) {
                                outputImage.delete()
                            }
                            outputImage.createNewFile()
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }

                        imageUri = if (Build.VERSION.SDK_INT < 24) {
                            Uri.fromFile(outputImage)
                        } else {
                            //Android 7.0系统开始 使用本地真实的Uri路径不安全,使用FileProvider封装共享Uri
                            //参数二:fileprovider绝对路径 com.dyb.testcamerademo：项目包名
                            FileProvider.getUriForFile(mActivity!!, "happyheadline", outputImage)
                        }
                        // 启动相机程序
                        val intent = Intent("android.media.action.IMAGE_CAPTURE")
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
                        mActivity!!.startActivityForResult(intent, REQUEST_TAKE_PHOTO)
                    } else {//表示用户还有权限没有授权

                    }
                }
    }

    private fun openAlbum() {
        if (mActivity == null) return
        val intent = Intent("android.intent.action.GET_CONTENT")
        intent.type = "image/*"
        mActivity!!.startActivityForResult(intent, REQUEST_CHOOSE_PHOTO) // 打开相册
    }


    /**
     * 处理放回结果
     */
    fun doResult(requestCode: Int, resultCode: Int, data: Intent) {
        when (requestCode) {
            REQUEST_TAKE_PHOTO//拍照
            -> if (resultCode ==-1) {
                cropPhoto()//裁剪图片
            }
            REQUEST_CHOOSE_PHOTO//打开相册
            ->
                // 判断手机系统版本号
                if (Build.VERSION.SDK_INT >= 19) {
                    // 4.4及以上系统使用这个方法处理图片
                    handleImageOnKitKat(data)
                } else {
                    // 4.4以下系统使用这个方法处理图片
                    handleImageBeforeKitKat(data)
                }
            REQUEST_PICTURE_CUT//裁剪完成
            -> try {
                imagePath = outputUri!!.path
                uploadFile(File(imagePath!!))
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

    }


    /**
     * 裁剪图片
     */
    fun cropPhoto() {
        if (mActivity == null) return
        // 创建File对象，用于存储裁剪后的图片，避免更改原图
        val file = File(mActivity!!.externalCacheDir, "crop_image.jpg")
        try {
            if (file.exists()) {
                file.delete()
            }
            file.createNewFile()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        outputUri = Uri.fromFile(file)
        val intent = Intent("com.android.camera.action.CROP")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        intent.setDataAndType(imageUri, "image/*")
        //裁剪图片的宽高比例
        intent.putExtra("aspectX", 1)
        intent.putExtra("aspectY", 1)
        intent.putExtra("crop", "true")//可裁剪
        // 裁剪后输出图片的尺寸大小

        when (type) {
            0 -> {
                intent.putExtra("outputX", 150)
                intent.putExtra("outputY", 150)
            }
            1 -> {
                intent.putExtra("outputX", 600)
                intent.putExtra("outputY", 320)
            }
            2 -> {
                intent.putExtra("outputX", 720)
                intent.putExtra("outputY", 480)
            }
        }
        intent.putExtra("scale", true)//支持缩放
        intent.putExtra("return-data", false)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri)
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString())//输出图片格式
        intent.putExtra("noFaceDetection", true)//取消人脸识别
        mActivity!!.startActivityForResult(intent, REQUEST_PICTURE_CUT)
    }

    // 4.4及以上系统使用这个方法处理图片 相册图片返回的不再是真实的Uri,而是分装过的Uri
    @TargetApi(19)
    private fun handleImageOnKitKat(data: Intent) {
        if (mActivity == null) return
        imagePath = null
        imageUri = data.data
        Log.d("TAG", "handleImageOnKitKat: uri is " + imageUri!!)
        if (DocumentsContract.isDocumentUri(mActivity, imageUri)) {
            // 如果是document类型的Uri，则通过document id处理
            val docId = DocumentsContract.getDocumentId(imageUri)
            if ("com.android.providers.media.documents" == imageUri!!.authority) {
                val id = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1] // 解析出数字格式的id
                val selection = MediaStore.Images.Media._ID + "=" + id
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection)
            } else if ("com.android.providers.downloads.documents" == imageUri!!.authority) {
                val contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), java.lang.Long.valueOf(docId))
                imagePath = getImagePath(contentUri, null)
            }
        } else if ("content".equals(imageUri!!.scheme!!, ignoreCase = true)) {
            // 如果是content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(imageUri, null)
        } else if ("file".equals(imageUri!!.scheme!!, ignoreCase = true)) {
            // 如果是file类型的Uri，直接获取图片路径即可
            imagePath = imageUri!!.path
        }
        cropPhoto()
    }

    private fun getImagePath(uri: Uri?, selection: String?): String? {
        var path: String? = null
        // 通过Uri和selection来获取真实的图片路径
        val cursor = mActivity!!.contentResolver.query(uri!!, null, selection, null, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
            }
            cursor.close()
        }
        return path
    }

    private fun handleImageBeforeKitKat(data: Intent) {
        imageUri = data.data
        imagePath = getImagePath(imageUri, null)
        cropPhoto()
    }


    //=====================================上传图片=====================================================


    private fun uploadFile(file: File) {
        if (!file.exists()) return
        val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
        val body = MultipartBody.Part.createFormData("image", file.name, requestFile)
        val descriptionString = "图片上传"
        val description = RequestBody.create(MediaType.parse("multipart/form-data"), descriptionString)
        val call = LoginManager.instance.upload(LoginHelper.instance.getmToken()!!, description, body)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                var jsonString: String? = null
                try {
                    jsonString = String(response.body()!!.bytes())
                    val jsonObject = JSONObject(jsonString)
                    val status = jsonObject.getInt("status")
                    if (status == 200) {
                        val url = jsonObject.getString("url")
                        ULog.d("图片上传的url:$url")
                        modifyUserIcon(url)
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                } catch (e: JSONException) {
                    e.printStackTrace()
                }

                Log.v("Upload", "success")
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("Upload error:", t.message)
            }
        })
    }

    /**
     * 修改用户头像
     */
    private fun modifyUserIcon(url: String) {
        if (TextUtils.isEmpty(url)) return
        if (type == 1) {
            UCompositeDisposable.getInstance().addDisposable(LoginManager.modifyBg(url), object : BaseObserver<BaseModle>() {
                override fun onError(errorType: Int, msg: String?) {
                    URxBus.get().post(BaseRxBean<Nothing>(RxBusConfig.LoginConfig.Modify_Bg_Type, RxBusConfig.LoginConfig.Fali_Code, "修改失败"))
                }

                override fun onSuccess(o: BaseModle) {
                    URxBus.get().post(BaseRxBean<Nothing>(RxBusConfig.LoginConfig.Modify_Bg_Type, RxBusConfig.LoginConfig.Success_Code, url))
                }
            })
        } else if (type == 0) {
            UCompositeDisposable.getInstance().addDisposable(LoginManager.modifyUserInco(url), object : BaseObserver<BaseModle>() {
                override fun onSuccess(o: BaseModle) {
                    URxBus.get().post(BaseRxBean<Nothing>(RxBusConfig.LoginConfig.Modify_Image_Type, RxBusConfig.LoginConfig.Success_Code, url))
                }
                override fun onError(errorType: Int, msg: String?) {
                    URxBus.get().post(BaseRxBean<Nothing>(RxBusConfig.LoginConfig.Modify_Bg_Type, RxBusConfig.LoginConfig.Fali_Code, "修改失败"))
                }
            })
        } else if (type == 2) {//上传的图片
            URxBus.get().post(BaseRxBean<Nothing>(RxBusConfig.LoginConfig.Upload_Image_Type, RxBusConfig.LoginConfig.Success_Code, url))
        }
    }

    companion object {
        val REQUEST_TAKE_PHOTO = 2222
        val REQUEST_CHOOSE_PHOTO = 2223
        val REQUEST_PICTURE_CUT = 2224
        private val TAG = "UploadHelper"
        private var uploadHelper: UploadHelper? = null
        val instance: UploadHelper
            get() {
                if (uploadHelper == null) {
                    synchronized(UploadHelper::class.java) {
                        if (uploadHelper == null) {
                            uploadHelper = UploadHelper()
                        }
                    }
                }
                return uploadHelper!!
            }
    }
}
