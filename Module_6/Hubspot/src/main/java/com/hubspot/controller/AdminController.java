package com.hubspot.controller;

import com.hubspot.constants.UserConstant;
import com.hubspot.dto.ResponseDto;
import com.hubspot.dto.SignUpUserDto;
import com.hubspot.entity.SignUser;
import com.hubspot.service.IUserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@Validated
@RequestMapping("Admin")
public class AdminController {

    private final IUserService iUserService;


    @GetMapping("sayHello")
    public String sayHello() {
        return "Hello .....";
    }

    @PostMapping("create")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUB_ADMIN')")
    public ResponseEntity<ResponseDto> createUser(@Valid @RequestBody SignUpUserDto userDto) {
        iUserService.createUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(UserConstant.STATUS_201, UserConstant.MESSAGE_201));
    }


    @GetMapping("fetch")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUB_ADMIN')")
    public ResponseEntity<Optional<SignUser>> fetchUserDetails(@RequestParam
                                                     @Pattern(regexp = "(^$|[0-9]{10})",
                                                             message = "Mobile Number must be 10 digits")

                                                     String phoneNumber) {
        Optional<SignUser> user = iUserService.fetchUser(phoneNumber);
        return ResponseEntity.status(HttpStatus.OK).body(user);

    }

    @GetMapping("fetchAll")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUB_ADMIN')")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SignUser> fetchAllUsers() {

        try {

            List<SignUser> list = iUserService.fetchAllUsers();

            if (list.isEmpty()) {
                return new ResponseEntity("User details are not present..", HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity(list, HttpStatus.FOUND);
            }


        } catch (Exception e) {

            e.printStackTrace();
            return new ResponseEntity("This is Bad Request..", HttpStatus.BAD_REQUEST);

        }

    }

    @PutMapping("update")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUB_ADMIN')")
    public ResponseEntity<ResponseDto> updateUserDetail(@Valid @RequestBody SignUpUserDto userDto) {
        boolean isUpdated = iUserService.updateUser(String.valueOf(userDto));
        if (isUpdated) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(UserConstant.STATUS_200, UserConstant.MESSAGE_200));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(UserConstant.STATUS_500, UserConstant.MESSAGE_500));
        }
    }

    @DeleteMapping("delete")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUB_ADMIN')")
    public ResponseEntity<ResponseDto> deleteUserDetail(@RequestParam
                                                        @Pattern(regexp = "(^$|[0-9]{10})",
                                                                message = "Mobile Number must be 10 digits")
                                                        String phoneNumber) {
        boolean isDeleted = iUserService.deleteUser(phoneNumber);
        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(UserConstant.STATUS_200, UserConstant.STATUS_200));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(UserConstant.STATUS_500, UserConstant.MESSAGE_500));
        }
    }
}
