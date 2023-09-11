package com.windsor.traineerushbe.rowmapper;

import com.windsor.traineerushbe.model.OrderItem;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderItemRowMapper implements RowMapper<OrderItem> {
    @Override
    public OrderItem mapRow(ResultSet resultSet, int i) throws SQLException {
        OrderItem orderItem = new OrderItem();

        orderItem.setOrderItemId(resultSet.getInt("order_item_id"));
        orderItem.setOrderItemKey(resultSet.getString("order_item_key"));
        orderItem.setOrderId(resultSet.getInt("order_id"));
        orderItem.setProductName(resultSet.getString("product_name"));
        orderItem.setIce(resultSet.getString("ice"));
        orderItem.setSweetness(resultSet.getString("sweetness"));
        orderItem.setQuantity(resultSet.getInt("quantity"));
        orderItem.setPrice(resultSet.getInt("price"));
        orderItem.setItemTotalPrice(resultSet.getInt("item_total_price"));

        return orderItem;
    }
}
