package com.windsor.traineerushbe.dao;

import com.windsor.traineerushbe.model.User;
import com.windsor.traineerushbe.rowmapper.UserRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
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

    public User getUserById(Integer userId) {
        String sql = "SELECT user_id, name, phone, address, created_date, last_modified_date " +
                "FROM user WHERE user_id = :user_id";

        Map<String, Object> map = new HashMap<>();
        map.put("user_id", userId);

        List<User> userList = namedParameterJdbcTemplate.query(sql, map, new UserRowMapper());

        if (userList.isEmpty()) {
            return null;
        }

        return userList.get(0);
    }
}

