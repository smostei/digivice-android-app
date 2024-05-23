package com.example.digivice_android_app.framework.presentation.login.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.digivice_android_app.databinding.FragmentLoginBinding
import com.example.digivice_android_app.framework.extensions.finishOnBackPressed
import com.example.digivice_android_app.framework.extensions.navigateTo
import com.example.digivice_android_app.framework.extensions.viewBinding
import com.example.digivice_android_app.framework.presentation.login.viewModel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val binding by viewBinding {
        FragmentLoginBinding.inflate(layoutInflater)

    }
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addObservers()
        setListeners()
    }

    private fun addObservers() = with(viewModel) {
        showLoader.observe(viewLifecycleOwner) { handleProgressVisibility(it) }
        onLoginFailed.observe(viewLifecycleOwner) { onUserLoginFailed(it) }
        onNavigationEvent.observe(viewLifecycleOwner) { navigateTo(it) }
        clearInputError.observe(viewLifecycleOwner) { clearTextFromError() }
    }

    private fun setListeners() = with(binding) {
        loginButton.setOnClickListener {
            viewModel.getUser(
                usernameInputEditText.text?.trim().toString(),
                passwordInputEditText.text?.trim().toString()
            )
        }

        usernameInputEditText.setOnFocusChangeListener { _, _ ->
            viewModel.resetEditText(usernameInputLayout.error)
        }

        passwordInputEditText.setOnFocusChangeListener { _, _ ->
            viewModel.resetEditText(passwordInputLayout.error)
        }

        finishOnBackPressed()
    }

    private fun clearTextFromError() = with(binding) {
        usernameInputLayout.error = null
        passwordInputLayout.error = null
        usernameInputEditText.text?.clear()
        passwordInputEditText.text?.clear()
    }

    private fun onUserLoginFailed(errorTextResource: Int) = with(binding) {
        usernameInputLayout.error = getString(errorTextResource)
        passwordInputLayout.error = getString(errorTextResource)
    }

    private fun handleProgressVisibility(show: Boolean) = with(binding) {
        progress.isVisible = show
        groupView.isVisible = !show
    }

}