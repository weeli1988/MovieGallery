package com.example.moviegallery.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.moviegallery.R
import com.example.moviegallery.databinding.FragmentLoginBinding

class LoginFragment : BaseDialogFragment() {

    // hardcoded username and password, not recommended
    // proper way:
    // 1) authenticate thru web rest api, get response and react based on response, usually in json format
    // 2) default user in room database, with a mechanism to add or remove user
    private val defaultUserName = "VVVBB"
    private val defaultUserPassword = "@bcd1234"

    private lateinit var binding : FragmentLoginBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.header.textviewTitle.text = getString(R.string.app_name_in_app)

        binding.buttonExit.setOnClickListener {
            setActionResult(ActionResult.CANCEL)
            dismiss()
        }

        binding.layoutLogin.buttonLogin.setOnClickListener {

            // get text from edittext
            val userName = binding.layoutLogin.edittextUsername.text.toString().trim()
            val userPassword = binding.layoutLogin.edittextPassword.text.toString().trim()
            // authenticate
            authenticate(userName, userPassword)
        }

        return view
    }

    // not used at this moment
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun authenticate(userName: String, userPassword: String) {

        // check if username is empty
        if (userName.isEmpty()) {
            Toast.makeText(requireContext(), getString(R.string.username_is_empty), Toast.LENGTH_SHORT).show()
            return
        }

        // check if password is empty
        if (userPassword.isEmpty()) {
            Toast.makeText(requireContext(), getString(R.string.password_is_empty), Toast.LENGTH_SHORT).show()
            return
        }

        // auth username and password
        if (userName != defaultUserName || userPassword != defaultUserPassword) {
            Toast.makeText(requireContext(), getString(R.string.username_password_invalid), Toast.LENGTH_SHORT).show()
            return
        }

        // auth successful
        // go to landing page
        setActionResult(ActionResult.OK)
        dismiss()
        Toast.makeText(requireContext(), getString(R.string.login_successfully), Toast.LENGTH_SHORT).show()
    }
}