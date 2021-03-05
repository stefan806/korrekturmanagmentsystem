package com.iubh.isef.korrekturmanagementsystem.view;

import com.iubh.isef.korrekturmanagementsystem.entity.User;
import com.iubh.isef.korrekturmanagementsystem.repository.UserRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.ManagedBean;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
@Scope("view")
@ManagedBean
@NoArgsConstructor
public class UserBean {

	@Autowired
	private UserRepository userRepository;

	public List<User> getAllUser() {
		List<User> users = StreamSupport.stream(userRepository.findAll().spliterator(), false).collect(Collectors.toList());
		return users;
	}
}
