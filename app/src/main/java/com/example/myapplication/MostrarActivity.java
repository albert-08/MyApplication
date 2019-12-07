package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.database.Company;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MostrarActivity extends AppCompatActivity {

    private final String JSON_URL = "http://192.168.43.180:8000";
    private JsonArrayRequest request ;
    private RequestQueue requestQueue ;
    private List<Company> listCompany ;
    private RecyclerView recyclerView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar);

        listCompany = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerviewid);

        Log.d("Entra", "Lo ejecuta");

        jsonrequest();
    }

    public void jsonrequest() {


        request = new JsonArrayRequest(JSON_URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("Entra", "JsonRequest");

                JSONObject jsonObject  = null ;

                for (int i = 0 ; i < response.length(); i++ ) {


                    try {
                        jsonObject = response.getJSONObject(i) ;
                        Company company = new Company() ;
                        company.setName(jsonObject.getString("name"));
                        company.setDescription(jsonObject.getString("description"));
                        company.setRating(jsonObject.getString("rating"));
                        company.setImage_url(jsonObject.getString("img"));
                        listCompany.add(company);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

                setuprecyclerview(listCompany);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERROR!!!!!! " , "" + error);
            }
        });


        requestQueue = Volley.newRequestQueue(MostrarActivity.this);
        requestQueue.add(request) ;


    }

    private void setuprecyclerview(List<Company> listCompany) {

        Log.d("Esta es la lista","Lista" + listCompany);

        RecyclerViewAdapter myadapter = new RecyclerViewAdapter(this,listCompany) ;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myadapter);

    }
}
