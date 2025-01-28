package org.example.blockchaintopic3.controller;

import org.example.blockchaintopic3.classes.Block;
import org.example.blockchaintopic3.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.example.blockchaintopic3.services.ProductService.blockChain;

@RestController
@RequestMapping("/products")
public class Controllers {

    @Autowired
    private ProductService productService;

    // Μέθοδος για την ανάκτηση όλων των blocks
    @GetMapping("/all")
    public List<Block> getAllProducts() {
        return blockChain;
    }

    // Μέθοδος για την προσθήκη ενός block
    @PostMapping("/add")
    public String addProduct(@RequestParam String productCode,
                             @RequestParam String title,
                             @RequestParam double price,
                             @RequestParam String description,
                             @RequestParam String category) {

        productService.createBlock(productCode, title, price, description, category);

        return "Block added successfully!";
    }

//    @PostMapping("/add_many")
//    public List<String> addProductMany() {
//    }

//    @GetMapping("/search")
//    public List<Block> searchProduct(@RequestParam String productCode) {
//        return productService.getBlocksByProductCode(productCode);
//    }

//    @GetMapping("/statistics")
//    public List<String> getStatistics() {
//    }

}
