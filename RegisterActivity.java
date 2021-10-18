package com.example.vms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    EditText username, password, email, phone;
    Button register;
    String uri = "";
    String Username = "", Password = "", Email = "", Phone = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().setTitle("Registration");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        uri = "http://"+IpAddressActivity.ipAddress+"/database/register.php";

        username = (EditText) findViewById(R.id.editTextTextUsername);
        password = (EditText) findViewById(R.id.editTextTextPassword);
        email = (EditText) findViewById(R.id.editTextTextEmailAddress);
        phone = (EditText) findViewById(R.id.editTextTextPhone);
        register = (Button) findViewById(R.id.buttonRegister);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validation();
            }
        });
    }

    public void validation() {
        int f = 0;

        if (username.getText().toString().isEmpty() == true) {
            username.setError("Enter Username");
            username.requestFocus();
        } else if (password.getText().toString().isEmpty() == true) {
            password.setError("Enter password");
            password.requestFocus();
        } else if (!(password.getText().toString().length() >= 4)) {
            password.setError("Your Password is Minimum 4 Digits");
            password.requestFocus();
        } else if (email.getText().toString().isEmpty() == true) {
            email.setError("Enter Your Email Address");
            email.requestFocus();
        } else if (!(Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches())) {
            email.setError("Enter Your Email Address");
            email.requestFocus();
        } else if (phone.getText().toString().isEmpty() == true) {
            phone.setError("Enter Phone Number");
            phone.requestFocus();
        } else if (!(phone.getText().toString().length() == 10)) {
            phone.setError("Your Phone number is 10 Digits");
            phone.requestFocus();
        } else {
            f = 1;
        }
        if (f == 1) {
            Username = username.getText().toString();
            Password = password.getText().toString();
            Email = email.getText().toString();
            Phone = phone.getText().toString();
            clearTextView();
            addUser();
        }
    }

    public void clearTextView() {
        username.setText("");
        password.setText("");
        email.setText("");
        phone.setText("");
    }

    public void addUser() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, uri,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String string) {
                        Toast.makeText(getApplicationContext(), "Registration is " + string, Toast.LENGTH_LONG).show();
                        userRegister(string);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getApplicationContext(), "Registration is " + volleyError, Toast.LENGTH_LONG).show();
                    }
                }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameter = new HashMap<String, String>();
                parameter.put("username", Username);
                parameter.put("password", Password);
                parameter.put("email", Email);
                parameter.put("phone", Phone);
                return parameter;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    public void userRegister(String string){
        if(string.equals("Successfully")) {
            Intent intent = new Intent(getApplicationContext(), UserActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }
}