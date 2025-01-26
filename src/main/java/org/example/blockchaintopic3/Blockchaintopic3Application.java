package org.example.blockchaintopic3;

import org.example.blockchaintopic3.classes.Block;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class Blockchaintopic3Application {

    public static List<Block> blockChain = new ArrayList<>();
    public static final int prefix = 3;

    public static void main(String[] args) {
        SpringApplication.run(Blockchaintopic3Application.class, args);

        System.out.println("Process started");
        Block genesisBlock = new Block.Builder("0","AB123C","Milk",new Date().getTime(),2.16,"Cow Milk","B class").build();
        genesisBlock.mineBlock(prefix);
        blockChain.add(genesisBlock);
        System.out.println("Node "+(blockChain.size())+" created");


    }

}
