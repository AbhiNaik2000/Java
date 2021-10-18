package com.example.vms;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class NewOtherActivity extends AppCompatActivity {

    ImageView imageView;
    EditText firstName, lastName, phone, location, purpose, reference;
    Button capture, add;
    String uri = "";
    String Image = "", FirstName = "", LastName = "", Phone = "", Location = "", Purpose = "", Reference = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_other);

        getSupportActionBar().setTitle("Add Other");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        uri = "http://"+IpAddressActivity.ipAddress+"/database/addOther.php";

        imageView = (ImageView) findViewById(R.id.imageViewOther);
        firstName = (EditText) findViewById(R.id.editTextTextOtherFirstName);
        lastName = (EditText) findViewById(R.id.editTextTextOtherLastName);
        phone = (EditText) findViewById(R.id.editTextTextOtherPhone);
        location = (EditText) findViewById(R.id.editTextTextOtherLocation);
        purpose = (EditText) findViewById(R.id.editTextTextOtherPurpose);
        reference = (EditText) findViewById(R.id.editTextTextOtherReference);
        capture = (Button) findViewById(R.id.buttonCaptureOther);
        add = (Button) findViewById(R.id.buttonOtherAdd);

        if (ContextCompat.checkSelfPermission(NewOtherActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(NewOtherActivity.this, new String[]
                    {
                            Manifest.permission.CAMERA
                    }, 100);
        }

        capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                capture();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();
            }
        });
    }

    public void capture(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 100) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);
            imageStore(bitmap);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void imageStore(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        Image = Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    public void validation() {
        int f = 0;
        if (firstName.getText().toString().isEmpty() == true) {
            firstName.setError("Enter First Name");
            firstName.requestFocus();
        } else if (lastName.getText().toString().isEmpty() == true) {
            lastName.setError("Enter Last Name");
            lastName.requestFocus();
        } else if (phone.getText().toString().isEmpty() == true) {
            phone.setError("Enter Phone Number");
            phone.requestFocus();
        } else if (!(phone.getText().toString().length() == 10)) {
            phone.setError("Your Phone number is 10 Digits");
            phone.requestFocus();
        } else if (location.getText().toString().isEmpty() == true) {
            location.setError("Enter Your Location");
            location.requestFocus();
        } else {
            f = 1;
        }
        if (f == 1) {
            FirstName = firstName.getText().toString();
            LastName = lastName.getText().toString();
            Phone = phone.getText().toString();
            Location = location.getText().toString();
            Purpose = purpose.getText().toString();
            Reference = reference.getText().toString();
            clearTextView();
            addOther();
        }
    }

    public void clearTextView() {
        firstName.setText("");
        lastName.setText("");
        phone.setText("");
        location.setText("");
        purpose.setText("");
        reference.setText("");
    }

    public void addOther() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, uri,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String string) {
                        Toast.makeText(getApplicationContext(), "Response is " + string, Toast.LENGTH_LONG).show();
                        otherAdd(string);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getApplicationContext(), "VolleyError is " + volleyError, Toast.LENGTH_LONG).show();
                    }
                }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameter = new HashMap<String, String>();
                parameter.put("imageView", Image);
                parameter.put("firstName", FirstName);
                parameter.put("lastName", LastName);
                parameter.put("phone", Phone);
                parameter.put("location", Location);
                parameter.put("purpose", Purpose);
                parameter.put("reference", Reference);
                return parameter;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    public void otherAdd(String string){
        if(string.equals("Successfully")) {
            Intent intent = new Intent(getApplicationContext(), OtherActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), OtherActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }
}