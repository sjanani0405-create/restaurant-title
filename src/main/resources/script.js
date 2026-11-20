const menuData = [
    { id: 1, name: "Garlic Bread", category: "starters", price: 5.50 },
    { id: 2, name: "Paneer Tikka", category: "starters", price: 8.00 },
    { id: 3, name: "Butter Chicken", category: "mains", price: 14.50 },
    { id: 4, name: "Margherita Pizza", category: "mains", price: 12.00 },
    { id: 5, name: "Chocolate Lava Cake", category: "desserts", price: 6.50 },
    { id: 6, name: "Gulab Jamun", category: "desserts", price: 4.50 }
];

let cart = [];

const menuContainer = document.getElementById("menu-container");
const cartItemsList = document.getElementById("cart-items");
const cartCount = document.getElementById("cart-count");
const cartTotal = document.getElementById("cart-total");
const checkoutBtn = document.getElementById("checkout-btn");
const orderConfirmation = document.getElementById("order-confirmation");
const filterButtons = document.querySelectorAll(".filter-btn");

function renderMenu(category = "all") {
    menuContainer.innerHTML = "";
    const filtered = category === "all"
        ? menuData
        : menuData.filter(item => item.category === category);

    filtered.forEach(item => {
        const card = document.createElement("div");
        card.className = "menu-card";
        card.setAttribute("data-category", item.category);
        card.innerHTML = `
            <div>
                <span class="category-tag">${item.category}</span>
                <h3>${item.name}</h3>
                <p class="price">$${item.price.toFixed(2)}</p>
            </div>
            <button class="add-btn" data-id="${item.id}" onclick="addToCart(${item.id})">Add to Cart</button>
        `;
        menuContainer.appendChild(card);
    });
}

function addToCart(id) {
    const item = menuData.find(product => product.id === id);
    cart.push(item);
    orderConfirmation.classList.add("hidden");
    updateCartUI();
}

function updateCartUI() {
    cartItemsList.innerHTML = "";
    let total = 0;

    cart.forEach(item => {
        total += item.price;
        const li = document.createElement("li");
        li.innerHTML = `<span>${item.name}</span><span>$${item.price.toFixed(2)}</span>`;
        cartItemsList.appendChild(li);
    });

    cartCount.textContent = cart.length;
    cartTotal.textContent = total.toFixed(2);
    checkoutBtn.disabled = cart.length === 0;
}

filterButtons.forEach(btn => {
    btn.addEventListener("click", () => {
        document.querySelector(".filter-btn.active").classList.remove("active");
        btn.classList.add("active");
        renderMenu(btn.getAttribute("data-category"));
    });
});

checkoutBtn.addEventListener("click", () => {
    const finalTotal = cartTotal.textContent;
    cart = [];
    updateCartUI();
    orderConfirmation.textContent = `Order placed successfully! Total charged: $${finalTotal}`;
    orderConfirmation.classList.remove("hidden");
});

// Initial render
renderMenu();
