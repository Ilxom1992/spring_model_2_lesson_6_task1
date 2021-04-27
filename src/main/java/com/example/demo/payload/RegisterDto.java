package com.example.demo.payload;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
public class RegisterDto {

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @Email
    private String email;

    @NotNull
    private String password;

    private List<Integer> roleListId;
    private Integer companyId;

    private String nationality;

    private LocalDate DateOfBirth;

    private boolean gender;

    private String placeOfBirth;

    private String passport;

    private boolean type;
}
