package com.danmag.ecommerce.service.controller;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//TODO make Cart on server side and use Singleton
//TODO Command: You could use the Command pattern to handle undo/redo operations when a customer updates their shopping cart.
// Each time the customer makes a change to the cart,
// the change would be represented by a command object that could be stored and later used to undo or redo the change.
@CrossOrigin
@RestController
@RequestMapping(value = "/api/cart")
public class OrderItemController {


}
