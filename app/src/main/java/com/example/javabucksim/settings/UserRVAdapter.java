package com.example.javabucksim.settings;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.javabucksim.R;

import java.util.ArrayList;

public class UserRVAdapter extends RecyclerView.Adapter<UserRVAdapter.MyViewHolder> {

    // may need to be changed?
    static OnItemClickListener listener;

    Context context;
    ArrayList<User> userArrayList;

    public UserRVAdapter(Context context, ArrayList<User> userArrayList, OnItemClickListener listener) {
        this.context = context;
        this.userArrayList = userArrayList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public UserRVAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.user_item, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UserRVAdapter.MyViewHolder holder, int position) {

        User user = userArrayList.get(position);

        holder.email.setText(user.email);
        holder.firstLastName.setText(user.firstName + " " + user.lastName);
        holder.role.setText(user.role);

    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView email, firstLastName, role;


        public MyViewHolder(View itemView) {
            super(itemView);
            email = itemView.findViewById(R.id.idTVEmail);
            firstLastName = itemView.findViewById(R.id.idTVFirstnameLastname);
            role = itemView.findViewById(R.id.idTVCUserRole);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onItemClick(view, getAdapterPosition());
        }
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }




}
