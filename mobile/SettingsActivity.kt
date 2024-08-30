package com.example.mygooglecalendar

import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import android.widget.CheckBox

class SettingsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val sharedPreferences = getSharedPreferences("AppSettings", Context.MODE_PRIVATE)

        val urlEditText = findViewById<EditText>(R.id.url_edittext)
        val usernameEditText = findViewById<EditText>(R.id.username_edittext)
        val passwordEditText = findViewById<EditText>(R.id.password_edittext)
        val showPasswordCheckbox = findViewById<CheckBox>(R.id.show_password_checkbox)

        // Load existing values if they exist
        urlEditText.setText(sharedPreferences.getString("url", ""))
        usernameEditText.setText(sharedPreferences.getString("username", ""))
        passwordEditText.setText(sharedPreferences.getString("password", ""))

        findViewById<Button>(R.id.save_button).setOnClickListener {
            with(sharedPreferences.edit()) {
                putString("url", urlEditText.text.toString())
                putString("username", usernameEditText.text.toString())
                putString("password", passwordEditText.text.toString())
                apply()
            }
            finish() // Close the settings activity
        }

        findViewById<Button>(R.id.cancel_button).setOnClickListener {
            finish() // Close the settings activity without saving
        }

        // Toggle password visibility when the checkbox is checked/unchecked
        showPasswordCheckbox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                passwordEditText.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                passwordEditText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
            // Move cursor to the end of the text after toggling
            passwordEditText.setSelection(passwordEditText.text.length)
        }

    }
}
