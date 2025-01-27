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

    public static List<Block> blockChain = new ArrayList<>();
    public static final int prefix = 1;

    public static void main(String[] args) {
        SpringApplication.run(Blockchaintopic3Application.class, args);

        //1st block
        System.out.println("Process started");
        Block genesisBlock = new Block.Builder("0","AB123C","Milk",new Date().getTime(),2.16,"Cow Milk","B class").build();
        genesisBlock.mineBlock(prefix);
        blockChain.add(genesisBlock);
        System.out.println("Node "+(blockChain.size())+" created");

        //2nd block
        System.out.println("Process started");
        Block secondBlock = new Block.Builder(blockChain.get(blockChain.size()-1).getHash(),"A3323C","Beer",new Date().getTime(),4.10,"Mamos","A class").build();
        secondBlock.mineBlock(prefix);
        blockChain.add(secondBlock);
        System.out.println("Node "+(blockChain.size())+" created");

        //3rd block
        System.out.println("Process started");
        Block thirdBlock = new Block.Builder(blockChain.get(blockChain.size()-1).getHash(),"AB3DSB","Juice",new Date().getTime(),3.55,"Orange","C class").build();
        thirdBlock.mineBlock(prefix);
        blockChain.add(thirdBlock);
        System.out.println("Node "+(blockChain.size())+" created");


        //Transform into Json
        String json = new GsonBuilder().setPrettyPrinting().create().toJson(blockChain);
        System.out.println("The blockChain:");
        System.out.println(json);

        System.out.println("Is chain valid? Result: "+isChainValid());
    }

    public static boolean isChainValid(){
        Block currentBlock;
        Block previousBlock;
        String hashTarget = new String(new char[prefix]).replace('\0','0');
        for (int i=1;i<blockChain.size();i++){
            currentBlock = blockChain.get(i);
            previousBlock = blockChain.get(i-1);
            if (!currentBlock.getHash().equals(currentBlock.calculateBlockHash()))
                return false;
            if (!previousBlock.getHash().equals(currentBlock.getPreviousHash()))
                return false;
            if (!currentBlock.getHash().substring(0,prefix).equals(hashTarget))
                return false;
        }
        return true;
    }
}
