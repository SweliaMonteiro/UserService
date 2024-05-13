package com.example.userservice.dtos;

import lombok.Getter;
import lombok.Setter;


// This class is used to send exception messages to the client

@Getter
@Setter
public class ExceptionDto {

    private String message;

}
