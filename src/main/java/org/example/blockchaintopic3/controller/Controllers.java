package org.example.blockchaintopic3.controller;

import org.example.blockchaintopic3.classes.Block;
import org.example.blockchaintopic3.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.example.blockchaintopic3.services.ProductService.blockChain;
import static org.example.blockchaintopic3.services.ProductService.isChainValid;

@RestController
@RequestMapping("/products")
public class Controllers {

    @Autowired
    private ProductService productService;

    // get all blocks
    @GetMapping("/all")
    public List<Block> getAllProducts() {
        System.out.println(isChainValid());
        return blockChain;
    }

    // Add one block
    @PostMapping("/add")
    public String addProduct(@RequestParam String productCode,
                             @RequestParam String title,
                             @RequestParam double price,
                             @RequestParam String description,
                             @RequestParam String category) {

        productService.createBlock(productCode, title, price, description, category);
        System.out.println(isChainValid());
        return "Block added successfully!";
    }

    /**
     * Search using a select method and a value for it
     * Valid values product_code, title, price, category, description
     */
    @GetMapping("/search")
    public List<Block> searchProduct(@RequestParam String select, @RequestParam String value) {
        return productService.searchBySelection(select, value);
    }

    // Add number of products and values
    // http://localhost:8080/products/add_many?quantity=2&productCodes=B23234&titles=Product1&prices=10.0&descriptions=Description1&categories=Category1&productCodes=B23235&titles=Product2&prices=20.0&descriptions=Description2&categories=Category2
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
                // create the product
                productService.createBlock(productCodes.get(i), titles.get(i), prices.get(i),
                        descriptions.get(i), categories.get(i));
                results.add("Product " + (i + 1) + " added successfully.");
            } catch (Exception e) {
                results.add("Error adding product " + (i + 1) + ": " + e.getMessage());
            }
        }
        System.out.println(isChainValid());
        return results;
    }

    @GetMapping("/statistics")
    public List<String> getStatistics(@RequestParam String title) {
        return productService.getStatistics(title);
    }

}
