package com.iubh.isef.korrekturmanagementsystem.repository;

import com.iubh.isef.korrekturmanagementsystem.entity.Rolle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Rolle, Integer> {

    Rolle findByBezeichnung(String bezeichnung);
}
