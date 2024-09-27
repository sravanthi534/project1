let itemIdToRemove = null;

function showRemoveModal(itemId) {
    itemIdToRemove = itemId;
    const removeModal = new bootstrap.Modal(document.getElementById('removeModal'));
    removeModal.show();
}

document.getElementById('confirmRemoveBtn').addEventListener('click', function () {
    if (itemIdToRemove !== null) {
        removeItem(itemIdToRemove);
        itemIdToRemove = null;
        const removeModal = bootstrap.Modal.getInstance(document.getElementById('removeModal'));
        removeModal.hide();
    }
});

function removeItem(itemId) {
    const item = document.querySelector(`.cart-item[data-item-id="${itemId}"]`);
    if (item) {
        item.remove();
        updatePrice(); // Recalculate price after item removal
    }
}

function saveForLater(itemId) {
    const cartItem = document.querySelector(`.cart-item[data-item-id="${itemId}"]`);
    if (cartItem) {
        const savedItemsContainer = document.getElementById('saved-items');
        const saveForLaterButton = cartItem.querySelector('.save-for-later-btn');
        saveForLaterButton.textContent = 'MOVE TO CART';
        saveForLaterButton.setAttribute('onclick', `moveToCart(${itemId})`);
        savedItemsContainer.appendChild(cartItem);
    }
}

function moveToCart(itemId) {
    const savedItem = document.querySelector(`.cart-item[data-item-id="${itemId}"]`);
    if (savedItem) {
        const cartItemsContainer = document.getElementById('cart-items');
        const moveToCartButton = savedItem.querySelector('.save-for-later-btn');
        moveToCartButton.textContent = 'SAVE FOR LATER';
        moveToCartButton.setAttribute('onclick', `saveForLater(${itemId})`);
        cartItemsContainer.appendChild(savedItem);
    }
}

function updateQuantity(itemId, price, increase) {
    const quantityInput = document.getElementById(`quantity${itemId}`);
    let currentQuantity = parseInt(quantityInput.value);
    if (increase) {
        currentQuantity++;
    } else {
        currentQuantity = currentQuantity > 1 ? currentQuantity - 1 : 1;
    }
    quantityInput.value = currentQuantity;
    updatePrice(); // Call function to update price when quantity changes
}

function updatePrice() {
    // Recalculate price logic here, update totalAmount and totalPrice
}
