package com.example.vitasoft_task.controllers;


import com.example.vitasoft_task.entities.UserEntity;
import com.example.vitasoft_task.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @PostMapping("save")
    public ResponseEntity saveAppeal(@RequestBody String appeal,
                                     @AuthenticationPrincipal UserEntity user){
        return userService.createAppeal(appeal,user);
    }

    @GetMapping("get_appeals")
    public ResponseEntity getAppeals(@RequestParam(required = false, defaultValue = "0") int page,
                                     @RequestParam(required = false, defaultValue = "ASC") String sort,
                                     @AuthenticationPrincipal UserEntity user){
        return userService.getAll(page,sort,user);
    }

    @PostMapping("change_appeal/{id}")
    public ResponseEntity changeAppeal(@RequestBody String appeal, @PathVariable Long id,
                                       @AuthenticationPrincipal UserEntity user){
        return userService.changeAppeal(appeal,id,user);
    }

    @PostMapping("send/{id}")
    public ResponseEntity sendAppeal(@PathVariable Long id,
                                     @AuthenticationPrincipal UserEntity user){
        return userService.changeAppeal(null,id,user);
    }


}
