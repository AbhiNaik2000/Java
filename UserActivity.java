package com.example.vms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class UserActivity extends AppCompatActivity {

    TextView textView;
    Button selectClient, selectVendor, selectContractor, selectMarketing, selectCandidate, selectOther;
    Animation fadeSlow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        getSupportActionBar().setTitle("Select Visitor");

        textView = (TextView) findViewById(R.id.textViewVisitorSelect);
        selectClient = (Button) findViewById(R.id.buttonSelectVisitorClient);
        selectVendor = (Button) findViewById(R.id.buttonSelectVisitorVendor);
        selectContractor = (Button) findViewById(R.id.buttonSelectVisitorContractor);
        selectMarketing = (Button) findViewById(R.id.buttonSelectVisitorMarketing);
        selectCandidate = (Button) findViewById(R.id.buttonSelectVisitorCandidate);
        selectOther = (Button) findViewById(R.id.buttonSelectVisitorOther);

        fadeSlow = AnimationUtils.loadAnimation(this, R.anim.fadeslow);
        textView.setAnimation(fadeSlow);

        selectClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ClientActivity.class);
                startActivity(intent);
            }
        });

        selectVendor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), VendorActivity.class);
                startActivity(intent);
            }
        });

        selectContractor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ContractorActivity.class);
                startActivity(intent);
            }
        });

        selectMarketing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MarketingActivity.class);
                startActivity(intent);
            }
        });

        selectCandidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CandidateActivity.class);
                startActivity(intent);
            }
        });

        selectOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), OtherActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.log_out, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        Toast.makeText(this, "User Log Out", Toast.LENGTH_SHORT).show();
        return true;
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