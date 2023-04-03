package com.example.vitasoft_task.configurations.security;

import com.example.vitasoft_task.entities.UserEntity;
import com.example.vitasoft_task.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<UserEntity> userEntityList = userRepository.findUserEntityByName(username);
        if (userEntityList.size() != 1) throw new UsernameNotFoundException("Нет пользователя с именем " + username);
        return userEntityList.get(0);
    }
}
