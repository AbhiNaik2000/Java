package com.example.vms;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

public class IpAddressActivity extends AppCompatActivity {

    EditText Ip;
    public static String ipAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ip_address);

        final AlertDialog.Builder getIp = new AlertDialog.Builder(this);
        getIp.setTitle("Ip Address");
        getIp.setMessage("Enter Your Ip Address");
        Ip = new EditText(this);
        getIp.setView(Ip);
        if (ipAddress == null) {
            getIp.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    ipAddress = Ip.getText().toString();
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
            getIp.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    finish();
                }
            });
            AlertDialog alertDialog = getIp.create();
            alertDialog.show();
        }
    }
}