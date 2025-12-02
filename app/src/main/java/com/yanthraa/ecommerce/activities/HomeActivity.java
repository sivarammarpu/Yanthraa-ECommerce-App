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
    private CartManager cartManager;
    private List<Product> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initViews();
        setupRecyclerView();
        setupCategoryChips();
        loadProducts();
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        if (item.getItemId() == R.id.action_cart) {
            startActivity(new Intent(this, CartActivity.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initViews() {
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        productsRecyclerView = findViewById(R.id.productsRecyclerView);
        categoryChipGroup = findViewById(R.id.categoryChipGroup);

        cartManager = CartManager.getInstance();

        setupSearch();
    }

    private void setupSearch() {
        android.widget.EditText searchEditText = findViewById(R.id.searchEditText);
        searchEditText.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (productAdapter != null) {
                    productAdapter.filter(s.toString());
                }
            }

            @Override
            public void afterTextChanged(android.text.Editable s) {
            }
        });
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

    private void loadProducts() {
        products = ProductDataLoader.loadProducts(this);
        productAdapter.setProducts(products);
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
        // Cart updated - no UI update needed since cart icon removed
    }

    @Override
    protected void onResume() {
        super.onResume();
        cartManager.addListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        cartManager.removeListener(this);
    }
}
