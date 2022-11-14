package com.example.javabucksim;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.javabucksim.R;

import java.util.ArrayList;

public class order_RVAdapter extends RecyclerView.Adapter<order_RVAdapter.MyViewHolder> {
    Context context;
    private ArrayList<orderTile> orderItems;


    public order_RVAdapter(Context context, ArrayList<orderTile> orderItems){
        this.context = context;
        this.orderItems= orderItems;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.order_view_row,parent,false);
        return new MyViewHolder(view); //order_RVAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.itemName.setText(orderItems.get(position).getItemName());
        holder.amount.setText((orderItems.get(position).getItemquantity()));
    }

    @Override
    public int getItemCount() {
        return orderItems.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView itemName;
        TextView amount;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.item_name);
            amount = itemView.findViewById(R.id.quantity);
        }
    }
}
