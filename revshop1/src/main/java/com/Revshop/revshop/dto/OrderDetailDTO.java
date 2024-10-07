package com.Revshop.revshop.dto;

public class OrderDetailDTO {
	private long productId;
    private String productName;
    private String productImage;
    private double discountPrice;
    private int quantity;

    // Constructor
    public OrderDetailDTO(long productId, String productName, String productImage, double discountPrice, int quantity) {
    	this.productId = productId;
        this.productName = productName;
        this.productImage = productImage;
        this.discountPrice = discountPrice;
        this.quantity = quantity;
    }

    public long getProductId() {
		return productId;
	}

	// Getters
    public String getProductName() {
        return productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public double getDiscountPrice() {
        return discountPrice;
    }

    public int getQuantity() {
        return quantity;
    }
}