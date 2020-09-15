package com.wbrawner.flayre.app.login

import android.content.Intent
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.fragment.app.viewModels
import androidx.preference.PreferenceManager
import com.wbrawner.flayre.app.FlayreApplication
import com.wbrawner.flayre.app.MainActivity
import com.wbrawner.flayre.app.R
import com.wbrawner.flayre.app.di.KEY_AUTH
import com.wbrawner.flayre.app.di.KEY_SERVER
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_login, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        signInButton.setOnClickListener {
            PreferenceManager.getDefaultSharedPreferences(view.context).edit {
                putString(KEY_SERVER, serverUrl.text.toString())
                val auth = "${username.text.toString()}:${password.text.toString()}"
                    .toByteArray()
                    .base64Encode()
                putString(KEY_AUTH, auth)
            }
            try {
                (requireActivity().application as FlayreApplication).apply {
                    resetAppComponent()
                    appComponent.inject(viewModel)
                }
                viewModel.loadApps()
                startActivity(Intent(view.context.applicationContext, MainActivity::class.java))
            } catch (e: Exception) {
                Log.e("LoginFragment", "Unable to authenticate with Flayre server", e)
                username.error = "Authentication failed"
                password.error = "Authentication failed"
            }
        }
    }
}

fun ByteArray.base64Encode(): String = Base64.encodeToString(this, 0)
    .replace(Regex("\\s+"), "")