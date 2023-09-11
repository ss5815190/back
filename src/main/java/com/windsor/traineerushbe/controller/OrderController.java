package com.windsor.traineerushbe.controller;

import com.windsor.traineerushbe.dto.OrderQueryParams;
import com.windsor.traineerushbe.model.Order;
import com.windsor.traineerushbe.service.OrderService;
import com.windsor.traineerushbe.util.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/orders")
@Tag(name = "Order")
public class OrderController {


    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Operation(
            summary = "View historical order records",
            responses = {
                    @ApiResponse(
                            description = "Ok",
                            responseCode = "200"
                    )
            }
    )
    @GetMapping
    public ResponseEntity<Page<Order>> getOrders(
            @RequestParam(defaultValue = "10") @Max(1000) @Min(0) Integer limit,
            @RequestParam(defaultValue = "0") @Min(0) Integer offset) {

        OrderQueryParams orderQueryParams = new OrderQueryParams();
        orderQueryParams.setLimit(limit);
        orderQueryParams.setOffset(offset);

        // 取得 order list
        List<Order> orderList = orderService.getOrders(orderQueryParams);

        // 取得 order 總數
        Integer count = orderService.countOrder(orderQueryParams);

        // 分頁
        Page<Order> page = new Page<>();
        page.setLimit(limit);
        page.setOffset(offset);
        page.setTotal(count);
        page.setResults(orderList);

        return ResponseEntity.status(HttpStatus.OK).body(page);
    }

    @Operation(
            summary = "Create an order",
            responses = {
                    @ApiResponse(
                            description = "Created",
                            responseCode = "201"
                    )
            }
    )
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {

        Integer orderId = orderService.createOrder(order);

        Order response = orderService.getOrderById(orderId);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
