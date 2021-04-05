package com.iubh.isef.korrekturmanagementsystem.repository;

import com.iubh.isef.korrekturmanagementsystem.entity.Rolle;
import com.iubh.isef.korrekturmanagementsystem.entity.User;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Configuration
public interface UserRepository extends CrudRepository<User, String> {

    User findByEmail(String email);

    List<User> findAllByRolleIsIn(List<Rolle> roles);
}
