package com.windsor.traineerushbe.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
public class User {
    @Schema(example = "1")
    private Integer userId;

    @Schema(example = "Windsor")
    private String name;

    @Schema(example = "0912345678")
    private String phone;

    @Schema(example = "Tainan")
    private String address;

    @Schema(example = "2023-08-09 18:28:20")
    private Date createdDate;

    @Schema(example = "2023-08-09 18:28:20")
    private Date lastModifiedDate;
}
