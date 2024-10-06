package com.Revshop.revshop.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Revshop.revshop.dto.OrderDetailDTO;
import com.Revshop.revshop.model.CartItem;
import com.Revshop.revshop.model.OrderDetails;
import com.Revshop.revshop.model.Orders;
import com.Revshop.revshop.model.Product;
import com.Revshop.revshop.model.User;
import com.Revshop.revshop.repository.CartItemRepository;
import com.Revshop.revshop.repository.OrderDetailsRepository;
import com.Revshop.revshop.repository.OrdersRepository;
import com.Revshop.revshop.repository.ProductRepository;
import com.Revshop.revshop.repository.UserRepository;
import com.Revshop.revshop.service.BuyerOrderService;

import jakarta.servlet.http.HttpSession;

@Controller
public class BuyerOrderController {

	@Autowired
    private ProductRepository productRepo;
	@Autowired
    private OrdersRepository ordersRepo;    
    @Autowired
    private OrderDetailsRepository orderDetailsRepo;
	@Autowired
	private BuyerOrderService buyerOrderService;
	@Autowired
	private UserRepository userRepo;
	
	@PostMapping("/checkout")
	public String handleCheckout(@RequestParam("totalPrice") double totalPrice,HttpSession session, Model model) {
	    
	    String email = (String)session.getAttribute("email");
	    if (email == null) {
            return "redirect:/login";
        }
	    
	    Optional<User> user = userRepo.findByEmail(email);
        if (user.isPresent()) {
        	 buyerOrderService.createOrders(email, totalPrice);
        }
        else
        {
        	return "redirect:/login";
        } 	    
	    return "orderConfirmation"; 
	}
	
	@GetMapping("/viewOrders")
    public String viewOrders(HttpSession session, Model model) {
        String email = (String) session.getAttribute("email");
        if (email == null) {
            return "redirect:/login";
        }

        Optional<User> userOpt = userRepo.findByEmail(email);
        if (userOpt.isPresent()) {
            Long userId = userOpt.get().getUserId();

            // Step 1: Fetch all orders for the user
            List<Orders> orders = ordersRepo.findOrdersByUser_UserId(userId);
            List<OrderDetailDTO> orderDetailsList = new ArrayList<>();

            // Step 2: Loop through each order
            for (Orders order : orders) {
                // Step 3: Get the order details for each order
                var orderDetails = orderDetailsRepo.findByOrder_OrderId(order.getOrderId());

                // Step 4: For each order detail, get product details
                for (var orderDetail : orderDetails) {
                    var productOpt = productRepo.findById(orderDetail.getProduct().getProductId());
                    if (productOpt.isPresent()) {
                        var product = productOpt.get();
                        OrderDetailDTO orderDetailDTO = new OrderDetailDTO(
                        		product.getProductId(),
                                product.getName(),
                                product.getImage(),
                                product.getDiscountprice(),
                                orderDetail.getQuantity()
                        );
                        orderDetailsList.add(orderDetailDTO);
                    }
                }
            }

            // Step 5: Add the order details to the model
            model.addAttribute("orderDetailsList", orderDetailsList);
            return "BuyerOrders"; // The view to display order details
        } else {
            return "redirect:/login";
        }
    }

	
}