package com.iubh.isef.korrekturmanagementsystem.repository;

import com.iubh.isef.korrekturmanagementsystem.entity.Kurs;
import com.iubh.isef.korrekturmanagementsystem.entity.Meldung;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.CrudRepository;

@Configuration
public interface MeldungRepository extends CrudRepository<Meldung, Long> {
}
