package org.example.blockchaintopic3.repositories;

import org.example.blockchaintopic3.classes.Block;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
        String sql = "SELECT * FROM blocks ORDER BY timestamp ASC";

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new Block.Builder(
                        rs.getString("previous_hash"),
                        rs.getString("product_code"),
                        rs.getString("title"),
                        rs.getLong("timestamp"),
                        rs.getDouble("price"),
                        rs.getString("description"),
                        rs.getString("category")
                ).setHash(rs.getString("hash")).build()
        );
    }

    public List<Block> searchBySelection(String select, String value) {

        // Only these selections are valid
        List<String> validColumns = Arrays.asList("product_code", "title", "price", "category", "description");

        if (!validColumns.contains(select)) {
            throw new IllegalArgumentException("Invalid column name");
        }

        String sql = "SELECT * FROM blocks WHERE " + select + " = ? ORDER BY timestamp ASC";

        List<Block> blocks = jdbcTemplate.query(sql, new Object[]{value}, (rs, rowNum) -> new Block.Builder(
                rs.getString("previous_hash"),
                rs.getString("product_code"),
                rs.getString("title"),
                rs.getLong("timestamp"),
                rs.getDouble("price"),
                rs.getString("description"),
                rs.getString("category")
        ).build());

        if (blocks.isEmpty()) {
            return blocks; // Return empty list if no records are found
        }

        // If multiple records found, return only first and last
        if(blocks.size() > 1) {
            return Arrays.asList(blocks.get(0), blocks.get(blocks.size() - 1));
        } else {
            return blocks;
        }
    }

    public List<Map<String, Object>> getStatistics(String title) {
        String sql = "SELECT title, price, category, timestamp FROM blocks WHERE title = ?";
        return jdbcTemplate.queryForList(sql, title);
    }
}