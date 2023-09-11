package com.windsor.traineerushbe.rowmapper;


import com.windsor.traineerushbe.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        User user = new User();

        user.setUserId(resultSet.getInt("user_id"));
        user.setName(resultSet.getString("name"));
        user.setPhone(resultSet.getString("phone"));
        user.setAddress(resultSet.getString("address"));
        user.setCreatedDate(resultSet.getTimestamp("created_date"));
        user.setLastModifiedDate(resultSet.getTimestamp("last_modified_date"));

        return user;
    }
}
