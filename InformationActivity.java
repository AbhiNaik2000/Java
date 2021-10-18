package com.example.vms;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class InformationActivity extends AppCompatActivity {

    TextView firstName, lastName, phone, location, date, time, companyName, purpose, reference, service, document;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        getSupportActionBar().setTitle("Information");

        firstName = (TextView) findViewById(R.id.textViewFirstName);
        lastName = (TextView) findViewById(R.id.textViewLastName);
        phone = (TextView) findViewById(R.id.textViewPhone);
        location = (TextView) findViewById(R.id.textViewLocation);
        date = (TextView) findViewById(R.id.textViewDate);
        time = (TextView) findViewById(R.id.textViewTime);
        companyName = (TextView) findViewById(R.id.textViewCompanyName);
        purpose = (TextView) findViewById(R.id.textViewPurpose);
        reference = (TextView) findViewById(R.id.textViewReference);
        service = (TextView) findViewById(R.id.textViewService);
        document = (TextView) findViewById(R.id.textViewDocument);

        firstName.setText(getIntent().getStringExtra("firstName"));
        lastName.setText(getIntent().getStringExtra("lastName"));
        phone.setText(getIntent().getStringExtra("phone"));
        location.setText(getIntent().getStringExtra("location"));
        date.setText(getIntent().getStringExtra("date"));
        time.setText(getIntent().getStringExtra("time"));
        companyName.setText(getIntent().getStringExtra("companyName"));
        purpose.setText(getIntent().getStringExtra("purpose"));
        reference.setText(getIntent().getStringExtra("reference"));
        service.setText(getIntent().getStringExtra("service"));
        document.setText(getIntent().getStringExtra("document"));
    }
}