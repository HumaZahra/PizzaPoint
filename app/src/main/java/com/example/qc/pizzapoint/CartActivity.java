package com.example.qc.pizzapoint;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
    RecyclerView rvCartItems;
    ArrayList<CartItem> cartList;
    CartAdapter cartAdapter;
    CartHelper cartHelper;

    TextView tvCartTotal;
    RelativeLayout checkoutLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        rvCartItems = findViewById(R.id.rv_cartitems);
        tvCartTotal = findViewById(R.id.tv_cart_total);
        checkoutLayout = findViewById(R.id.checkout_layout);

        cartHelper =new CartHelper(CartActivity.this);
        cartList = cartHelper.getAllCartProducts();

        cartAdapter = new CartAdapter(cartList, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //add listener
                CartItem selectedCartItem = cartList.get(position);
                int qty = selectedCartItem.quantity;
                qty++;
                selectedCartItem.quantity = qty;
                cartHelper.addOrUpdateToCart(selectedCartItem.p, qty);
                cartAdapter.notifyDataSetChanged();
                updateCartTotal();
            }
        }, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //subtract listener
                CartItem selectedCartItem = cartList.get(position);
                int qty = selectedCartItem.quantity;
                if(qty > 1){
                    qty--;
                    selectedCartItem.quantity = qty;
                    cartHelper.addOrUpdateToCart(selectedCartItem.p, qty);
                    cartAdapter.notifyDataSetChanged();
                    updateCartTotal();
                }
            }
        }, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //remove listener
                CartItem selectedCartItem = cartList.get(position);
                cartHelper.removeFromCart(selectedCartItem.p.productId);
                cartList.remove(position);
                cartAdapter.notifyItemRemoved(position);
                updateCartTotal();
            }
        });
        LinearLayoutManager manager = new LinearLayoutManager(CartActivity.this, LinearLayoutManager.VERTICAL, false);
        rvCartItems.setLayoutManager(manager);
        rvCartItems.setAdapter(cartAdapter);

        checkoutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if user is logged in then show address activity
                //else take user to login activity
                //SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(CartActivity.this);
               // boolean isUserLoggedIn = sharedPrefs.getBoolean("is_user_logged_in",false);
                if(!SessionHelper.isUserLoggedIn(CartActivity.this)){
                Intent intent = new Intent(CartActivity.this,LoginActivity.class);
                intent.putExtra("destination","Address");
                startActivity(intent);
            }else{
                    Intent intent = new Intent(CartActivity.this,AddressActivity.class);
                    startActivity(intent);

                }
            }
        });
        updateCartTotal();
    }
    private void updateCartTotal(){
        int cartTotal = cartHelper.getCartTotalAmount();
        tvCartTotal.setText("Rs. " + cartTotal);
    }
}
