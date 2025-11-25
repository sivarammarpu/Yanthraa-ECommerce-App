# Yanthraa E-Commerce Android App

> **Author**: Sivaram Marpu  
> **Copyright**: © 2025 Sivaram Marpu. All rights reserved.  
> **License**: MIT License  
> **Development**: Independently developed Android e-commerce application

---

A polished, production-quality Android e-commerce application built with Java, featuring a modern UI, smooth navigation, and complete shopping cart functionality.

## Features

- **Home Screen**: Product grid with 2-column layout, category filtering, search bar, and cart badge
- **Product Details**: Large hero images, ratings, descriptions, quantity selector, and add to cart
- **Shopping Cart**: Item management, quantity display, price calculations with tax, and empty state
- **Smooth Navigation**: Slide transitions between screens with proper back navigation
- **Modern UI**: Material Design 3 components with custom purple/pink color scheme
- **Image Loading**: Glide library for efficient image loading and caching
- **In-Memory Cart**: Singleton CartManager with observer pattern for real-time updates

## Tech Stack

- **Language**: Java
- **Min SDK**: 21 (Android 5.0 Lollipop)
- **Target SDK**: 33 (Android 13)
- **Architecture**: MVC pattern with singleton cart management
- **Libraries**:
  - AndroidX (AppCompat, ConstraintLayout, RecyclerView, CardView, CoordinatorLayout)
  - Material Components 1.9.0
  - Glide 4.15.1 (image loading)
  - Gson 2.10.1 (JSON parsing)

## Project Structure

```
app/src/main/
├── java/com/yanthraa/ecommerce/
│   ├── activities/
│   │   ├── HomeActivity.java
│   │   ├── ProductDetailsActivity.java
│   │   └── CartActivity.java
│   ├── adapters/
│   │   ├── ProductAdapter.java
│   │   └── CartAdapter.java
│   ├── models/
│   │   ├── Product.java
│   │   └── CartItem.java
│   └── utils/
│       ├── CartManager.java
│       └── ProductDataLoader.java
├── res/
│   ├── layout/ (activity and item layouts)
│   ├── drawable/ (icons and product images)
│   ├── anim/ (slide transitions)
│   └── values/ (colors, strings, dimensions, themes)
└── assets/
    └── products.json (sample product data)
```

## Build & Run Instructions

### Prerequisites

- Android Studio Hedgehog (2023.1.1) or later
- JDK 8 or higher
- Android SDK with API 33 installed
- Gradle 8.1.0 (included via wrapper)

### Steps to Build

1. **Clone or Extract the Project**
   ```bash
   git clone <repository-url>
   cd Yanthraa-ECommerce-App
   ```

2. **Open in Android Studio**
   - Launch Android Studio
   - Select "Open an Existing Project"
   - Navigate to the project directory and click OK
   - Wait for Gradle sync to complete

3. **Sync Gradle**
   - Android Studio should automatically sync
   - If not, click "File → Sync Project with Gradle Files"

4. **Run the App**
   - Connect an Android device (API 21+) or start an emulator
   - Click the "Run" button (green triangle) or press Shift+F10
   - Select your target device
   - Wait for the app to build and install

### Alternative: Build APK via Command Line

```bash
# Debug APK
./gradlew assembleDebug

# The APK will be at: app/build/outputs/apk/debug/app-debug.apk
```

## Testing Checklist

### Functional Tests

- [ ] App launches without crashes on API 21, 30, and 33
- [ ] Home screen displays 8 products in 2-column grid
- [ ] Product cards show image, name, price, and rating
- [ ] Category chips filter products correctly (All, Electronics, Fashion, Home, Beauty)
- [ ] Cart badge shows correct item count
- [ ] Tapping a product navigates to Product Details with slide animation
- [ ] Product Details displays correct product information
- [ ] Quantity selector increments/decrements (min: 1, max: 99)
- [ ] "Add to Cart" button adds item and shows toast confirmation
- [ ] Cart badge updates after adding items
- [ ] Cart icon navigates to Cart screen
- [ ] Cart screen displays all added items with correct quantities
- [ ] Cart calculates subtotal, tax (8%), and total correctly
- [ ] Remove button deletes items from cart
- [ ] Empty cart shows "Your cart is empty" message
- [ ] Back button navigation works correctly on all screens
- [ ] Slide animations play smoothly during navigation

### UI/Layout Tests

- [ ] Layouts display correctly on 5" screen (1080x1920)
- [ ] Layouts display correctly on 6.5" screen (1440x3040)
- [ ] No text overflow or clipping
- [ ] Images load properly via Glide
- [ ] Touch feedback (ripple) visible on buttons and cards
- [ ] Proper spacing and alignment throughout
- [ ] Status bar color matches app theme

### Edge Cases

- [ ] Adding same product multiple times increases quantity
- [ ] Cart persists during navigation (until app is closed)
- [ ] Favorite icon toggles on product cards
- [ ] Decrement button disabled when quantity is 1

## Known Issues & Limitations

See [CHANGELOG.md](CHANGELOG.md) for detailed list of known issues.

- Checkout functionality is not implemented (demo only)
- Search bar is non-functional (UI placeholder)
- Favorite functionality doesn't persist (in-memory only)
- Product data is loaded from local JSON (no backend integration)
- Cart data is lost when app is closed (no persistence)
- No user authentication or profile management
- Limited to 8 sample products

## Sample Products

The app includes 8 sample products across 4 categories:

- **Electronics**: Wireless Headphones, Smart Watch Pro
- **Fashion**: Designer Handbag, Running Shoes
- **Home**: Aromatherapy Diffuser, Coffee Maker
- **Beauty**: Skincare Set, Hair Styling Tool

## Screenshots

*(Screenshots would be added here in a production README)*

## License

This is a demo project for educational purposes.

## Contact

For questions or issues, please contact the development team.
