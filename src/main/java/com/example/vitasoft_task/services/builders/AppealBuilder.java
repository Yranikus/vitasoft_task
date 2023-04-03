package com.example.vitasoft_task.services.builders;

import com.example.vitasoft_task.entities.AppealEntity;
import com.example.vitasoft_task.entities.UserEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;

@Component
public class AppealBuilder {

    @Value("#{propertiesfilemapping['status.draft']}")
    private String draft;

    public AppealEntity UserAppealBuild(String appeal, UserEntity user){
        AppealEntity appealEntity = new AppealEntity();
        appealEntity.setText(appeal);
        appealEntity.setStatus(draft);
        appealEntity.setDate(Date.valueOf(LocalDate.now()));
        appealEntity.setUserEntity(user);
        return appealEntity;
    }

    public AppealEntity OpAppealBuild(AppealEntity appealEntity){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < appealEntity.getText().length(); i++){
           stringBuilder.append(appealEntity.getText().charAt(i));
           if (i != appealEntity.getText().length()-1){
               stringBuilder.append("-");
           }
        }
        appealEntity.setText(stringBuilder.toString());
        return appealEntity;
    }


}
