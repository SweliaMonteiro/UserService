package com.example.userservice.dtos;

import com.example.userservice.models.Token;
import com.example.userservice.models.User;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;


@Getter
@Setter
public class TokenDto {

    private String value;

    private Date expiryAt;

    private User user;

    private boolean isDeleted;


    // This method is used to convert a Token object to a TokenDto object
    public static TokenDto from(Token token) {
        TokenDto tokenDto = new TokenDto();
        tokenDto.setValue(token.getValue());
        tokenDto.setExpiryAt(token.getExpiryAt());
        tokenDto.setUser(token.getUser());
        tokenDto.setDeleted(token.isDeleted());
        return tokenDto;
    }

}
