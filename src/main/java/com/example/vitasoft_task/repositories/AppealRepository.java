package com.example.vitasoft_task.repositories;

import com.example.vitasoft_task.entities.AppealEntity;
import com.example.vitasoft_task.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AppealRepository extends JpaRepository<AppealEntity,Long> {

    @EntityGraph(attributePaths = {"userEntity"})
    Page<AppealEntity> findAppealEntitiesByUserEntity(UserEntity userEntity, Pageable pageable);

    Page<AppealEntity> findAllByStatus(String status,Pageable pageable);


    @EntityGraph(attributePaths = {"userEntity"})
    Page<AppealEntity> findAppealEntitiesByUserEntityAndStatus(UserEntity userEntity,String status, Pageable pageable);

}
