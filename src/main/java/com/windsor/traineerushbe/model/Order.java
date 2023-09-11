package com.windsor.traineerushbe.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Order {
    // 對應到order table
    @Schema(example = "1")
    private Integer orderId;

    @Schema(example = "1")
    private Integer userId;

    @Schema(example = "1000")
    private Integer totalAmount;

    @Schema(example = "2023-08-09 18:28:20")
    private Date createdDate;

    @Schema(example = "2023-08-09 18:28:20")
    private Date lastModifiedDate;

    // 回傳給前端的orderItemList
    private User user;
    private List<OrderItem> orderItemList;
}
