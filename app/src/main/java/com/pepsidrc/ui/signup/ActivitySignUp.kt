package com.pepsidrc.ui.signup

import android.app.ActivityOptions
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import androidx.lifecycle.Observer
import com.pepsidrc.util.UiUtils
import com.pepsidrc.R
import com.pepsidrc.base.BaseActivity
import com.pepsidrc.managers.PreferenceManager
import com.pepsidrc.ui.login.LoginActivity
import com.pepsidrc.ui.navigation.LandingNavigationActivity
import com.pepsidrc.utils.AndroidUtils
import com.pepsidrc.utils.Config
import com.pepsidrc.utils.Logger
import com.pepsidrc.utils.NetworkUtil
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_sign_up.*

class ActivitySignUp : BaseActivity<SignUpViewModel>(SignUpViewModel::class) {

    override fun layout(): Int = R.layout.activity_sign_up

    companion object {
        fun getIntent(context: Context?): Intent? {
            if (context == null) {
                return null
            }

            return Intent(context, ActivitySignUp::class.java)

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        btnSignUp.setOnClickListener { gotoHome() }
        tvExistingUSerSignIn.setOnClickListener { gotoLogin() }
        btnSignUp.setOnClickListener { doSignUp() }
        subscribeLoading()
        subscribeUiSignUp()
        etUsernameSignUp.setOnFocusChangeListener { view, hasFocus ->
            if (!hasFocus) {

                try {
                    val validateEmailError =
                        AndroidUtils.validateEmail(etUsernameSignUp.text.toString())

                    val validateEmailValidError =
                        AndroidUtils.validateEmail(etUsernameSignUp.text.toString())
                    if (TextUtils.isEmpty(validateEmailError) && TextUtils.isEmpty(
                            validateEmailValidError
                        )
                    ) {
                        etUsernameSignUp.setCompoundDrawablesWithIntrinsicBounds(
                            0,
                            0,
                            R.drawable.vector_smart_object,
                            0
                        )
                    } else {
                        etUsernameSignUp.error = validateEmailValidError
                        etUsernameSignUp.error = validateEmailError

                    }
                }
                catch(e: Exception){

                }

            }

        }
        etConfirmPassword.setOnFocusChangeListener { view, hasFocus ->
            if (!hasFocus) {
                try{
                val validatePasswordMatchError = AndroidUtils.validateMatchPassword(etPasswordsignUp.text.toString(),etConfirmPassword.text.toString())

                if( TextUtils.isEmpty(validatePasswordMatchError)){
                    etConfirmPassword.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.vector_smart_object,0)
                }
                else{
                    etConfirmPassword.error = validatePasswordMatchError

                }
                }
                catch(e: Exception){

                }
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

    fun gotoHome() {

        this.let { UiUtils.hideSoftKeyboard(it) }
        startActivity(LandingNavigationActivity.getIntent(this,1))

    }

    fun gotoLogin() {

        this.let { UiUtils.hideSoftKeyboard(it) }
        startActivity(LoginActivity.getIntent(this), ActivityOptions.makeSceneTransitionAnimation(this).toBundle())

    }

    fun doSignUp() {

        this?.let { UiUtils.hideSoftKeyboard(it) }

        val validateEmailError = AndroidUtils.validateEmail(etUsernameSignUp.text.toString())
        val validatePasswordError = AndroidUtils.validatePassword(etPasswordsignUp.text.toString())
        val validatePasswordMatchError = AndroidUtils.validateMatchPassword(etPasswordsignUp.text.toString(),etConfirmPassword.text.toString())
        val validateEmailValidError = AndroidUtils.validateEmail(etUsernameSignUp.text.toString())

        if (
            TextUtils.isEmpty(validateEmailError) &&
            TextUtils.isEmpty(validatePasswordError) &&
            TextUtils.isEmpty(validatePasswordMatchError) &&
            TextUtils.isEmpty(validateEmailValidError)
        ) {
            if (NetworkUtil.isInternetAvailable(this)) {

                if(etErnNumber.text.toString().length==0 || etErnNumber.text.toString().length>=6) {
                    model.signUp(
                        etUsernameSignUp.text.toString(),
                        etPasswordsignUp.text.toString(), etConfirmPassword.text.toString(),
                        etErnNumber.text.toString(), etFullName.text.toString()
                    )
                }else{
                    etErnNumber.error = AndroidUtils.getString(R.string.erp_validation)
                }
            }

        } else {
            etUsernameSignUp.error = validateEmailError
            etPasswordsignUp.error = validatePasswordError
            etConfirmPassword.error = validatePasswordMatchError
            etUsernameSignUp.error = validateEmailValidError

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
                UiUtils.showInternetDialog(this@ActivitySignUp, R.string.something_went_wrong)
            }
        })
    }
    private fun subscribeUiSignUp() {
        model.signUpModel.observe(this, Observer {
            Logger.Debug("DEBUG",it.toString())
            showSnackbar(it.message,true)
            signin(it.message)
         //   gotoLogin()
        })
        model.signUpModelError.observe(this, Observer {
            Logger.Debug("DEBUG",it.errors?.get(0).toString())
            showSnackbar(it.errors?.get(0)?.fieldLabel+ " "+ it.errors?.get(0)?.detail,false)
        })
    }

    fun showProgressDialog() {

        showProgressDialog(null, AndroidUtils.getString(R.string.please_wait))
    }

    private fun signin(msg : String?) {
        let {
            val dialogBuilder = AlertDialog.Builder(it)

            // set message of alert dialog
            dialogBuilder.setMessage(msg)
                // if the dialog is cancelable
                .setCancelable(false)
                // positive button text and action
                .setPositiveButton("Ok", DialogInterface.OnClickListener { dialog, id ->

                    dialog.dismiss()
                    gotoLogin()



                })


            // create dialog box
            val alert = dialogBuilder.create()
            // set title for alert dialog box
            // alert.setTitle(AndroidUtils.getString(R.string.logout))
            // show alert dialog
            alert.show()
        }
    }

}
