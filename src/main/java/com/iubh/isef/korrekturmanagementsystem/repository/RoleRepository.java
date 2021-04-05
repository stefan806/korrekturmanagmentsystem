package com.iubh.isef.korrekturmanagementsystem.repository;

import com.iubh.isef.korrekturmanagementsystem.entity.Rolle;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@Configuration
public interface RoleRepository extends CrudRepository<Rolle, Integer> {

    Rolle findByBezeichnung(String bezeichnung);
}
