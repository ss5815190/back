package com.windsor.traineerushbe.service;

import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import com.windsor.traineerushbe.dao.OrderDao;
import com.windsor.traineerushbe.dao.UserDao;
import com.windsor.traineerushbe.dto.OrderQueryParams;
import com.windsor.traineerushbe.dto.OrderRequest;
import com.windsor.traineerushbe.dto.UserRequest;
import com.windsor.traineerushbe.model.Order;
import com.windsor.traineerushbe.model.OrderItem;
import com.windsor.traineerushbe.model.User;
import com.windsor.traineerushbe.util.ExcelUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {


    private final OrderDao orderDao;
    private final UserDao userDao;

    @Value("./src/main/resources/template/tempFiles/")
    private String tempFiles;

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
            User user = userDao.getUserById(order.getUserId());
            List<OrderItem> orderItemList = orderDao.getOrderItemsByOrderId(order.getOrderId());

            order.setUser(user);
            order.setOrderItemList(orderItemList);
        }

        return orderList;
    }


    public Order getOrderById(Integer orderId) {
        Order order = orderDao.getOrderById(orderId);

        User user = userDao.getUserById(order.getUserId());

        List<OrderItem> orderItemList = orderDao.getOrderItemsByOrderId(orderId);

        order.setUser(user);
        order.setOrderItemList(orderItemList);

        return order;
    }


    public Integer createUser(UserRequest userRequest) {
        return userDao.createUser(userRequest);
    }

    @Transactional
    public Integer createOrder(Integer userId, OrderRequest orderRequest) {

        Integer orderId = orderDao.createOrder(userId, orderRequest.getTotalAmount());
        orderDao.createOrderItems(orderId, orderRequest.getOrderItemRequestList());

        return orderId;
    }

    @Transactional
    public String orderPrint(Integer orderId) {

        List<OrderItem> orderList = orderDao.getOrderItemsByOrderId(orderId);

        String fileDatetime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String fileName = "order_list"+ fileDatetime + ".xlsx";
        String filePath = tempFiles + fileName;

        List<ExcelExportEntity> excelParams;

        excelParams = List.of(
                new ExcelExportEntity("Order_Item", "orderItemId", 20),
                new ExcelExportEntity("Order_item_key","orderItemKey",25),
                new ExcelExportEntity("order_id", "orderId", 20),
                new ExcelExportEntity("product", "productName", 20),
                new ExcelExportEntity("Ice","ice",20),
                new ExcelExportEntity("Sweetness","sweetness",20),
                new ExcelExportEntity("Quantity","quantity",20),
                new ExcelExportEntity("price","price",20),
                new ExcelExportEntity("Item_total_price","itemTotalPrice",20)
        );

        ExcelUtil.exportExcel(orderList,null , fileName, excelParams, filePath, true);
        return filePath;
    }
}
