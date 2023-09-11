package com.windsor.traineerushbe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class OrderItemRequest {

    @Schema(example = "fb3.less-ice.less-sugar")
    @JsonProperty("id")
    private String orderItemKey;

    @Schema(example = "紅茶拿鐵")
    @JsonProperty("name")
    private String productName;

    @Schema(example = "less-ice")
    private String ice;

    @Schema(example = "no-sugar")
    private String sweetness;

    @Schema(example = "2")
    private Integer quantity;

    @Schema(example = "70")
    private Integer price;

    @Schema(example = "140")
    private Integer itemTotalPrice;
}
