package com.example.qc.pizzapoint;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartHolder> {
    ArrayList<CartItem> dataset;
    AdapterView.OnItemClickListener addClickListener;
    AdapterView.OnItemClickListener subtractClickListener;

    public CartAdapter(ArrayList<CartItem> dataset, AdapterView.OnItemClickListener addClickListener, AdapterView.OnItemClickListener subtractClickListener, AdapterView.OnItemClickListener removeClickListener) {
        this.dataset = dataset;
        this.addClickListener = addClickListener;
        this.subtractClickListener = subtractClickListener;
        this.removeClickListener = removeClickListener;
    }

    AdapterView.OnItemClickListener removeClickListener;


    @NonNull
    @Override
    public CartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.cart_item_layout, parent, false);
        return new CartHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final CartHolder holder, int position) {
        holder.tvName.setText(dataset.get(position).p.productName);
        holder.tvPrice.setText("Rs. "+ dataset.get(position).p.price);

        int amount = dataset.get(position).p.price * dataset.get(position).quantity;
        holder.tvAmount.setText("Rs. "+ amount);
        holder.tvQuantity.setText(String.valueOf(dataset.get(position).quantity));
        // change is here
        holder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subtractClickListener.onItemClick(null, holder.btnRemove, holder.getAdapterPosition(), 0);
            }
        });
        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addClickListener.onItemClick(null, holder.btnAdd, holder.getAdapterPosition(), 0);
            }
        });
        holder.tvRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeClickListener.onItemClick(null, holder.tvQuantity, holder.getAdapterPosition(), 0);
            }
        });


    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public class CartHolder extends RecyclerView.ViewHolder{

        TextView tvName;
        TextView tvPrice;
        TextView tvAmount;
        TextView tvRemove;

        Button btnAdd;
        TextView tvQuantity;
        Button btnRemove;

        public CartHolder(View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tv_product_name);
            tvPrice = itemView.findViewById(R.id.tv_product_price);
            tvAmount = itemView.findViewById(R.id.tv_product_amount);
            tvRemove = itemView.findViewById(R.id.tv_remove);
            btnAdd = itemView.findViewById(R.id.btn_add);
            tvQuantity = itemView.findViewById(R.id.tv_quantity);
            btnRemove = itemView.findViewById(R.id.btn_remove);
        }
    }
}
