package com.pepsidrc.ui.selectAuth

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pepsidrc.R
import com.pepsidrc.ui.login.LoginActivity
import com.pepsidrc.ui.resetPassword.ActivityResetPassword
import com.pepsidrc.ui.signup.ActivitySignUp
import com.pepsidrc.util.UiUtils
import kotlinx.android.synthetic.main.activity_selected_authentication.*

class SelectedAuthentication : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selected_authentication)
        rl_sign_in.setOnClickListener {
            this.let { UiUtils.hideSoftKeyboard(it) }
            startActivity(
                LoginActivity.getIntent(this),
                ActivityOptions.makeSceneTransitionAnimation(this).toBundle()
            )
        }
        rl_sign_up.setOnClickListener {
            this.let { UiUtils.hideSoftKeyboard(it) }
            startActivity(
                ActivitySignUp.getIntent(this),
                ActivityOptions.makeSceneTransitionAnimation(this).toBundle()
            )
        }
    }

    companion object {
        fun getIntent(context: Context): Intent? {
            val intent = Intent(context, SelectedAuthentication::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            return intent
        }
    }

}
