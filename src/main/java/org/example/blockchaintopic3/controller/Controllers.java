package org.example.blockchaintopic3.controller;

import org.example.blockchaintopic3.classes.Block;
import org.example.blockchaintopic3.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @GetMapping("/search")
    public List<Block> searchProduct(@RequestParam String select, @RequestParam String value) {
        return productService.searchBySelection(select, value);
    }


    @PostMapping("/add_many")
    public List<String> addProductMany(@RequestParam int quantity,
                                       @RequestParam List<String> productCodes,
                                       @RequestParam List<String> titles,
                                       @RequestParam List<Double> prices,
                                       @RequestParam List<String> descriptions,
                                       @RequestParam List<String> categories) {
        List<String> results = new ArrayList<>();

        // Make sure the number of inputs matches the quantity
        if (productCodes.size() != quantity || titles.size() != quantity || prices.size() != quantity
                || descriptions.size() != quantity || categories.size() != quantity) {
            results.add("Error: Mismatch in the number of fields provided.");
            return results;
        }

        // Loop over the provided product details and create each product
        for (int i = 0; i < quantity; i++) {
            try {
                // Call the service to create the product
                productService.createBlock(productCodes.get(i), titles.get(i), prices.get(i),
                        descriptions.get(i), categories.get(i));
                results.add("Product " + (i + 1) + " added successfully.");
            } catch (Exception e) {
                results.add("Error adding product " + (i + 1) + ": " + e.getMessage());
            }
        }

        return results;
    }



//    @GetMapping("/statistics")
//    public List<String> getStatistics() {
//    }

}
