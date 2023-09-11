package com.windsor.traineerushbe.service;

import com.windsor.traineerushbe.dao.OrderDao;
import com.windsor.traineerushbe.dao.UserDao;
import com.windsor.traineerushbe.dto.OrderQueryParams;
import com.windsor.traineerushbe.dto.OrderRequest;
import com.windsor.traineerushbe.model.Order;
import com.windsor.traineerushbe.model.OrderItem;
import com.windsor.traineerushbe.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {


    private final OrderDao orderDao;
    private final UserDao userDao;

    public OrderService(OrderDao orderDao, UserDao userDao) {
        this.orderDao = orderDao;
        this.userDao = userDao;
    }

    public Integer countOrder() {
        return orderDao.countOrder();
    }


    public List<Order> getOrders(OrderQueryParams orderQueryParams) {
        List<Order> orderList = orderDao.getOrders(orderQueryParams);

        for (Order order : orderList) {
            List<OrderItem> orderItemList = orderDao.getOrderItemsByOrderId(order.getOrderId());

            order.setOrderItemList(orderItemList);
        }

        return orderList;
    }


    public Order getOrderById(Integer orderId) {
        Order order = orderDao.getOrderById(orderId);

        User user = userDao.getUserById(order.getUserId());

        List<OrderItem> orderItemList = orderDao.getOrderItemsByOrderId(orderId);

        order.setOrderItemList(orderItemList);

        return order;
    }


    public Integer createUser(User user) {
        return userDao.createUser(user);
    }

    @Transactional
    public Integer createOrder(Integer userId, OrderRequest orderRequest) {

        Integer orderId = orderDao.createOrder(userId, orderRequest.getTotalAmount());
        orderDao.createOrderItems(orderId, orderRequest.getOrderItemList());

        return orderId;
    }


}
