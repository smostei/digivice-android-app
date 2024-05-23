package com.example.digivice_android_app.framework.extensions

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.MainThread
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.NavHostFragment
import androidx.viewbinding.ViewBinding
import com.example.digivice_android_app.R
import com.example.digivice_android_app.domain.NavigationEvent
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

fun Fragment.showErrorMessage() {
    Toast.makeText(requireContext(), getString(R.string.generic_error_message), Toast.LENGTH_SHORT).show()
}

fun Fragment.showShortMessage(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}

fun Fragment.back() {
    childFragmentManager.executePendingTransactions()
    requireActivity().onBackPressed()
}

fun String.capitalized(): String {
    return this.replaceFirstChar {
        if (it.isLowerCase()) {
            it.uppercase()
        } else {
            it.toString()
        }
    }
}

fun Fragment.navigateTo(navigationEvent: NavigationEvent) {
    try {
        NavHostFragment.findNavController(this).navigate(
            navigationEvent.navigationActionId,
            navigationEvent.bundle
        )
    } catch (e: Exception) {
        showErrorMessage()
    }
}

fun Fragment.finishOnBackPressed() {
    val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            requireActivity().finish()
        }
    }
    requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
}

/**
 *  lazy delegate that inflates the binding on Activities
 **/
inline fun <T : ViewBinding> AppCompatActivity.viewBinding(crossinline bindingInflater: (LayoutInflater) -> T) =
        lazy(LazyThreadSafetyMode.NONE) {
            bindingInflater.invoke(layoutInflater)
        }

/**
 *  lazy delegate that inflates the binding on Fragments
 **/
inline fun <T : ViewBinding> Fragment.viewBinding(crossinline factory: (View?) -> T): ReadOnlyProperty<Fragment, T> =
    object : ReadOnlyProperty<Fragment, T>, DefaultLifecycleObserver {
        private var binding: T? = null

        @MainThread
        override fun getValue(thisRef: Fragment, property: KProperty<*>): T =
            binding ?: factory(view).also {
                // if binding is accessed after Lifecycle is DESTROYED, create new instance, but don't cache it
                if (viewLifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
                    viewLifecycleOwner.lifecycle.addObserver(this)
                    binding = it
                }
            }

        override fun onDestroy(owner: LifecycleOwner) {
            viewLifecycleOwner.lifecycle.removeObserver(this)
            // Fragment.viewLifecycleOwner call LifecycleObserver.onDestroy() before Fragment.onDestroyView().
            // That's why we need to postpone reset of the viewBinding
            Handler(Looper.getMainLooper()).post {
                binding = null
            }
        }
    }

inline fun <T1: Any, T2: Any, R: Any> doLet(p1: T1?, p2: T2?, invoke: (T1, T2)->R?): R? {
    return if (p1 != null && p2 != null) invoke(p1, p2) else null
}