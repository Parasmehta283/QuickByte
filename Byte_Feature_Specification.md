# Byte — Feature Specification

## 1. Customer

### Authentication
- Sign up
- Login
- Logout
- Forgot password
- Edit profile

### Home / Menu
- View restaurant menu
- Browse food by category
- Search food items
- View item details
- See price and availability
- See whether an item is out of stock

### Cart
- Add item to cart
- Remove item from cart
- Increase/decrease quantity
- View subtotal and total amount
- Proceed to checkout

### Checkout / Payment
- Review order before placing it
- Select payment method
- Place order
- See order confirmation

### Orders
- View active order
- Track order status:
  - Placed
  - Accepted
  - Preparing
  - Ready
  - Completed
  - Cancelled
- Cancel order when cancellation is allowed
- View previous orders
- View order details

---

## 2. Restaurant Staff

### Account
- Login with staff account
- Staff account is linked to one restaurant only
- Staff can only access orders belonging to that restaurant

### Order Management
- View new orders
- View order details
- Accept an order
- Reject an order
- Update order status:
  - Accepted
  - Preparing
  - Ready
  - Completed
- View currently active orders
- View completed orders

### Restrictions
Staff cannot:
- Add or remove staff
- Change restaurant details
- Add, edit, or delete menu items
- Change item prices
- Access another restaurant's data

---

## 3. Restaurant Owner / Admin

### Restaurant Dashboard
- View today's orders
- View pending orders
- View completed orders
- View cancelled orders
- View basic daily revenue
- Open or close the restaurant manually

### Order Management
- View all restaurant orders
- Accept or reject orders
- Update order status
- View order history

### Menu Management
- Add menu item
- Edit menu item
- Delete menu item
- Mark item as out of stock
- Mark item as available again
- Change item price
- Change item description
- Upload/change item image
- Create menu categories
- Edit menu categories
- Remove menu categories

### Staff Management
- Invite/create staff
- View restaurant staff
- Remove staff
- Disable staff access
- Each staff member remains linked to this restaurant only

### Restaurant Management
- Edit restaurant name
- Edit restaurant description
- Edit restaurant image/logo
- Set opening and closing time
- Temporarily open/close restaurant
- Manage restaurant contact information

---

## 4. Restaurant

Each restaurant should contain:

- Restaurant name
- Description
- Image/logo
- Opening time
- Closing time
- Open/closed status
- Menu categories
- Menu items
- Owner
- Staff members
- Orders

### Menu Item

Each menu item should contain:

- Name
- Description
- Price
- Image
- Category
- Availability status
- Vegetarian/non-vegetarian type if required

---

## 5. Main App Flow

### Customer
Login → Home/Menu → Item Details → Cart → Checkout → Payment → Order Tracking → Order History

### Staff
Login → New Orders → Order Details → Accept/Reject → Update Status → Complete Order

### Owner
Login → Dashboard → Orders / Menu / Staff / Restaurant Settings

---

## 6. Recommended Scope for Internship Project

Keep the first version focused on:

- Customer ordering
- Cart and checkout
- Live order status
- Owner menu management
- Owner staff management
- Staff order handling
- Out-of-stock handling
- Restaurant open/closed state
- Basic order history
- Basic owner dashboard

Avoid adding delivery partners, live driver tracking, coupons, loyalty programs, recommendations, or advanced analytics in the first version.
