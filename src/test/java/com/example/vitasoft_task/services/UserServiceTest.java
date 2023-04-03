package com.example.vitasoft_task.services;

import com.example.vitasoft_task.entities.AppealEntity;
import com.example.vitasoft_task.entities.UserEntity;
import com.example.vitasoft_task.repositories.AppealRepository;
import com.example.vitasoft_task.services.builders.AppealBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    AppealBuilder appealBuilder;
    @Mock
    AppealRepository appealRepository;
    @InjectMocks
    UserService userService;
    private static UserEntity user;
    private static List<AppealEntity> appealsList = new ArrayList<>();


    @BeforeAll
    static void createUserForTests(){
        user = new UserEntity();
        user.setId(1L);
        user.setName("Вовчик");
        AppealEntity appeal1 = new AppealEntity();
        appeal1.setText("Помогите , у меня упали тесты");
        appeal1.setDate(Date.valueOf(LocalDate.now()));
        appeal1.setStatus("отправлено");
        appealsList.add(appeal1);
    }

    @Test
    void createAppeal() {
        String appeal = "Помогите , у меня упали тесты";
        Mockito.doReturn(appealsList.get(0)).when(this.appealBuilder).UserAppealBuild(appeal,user);
        Mockito.doReturn(appealsList.get(0)).when(this.appealRepository).save(appealsList.get(0));

        ResponseEntity<String> response = this.userService.createAppeal(appeal,user);

        assertNotNull(response);
        assertEquals(HttpStatus.ACCEPTED,response.getStatusCode());

    }

}