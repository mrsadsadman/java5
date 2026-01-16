package edu.poly.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class registerModel {
    private String username;
    private String password;
    private String confirmPassword;

}