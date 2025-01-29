package org.example.blockchaintopic3.classes;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Block {
    private String hash;
    private final String previousHash;
    private final long timestamp;
    private int nonce;
    private final String productCode;
    private final String title;
    private final double price;
    private final String description;
    private final String category;

    /**
     * @builder.hash
     * if has value get it from the database's query
     * if hans't calculate it
     */
    private Block(Builder builder) {
        this.previousHash = builder.previousHash;
        this.timestamp = builder.timestamp;
        this.productCode = builder.productCode;
        this.title = builder.title;
        this.price = builder.price;
        this.description = builder.description;
        this.category = builder.category;

        if (builder.hash != null) {
            this.hash = builder.hash;  // get it from the database's query
        } else {
            this.hash = calculateBlockHash(); // calculate only if it must created
        }
    }

    static public class Builder {
        private String hash;
        private final String previousHash;
        private final long timestamp;
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

        /**
         * Set the database's hash after build the object
         * @return this to get the Builder object
         */
        public Builder setHash(String hash) {
            this.hash = hash;
            return this;
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
            bytes = digest.digest(dataToHash.getBytes("UTF-8")); // Χρήση του digest.digest()
        }catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        StringBuilder builder = new StringBuilder();
        for (byte b : bytes){
            builder.append(String.format("%02x",b));
        }
        return builder.toString();
    }

    public String getHash() {
        return hash;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public String getProductCode() {
        return productCode;
    }

    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "Block{" +
            "productCode='" + productCode + '\'' +
            ", title='" + title + '\'' +
            ", timestamp=" + timestamp +
            ", price=" + price +
            ", description='" + description + '\'' +
            ", category='" + category + '\'' +
            ", previousHash='" + previousHash + '\'' +
            ", hash='" + hash + '\'' +
            '}';
    }
}