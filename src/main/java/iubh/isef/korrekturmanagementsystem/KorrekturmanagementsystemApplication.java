package iubh.isef.korrekturmanagementsystem;

import iubh.isef.korrekturmanagementsystem.entity.User;
import iubh.isef.korrekturmanagementsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class KorrekturmanagementsystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(KorrekturmanagementsystemApplication.class, args);
    }
}
