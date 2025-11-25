package com.yanthraa.ecommerce.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.yanthraa.ecommerce.R;
import com.yanthraa.ecommerce.adapters.CartAdapter;
import com.yanthraa.ecommerce.models.CartItem;
import com.yanthraa.ecommerce.utils.CartManager;

import java.util.List;

public class CartActivity extends AppCompatActivity implements 
        CartAdapter.OnCartItemActionListener, CartManager.CartUpdateListener {

    private RecyclerView cartRecyclerView;
    private LinearLayout emptyCartView;
    private TextView subtotalValue;
    private TextView taxValue;
    private TextView totalValue;
    private MaterialButton checkoutButton;

    private CartAdapter cartAdapter;
    private CartManager cartManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        initViews();
        setupRecyclerView();
        setupCheckoutButton();
        loadCartData();
    }

    private void initViews() {
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        cartRecyclerView = findViewById(R.id.cartRecyclerView);
        emptyCartView = findViewById(R.id.emptyCartView);
        subtotalValue = findViewById(R.id.subtotalValue);
        taxValue = findViewById(R.id.taxValue);
        totalValue = findViewById(R.id.totalValue);
        checkoutButton = findViewById(R.id.checkoutButton);

        cartManager = CartManager.getInstance();
    }

    private void setupRecyclerView() {
        cartAdapter = new CartAdapter(this);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartRecyclerView.setAdapter(cartAdapter);
    }

    private void setupCheckoutButton() {
        checkoutButton.setOnClickListener(v -> {
            if (!cartManager.isEmpty()) {
                Toast.makeText(this, "Checkout functionality not implemented in demo", 
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadCartData() {
        List<CartItem> cartItems = cartManager.getCartItems();
        cartAdapter.setCartItems(cartItems);
        updateCartSummary();
        updateEmptyState();
    }

    private void updateCartSummary() {
        subtotalValue.setText(cartManager.getFormattedSubtotal());
        taxValue.setText(cartManager.getFormattedTax());
        totalValue.setText(cartManager.getFormattedTotal());
    }

    private void updateEmptyState() {
        if (cartManager.isEmpty()) {
            cartRecyclerView.setVisibility(View.GONE);
            emptyCartView.setVisibility(View.VISIBLE);
        } else {
            cartRecyclerView.setVisibility(View.VISIBLE);
            emptyCartView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onRemoveItem(CartItem item) {
        cartManager.removeFromCart(item.getProduct().getId());
        Toast.makeText(this, getString(R.string.removed_from_cart), 
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCartUpdated() {
        loadCartData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cartManager.addListener(this);
        loadCartData();
    }

    @Override
    protected void onPause() {
        super.onPause();
        cartManager.removeListener(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
