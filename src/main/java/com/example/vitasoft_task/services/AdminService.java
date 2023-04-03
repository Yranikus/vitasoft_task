package com.example.vitasoft_task.services;

import com.example.vitasoft_task.entities.RoleEntity;
import com.example.vitasoft_task.entities.UserEntity;
import com.example.vitasoft_task.exceptions.NotFoundException;
import com.example.vitasoft_task.repositories.RoleRepository;
import com.example.vitasoft_task.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Value("#{propertiesfilemapping['user_notfound_exception']}")
    private String userNotfound;
    @Value("#{propertiesfilemapping['role.operator']}")
    private String operator;
    @Value("#{propertiesfilemapping['role_already']}")
    private String roleAlready;
    @Value("#{propertiesfilemapping['role_updated']}")
    private String roleUpdated;
    @Value("#{propertiesfilemapping['role_notfound_exception']}")
    private String roleNotFound;

    @Transactional(readOnly = true)
    public ResponseEntity<List<UserEntity>> getUsers(int page, String order){
        Sort sort = Sort.by("name").ascending();
        if (order.equals("DEC")) sort = Sort.by("name").descending();
        Pageable pageable = PageRequest.of(page, 5, sort);
        return new ResponseEntity(userRepository.findAll(pageable).toList(), HttpStatus.ACCEPTED);
    }


    @Transactional(readOnly = true)
    public ResponseEntity<UserEntity> getUserByName(boolean nameLike, String name){
        List<UserEntity> userEntities;
        if (nameLike) userEntities = userRepository.findByNameLike("%" + name + "%");
        else userEntities = userRepository.findUserEntityByName(name);
        System.out.println(userEntities.get(0).getName());
        if (userEntities.size() != 1) throw new NotFoundException(userNotfound);
        return new ResponseEntity(userEntities.get(0),HttpStatus.ACCEPTED);
    }

    @Transactional
    @Retryable
    public ResponseEntity<String> setOpRole(Long id){
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new NotFoundException(userNotfound));
        RoleEntity roleEntity = roleRepository.findByRole(operator).orElseThrow(() -> new NotFoundException(roleNotFound));
        if (userEntity.getRoles().contains(roleEntity)) return new ResponseEntity(roleAlready, HttpStatus.ACCEPTED);
        userEntity.getRoles().add(roleEntity);
        userRepository.save(userEntity);
        return new ResponseEntity(roleUpdated, HttpStatus.ACCEPTED);
    }



}
