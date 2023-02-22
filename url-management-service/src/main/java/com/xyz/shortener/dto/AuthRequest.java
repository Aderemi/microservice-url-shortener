package com.xyz.shortener.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {
    @Size(min = 3, max = 20)
    private String userName;

    @Size(min = 6, max = 40)
    private String password;
}
