package com.windsor.traineerushbe.dao;

import com.windsor.traineerushbe.model.User;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class UserDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public UserDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public Integer createUser(User user) {
        String sql = "INSERT INTO user(name, phone, address) VALUES (:name, :phone, :address)";

        Map<String, Object> map = new HashMap<>();
        map.put("name", user.getName());
        map.put("phone", user.getPhone());
        map.put("address", user.getAddress());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        return keyHolder.getKey().intValue();
    }
}

