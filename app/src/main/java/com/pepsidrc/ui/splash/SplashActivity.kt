package com.pepsidrc.ui.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.pepsidrc.R
import com.pepsidrc.base.BaseActivity
import com.pepsidrc.ui.login.LoginActivity
import com.pepsidrc.ui.navigation.LandingNavigationActivity
import com.pepsidrc.ui.selectAuth.SelectedAuthentication

class SplashActivity : BaseActivity<SplashViewModel>(SplashViewModel::class) {
    override fun tag(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun title(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun titleColor(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun layout(): Int = R.layout.activity_splash


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout())

        subscribeUi()

        model.loadData()
    }

    private fun subscribeUi() {
        model.nextIntent.observe(this, Observer {
            startActivity(
                when (it) {
                    SPLASH_NEXT_HOME_ACTIVITY -> {

                        LandingNavigationActivity.getIntent(this,1)
                    }
                    SPLASH_NEXT_ON_LOGIN_ACTIVITY -> {

                        SelectedAuthentication.getIntent(this)
                    }
                    else -> SelectedAuthentication.getIntent(this)
                }
            )

            finish()
        })
    }
}
