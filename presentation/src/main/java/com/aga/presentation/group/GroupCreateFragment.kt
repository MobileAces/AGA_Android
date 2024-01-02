package com.aga.presentation.group

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.fragment.app.viewModels
import com.aga.presentation.GroupActivity
import com.aga.presentation.R
import com.aga.presentation.base.BaseFragment
import com.aga.presentation.base.Constants
import com.aga.presentation.databinding.FragmentGroupCreateBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GroupCreateFragment : BaseFragment<FragmentGroupCreateBinding>(
    FragmentGroupCreateBinding::bind, R.layout.fragment_group_create
) {
    private lateinit var activity: GroupActivity
    private val groupCreateViewModel: GroupCreateViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity = requireActivity() as GroupActivity

//        // 백버튼 클릭시 그룹 리스트 프래그먼트로 전환
//        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
//            activity.navigate(Constants.TO_GROUP)
//        }

        registerListener()
        registerObserve()
    }

    private fun registerListener() {
        binding.btnCreateGroup.setOnClickListener {
            binding.btnCreateGroup.isEnabled = false
            if (checkValidationInput()) {
                groupCreateViewModel.createGroup(
                    binding.etGroupName.editText!!.text.toString().trim(),
                    binding.etGroupIntroduction.editText!!.text.toString().trim()
                )
            }
        }

        binding.etGroupIntroduction.editText!!.setOnKeyListener { view, i, keyEvent ->
            if ((keyEvent.action == KeyEvent.ACTION_DOWN) && (i == KeyEvent.KEYCODE_ENTER)) {
                hideKeyboard()
                true
            } else
                false
        }
    }

    private fun registerObserve() {
        groupCreateViewModel.createGroupResult.observe(viewLifecycleOwner) { result ->
            binding.btnCreateGroup.isEnabled = true
            if (result) {
                showToast("그룹 생성에 성공했습니다.")
                activity.navigate(Constants.TO_GROUP)
            } else {
                showToast("그룹 생성에 실패했습니다.")
            }
        }
    }

    // 그룹 이름, 소개 유효성 검사
    private fun checkValidationInput(): Boolean {
        val name = binding.etGroupName.editText!!.text.toString().trim()

        return if (name.length < 3) {
            showToast("그룹 이름은 3글자 이상이어야 합니다.")
            false
        } else {
            true
        }
    }
}