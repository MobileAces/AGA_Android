package com.aga.presentation

import android.annotation.TargetApi
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.viewModels
import com.aga.presentation.base.BaseActivity
import com.aga.presentation.base.Constants.JOINONE_TO_JOINTWO
import com.aga.presentation.base.Constants.JOINTWO_TO_JOINTHREE
import com.aga.presentation.base.Constants.JOIN_TO_LOGIN
import com.aga.presentation.base.Constants.LOGIN_TO_JOIN
import com.aga.presentation.base.Constants.PREF_AUTO_LOGIN
import com.aga.presentation.base.PrefManager
import com.aga.presentation.databinding.ActivityLoginBinding
import com.aga.presentation.login.JoinOneFragment
import com.aga.presentation.login.JoinThreeFragment
import com.aga.presentation.login.JoinTwoFragment
import com.aga.presentation.login.JoinViewModel
import com.aga.presentation.login.LoginFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>(
    ActivityLoginBinding::inflate
) {
    private val viewModel: JoinViewModel by viewModels()

    init {
        checkPermission()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 자동로그인 처리 부분이라 나중에 Splash로 빼주면 됩니다.
        if (PrefManager.read(PREF_AUTO_LOGIN, false)){
            val intent = Intent(this, GroupActivity::class.java)
            startActivity(intent)
            this.finish()
        }

    }

    private fun checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {   // 마시멜로우 이상일 경우
            if (!Settings.canDrawOverlays(this)) {              // 다른앱 위에 그리기 체크
                val uri = Uri.fromParts("package", packageName, null)
                val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, uri)
                startActivityForResult(intent, ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE)
            } else {
                startMain()
            }
        } else {
            startMain()
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE) {
            if (!Settings.canDrawOverlays(this)) {
                finish()
            } else {
                startMain()
            }
        }
    }

    private fun startMain(){
        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_login, LoginFragment())
            .commit()
    }

    fun navigate(id: Int){
        when(id){
            LOGIN_TO_JOIN -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fl_login, JoinOneFragment())
                    .addToBackStack("join")
                    .commit()
            }

            JOINONE_TO_JOINTWO -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fl_login, JoinTwoFragment())
                    .addToBackStack("join_two")
                    .commit()
            }

            JOINTWO_TO_JOINTHREE -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fl_login, JoinThreeFragment())
                    .addToBackStack("join_three")
                    .commit()
            }
            JOIN_TO_LOGIN -> {
                supportFragmentManager.clearBackStack("join")
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fl_login, LoginFragment())
                    .commit()
            }
        }
    }

    companion object{
        const val ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE = 50001
    }
}