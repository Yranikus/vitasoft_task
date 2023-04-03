package com.example.vitasoft_task.controllers;


import com.example.vitasoft_task.services.OperatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/op/")
@RequiredArgsConstructor
public class OperatorController {

    private final OperatorService operatorService;


    @GetMapping("get")
    public ResponseEntity getAppeals(@RequestParam(required = false) String name,
                                     @RequestParam(required = false,defaultValue = "false") boolean nameLike,
                                     @RequestParam(required = false, defaultValue = "0") int page,
                                     @RequestParam(required = false,defaultValue = "ASC") String order) {
        return operatorService.getAppeals(page,order,name,nameLike);
    }

    @PostMapping("changeStatus/{id}")
    public ResponseEntity changeStatus(@PathVariable Long id, @RequestBody boolean accept) {
        return operatorService.acceptOrDenyAppeal(id,accept);
    }



}
