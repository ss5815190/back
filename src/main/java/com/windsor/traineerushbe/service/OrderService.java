package com.windsor.traineerushbe.service;

import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import com.windsor.traineerushbe.dao.OrderDao;
import com.windsor.traineerushbe.dao.UserDao;
import com.windsor.traineerushbe.dto.OrderQueryParams;
import com.windsor.traineerushbe.model.Order;
import com.windsor.traineerushbe.model.OrderItem;
import com.windsor.traineerushbe.util.ExcelUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
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

    public Integer countOrder(OrderQueryParams orderQueryParams) {
        return orderDao.countOrder(orderQueryParams);
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

        List<OrderItem> orderItemList = orderDao.getOrderItemsByOrderId(orderId);

        order.setOrderItemList(orderItemList);

        return order;
    }


    @Transactional
    public Integer createOrder(Order order) {

        Integer userId = userDao.createUser(order.getUser());
        Integer orderId = orderDao.createOrder(userId, order.getTotalAmount());
        orderDao.createOrderItems(orderId, order.getOrderItemList());

        return orderId;
    }
    @Transactional
    public String orderPrint(Integer orderId) {

        var orderList = orderDao.getOrderItemsByOrderId(orderId);

        String fileDatetime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String fileName = "order_list"+ fileDatetime + "xlsx";
        String filePath = tempFiles + fileName;

        List<ExcelExportEntity> excelParams;

        excelParams = List.of(
                new ExcelExportEntity("Order_Item", "orderItemId", 20),
                new ExcelExportEntity("order_id", "orderId", 20),
                new ExcelExportEntity("product", "productName", 20),
                new ExcelExportEntity("Order_item_key","orderItemKey",25),
                new ExcelExportEntity("quantity","quantity",20),
                new ExcelExportEntity("Ice","ice",20),
                new ExcelExportEntity("Sweetness","sweetness",20),
                new ExcelExportEntity("price","price",20),
                new ExcelExportEntity("Item_total_price","itemTotalPrice",20)
        );

        ExcelUtil.exportExcel(orderList,null , fileName, excelParams, filePath, true);
        return filePath;
    }
}
