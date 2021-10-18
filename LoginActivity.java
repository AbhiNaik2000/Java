package com.example.vms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

public class LoginActivity extends AppCompatActivity {

    EditText username, password;
    Button login, register;
    String uri = "";
    String Username = "", Password = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().setTitle("Login");

        uri = "http://"+IpAddressActivity.ipAddress+"/database/login.php";

        username = (EditText) findViewById(R.id.editTextTextUsername);
        password = (EditText) findViewById(R.id.editTextTextPassword);
        login = (Button) findViewById(R.id.buttonLogin);
        register = (Button) findViewById(R.id.buttonRegister);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    public void validation() {
        int f = 0;
        if (username.getText().toString().isEmpty() == true) {
            username.setError("Enter Username");
            username.requestFocus();
        } else if (password.getText().toString().isEmpty() == true) {
            password.setError("Enter Password");
            password.requestFocus();
        } else if (!(password.getText().toString().length() >= 4)) {
            password.setError("Your Password is Minimum 4 Digits");
            password.requestFocus();
        } else {
            f = 1;
        }
        if (f == 1) {
            Username = username.getText().toString();
            Password = password.getText().toString();
            getUser();
        }
    }

    public void getUser(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, uri,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String string) {
                        Toast.makeText(getApplicationContext(), "Login is " + string, Toast.LENGTH_LONG).show();
                        userLogin(string);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getApplicationContext(), "Login is " + volleyError, Toast.LENGTH_LONG).show();
                    }
                })
        {
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> parameter=new HashMap<String,String>();
                parameter.put("username",Username);
                parameter.put("password",Password);
                return parameter;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }

    public void userLogin(String string){
        if(string.equals("Successfully")){
            Intent intent = new Intent(getApplicationContext(), UserActivity.class);
            startActivity(intent);
            finish();
        }
        else {
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
            builder.setTitle("Login Message");
            builder.setMessage("Invalid Username and Password");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            android.app.AlertDialog alert = builder.create();
            alert.show();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        System.exit(0);
        super.onBackPressed();
    }
}