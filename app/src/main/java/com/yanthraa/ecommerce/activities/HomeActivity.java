package com.yanthraa.ecommerce.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.yanthraa.ecommerce.R;
import com.yanthraa.ecommerce.adapters.ProductAdapter;
import com.yanthraa.ecommerce.models.Product;
import com.yanthraa.ecommerce.utils.CartManager;
import com.yanthraa.ecommerce.utils.ProductDataLoader;

import java.util.List;

public class HomeActivity extends AppCompatActivity implements 
        ProductAdapter.OnProductClickListener, CartManager.CartUpdateListener {

    private RecyclerView productsRecyclerView;
    private ProductAdapter productAdapter;
    private ChipGroup categoryChipGroup;
    private TextView cartBadge;
    private FrameLayout cartIconContainer;
    private CartManager cartManager;
    private List<Product> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initViews();
        setupRecyclerView();
        setupCategoryChips();
        setupCartIcon();
        loadProducts();
    }

    private void initViews() {
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        productsRecyclerView = findViewById(R.id.productsRecyclerView);
        categoryChipGroup = findViewById(R.id.categoryChipGroup);
        cartBadge = findViewById(R.id.cartBadge);
        cartIconContainer = findViewById(R.id.cartIconContainer);
        
        cartManager = CartManager.getInstance();
    }

    private void setupRecyclerView() {
        productAdapter = new ProductAdapter(this);
        productsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        productsRecyclerView.setAdapter(productAdapter);
    }

    private void setupCategoryChips() {
        categoryChipGroup.setOnCheckedStateChangeListener((group, checkedIds) -> {
            if (!checkedIds.isEmpty()) {
                int checkedId = checkedIds.get(0);
                Chip chip = findViewById(checkedId);
                if (chip != null) {
                    String category = chip.getText().toString();
                    productAdapter.filterByCategory(category);
                }
            }
        });
    }

    private void setupCartIcon() {
        cartIconContainer.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, CartActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
        
        updateCartBadge();
    }

    private void loadProducts() {
        products = ProductDataLoader.loadProducts(this);
        productAdapter.setProducts(products);
    }

    private void updateCartBadge() {
        int itemCount = cartManager.getCartItemCount();
        if (itemCount > 0) {
            cartBadge.setText(String.valueOf(itemCount));
            cartBadge.setVisibility(View.VISIBLE);
        } else {
            cartBadge.setVisibility(View.GONE);
        }
    }

    @Override
    public void onProductClick(Product product) {
        Intent intent = new Intent(this, ProductDetailsActivity.class);
        intent.putExtra("product", product);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void onFavoriteClick(Product product) {
        // Favorite functionality - could be extended to save to preferences
    }

    @Override
    public void onCartUpdated() {
        updateCartBadge();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cartManager.addListener(this);
        updateCartBadge();
    }

    @Override
    protected void onPause() {
        super.onPause();
        cartManager.removeListener(this);
    }
}
