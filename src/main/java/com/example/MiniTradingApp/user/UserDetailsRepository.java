package com.example.MiniTradingApp.user;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserDetailsRepository {
    private final JdbcTemplate jdbc;

    public UserDetailsRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public Optional<UserDetails> findByUsername(String username) {
        String sql = "SELECT id, username, name, balance FROM user_details WHERE username = ?";
        return jdbc.query(sql, userDetailsRowMapper(), username)
                .stream()
                .findFirst();
    }

    private RowMapper<UserDetails> userDetailsRowMapper() {
        return (rs, rowNum) -> new UserDetails(
                rs.getLong("id"),
                rs.getString("username"),
                rs.getString("name"),
                rs.getDouble("balance")
        );
    }
}
