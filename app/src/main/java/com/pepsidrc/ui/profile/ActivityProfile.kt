package com.pepsidrc.ui.profile

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.Observer
import com.pepsidrc.util.UiUtils
import com.github.dhaval2404.imagepicker.ImagePicker
import com.pepsidrc.R
import com.pepsidrc.base.BaseActivity
import com.pepsidrc.managers.ImageRequestManager
import com.pepsidrc.model.profile.UserDetailResponse
import com.pepsidrc.utils.AndroidUtils
import com.pepsidrc.utils.Logger
import com.pepsidrc.utils.NetworkUtil
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.app_custom_tool_bar.*
import kotlinx.android.synthetic.main.change_password.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

class ActivityProfile : BaseActivity<ProfileViewModel>(ProfileViewModel::class) {

    companion object {
        private val IMAGE_DIRECTORY = "/nalhdaf"
        private const val PROFILE_IMAGE_REQ_CODE = 101
        private const val GALLERY_IMAGE_REQ_CODE = 102
        private const val CAMERA_IMAGE_REQ_CODE = 103
        fun getIntent(context: Context?): Intent? {
            if (context == null) {
                return null
            }

            return Intent(context, ActivityProfile::class.java)

        }
    }

    private val FINAL_TAKE_PHOTO = 1
    private val FINAL_CHOOSE_PHOTO = 2
    private var imageUri: Uri? = null

    override fun layout(): Int = R.layout.activity_profile
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ivDownArrow.setOnClickListener {
            if (llPasswordChange.visibility === View.VISIBLE) {
                llPasswordChange.visibility = View.GONE
                btnChangePassword.visibility = View.GONE
            } else {
                llPasswordChange.visibility = View.VISIBLE
                btnChangePassword.visibility = View.VISIBLE

            }

        }
        fl_left_img_container.setOnClickListener {
            onBackPressed()
        }
        barIcons.visibility = View.GONE

        img_profile.setOnClickListener {
            selectImage()
        }
        btnChangePassword.setOnClickListener {
            val validatePasswordOldError =
                AndroidUtils.validatePassword(etOldPassword.text.toString())
            val validatePasswordNewError =
                AndroidUtils.validatePassword(etNewPassword.text.toString())

            val validatePasswordMatchError = AndroidUtils.validateMatchPassword(
                etNewPassword.text.toString(),
                etConfirmPasswordtxt.text.toString()
            )

            this?.let { UiUtils.hideSoftKeyboard(it) }
            if (
                TextUtils.isEmpty(validatePasswordOldError) &&
                TextUtils.isEmpty(validatePasswordNewError) &&
                TextUtils.isEmpty(validatePasswordMatchError)
            ) {

                if (NetworkUtil.isInternetAvailable(this)) {
                    model.changePassword(
                        etOldPassword?.text.toString(),
                        etNewPassword.text.toString()
                    )
                }
            } else {
                etOldPassword.error = validatePasswordOldError
                etNewPassword.error = validatePasswordNewError
                etConfirmPasswordtxt.error = validatePasswordMatchError

            }


        }
        subscribeLoading()
        subscribeUi()
        fetchData()
    }

    private fun selectImage() {
        val options = arrayOf<CharSequence>("Take Photo", "Choose from Gallery", "Cancel")
        val builder = AlertDialog.Builder(this@ActivityProfile)
        builder.setTitle("Add Photo!")
        builder.setItems(options, DialogInterface.OnClickListener { dialog, item ->
            if (options[item] == "Take Photo") {
                pickCameraImage()
            } else if (options[item] == "Choose from Gallery") {
                pickGalleryImage()
            } else if (options[item] == "Cancel") {
                dialog.dismiss()
            }
        })
        builder.show()
    }

    fun takePhoto() {
        val outputImage = File(externalCacheDir, "output_image.jpg")
        if (outputImage.exists()) {
            outputImage.delete()
        }
        outputImage.createNewFile()
        imageUri = if (Build.VERSION.SDK_INT >= 24) {
            FileProvider.getUriForFile(this, "com.pepsidrc", outputImage)
        } else {
            Uri.fromFile(outputImage)
        }

        val intent = Intent("android.media.action.IMAGE_CAPTURE")
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        startActivityForResult(intent, FINAL_TAKE_PHOTO)
    }

    fun chooseFromAlbum() {
        val checkSelfPermission = ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        if (checkSelfPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                1
            )
        } else {
            openAlbum()
        }
    }

    private fun openAlbum() {
        val intent = Intent("android.intent.action.GET_CONTENT")
        intent.type = "image/*"
        startActivityForResult(intent, FINAL_CHOOSE_PHOTO)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 ->
                if (grantResults.isNotEmpty() && grantResults.get(0) == PackageManager.PERMISSION_GRANTED) {
                    openAlbum()
                } else {
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show()
                }
        }
    }

    fun pickCameraImage() {
        ImagePicker.with(this)
            .crop()
            // User can only capture image from Camera
            .cameraOnly()
            // Image size will be less than 1024 KB
            .compress(1024)
            .start(CAMERA_IMAGE_REQ_CODE)
    }

    fun pickGalleryImage() {
        ImagePicker.with(this)
            // Crop Image(User can choose Aspect Ratio)
            .crop()
            // User can only select image from Gallery
            .galleryOnly()
            // Image resolution will be less than 1080 x 1920
            .maxResultSize(1080, 1920)
            .start(GALLERY_IMAGE_REQ_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            CAMERA_IMAGE_REQ_CODE ->
                if (resultCode == Activity.RESULT_OK) {
                    val contentURI = data!!.data
                    /*  val bitmap =
                          BitmapFactory.decodeStream(getContentResolver().openInputStream(contentURI!!))*/
                    //   img_profile!!.setImageBitmap(bitmap)
                    if (NetworkUtil.isInternetAvailable(this)) {
                        model.updateUser(
                            et_Profile_Name.text.toString(),
                            "",
                            "",
                            ImagePicker.getFilePath(data)!!
                        )
                    }
                }
            GALLERY_IMAGE_REQ_CODE ->
                if (resultCode == Activity.RESULT_OK) {
                    //   val file = ImagePicker.getFile(data)!!

                    if (data != null) {
                        val contentURI = data!!.data
                        try {
                            /*  val bitmap =
                                  MediaStore.Images.Media.getBitmap(this.contentResolver, contentURI)
                              //       var imagePath=saveImage(bitmap)
                              //    Toast.makeText(this@MainActivity, "Image Show!", Toast.LENGTH_SHORT).show()
                              img_profile!!.setImageBitmap(bitmap)*/
                            if (NetworkUtil.isInternetAvailable(this)) {
                                model.updateUser(
                                    et_Profile_Name.text.toString(),
                                    et_profile_email.text.toString(),
                                    "",
                                    ImagePicker.getFilePath(data)!!
                                )
                            }
                        } catch (e: IOException) {
                            e.printStackTrace()
                            //   Toast.makeText(this@MainActivity, "Failed", Toast.LENGTH_SHORT).show()
                        }
                    }
                    /*if (Build.VERSION.SDK_INT >= 19) {
                        handleImageOnKitkat(data)
                    } else {
                        handleImageBeforeKitkat(data)
                    }*/
                }
        }
    }

    fun saveImage(myBitmap: Bitmap): String {
        val bytes = ByteArrayOutputStream()
        myBitmap.compress(Bitmap.CompressFormat.PNG, 90, bytes)
        val wallpaperDirectory = File(
            (Environment.getExternalStorageDirectory()).toString() + IMAGE_DIRECTORY
        )
        Log.d("fee", wallpaperDirectory.toString())
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs()
        }
        try {
            Log.d("heel", wallpaperDirectory.toString())
            val f = File(
                wallpaperDirectory, ((Calendar.getInstance()
                    .getTimeInMillis()).toString() + ".png")
            )
            f.createNewFile()
            val fo = FileOutputStream(f)
            fo.write(bytes.toByteArray())
            MediaScannerConnection.scanFile(this, arrayOf(f.getPath()), arrayOf("image/png"), null)
            fo.close()
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath())

            return f.getAbsolutePath()
        } catch (e1: IOException) {
            e1.printStackTrace()
        }
        return ""
    }


/*
    @TargetApi(19)
    private fun handleImageOnKitkat(data: Intent?) {
        var imagePath: String? = null
        val uri = data?.data
        if (DocumentsContract.isDocumentUri(this, uri)) {
            val docId = DocumentsContract.getDocumentId(uri)
            com.android.providers.media.documents

            if ("com.pepsidrc" == uri?.authority) {
                val id = docId.split(":")[1]
                val selsetion = MediaStore.Images.Media._ID + "=" + id
                imagePath = imagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selsetion)
            } else if ("com.android.providers.downloads.documents" == uri?.authority) {
                val contentUri = ContentUris.withAppendedId(
                    Uri.parse("content://downloads/public_downloads"),
                    java.lang.Long.valueOf(docId)
                )
                imagePath = imagePath(contentUri, null)
            }
        } else if ("content".equals(uri?.scheme, ignoreCase = true)) {
            imagePath = imagePath(uri!!, null)
        } else if ("file".equals(uri?.scheme, ignoreCase = true)) {
            imagePath = uri?.path
        }
        displayImage(imagePath)
    }
*/

    private fun handleImageBeforeKitkat(data: Intent?) {}

    private fun imagePath(uri: Uri, selection: String?): String {
        var path: String? = null
        val cursor = contentResolver.query(uri, null, selection, null, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
            }
            cursor.close()
        }
        return path!!
    }

    private fun displayImage(imagePath: String?) {
        if (imagePath != null) {
            val bitmap = BitmapFactory.decodeFile(imagePath)
            img_profile?.setImageBitmap(bitmap)
            if (NetworkUtil.isInternetAvailable(this)) {
                model.updateUser(
                    et_Profile_Name.text.toString(),
                    et_profile_email.text.toString(),
                    "",
                    imagePath
                )
            }
        } else {
            Toast.makeText(this, "Failed to get image", Toast.LENGTH_SHORT).show()
        }
    }

    override fun tag(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun title(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun titleColor(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun subscribeLoading() {

        model.searchEvent.observe(this, Observer {
            if (it.isLoading) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
            it.error?.run {
                UiUtils.showInternetDialog(this@ActivityProfile, R.string.something_went_wrong)
            }
        })
    }

    private fun subscribeUi() {
        model.userDetailResponse.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())

            setData(it)
            //showSnackbar(it.message,true)
            //gotoLogin()
        })

        model.userUpdateResponse.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())

            showData(it)
            //showSnackbar(it.message,true)
            //gotoLogin()
        })
        model.changepasswordResponse.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())
            if (it.success) {
                showSnackbar(it.message, false)
                etConfirmPasswordtxt.setText("")
                etOldPassword.setText("")
                etNewPassword.setText("")
                llPasswordChange.visibility = View.GONE
                btnChangePassword.visibility = View.GONE

            } else {
                //  showData(it)
                showSnackbar(it.errors?.get(0)?.fieldLabel + " " + it.errors?.get(0)?.detail, false)

            }//gotoLogin()
        })

    }

    override fun onResume() {
        super.onResume()
        tv_tool_title.text = AndroidUtils.getString(R.string.my_profile)
        iv_search.visibility = View.GONE
        iv_notification.visibility = View.GONE
        iv_cart.visibility = View.GONE

    }

    fun setData(userDetailResponse: UserDetailResponse) {
        et_Profile_Name?.setText(userDetailResponse?.data?.user?.firstName + " " + userDetailResponse?.data?.user?.lastName)
        et_profile_email?.setText(userDetailResponse?.data?.user?.email)
        er_profile_ern_number?.setText(userDetailResponse?.data?.user?.ernNumber)
        et_profile_date_of_joining?.setText(AndroidUtils.getDate(userDetailResponse?.data?.user?.createdAt))
        ImageRequestManager.with(img_profile)
            .setPlaceholderImage(R.drawable.profile)
            .url(userDetailResponse?.data?.user?.profilePicture?.url)
            .circular(true)
            .build()
    }

    fun showData(data: UserDetailResponse) {
        if (data!!.success) {
            ImageRequestManager.with(img_profile)
                .setPlaceholderImage(R.drawable.ic_launcher)
                .url(data?.data?.user?.profilePicture?.url)
                .circular(true)
                .build()
            showSnackbar(
                data?.message,
                false
            )
        } else {
            if (data?.message.equals("")) {
                data?.errors?.let {
                    if (it.size > 0) {
                        Logger.Debug("DEBUG", data.errors?.get(0).toString())
                        showSnackbar(
                            data.errors?.get(0)?.fieldLabel + " " + data.errors?.get(0)?.detail,
                            false
                        )
                    }
                }
            } else {
                showSnackbar(
                    data?.message,
                    false
                )
            }
        }

    }

    fun fetchData() {

        this?.let { UiUtils.hideSoftKeyboard(it) }


        if (NetworkUtil.isInternetAvailable(this)) {
            model.fetchUser()
        }

    }

    fun showProgressDialog() {

        showProgressDialog(null, AndroidUtils.getString(R.string.please_wait))
    }
}
