package com.windsor.traineerushbe.dto;

import com.windsor.traineerushbe.model.OrderItem;
import com.windsor.traineerushbe.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderRequest {

    @Schema(example = "1000")
    private Integer totalAmount;

    private User user;
    private List<OrderItem> orderItemList;
}
