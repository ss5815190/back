package com.windsor.traineerushbe.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Windsor Liu",
                        email = "windsor.liu@shoalter.com",
                        url = "https://github.com/windsorliu/traineeRush"
                ),
                title = "Training – E-commerce website",
                version = "1.0")
)
public class OpenApiConfig {
}
