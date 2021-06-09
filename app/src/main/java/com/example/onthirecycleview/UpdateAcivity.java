package com.example.onthirecycleview;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class UpdateAcivity extends AppCompatActivity {

    private TextView tvId;
    private EditText txtNameUpdate,txtEmailUpdate,txtAgeUpdate;
    private Button  btnCapNhat,btnQuaylai;
    private int id=0;
    public static final String url="https://60b094421f26610017ffe7e7.mockapi.io/User";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layoutupdate);
        txtNameUpdate=findViewById(R.id.txtUpdateName);
        txtEmailUpdate=findViewById(R.id.txtUpdateEmai);
        txtAgeUpdate=findViewById(R.id.txtUpateAge);
        tvId=findViewById(R.id.tvId);

        Intent intent= getIntent();
        Bundle bundle=intent.getBundleExtra("BUNDLE");
        id=bundle.getInt("id");
        tvId.setText(bundle.getInt("id")+"");
        txtNameUpdate.setText(bundle.getString("name"));
        txtEmailUpdate.setText(bundle.getString("email"));
        txtAgeUpdate.setText(bundle.getString("age"));

        btnCapNhat=findViewById(R.id.btnSaveUpdate);
        btnQuaylai=findViewById(R.id.btnQuayLaiUpdate);
        btnQuaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1= new Intent(UpdateAcivity.this,RecycleViewActivity.class);
                startActivity(intent1);
                finish();
            }
        });

        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    RequestQueue requestQueue= Volley.newRequestQueue(UpdateAcivity.this);
                    JSONObject jsonObject= new JSONObject();
                    jsonObject.put("name",txtNameUpdate.getText());
                    jsonObject.put("age",txtAgeUpdate.getText());
                    jsonObject.put("email",txtEmailUpdate.getText());
                    final String requestBody=jsonObject.toString();

                    StringRequest stringRequest= new StringRequest(Request.Method.PUT,url+"/"+id,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Toast.makeText(UpdateAcivity.this, "success", Toast.LENGTH_SHORT).show();
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("VOLLEY",error.toString());
                        }
                    }
                    ){
                        @Override
                        public String getBodyContentType() {
                            return "application/json; charset=utf-8";
                        }

                        @Override
                        public byte[] getBody() throws AuthFailureError {
                            try {
                                return requestBody == null ? null : requestBody.getBytes("utf-8");
                            } catch (UnsupportedEncodingException uee) {
                                VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                                return null;
                            }
                        }
                        @Override
                        protected Response<String> parseNetworkResponse(NetworkResponse response) {
                            String responseString = "";
                            if (response != null) {
                                responseString = String.valueOf(response.statusCode);

                            }
                            return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                        }
                    };
                    requestQueue.add(stringRequest);
                }catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        });

    }
}
