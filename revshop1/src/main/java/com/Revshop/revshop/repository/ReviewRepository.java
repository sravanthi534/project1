package com.Revshop.revshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Revshop.revshop.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Long>{

}