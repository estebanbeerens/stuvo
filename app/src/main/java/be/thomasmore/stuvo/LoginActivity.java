package be.thomasmore.stuvo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import be.thomasmore.stuvo.Database.HttpReader;
import be.thomasmore.stuvo.Database.JsonHelper;
import be.thomasmore.stuvo.Models.Student;
// import android.support.v7.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout inputLayoutName, inputLayoutPass;
    private EditText inputName, inputPass;
    private String name, pass;
    private boolean loginRight = false;

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
        Log.e("666666", inputName.getText() + " - Input username");
        Log.e("666666", inputPass.getText() + " - Input password");
        name = inputName.getText().toString();
        pass = inputPass.getText().toString();
        validate();
    }

    //Valideer ingevoerde waardes
    private void validate(){
        if (validateInputfields()) {
            validateStudentByUsername(name);
        } else {
            findViewById(R.id.login_error_nomatch).setVisibility(View.GONE);
        }
    }

    //PHP student ophalen op username
    private void validateStudentByUsername(String username) {
        HttpReader httpReader = new HttpReader();
        httpReader.setOnResultReadyListener(new HttpReader.OnResultReadyListener() {
            @Override
            public void resultReady(String result) {
                // Controleren als er iets in array zit
                // Er zit iets in de array ALS de username juist is
                if (result.equals("[]\n")) {
                    // Toon foutmelding als username fout is
                    findViewById(R.id.login_error_nomatch).setVisibility(View.VISIBLE);
                } else {
                    //Username bestaat! Controleer het wachtwoord
                    JsonHelper jsonHelper = new JsonHelper();
                    Student student = jsonHelper.getJSONStudent(result);
                    loginRight = validateUsernameAndPassword(student);
                    if(loginRight){
                        findViewById(R.id.login_error_nomatch).setVisibility(View.GONE);

                        Bundle bundle = new Bundle();
                        bundle.putLong("id", student.getId());
                        bundle.putString("username", student.getNumber());
                        bundle.putString("password", student.getPassword());
                        bundle.putString("firstName", student.getFirstName());
                        bundle.putString("lastName", student.getLastName());

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtras(bundle);
                        setResult(RESULT_OK, intent);
                        startActivity(intent);
                        finish();
                    } else {
                        // Toon foutmelding als username juist is maar pass fout
                        findViewById(R.id.login_error_nomatch).setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        httpReader.execute("https://beerensco.sinners.be/Maes/phpFiles/readStudentByUsername.php?username=\"" + username + "\"");
    }

    private boolean validateUsernameAndPassword(Student student) {
        Log.e("666666", student.getNumber() + " - JSON username");
        Log.e("666666", student.getPassword() + " - JSON password");
        if (student.getNumber().equals(name) && student.getPassword().equals(pass)) {
            return true;
        } else {
            return false;
        }
    }

    //Cheat login voor coding - IN COMMENTAAR ZETTEN IN LAATSTE VERSIE!!!!
    public void cheat_onClick(View v) {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }


    //Validatie
    private Boolean validateInputfields() {
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
            inputLayoutPass.setError(getString(R.string.err_msg_login_password));
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