package com.example.MiniTradingApp.order;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserOrderRepository {

    private final JdbcTemplate jdbc;

    public UserOrderRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public Optional<UserOrder> findByOrderId(long orderId) {
        String sql = """
                SELECT
                    uo.id,
                    uo.user_id,
                    s.ticker,
                    uo.direction,
                    uo.quantity,
                    uo.price_per_share,
                    uo.status,
                    uo.rejection_reason
                FROM user_order uo
                JOIN stock s ON uo.stock_id = s.id
                WHERE uo.id = ?
                """;

        return jdbc.query(sql, userOrderRowMapper(), orderId)
                .stream()
                .findFirst();
    }

    public List<UserOrder> findAllByUsername(String username) {
        String sql = """
                SELECT
                    uo.id,
                    uo.user_id,
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

    public List<UserOrder> findPendingOrders() {
        String sql = """
                SELECT
                    uo.id,
                    uo.user_id,
                    s.ticker,
                    uo.direction,
                    uo.quantity,
                    uo.price_per_share,
                    uo.status,
                    uo.rejection_reason
                FROM user_order uo
                JOIN stock s ON uo.stock_id = s.id
                WHERE uo.status = ?
                ORDER BY uo.id ASC
                """;

        return jdbc.query(sql, userOrderRowMapper(), OrderStatus.PENDING.name());
    }

    public void approve(long orderId) {
        String sql = """
                UPDATE user_order
                SET status = ?
                WHERE id = ?
                """;

        jdbc.update(sql, OrderStatus.APPROVED.name(), orderId);
    }

    public void rejectOrder(long orderId, String rejectionReason) {
        String sql = """
                UPDATE user_order
                SET status = ?,
                    rejection_reason = ?
                WHERE id = ?
                """;

        jdbc.update(sql, OrderStatus.REJECTED.name(), rejectionReason, orderId);
    }

    private RowMapper<UserOrder> userOrderRowMapper() {
        return (rs, rowNum) -> new UserOrder(
                rs.getLong("id"),
                rs.getLong("user_id"),
                rs.getString("ticker"),
                OrderDirection.valueOf(rs.getString("direction")),
                rs.getInt("quantity"),
                rs.getDouble("price_per_share"),
                OrderStatus.valueOf(rs.getString("status")),
                rs.getString("rejection_reason")
        );
    }
}
