package org.example.blockchaintopic3.repositories;

import org.example.blockchaintopic3.classes.Block;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void saveBlock(Block block) {
        String sql = "INSERT INTO blocks (hash, previous_hash, product_code, title, timestamp, price, description, category) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(
                sql,
                block.getHash(),
                block.getPreviousHash(),
                block.getProductCode(),
                block.getTitle(),
                block.getTimestamp(),
                block.getPrice(),
                block.getDescription(),
                block.getCategory()
        );
    }


    public List<Block> getAllBlocks() {
        String sql = "SELECT * FROM blocks";

        return jdbcTemplate.query(sql, (rs, rowNum) -> new Block.Builder(
                rs.getString("previous_hash"),
                rs.getString("product_code"),
                rs.getString("title"),
                rs.getLong("timestamp"),
                rs.getDouble("price"),
                rs.getString("description"),
                rs.getString("category")
        ).build());
    }
//
//    public List<Block> getBlocksByProductCode(String productCode) {
//        String sql = "SELECT * FROM blocks WHERE product_code = ?";
//        return jdbcTemplate.query(sql, new Object[]{productCode}, (rs, rowNum) -> new Block(
//                rs.getString("previous_hash"),
//                rs.getInt("id"),
//                rs.getString("product_code"),
//                rs.getString("title"),
//                rs.getLong("timestamp"),
//                rs.getDouble("price"),
//                rs.getString("description"),
//                rs.getString("category")
//        ));
//    }
}