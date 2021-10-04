package com.pepsidrc.ui.login

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.lifecycle.Observer
import com.pepsidrc.R
import com.pepsidrc.base.BaseActivity
import com.pepsidrc.ui.navigation.LandingNavigationActivity
import com.pepsidrc.ui.resetPassword.ActivityResetPassword
import com.pepsidrc.ui.signup.ActivitySignUp
import com.pepsidrc.util.UiUtils
import com.pepsidrc.utils.AndroidUtils
import com.pepsidrc.utils.Logger
import com.pepsidrc.utils.NetworkUtil
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity<LoginViewModel>(LoginViewModel::class) {

    companion object {
        fun getIntent(context: Context): Intent? {
            val intent = Intent(context, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            return intent
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

    override fun layout(): Int = R.layout.activity_login

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tvForgot.setOnClickListener { doResetPassword() }
        tvnewUserSignUp.setOnClickListener { }
        btnLogin.setOnClickListener { doSignIn() }
        tvnewUserSignUp.setOnClickListener { doSignUp() }
        /*etUsername.setOnFocusChangeListener { view, b ->
            if(b) {
                etUsername?.backgroundResource = R.drawable.rounded_corners_app_pink_edittext_bg
            }
            else{
                etUsername?.backgroundResource = R.drawable.rounded_corners_app_grey_edittext_bg

            }
        }
        etPasswordTextInput.setOnFocusChangeListener { view, b ->
            if(b) {
                etPasswordTextInput?.backgroundResource = R.drawable.rounded_corners_app_pink_edittext_bg
            }
            else{
                etPasswordTextInput?.backgroundResource = R.drawable.rounded_corners_app_grey_edittext_bg

            }
        }
        etERNNUmber.setOnFocusChangeListener { view, b ->
            if(b) {
                etERNNUmber?.backgroundResource = R.drawable.rounded_corners_app_pink_edittext_bg
            }
            else{
                etERNNUmber?.backgroundResource = R.drawable.rounded_corners_app_grey_edittext_bg

            }
        }*/
        subscribeLoading()
        subscribeUiLogin()
        etUsername.setOnFocusChangeListener { view, hasFocus ->
            if (!hasFocus) {

                try {
                    val validateEmailError =
                        AndroidUtils.validateEmail(etUsername.text.toString())

                    val validateEmailValidError =
                        AndroidUtils.validateEmail(etUsername.text.toString())
                    if (TextUtils.isEmpty(validateEmailError) && TextUtils.isEmpty(
                            validateEmailValidError
                        )
                    ) {
                        etUsername.setCompoundDrawablesWithIntrinsicBounds(
                            0,
                            0,
                            R.drawable.vector_smart_object,
                            0
                        )
                    } else {
                        etUsername.error = validateEmailValidError
                        etUsername.error = validateEmailError

                    }
                }
                catch(e: Exception){

                }

            }

        }

    }

    fun gotoHome() {

        this.let { UiUtils.hideSoftKeyboard(it) }
        startActivity(LandingNavigationActivity.getIntent(this,1))

    }

    fun doResetPassword() {

        this.let { UiUtils.hideSoftKeyboard(it) }
        startActivity(ActivityResetPassword.getIntent(this))

    }

    fun doSignUp() {

        this.let { UiUtils.hideSoftKeyboard(it) }
        startActivity(
            ActivitySignUp.getIntent(this),
            ActivityOptions.makeSceneTransitionAnimation(this).toBundle()
        )

    }

    fun doSignIn() {

        this?.let { UiUtils.hideSoftKeyboard(it) }

        val validateEmailError = AndroidUtils.validateEmail(etUsername.text.toString())
        val validatePasswordError = AndroidUtils.validatePassword(etPassword.text.toString())
        if (etERNNUmber.text.toString().isEmpty()) {
            if (
                TextUtils.isEmpty(validateEmailError)

            ) {
                if (NetworkUtil.isInternetAvailable(this)) {
                    model.login(etUsername.text.toString(), etPassword.text.toString())
                }

            } else {
                etUsername.error = validateEmailError
                etPasswordTextInput.error = validatePasswordError
            }


        } else {
            if (NetworkUtil.isInternetAvailable(this)) {
                model.loginWithERNNumber(etERNNUmber.text.toString(), etPassword.text.toString())
            }
        }
    }

    private fun subscribeLoading() {

        model.searchEvent.observe(this, Observer {
            if (it.isLoading) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
            it.error?.run {
                UiUtils.showInternetDialog(this@LoginActivity, R.string.something_went_wrong)
            }
        })
    }

    private fun subscribeUiLogin() {
        model.loginData.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())
            showSnackbar("Login Success", true)
            if (rememberCheck.isChecked) {
                model.saveUserDetail(it.data?.response?.token, it.data.response.user,true)
            }else{
                model.saveUserDetail(it.data?.response?.token, it.data.response.user,false)

            }
            this.let { UiUtils.hideSoftKeyboard(it) }
            startActivity(
                LandingNavigationActivity.getIntent(this,1),
                ActivityOptions.makeSceneTransitionAnimation(this).toBundle()
            )
        })
        model.loginDataError.observe(this, Observer {
            Logger.Debug("DEBUG", it.errors)
            showSnackbar(it.errors, false)

        })
    }

    fun showProgressDialog() {
        showProgressDialog(null, AndroidUtils.getString(R.string.please_wait))
    }
}
