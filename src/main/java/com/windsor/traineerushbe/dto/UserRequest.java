package com.windsor.traineerushbe.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserRequest {

    @Schema(example = "Windsor")
    private String name;

    @Schema(example = "0912345678")
    private String phone;

    @Schema(example = "Tainan")
    private String address;
}
