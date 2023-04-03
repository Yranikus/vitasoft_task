package com.example.vitasoft_task.controllers;

import com.example.vitasoft_task.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("get_users")
    public ResponseEntity getUsers(@RequestParam(required = false, defaultValue = "0") int page,
                                   @RequestParam(required = false,defaultValue = "ASC") String order){
        return adminService.getUsers(page,order);
    }

    @GetMapping("get_user")
    public ResponseEntity getUserByName(@RequestParam String name,
                                        @RequestParam(required = false,defaultValue = "false") boolean nameLike){
        return adminService.getUserByName(nameLike,name);
    }

    @PostMapping("set_op_role")
    public ResponseEntity setOpRole(@RequestBody Long id){
        return adminService.setOpRole(id);
    }


}
