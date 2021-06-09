package com.example.onthirecycleview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class ListRecycleAdapter extends RecyclerView.Adapter<ListRecycleAdapter.UserViewHolder> {

    private Context context;
    private final ArrayList<User> array;
    private LayoutInflater inflater;

    public ListRecycleAdapter(Context context, ArrayList<User> array) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.array = array;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item, parent, false);
        return new UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = array.get(position);

        holder.tvName.setText(user.getName());
        holder.tvEmail.setText(user.getEmail());
        holder.tvAge.setText(user.getAge());
        holder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stringRequest = new StringRequest(
                        Request.Method.DELETE, RecycleViewActivity.url + '/' + user.getId(), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(v.getContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
                        RecycleViewActivity.arraylist.clear();
                        Intent intent = new Intent(v.getContext(), RecycleViewActivity.class);
                        context.startActivity(intent);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(v.getContext(), "Error by Post data!", Toast.LENGTH_SHORT).show();
                    }
                });

                RequestQueue requestQueue = Volley.newRequestQueue(v.getContext());
                requestQueue.add(stringRequest);
            }
        });

        holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecycleViewActivity.arraylist.clear();


                Intent intent = new Intent(v.getContext(), UpdateAcivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("id", user.getId());
                bundle.putString("name",user.getName());
                bundle.putString("email",user.getEmail());
                bundle.putString("age",user.getAge());

                intent.putExtra("BUNDLE", bundle);
                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return array.size();
    }


    public class UserViewHolder extends RecyclerView.ViewHolder {
        private TextView tvAge;
        private TextView tvName;
        private TextView tvEmail;
        private Button  btnUpdate;
        private Button btnRemove;
         public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tvAge = itemView.findViewById(R.id.tvAge);
            this.tvName = itemView.findViewById(R.id.tvName);
            this.tvEmail = itemView.findViewById(R.id.tvEmail);
            this.btnRemove=itemView.findViewById(R.id.btnDelete);
            this.btnUpdate=itemView.findViewById(R.id.btnUpdate);

        }

    }
}

