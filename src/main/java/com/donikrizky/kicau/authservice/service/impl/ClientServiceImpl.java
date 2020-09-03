package com.donikrizky.kicau.authservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.donikrizky.kicau.authservice.repository.UserRepository;
import com.donikrizky.kicau.authservice.service.ClientService;

@Service
public class ClientServiceImpl implements ClientService {

	private UserRepository userRepository;

	@Autowired
	ClientServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public String findUsernameByUserId(Integer userId) {
		return userRepository.findUsernameByUserId(userId);
	}


}
