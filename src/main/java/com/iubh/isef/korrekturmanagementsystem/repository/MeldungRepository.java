package com.iubh.isef.korrekturmanagementsystem.repository;

import com.iubh.isef.korrekturmanagementsystem.entity.Kurs;
import com.iubh.isef.korrekturmanagementsystem.entity.Meldung;
import org.springframework.data.repository.CrudRepository;

public interface MeldungRepository extends CrudRepository<Meldung, Long> {
}
