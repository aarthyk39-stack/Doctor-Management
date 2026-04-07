package com.tekpyramid.DoctorFlow.Dto;

import lombok.Data;

@Data
public class DoctorSignUpDTO {
    private String userName;
    private String email;
    private String password;
    private long mobile;
    private String Specialist;
    private double rating;


}
