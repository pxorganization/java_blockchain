package org.example.blockchaintopic3.services;

import jakarta.annotation.PostConstruct;
import org.example.blockchaintopic3.classes.Block;
import org.example.blockchaintopic3.repositories.ProductDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductDAO productDAO;

    public static List<Block> blockChain = new ArrayList<>();
    public static final int prefix = 3;

    // Μέθοδος για την προσθήκη ενός block
    public void createBlock(String productCode, String title, double price, String description, String category) {

        String previousHash = blockChain.isEmpty() ? "0" : blockChain.get(blockChain.size() - 1).getHash();
        long timestamp = System.currentTimeMillis();

        Block newBlock = new Block.Builder(previousHash, productCode, title, timestamp, price, description, category).build();

        // Mine the block
        newBlock.mineBlock(prefix);

        // Add the block to the in-memory blockchain
        blockChain.add(newBlock);
        System.out.println("Node " + blockChain.size() + " created successfully.");

        // Save the block to the database
        productDAO.saveBlock(newBlock);
        System.out.println("Block saved to database successfully.");
    }

    // Μέθοδος για την ανάκτηση όλων των blocks
    public List<Block> getAllBlocks() {
        return productDAO.getAllBlocks();
    }

    @PostConstruct
    public void loadBlockchainFromDatabase() {
        List<Block> tempBlockChain = productDAO.getAllBlocks();

        if (!tempBlockChain.isEmpty()) { // Check if the database contains blocks
            blockChain.clear(); // Clear in-memory blockchain before loading
            blockChain.addAll(tempBlockChain);

            System.out.println("Blockchain loaded with " + blockChain.size() + " blocks.");
        } else {
            System.out.println("No blocks found in database. Starting with an empty blockchain.");
        }

        boolean isValid = isBlockchainValid();
        System.out.println("Is chain valid? " + isValid);
    }

    public boolean isBlockchainValid() {
        // Check if the blockchain is empty
        if (blockChain.isEmpty()) {
            System.out.println("Blockchain is empty.");
            return false;
        }

        // Iterate over each block to check its validity
        for (int i = 1; i < blockChain.size(); i++) {
            Block currentBlock = blockChain.get(i);
            Block previousBlock = blockChain.get(i - 1);

            // 1. Check if the hash of the current block is correct
            if (!currentBlock.getHash().equals(currentBlock.calculateBlockHash())) {
                System.out.println("Block " + i + " has an invalid hash.");
                return false;
            }
            // 2. Check if the previousHash of the current block matches the hash of the previous block
            if (!currentBlock.getPreviousHash().equals(previousBlock.getHash())) {
                System.out.println("Block " + i + " has an invalid previous hash.");
                return false;
            }
        }
        System.out.println("Blockchain is valid.");
        return true;
    }




//
//    public List<Block> getBlocksByProductCode(String productCode) {
//        return productDAO.getBlocksByProductCode(productCode);
//    }
}
