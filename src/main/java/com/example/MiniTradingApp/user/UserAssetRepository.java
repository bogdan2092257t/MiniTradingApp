package com.example.MiniTradingApp.user;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserAssetRepository {

    private final JdbcTemplate jdbc;

    public UserAssetRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<UserAsset> findByUserDetails(UserDetails userDetails) {
        String sql = """
                SELECT
                    s.id AS stock_id,
                    s.ticker AS stock_ticker,
                    ua.quantity,
                    ua.price_per_share
                FROM user_asset ua
                JOIN stock s ON ua.stock_id = s.id
                WHERE ua.user_id = ?
                """;

        return jdbc.query(sql, userAssetRowMapper(), userDetails.getId());
    }

    private RowMapper<UserAsset> userAssetRowMapper() {
        return (rs, rowNum) -> {
            Stock stock = new Stock(
                    rs.getLong("stock_id"),
                    rs.getString("stock_ticker")
            );

            return new UserAsset(
                    stock,
                    rs.getInt("quantity"),
                    rs.getDouble("price_per_share"),
                    rs.getInt("quantity") * rs.getDouble("price_per_share")
            );
        };
    }
}
