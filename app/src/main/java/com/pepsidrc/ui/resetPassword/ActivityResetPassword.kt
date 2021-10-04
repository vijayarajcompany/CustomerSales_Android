package com.pepsidrc.ui.resetPassword

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.pepsidrc.util.UiUtils
import com.pepsidrc.R
import com.pepsidrc.base.BaseActivity
import com.pepsidrc.ui.login.LoginActivity
import com.pepsidrc.ui.navigation.LandingNavigationActivity
import com.pepsidrc.utils.AndroidUtils
import com.pepsidrc.utils.Logger
import com.pepsidrc.utils.NetworkUtil
import kotlinx.android.synthetic.main.activity_reset_password.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.dialog_custom.view.*

class ActivityResetPassword : BaseActivity<ResetPasswordViewModel>(ResetPasswordViewModel::class) {
    override fun layout(): Int = R.layout.activity_reset_password

    companion object {
        fun getIntent(context: Context?): Intent? {
            if (context == null) {
                return null
            }

            return Intent(context, ActivityResetPassword::class.java)

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subscribeLoading()
        subscribeUi()
        btnSendALiknk.setOnClickListener {
            this?.let { UiUtils.hideSoftKeyboard(it) }

            val validateEmailError = AndroidUtils.validateEmail(etEmail.text.toString())

            if (
                TextUtils.isEmpty(validateEmailError)

            ) {
                if (NetworkUtil.isInternetAvailable(this)) {
                    model.resetPassword(etEmail.text.toString())

                }

            } else {
                etUsernameSignUp.error = validateEmailError
            }


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
                UiUtils.showInternetDialog(
                    this@ActivityResetPassword,
                    R.string.something_went_wrong
                )
            }
        })
    }

    private fun subscribeUi() {
        model.resetPasswordModel.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())
            // showSnackbar(it.message, true)
            if (it.success) {
                customAlertDialog(it.message)
                etEmail.setText("")
            } else {
                showSnackbar(it?.message, false)
            }
        })
        model.resetPasswordErrorModel.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())
            // showSnackbar(it.message, true)
            if (!it.success) {
                showSnackbar(it?.errors, false)
            }
        })
    }

    fun showProgressDialog() {

        showProgressDialog(null, AndroidUtils.getString(R.string.please_wait))
    }


    fun customAlertDialog(m: String) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this@ActivityResetPassword)
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val customView = inflater.inflate(R.layout.dialog_custom, null, false)

        builder.setView(customView)

        val alertDialog: AlertDialog = builder.create()
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.show()
        customView.tvMessage.text = m
        customView.tvSuccess.setOnClickListener({
            alertDialog.dismiss()
            gotoLogin()
        })


    }

    fun gotoLogin() {

        this.let { UiUtils.hideSoftKeyboard(it) }
        startActivity(
            LoginActivity.getIntent(this),
            ActivityOptions.makeSceneTransitionAnimation(this).toBundle()
        )

    }
}
