package com.yanthraa.ecommerce.activities;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.yanthraa.ecommerce.R;
import com.yanthraa.ecommerce.models.Product;
import com.yanthraa.ecommerce.utils.CartManager;

public class ProductDetailsActivity extends AppCompatActivity {

    private ImageView productHeroImage;
    private TextView productName;
    private TextView productPrice;
    private RatingBar productRating;
    private TextView ratingText;
    private TextView productDescription;
    private TextView quantityText;
    private ImageButton decrementButton;
    private ImageButton incrementButton;
    private MaterialButton addToCartButton;

    private Product product;
    private int quantity = 1;
    private CartManager cartManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        initViews();
        loadProductData();
        setupQuantitySelector();
        setupAddToCart();
    }

    private void initViews() {
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        productHeroImage = findViewById(R.id.productHeroImage);
        productName = findViewById(R.id.productName);
        productPrice = findViewById(R.id.productPrice);
        productRating = findViewById(R.id.productRating);
        ratingText = findViewById(R.id.ratingText);
        productDescription = findViewById(R.id.productDescription);
        quantityText = findViewById(R.id.quantityText);
        decrementButton = findViewById(R.id.decrementButton);
        incrementButton = findViewById(R.id.incrementButton);
        addToCartButton = findViewById(R.id.addToCartButton);

        cartManager = CartManager.getInstance();
    }

    private void loadProductData() {
        product = (Product) getIntent().getSerializableExtra("product");

        if (product != null) {
            productName.setText(product.getName());
            productPrice.setText(product.getFormattedPrice());
            productRating.setRating(product.getRating());
            ratingText.setText("(" + product.getRating() + ")");
            productDescription.setText(product.getDescription());

            // Load hero image
            int imageResId = getResources().getIdentifier(
                    product.getImageUrl(), "drawable", getPackageName());

            if (imageResId != 0) {
                Glide.with(this)
                        .load(imageResId)
                        .centerCrop()
                        .placeholder(R.color.surface_variant)
                        .into(productHeroImage);
            } else {
                productHeroImage.setImageResource(R.color.surface_variant);
            }
        }
    }

    private void setupQuantitySelector() {
        updateQuantityDisplay();

        decrementButton.setOnClickListener(v -> {
            if (quantity > 1) {
                quantity--;
                updateQuantityDisplay();
            }
        });

        incrementButton.setOnClickListener(v -> {
            if (quantity < 99) {
                quantity++;
                updateQuantityDisplay();
            }
        });
    }

    private void updateQuantityDisplay() {
        quantityText.setText(String.valueOf(quantity));
        decrementButton.setEnabled(quantity > 1);
    }

    private void setupAddToCart() {
        addToCartButton.setOnClickListener(v -> {
            cartManager.addToCart(product, quantity);
            Toast.makeText(this, "Item added to cart.", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
