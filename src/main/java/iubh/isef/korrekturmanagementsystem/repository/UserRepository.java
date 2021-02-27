package iubh.isef.korrekturmanagementsystem.repository;

import iubh.isef.korrekturmanagementsystem.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
}
