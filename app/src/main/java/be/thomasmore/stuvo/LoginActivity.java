package be.thomasmore.stuvo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
// import android.support.v7.app.AppCompatActivity;


public class LoginActivity extends AppCompatActivity {

    private TextInputLayout inputLayoutName, inputLayoutPass;
    private EditText inputName, inputPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputLayoutName = findViewById(R.id.login_username);
        inputName = inputLayoutName.getEditText();

        inputLayoutPass = findViewById(R.id.login_password);
        inputPass = inputLayoutPass.getEditText();
    }

    //OnClick Log in button
    public void checkLogin_onClick(View v) {
        Log.e("666666", inputName.getText() + "");
        Log.e("666666", inputPass.getText() + "");
        validate(inputName.getText().toString(), inputPass.getText().toString());
    }

    //Valideer ingevoerde waardes
    private void validate(String name, String pass){
        validateNewActivity();
        if((name.equals("Admin")) && (pass.equals("1234"))){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    //Cheat login voor coding - IN COMMENTAAR ZETTEN IN LAATSTE VERSIE!!!!
    public void cheat_onClick(View v) {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }


    //Validatie
    private Boolean validateNewActivity() {
        boolean b = true;

        if (!validateName()) {
            b = false;
        }

        if (!validatePass()) {
            b = false;
        }
        return b;
    }

    private boolean validateName() {
        if (inputName.getText().toString().trim().isEmpty()) {
            inputLayoutName.setError(getString(R.string.err_msg_login_username));
            requestFocus(inputName);
            return false;
        } else {
            inputLayoutName.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validatePass() {
        if (inputPass.getText().toString().trim().isEmpty()) {
            inputLayoutName.setError(getString(R.string.err_msg_login_password));
            requestFocus(inputPass);
            return false;
        } else {
            inputLayoutPass.setErrorEnabled(false);
        }
        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}