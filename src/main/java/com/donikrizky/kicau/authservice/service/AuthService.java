package com.donikrizky.kicau.authservice.service;

import com.donikrizky.kicau.authservice.dto.request.RegisterUserRequestDTO;

public interface AuthService {
	public boolean login(String email, String password);

	public boolean logout(Long userId);

	public void save(RegisterUserRequestDTO requestDTO);
}
