package org.example.blockchaintopic3.classes;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Block {
    private String hash;
    private final String previousHash;
    private final long timestamp;
    private int nonce = 0;
    private final int id = 0;
    private final String productCode;
    private final String title;
    private final double price;
    private final String description;
    private final String category;

    public Block(Builder builder) {
        this.previousHash = builder.previousHash;
        this.timestamp = builder.timestamp;
        this.productCode = builder.productCode;
        this.title = builder.title;
        this.price = builder.price;
        this.description = builder.description;
        this.category = builder.category;
        this.hash = calculateBlockHash();
    }

    static public class Builder {
        private final String previousHash;
        private final long timestamp;
        private int nonce = 0;
        private final int id = 0;
        private final String productCode;
        private final String title;
        private final double price;
        private final String description;
        private final String category;

        public Builder(String previousHash, String productCode, String title, long timestamp, double price, String description, String category) {
            this.previousHash = previousHash;
            this.timestamp = timestamp;
            this.productCode = productCode;
            this.title = title;
            this.price = price;
            this.description = description;
            this.category = category;
        }

        public Block build(){
            return new Block(this);
        }
    }

    public String mineBlock(int prefix){
        String prefixString = new String(new char[prefix]).replace('\0','0');
        while (!hash.substring(0,prefix).equals(prefixString)){
            nonce++;
            hash = calculateBlockHash();
        }
        return hash;
    }

    public String calculateBlockHash() {
        String dataToHash = previousHash + timestamp + productCode + price + title + description + category + nonce;
        MessageDigest digest = null;
        byte[] bytes = null;

        try{
            digest = MessageDigest.getInstance("SHA-256");
            bytes = dataToHash.getBytes("UTF-8");
        }catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        StringBuilder builder = new StringBuilder();
        for (byte b : bytes){
            builder.append(String.format("%02x",b));
        }
        return builder.toString();
    }
}
