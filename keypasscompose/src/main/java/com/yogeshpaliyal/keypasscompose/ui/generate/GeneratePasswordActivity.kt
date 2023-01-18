package com.yogeshpaliyal.keypasscompose.ui.generate

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.yogeshpaliyal.common.utils.PasswordGenerator
import com.yogeshpaliyal.keypasscompose.R
import com.yogeshpaliyal.keypasscompose.databinding.ActivityGeneratePasswordBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GeneratePasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGeneratePasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGeneratePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        generatePassword()

        binding.btnRefresh.setOnClickListener {
            generatePassword()
        }

        binding.tilPassword.setEndIconOnClickListener {
            val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("random_password", binding.etPassword.text)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(this, getString(R.string.copied_to_clipboard), Toast.LENGTH_SHORT).show()
        }
    }

    private fun generatePassword() {
        val password = PasswordGenerator(
            length = binding.sliderPasswordLength.value.toInt(),
            includeUpperCaseLetters = binding.cbCapAlphabets.isChecked,
            includeLowerCaseLetters = binding.cbLowerAlphabets.isChecked,
            includeSymbols = binding.cbSymbols.isChecked,
            includeNumbers = binding.cbNumbers.isChecked
        ).generatePassword()

        binding.etPassword.setText(password)
        binding.etPassword.setSelection(password.length)
    }
}
