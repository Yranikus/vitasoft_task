package com.example.vitasoft_task.services;

import com.example.vitasoft_task.entities.AppealEntity;
import com.example.vitasoft_task.entities.UserEntity;
import com.example.vitasoft_task.exceptions.ImmutableStatusException;
import com.example.vitasoft_task.repositories.AppealRepository;
import com.example.vitasoft_task.services.builders.AppealBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Retryable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private AppealBuilder appealBuilder;
    @Autowired
    private AppealRepository appealRepository;


    @Value("#{propertiesfilemapping['appeal_saved']}")
    private String saved;
    @Value("#{propertiesfilemapping['status.sended']}")
    private String sended;
    @Value("#{propertiesfilemapping['status.draft']}")
    private String draft;
    @Value("#{propertiesfilemapping['immutable_exception']}")
    private String immutable;
    @Value("#{propertiesfilemapping['appeal_updated']}")
    private String updated;
    @Value("#{propertiesfilemapping['access_denied']}")
    private String accessDenied;


    public ResponseEntity<String> createAppeal(String appeal, UserEntity user){
        appealRepository.save(appealBuilder.UserAppealBuild(appeal,user));
        return new ResponseEntity(saved, HttpStatus.ACCEPTED);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<List<AppealEntity>> getAll(int page, String order, UserEntity user){
        Sort sort = Sort.by("date").ascending();
        List<AppealEntity> appealEntities;
        if (order.equals("DEC")) sort = Sort.by("date").descending();
        Pageable pageable = PageRequest.of(page,5,sort);
        appealEntities = appealRepository.findAppealEntitiesByUserEntity(user,pageable).toList();
        return new ResponseEntity(appealEntities,HttpStatus.ACCEPTED);
    }

    @Transactional
    @Retryable(maxAttempts = 5)
    public ResponseEntity<String> changeAppeal(String appeal, Long id, UserEntity userEntity){
        AppealEntity appealEntity = appealRepository.getReferenceById(id);
        if (!appealEntity.getUserEntity().getId().equals(userEntity.getId())) throw new AccessDeniedException(accessDenied);
        if (!appealEntity.getStatus().equals(draft)) throw new ImmutableStatusException(immutable);
        if (appeal != null) appealEntity.setText(appeal);
        else appealEntity.setStatus(sended);
        appealRepository.save(appealEntity);
        return new ResponseEntity(updated, HttpStatus.ACCEPTED);
    }



}
