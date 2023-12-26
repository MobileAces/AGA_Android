package com.aga.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aga.presentation.base.BaseActivity
import com.aga.presentation.databinding.ActivityGroupBinding

class GroupActivity : BaseActivity<ActivityGroupBinding>(
    ActivityGroupBinding::inflate
) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_group, GroupFragment())
            .commit()
    }
}