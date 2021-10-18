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

public class ClientActivity extends AppCompatActivity {

    EditText clientId;
    Button add;
    ImageButton delete;
    String Url="", Uri1="", Uri2="";
    String Id = "", Image = "", FirstName = "", LastName = "", Phone ="", Location = "", Date = "", Time = "", CompanyName = "", Purpose = "", Reference = "";
    String ClientId = "";

    RecyclerView recyclerView;
    ClientAdapter clientAdapter;
    List<Client> clientList;
    Client client;
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        getSupportActionBar().setTitle("Client");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Uri1 = "http://"+IpAddressActivity.ipAddress+"/database/retrieveClient.php";
        Uri2 = "http://"+IpAddressActivity.ipAddress+"/database/removeClient.php";

        clientId = (EditText) findViewById(R.id.editTextNumberClientId);
        add = (Button) findViewById(R.id.buttonClientAdd);
        delete = (ImageButton) findViewById(R.id.imageButtonClientDelete);

        addClient();
        deleteClient();

        recyclerView = (RecyclerView) findViewById(R.id.ClientList);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        clientList = new ArrayList<>();
        clientAdapter = new ClientAdapter(this, clientList);
        recyclerView.setAdapter(clientAdapter);

        retrieveClient();

    }

    public void addClient(){
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NewClientActivity.class);
                startActivity(intent);
            }
        });
    }

    public void retrieveClient(){
        final StringRequest stringRequest=new StringRequest(Request.Method.POST, Uri1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String Success = jsonObject.getString("Success");
                            JSONArray jsonArray = jsonObject.getJSONArray("client_info");
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
                                    CompanyName = obj.getString("company_name");
                                    Purpose = obj.getString("purpose");
                                    Reference = obj.getString("reference");
                                    client = new Client(Id, Url, FirstName, LastName, Phone, Location, Date, Time, CompanyName, Purpose, Reference);
                                    clientList.add(client);
                                    clientAdapter.notifyDataSetChanged();
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

    public void deleteClient(){
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();
            }
        });
    }

    public void validation() {
        int f = 0;
        if (clientId.getText().toString().isEmpty() == true) {
            clientId.setError("Enter Id");
            clientId.requestFocus();
        } else {
            f = 1;
        }
        if (f == 1) {
            ClientId = clientId.getText().toString();
            clearTextView();
            removeDialog();
        }
    }

    public void clearTextView() {
        clientId.setText("");
    }

    public void removeDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Confirmation");
        builder.setMessage("Are You Sure To Remove Client Visitor ?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                removeClient();
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

    public void removeClient(){
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
                parameter.put("id",ClientId);
                return parameter;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }

    public void checkResponse(String string) {
        if (string.equals("Successfully")) {
            finish();
            Intent intent = new Intent(getApplicationContext(), ClientActivity.class);
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
                clientAdapter.getFilter().filter(newText);
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