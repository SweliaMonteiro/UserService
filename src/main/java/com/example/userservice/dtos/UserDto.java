package com.example.userservice.dtos;

import com.example.userservice.models.User;
import lombok.Getter;
import lombok.Setter;
import com.example.userservice.models.Role;
import java.util.List;


@Getter
@Setter
public class UserDto {

    private String name;

    private String email;

    private List<Role> roles;

    private boolean isEmailVerified;


    // This method is used to convert a User object to a UserDto object
    public static UserDto from(User user) {
        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setEmailVerified(user.isEmailVerified());
        return userDto;
    }

}
