package org.example.blockchaintopic3;

import org.example.blockchaintopic3.classes.Block;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.google.gson.GsonBuilder;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class Blockchaintopic3Application {

//    public static List<Block> blockChain = new ArrayList<>();
//    public static final int prefix = 1;

    public static void main(String[] args) {
        SpringApplication.run(Blockchaintopic3Application.class, args);



//        //Transform into Json
//        String json = new GsonBuilder().setPrettyPrinting().create().toJson(blockChain);
//        System.out.println("The blockChain:");
//        System.out.println(json);
//
//        System.out.println("Is chain valid? Result: "+isChainValid());

        // Τερματισμός της εφαρμογής
        //System.exit(0);
    }


}
