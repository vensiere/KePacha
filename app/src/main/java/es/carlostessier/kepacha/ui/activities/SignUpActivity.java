package es.carlostessier.kepacha.ui.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import es.carlostessier.kepacha.R;


public class SignUpActivity extends Activity {

    private EditText usernameField;
    private EditText passwordField;
    private EditText emailAddressField;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initializeViews();

    }

    private void errorFieldDialog(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
        builder.setMessage(message);
        builder.setPositiveButton(android.R.string.ok, null);
        builder.setTitle(R.string.signup_error_title);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        AlertDialog dialog = builder.create();

        dialog.show();

    }

    public void actionSignupButton(View v){

        String sUsername = usernameField.getText().toString().trim();
        String sPassword = passwordField.getText().toString().trim();
        String sEmailAddress = emailAddressField.getText().toString().trim();


        String message ;

        if (sUsername.isEmpty())
        {
            message = String.format(getString(R.string.empty_field_message),getString(R.string.username_hint));
            errorFieldDialog(message);
        }
        else if (sPassword.isEmpty())
        {
            message = String.format(getString(R.string.empty_field_message),getString(R.string.password_hint));
            errorFieldDialog(message);

        }
        else if (sEmailAddress.isEmpty())
        {
            message = String.format(getString(R.string.empty_field_message),getString(R.string.email_hint));
            errorFieldDialog(message);

        }
        else{
           addUser(sUsername,sPassword,sEmailAddress);
        }



    }

    private void addUser(final String username, String password, String email) {

        ParseUser user = new ParseUser();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);


        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if(e==null){
                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                else{
                    String message = String.format(getString(R.string.add_user_error_message),username);
                    errorFieldDialog(message);
                }

            }
        });
    }

    private void initializeViews() {
        usernameField = (EditText) findViewById(R.id.usernameField);
        passwordField = (EditText) findViewById(R.id.passwordField);
        emailAddressField = (EditText) findViewById(R.id.emailField);
    }



}
