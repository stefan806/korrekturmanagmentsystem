package com.iubh.isef.korrekturmanagementsystem.repository;

import com.iubh.isef.korrekturmanagementsystem.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {

    Role findByBezeichnung(String bezeichnung);
}
