package com.windsor.traineerushbe.dao;

import com.windsor.traineerushbe.dto.OrderItemRequest;
import com.windsor.traineerushbe.dto.OrderQueryParams;
import com.windsor.traineerushbe.model.Order;
import com.windsor.traineerushbe.model.OrderItem;
import com.windsor.traineerushbe.rowmapper.OrderItemRowMapper;
import com.windsor.traineerushbe.rowmapper.OrderRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OrderDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public OrderDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }


    public Integer countOrder() {
        String sql = "SELECT count(*) FROM `order`";

        return namedParameterJdbcTemplate.queryForObject(sql, new HashMap<>(), Integer.class);
    }


    public List<Order> getOrders(OrderQueryParams orderQueryParams) {
        String sql = "SELECT order_id, user_id, total_amount, created_date, last_modified_date " +
                "FROM `order` WHERE 1 = 1";

        Map<String, Object> map = new HashMap<>();

        // 排序
        sql = sql + " ORDER BY created_date DESC";

        // 分頁
        sql = sql + " LIMIT :limit OFFSET :offset";
        map.put("limit", orderQueryParams.getLimit());
        map.put("offset", orderQueryParams.getOffset());

        return namedParameterJdbcTemplate.query(sql, map, new OrderRowMapper());
    }


    public Order getOrderById(Integer orderId) {
        String sql = "SELECT order_id, user_id, total_amount, created_date, last_modified_date " +
                "FROM `order` WHERE order_id = :orderId";

        Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);

        List<Order> orderList = namedParameterJdbcTemplate.query(sql, map, new OrderRowMapper());

        if (orderList.isEmpty()) {
            return null;
        }

        return orderList.get(0);
    }


    public List<OrderItem> getOrderItemsByOrderId(Integer orderId) {

        String sql = "SELECT order_item_id, order_item_key, order_id, product_name, " +
                "ice, sweetness, quantity, price, item_total_price " +
                "FROM order_item WHERE order_id = :orderId";

        Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);

        return namedParameterJdbcTemplate.query(sql, map, new OrderItemRowMapper());
    }

    public Integer createOrder(Integer userId, Integer totalAmount) {
        String sql = "INSERT INTO `order`(user_id, total_amount) VALUES (:userId, :totalAmount)";

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("totalAmount", totalAmount);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        return keyHolder.getKey().intValue();
    }


    public void createOrderItems(Integer orderId, List<OrderItemRequest> orderItemRequestList) {
        String sql = "INSERT INTO order_item(order_item_key, order_id, product_name, ice, sweetness, quantity, price, item_total_price) " +
                "VALUES (:order_item_key, :order_id, :product_name, :ice, :sweetness, :quantity, :price, :item_total_price)";

        // 使用batchUpdate 一次加入數據，效率較高

        MapSqlParameterSource[] parameterSources = new MapSqlParameterSource[orderItemRequestList.size()];

        for (int i = 0; i < orderItemRequestList.size(); i++) {
            OrderItemRequest orderItemRequest = orderItemRequestList.get(i);

            parameterSources[i] = new MapSqlParameterSource();
            parameterSources[i].addValue("order_item_key", orderItemRequest.getOrderItemKey());
            parameterSources[i].addValue("order_id", orderId);
            parameterSources[i].addValue("product_name", orderItemRequest.getProductName());
            parameterSources[i].addValue("ice", orderItemRequest.getIce());
            parameterSources[i].addValue("sweetness", orderItemRequest.getSweetness());
            parameterSources[i].addValue("quantity", orderItemRequest.getQuantity());
            parameterSources[i].addValue("price", orderItemRequest.getPrice());
            parameterSources[i].addValue("item_total_price", orderItemRequest.getItemTotalPrice());
        }

        namedParameterJdbcTemplate.batchUpdate(sql, parameterSources);
    }
}
