package com.yanthraa.ecommerce.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.yanthraa.ecommerce.R;
import com.yanthraa.ecommerce.models.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> products;
    private List<Product> productsFiltered;
    private OnProductClickListener listener;

    public interface OnProductClickListener {
        void onProductClick(Product product);
        void onFavoriteClick(Product product);
    }

    public ProductAdapter(OnProductClickListener listener) {
        this.products = new ArrayList<>();
        this.productsFiltered = new ArrayList<>();
        this.listener = listener;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
        this.productsFiltered = new ArrayList<>(products);
        notifyDataSetChanged();
    }

    public void filterByCategory(String category) {
        if (category == null || category.equals("All")) {
            productsFiltered = new ArrayList<>(products);
        } else {
            productsFiltered = new ArrayList<>();
            for (Product product : products) {
                if (product.getCategory().equals(category)) {
                    productsFiltered.add(product);
                }
            }
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product_card, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productsFiltered.get(position);
        holder.bind(product);
    }

    @Override
    public int getItemCount() {
        return productsFiltered.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName;
        TextView productPrice;
        RatingBar productRating;
        ImageButton favoriteButton;

        ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
            productRating = itemView.findViewById(R.id.productRating);
            favoriteButton = itemView.findViewById(R.id.favoriteButton);
        }

        void bind(Product product) {
            productName.setText(product.getName());
            productPrice.setText(product.getFormattedPrice());
            productRating.setRating(product.getRating());

            // Load image using Glide
            int imageResId = itemView.getContext().getResources()
                    .getIdentifier(product.getImageUrl(), "drawable", 
                            itemView.getContext().getPackageName());
            
            if (imageResId != 0) {
                Glide.with(itemView.getContext())
                        .load(imageResId)
                        .centerCrop()
                        .placeholder(R.color.surface_variant)
                        .into(productImage);
            } else {
                productImage.setImageResource(R.color.surface_variant);
            }

            // Set favorite icon
            favoriteButton.setImageResource(product.isFavorite() ? 
                    R.drawable.ic_favorite : R.drawable.ic_favorite_border);

            // Click listeners
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onProductClick(product);
                }
            });

            favoriteButton.setOnClickListener(v -> {
                product.setFavorite(!product.isFavorite());
                favoriteButton.setImageResource(product.isFavorite() ? 
                        R.drawable.ic_favorite : R.drawable.ic_favorite_border);
                if (listener != null) {
                    listener.onFavoriteClick(product);
                }
            });
        }
    }
}
