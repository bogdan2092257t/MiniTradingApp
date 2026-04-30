package com.example.MiniTradingApp.order;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserOrderRepository {

    private final JdbcTemplate jdbc;

    public UserOrderRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<UserOrder> findAllByUsername(String username) {
        String sql = """
                SELECT
                    uo.id,
                    s.ticker,
                    uo.direction,
                    uo.quantity,
                    uo.price_per_share,
                    uo.status,
                    uo.rejection_reason
                FROM user_order uo
                JOIN users u ON uo.user_id = u.id
                JOIN stock s ON uo.stock_id = s.id
                WHERE u.username = ?
                ORDER BY uo.id DESC
                """;

        return jdbc.query(sql, userOrderRowMapper(), username);
    }

    private RowMapper<UserOrder> userOrderRowMapper() {
        return (rs, rowNum) -> new UserOrder(
                rs.getLong("id"),
                rs.getString("ticker"),
                OrderDirection.valueOf(rs.getString("direction")),
                rs.getInt("quantity"),
                rs.getDouble("price_per_share"),
                OrderStatus.valueOf(rs.getString("status")),
                rs.getString("rejection_reason")
        );
    }
}
