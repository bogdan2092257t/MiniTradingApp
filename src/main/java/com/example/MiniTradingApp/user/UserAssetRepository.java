package com.example.MiniTradingApp.user;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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

    public Optional<UserAsset> findByUserDetailsAndTicker(UserDetails userDetails, String ticker) {
        String sql = """
                SELECT
                    s.id AS stock_id,
                    s.ticker AS stock_ticker,
                    ua.quantity,
                    ua.price_per_share
                FROM user_asset ua
                JOIN stock s ON ua.stock_id = s.id
                WHERE ua.user_id = ?
                  AND s.ticker = ?
                """;

        return jdbc.query(sql, userAssetRowMapper(), userDetails.getId(), ticker)
                .stream()
                .findFirst();
    }

    public void save(UserDetails userDetails, String ticker, int quantity, double pricePerShare) {
        String sql = """
                INSERT INTO user_asset (
                    user_id,
                    stock_id,
                    quantity,
                    price_per_share
                )
                VALUES (
                    ?,
                    (SELECT id FROM stock WHERE ticker = ?),
                    ?,
                    ?
                )
                """;

        jdbc.update(sql, userDetails.getId(), ticker, quantity, pricePerShare);
    }

    public void delete(UserDetails userDetails, String ticker) {
        String sql = """
                DELETE FROM user_asset
                WHERE user_id = ?
                  AND stock_id = (
                      SELECT id FROM stock WHERE ticker = ?
                  )
                """;

        jdbc.update(sql, userDetails.getId(), ticker);
    }

    public void updateQuantityAndPricePerShare(UserDetails userDetails, String ticker, int quantity, double pricePerShare) {
        String sql = """
                UPDATE user_asset
                SET quantity = ?,
                    price_per_share = ?
                WHERE user_id = ?
                  AND stock_id = (
                      SELECT id FROM stock WHERE ticker = ?
                  )
                """;

        jdbc.update(sql, quantity, pricePerShare, userDetails.getId(), ticker);
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
