package com.windsor.traineerushbe.util;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class Page<T> {

    @Schema(description = "The amount of data that can be displayed", example = "10")
    private Integer limit;

    @Schema(description = "How many records were skipped", example = "10")
    private Integer offset;

    @Schema(description = "Total number of data", example = "1000")
    private Integer total;

    private List<T> results;
}
