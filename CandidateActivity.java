package com.example.vms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CandidateActivity extends AppCompatActivity {

    EditText candidateId;
    Button add;
    ImageButton delete;
    String Url="", Uri1="", Uri2="";
    String Id = "", Image = "", FirstName = "", LastName = "", Phone ="", Location = "", Date = "", Time = "";
    String CandidateId = "";

    RecyclerView recyclerView;
    CandidateAdapter candidateAdapter;
    List<Candidate> candidateList;
    Candidate candidate;
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate);

        getSupportActionBar().setTitle("Candidate");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Uri1 = "http://"+IpAddressActivity.ipAddress+"/database/retrieveCandidate.php";
        Uri2 = "http://"+IpAddressActivity.ipAddress+"/database/removeCandidate.php";

        candidateId = (EditText) findViewById(R.id.editTextNumberCandidateId);
        add = (Button) findViewById(R.id.buttonCandidateAdd);
        delete = (ImageButton) findViewById(R.id.imageButtonCandidateDelete);

        addCandidate();
        deleteCandidate();

        recyclerView = (RecyclerView) findViewById(R.id.CandidateList);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        candidateList = new ArrayList<>();
        candidateAdapter = new CandidateAdapter(this, candidateList);
        recyclerView.setAdapter(candidateAdapter);

        retrieveCandidate();

    }

    public void addCandidate(){
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NewCandidateActivity.class);
                startActivity(intent);
            }
        });
    }

    public void retrieveCandidate(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Uri1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String Success = jsonObject.getString("Success");
                            JSONArray jsonArray = jsonObject.getJSONArray("candidate_info");
                            if (Success.equals("1")){
                                for (int i=0;i<jsonArray.length();i++){
                                    JSONObject obj = jsonArray.getJSONObject(i);
                                    Id = obj.getString("id");
                                    Image = obj.getString("image");
                                    Url = "http://"+IpAddressActivity.ipAddress+"/database/Images/"+Image;
                                    FirstName = obj.getString("first_name");
                                    LastName = obj.getString("last_name");
                                    Phone = obj.getString("phone_number");
                                    Location = obj.getString("location");
                                    Date = obj.getString("date");
                                    Time = obj.getString("time");
                                    candidate = new Candidate(Id, Url, FirstName, LastName, Phone, Location, Date, Time);
                                    candidateList.add(candidate);
                                    candidateAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getApplicationContext(), "VolleyError is " + volleyError, Toast.LENGTH_LONG).show();
                    }
                })
        {

        };
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    public void deleteCandidate(){
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();
            }
        });
    }

    public void validation() {
        int f = 0;
        if (candidateId.getText().toString().isEmpty() == true) {
            candidateId.setError("Enter Id");
            candidateId.requestFocus();
        } else {
            f = 1;
        }
        if (f == 1) {
            CandidateId = candidateId.getText().toString();
            clearTextView();
            removeDialog();
        }
    }

    public void clearTextView() {
        candidateId.setText("");
    }

    public void removeDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Confirmation");
        builder.setMessage("Are You Sure To Remove Candidate Visitor ?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                removeCandidate();
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    public void removeCandidate(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Uri2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String string) {
                        Toast.makeText(getApplicationContext(), "Response is " + string, Toast.LENGTH_LONG).show();

                        checkResponse(string);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getApplicationContext(), "VolleyError is " + volleyError, Toast.LENGTH_LONG).show();
                    }
                })
        {
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> parameter=new HashMap<String,String>();
                parameter.put("id",CandidateId);
                return parameter;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }

    public void checkResponse(String string) {
        if (string.equals("Successfully")) {
            finish();
            Intent intent = new Intent(getApplicationContext(), CandidateActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "Unsuccessfully", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        MenuItem menuItem = menu.findItem(R.id.buttonSearch);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                candidateAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), UserActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }
}