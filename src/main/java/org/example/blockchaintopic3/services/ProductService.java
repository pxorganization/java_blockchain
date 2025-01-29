package org.example.blockchaintopic3.services;

import jakarta.annotation.PostConstruct;
import org.example.blockchaintopic3.classes.Block;
import org.example.blockchaintopic3.repositories.ProductDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ProductService {

    @Autowired
    private ProductDAO productDAO;

    public static List<Block> blockChain = new ArrayList<>();
    public static final int prefix = 3;

    public void createBlock(String productCode, String title, double price, String description, String category) {

        //Check if previous hash is existing
        String previousHash = blockChain.isEmpty() ? "0" : blockChain.get(blockChain.size() - 1).getHash();
        long timestamp = System.currentTimeMillis();

        Block newBlock = new Block.Builder(previousHash, productCode, title, timestamp, price, description, category).build();

        newBlock.mineBlock(prefix);

        blockChain.add(newBlock);
        System.out.println("Node " + blockChain.size() + " created successfully.");

        // Save the block to the database
        productDAO.saveBlock(newBlock);
        System.out.println("Block saved to database successfully.");
    }

    /**
     * Create the blockchain from database's blocks
     */
    @PostConstruct
    public void loadBlockchainFromDatabase() {
        List<Block> tempBlockChain = productDAO.getAllBlocks();

        // Check if the database contains blocks
        if (!tempBlockChain.isEmpty()) {
            blockChain.clear();
            blockChain.addAll(tempBlockChain);

            System.out.println("Blockchain loaded with " + blockChain.size() + " blocks.");
        } else {
            System.out.println("No blocks found in database. Starting with an empty blockchain.");
        }

        boolean isValid = isChainValid();
        if (!blockChain.isEmpty()) {
            System.out.println("Is chain valid? " + isValid);
        }
    }

    /**
     * Check if blocks in blockchain are correct connected
     */
    public static boolean isChainValid(){
        if (blockChain.isEmpty()) {
            System.out.println("Blockchain is empty.");
            return false;
        }

        for (int i = 1; i < blockChain.size(); i++) {
            Block currentBlock = blockChain.get(i);
            Block previousBlock = blockChain.get(i - 1);
            if (!currentBlock.getPreviousHash().equals(previousBlock.getHash())) {
                return false;
            }
        }
        return true;
    }

    public List<Block> searchBySelection(String select, String value) {
        return productDAO.searchBySelection(select, value);
    }

    public List<String> getStatistics(String title) {
        List<Map<String, Object>> result = productDAO.getStatistics(title);

        if (result.isEmpty()) {
            return new ArrayList<>();
        }

        List<String> statistics = new ArrayList<>();

        for (Map<String, Object> row : result) {
            title = (String) row.get("title");
            Double price = (Double) row.get("price");
            String category = (String) row.get("category");
            Long timestamp = (Long) row.get("timestamp");

            String statistic = String.format("Title: %s, Price: %.2f, Category: %s, Timestamp: %d", title, price, category, timestamp);
            statistics.add(statistic);
        }
        return statistics;
    }
}
