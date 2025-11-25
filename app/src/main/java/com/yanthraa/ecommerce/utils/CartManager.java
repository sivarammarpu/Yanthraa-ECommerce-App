package com.yanthraa.ecommerce.utils;

import com.yanthraa.ecommerce.models.CartItem;
import com.yanthraa.ecommerce.models.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartManager {
    private static CartManager instance;
    private Map<String, CartItem> cartItems;
    private List<CartUpdateListener> listeners;

    private CartManager() {
        cartItems = new HashMap<>();
        listeners = new ArrayList<>();
    }

    public static synchronized CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    public void addToCart(Product product, int quantity) {
        if (cartItems.containsKey(product.getId())) {
            CartItem existingItem = cartItems.get(product.getId());
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
        } else {
            cartItems.put(product.getId(), new CartItem(product, quantity));
        }
        notifyListeners();
    }

    public void removeFromCart(String productId) {
        cartItems.remove(productId);
        notifyListeners();
    }

    public void updateQuantity(String productId, int quantity) {
        if (quantity <= 0) {
            removeFromCart(productId);
        } else if (cartItems.containsKey(productId)) {
            cartItems.get(productId).setQuantity(quantity);
            notifyListeners();
        }
    }

    public List<CartItem> getCartItems() {
        return new ArrayList<>(cartItems.values());
    }

    public int getCartItemCount() {
        int count = 0;
        for (CartItem item : cartItems.values()) {
            count += item.getQuantity();
        }
        return count;
    }

    public double getSubtotal() {
        double subtotal = 0;
        for (CartItem item : cartItems.values()) {
            subtotal += item.getItemTotal();
        }
        return subtotal;
    }

    public double getTax() {
        return getSubtotal() * 0.08; // 8% tax
    }

    public double getTotal() {
        return getSubtotal() + getTax();
    }

    public String getFormattedSubtotal() {
        return String.format("₹%.0f", getSubtotal());
    }

    public String getFormattedTax() {
        return String.format("₹%.0f", getTax());
    }

    public String getFormattedTotal() {
        return String.format("₹%.0f", getTotal());
    }

    public void clearCart() {
        cartItems.clear();
        notifyListeners();
    }

    public boolean isEmpty() {
        return cartItems.isEmpty();
    }

    // Observer pattern for cart updates
    public interface CartUpdateListener {
        void onCartUpdated();
    }

    public void addListener(CartUpdateListener listener) {
        listeners.add(listener);
    }

    public void removeListener(CartUpdateListener listener) {
        listeners.remove(listener);
    }

    private void notifyListeners() {
        for (CartUpdateListener listener : listeners) {
            listener.onCartUpdated();
        }
    }
}
