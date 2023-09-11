package com.windsor.traineerushbe.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {

    @Schema(example = "1000")
    private Integer totalAmount;

    private UserRequest userRequest;
    private List<OrderItemRequest> orderItemRequestList;
}
