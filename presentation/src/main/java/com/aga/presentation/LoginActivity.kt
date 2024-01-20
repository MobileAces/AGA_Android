package com.aga.presentation

import android.Manifest
import android.annotation.TargetApi
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
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
    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val responsePermissions = permissions.entries.filter {
                it.key in locationPermissions
            }

            if (responsePermissions.filter { it.value }.size == locationPermissions.size) {
                Toast.makeText(this, "위치 기반 비 예보 서비스를 이용하실 수 있습니다.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "위치 기반 비 예보 서비스를 이용하실 수 없습니다.", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkPermission()
        permissionLauncher.launch(locationPermissions)

        // 자동로그인 처리 부분이라 나중에 Splash로 빼주면 됩니다.
        if (PrefManager.read(PREF_AUTO_LOGIN, false)){
            val intent = Intent(this, GroupActivity::class.java)
            startActivity(intent)
            this.finish()
        }



    }

    private fun checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {   // 마시멜로우 이상일 경우
            if (!Settings.canDrawOverlays(applicationContext)) {              // 다른앱 위에 그리기 체크
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
        val locationPermissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    }
}