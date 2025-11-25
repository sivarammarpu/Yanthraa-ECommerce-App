package com.yanthraa.ecommerce.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.yanthraa.ecommerce.R;
import com.yanthraa.ecommerce.models.CartItem;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<CartItem> cartItems;
    private OnCartItemActionListener listener;

    public interface OnCartItemActionListener {
        void onRemoveItem(CartItem item);
    }

    public CartAdapter(OnCartItemActionListener listener) {
        this.cartItems = new ArrayList<>();
        this.listener = listener;
    }

    public void setCartItems(List<CartItem> items) {
        this.cartItems = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem item = cartItems.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView cartItemImage;
        TextView cartItemName;
        TextView cartItemPrice;
        TextView cartItemQuantity;
        TextView cartItemTotal;
        ImageButton removeButton;

        CartViewHolder(@NonNull View itemView) {
            super(itemView);
            cartItemImage = itemView.findViewById(R.id.cartItemImage);
            cartItemName = itemView.findViewById(R.id.cartItemName);
            cartItemPrice = itemView.findViewById(R.id.cartItemPrice);
            cartItemQuantity = itemView.findViewById(R.id.cartItemQuantity);
            cartItemTotal = itemView.findViewById(R.id.cartItemTotal);
            removeButton = itemView.findViewById(R.id.removeButton);
        }

        void bind(CartItem item) {
            cartItemName.setText(item.getProduct().getName());
            cartItemPrice.setText(item.getProduct().getFormattedPrice());
            cartItemQuantity.setText("Qty: " + item.getQuantity());
            cartItemTotal.setText(item.getFormattedItemTotal());

            // Load image using Glide
            int imageResId = itemView.getContext().getResources()
                    .getIdentifier(item.getProduct().getImageUrl(), "drawable",
                            itemView.getContext().getPackageName());

            if (imageResId != 0) {
                Glide.with(itemView.getContext())
                        .load(imageResId)
                        .centerCrop()
                        .placeholder(R.color.surface_variant)
                        .into(cartItemImage);
            } else {
                cartItemImage.setImageResource(R.color.surface_variant);
            }

            removeButton.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onRemoveItem(item);
                }
            });
        }
    }
}
