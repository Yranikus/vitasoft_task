package com.example.vitasoft_task.repositories;

import com.example.vitasoft_task.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {


    List<UserEntity> findAllByNameLike(String name);
    List<UserEntity> findAllByName(String name);

    @EntityGraph(attributePaths = {"roles"})
    Optional<UserEntity> findByName(String name);

    @EntityGraph(attributePaths = {"roles"})
    List<UserEntity> findUserEntityByName(String name);
    @EntityGraph(attributePaths = {"roles"})
    List<UserEntity> findByNameLike(String name);

    @Override
    @EntityGraph(attributePaths = {"roles"})
    Optional<UserEntity> findById(Long id);

    @Override
    @EntityGraph(attributePaths = {"roles"})
    Page<UserEntity> findAll(Pageable pageable);
}
