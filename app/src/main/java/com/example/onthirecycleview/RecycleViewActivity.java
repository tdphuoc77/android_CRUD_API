package com.example.onthirecycleview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RecycleViewActivity extends AppCompatActivity {
    private RecyclerView recycle;
    private ListRecycleAdapter adapter;
    public static  ArrayList<User> arraylist= new ArrayList<User>();
    private Button btnAdd,btnCancel;
    public static final String url="https://60b094421f26610017ffe7e7.mockapi.io/User";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layoutlistuser);

        recycle=findViewById(R.id.recycle);

        GetData(url);


        btnAdd=findViewById(R.id.btnSaveUpdate);
        btnCancel=findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(RecycleViewActivity.this,UserAddActivity.class);
                startActivity(intent);
                arraylist.clear();
                finish();
            }
        });
    }

    private void deleteUser(String url, int id){
        StringRequest stringRequest = new StringRequest(
                Request.Method.DELETE, url + '/' + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(RecycleViewActivity.this, "Successfully", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RecycleViewActivity.this, "Error by Post data!", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void GetData(String url){
        JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(Request.Method.GET,url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object =(JSONObject) response.get(i);
                                User user = new User();
                                user.setId(object.getInt("id"));
                                user.setName(object.getString("name"));
                                user.setEmail(object.getString("email"));
                                user.setAge(object.getString("age"));
                                arraylist.add(user);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        buildRecyclerView();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RecycleViewActivity.this, "Error :(", Toast.LENGTH_SHORT).show();
            }
        }

        );
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);

    }


    private void buildRecyclerView() {

        adapter = new ListRecycleAdapter(RecycleViewActivity.this,arraylist);

        LinearLayoutManager manager = new LinearLayoutManager(this);

        recycle.setLayoutManager(manager);

        recycle.setAdapter(adapter);
    }
}
