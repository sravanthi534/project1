package com.Revshop.revshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Revshop.revshop.model.OrderDetails;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long>{

	List<OrderDetails> findByOrder_OrderId(Long orderId);
}