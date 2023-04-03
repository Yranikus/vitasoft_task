package com.example.vitasoft_task.services;


import com.example.vitasoft_task.entities.AppealEntity;
import com.example.vitasoft_task.entities.UserEntity;
import com.example.vitasoft_task.exceptions.ImmutableStatusException;
import com.example.vitasoft_task.exceptions.NotFoundException;
import com.example.vitasoft_task.repositories.AppealRepository;
import com.example.vitasoft_task.repositories.UserRepository;
import com.example.vitasoft_task.services.builders.AppealBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OperatorService {

    private final AppealRepository appealRepository;
    private final UserRepository userRepository;
    private final AppealBuilder appealBuilder;

    @Autowired
    MessageSource messageSource;

    @Value("#{propertiesfilemapping['user_notfound_exception']}")
    private String userNotfound;
    @Value("#{propertiesfilemapping['status.sended']}")
    private String sended;
    @Value("#{propertiesfilemapping['status.accepted']}")
    private String accepted;
    @Value("#{propertiesfilemapping['status.denied']}")
    private String denied;
    @Value("#{propertiesfilemapping['immutable_exception']}")
    private String immutable;
    @Value("#{propertiesfilemapping['appeal_updated']}")
    private String updated;



    private List<AppealEntity> getAppealsByname(Pageable pageable, String name, boolean nameLike){
        List<UserEntity> userEntities;
        if (nameLike) userEntities = userRepository.findAllByNameLike(name);
        else userEntities = userRepository.findAllByName(name);
        if (userEntities.size() != 1) throw new NotFoundException(userNotfound);
        return appealRepository.findAppealEntitiesByUserEntityAndStatus(userEntities.get(0),sended,pageable).
                get().map(appealBuilder::OpAppealBuild).collect(Collectors.toList());
    }

    private List<AppealEntity> getAllAppeals(Pageable pageable){
        return appealRepository.findAllByStatus(sended,pageable).get().map(appealBuilder::OpAppealBuild).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ResponseEntity<List<AppealEntity>> getAppeals(int page, String order, String name, boolean nameLike){
        List<AppealEntity> appealEntities;
        Sort sort = Sort.by("date").ascending();
        if (order.equals("DEC")) sort = Sort.by("date").descending();
        Pageable pageable = PageRequest.of(page, 5, sort);
        if (name == null) appealEntities = getAllAppeals(pageable);
        else appealEntities = getAppealsByname(pageable,name,nameLike);
        return new ResponseEntity(appealEntities,HttpStatus.ACCEPTED);
    }

    @Transactional
    @Retryable(maxAttempts = 5)
    public ResponseEntity<String> acceptOrDenyAppeal(Long id, boolean accept){
        AppealEntity appealEntity = appealRepository.getReferenceById(id);
        if(!appealEntity.getStatus().equals(sended)) throw new ImmutableStatusException(immutable);
        if (accept) appealEntity.setStatus(accepted);
        else appealEntity.setStatus(denied);
        return new ResponseEntity(updated,HttpStatus.ACCEPTED);
    }


}
