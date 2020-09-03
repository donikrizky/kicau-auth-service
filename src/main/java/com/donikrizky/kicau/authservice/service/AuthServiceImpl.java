package com.donikrizky.kicau.authservice.service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.donikrizky.kicau.authservice.dto.request.RegisterUserRequestDTO;
import com.donikrizky.kicau.authservice.entity.User;
import com.donikrizky.kicau.authservice.exception.BadRequestException;
import com.donikrizky.kicau.authservice.exception.UnauthorizedException;
import com.donikrizky.kicau.authservice.repository.UserRepository;

import io.micrometer.core.instrument.util.StringUtils;

@Service
public class AuthServiceImpl implements AuthService {

	public static final Logger LOGGER = LoggerFactory.getLogger(AuthServiceImpl.class);

	PasswordEncoder passwordEncoder;
	UserRepository userRepository;

	@Autowired
	public AuthServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository) {
		this.passwordEncoder = passwordEncoder;
		this.userRepository = userRepository;
		this.addDummyUser();
	}

	private void addDummyUser() {
		List<User> users = new ArrayList<User>();
		users.add(
				User.builder().userId(1).username("username1").passwordHashed(passwordEncoder.encode("12345")).build());
		users.add(
				User.builder().userId(2).username("username2").passwordHashed(passwordEncoder.encode("12345")).build());
		users.add(
				User.builder().userId(3).username("username3").passwordHashed(passwordEncoder.encode("12345")).build());
		userRepository.saveAll(users);
	}

	@Override
	public boolean login(String username, String password) {

		if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
			throw new BadRequestException("Error: username or password cannot be empty");
		}

		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UnauthorizedException("Error: No username found"));

		if (!passwordEncoder.matches(password, user.getPasswordHashed())) {
			throw new UnauthorizedException("Error: username and password do not match");
		}

		return true;
	}

	@Override
	public boolean logout(Long userId) {
		User data = userRepository.findById(userId).orElseThrow(() -> new BadRequestException("Error: User Not Found"));

		data.setLogout(new Date());
		userRepository.save(data);

		return true;
	}

	@Override
	public void save(RegisterUserRequestDTO requestDTO) {
		// check username exist
		if (Boolean.TRUE.equals(userRepository.existsByUsername(requestDTO.getUsername()))) {
			throw new BadRequestException("Error: Username already exist!");
		}

		// check password empty
		if (requestDTO.getPassword() == null || requestDTO.getPassword().equals("")) {
			throw new BadRequestException("Error: Password cannot be empty!");
		}

		// check password pattern
		String regexPass = "^(?=.*[0-9])(?=.*[!@#$%^&*]).{6,20}$";
		Pattern patternPassword = Pattern.compile(regexPass);
		if (!patternPassword.matcher(requestDTO.getPassword()).matches()) {
			throw new BadRequestException("Error: Password pattern not valid!");
		}

		// check password match
		if (!requestDTO.getPassword().equals(requestDTO.getConfirmPassword())) {
			throw new BadRequestException("Error: Password and Confirm Password not match!");
		}

		// check dob valid
		LocalDate dob;
		DateTimeFormatter df = DateTimeFormatter.ofPattern("dd-MM-uuuu").withResolverStyle(ResolverStyle.STRICT);
		try {
			dob = LocalDate.parse(requestDTO.getDob(), df);
		} catch (Exception e) {
			throw new BadRequestException("Error: Date not valid!");
		}

		// check age over 13
		if (Period.between(dob, LocalDate.now()).getYears() < 13) {
			throw new BadRequestException("Error: Age should not under 13!");
		}

		// register
		User user = new User();
		user.setUsername(requestDTO.getUsername());
		user.setPasswordHashed(passwordEncoder.encode(requestDTO.getPassword()));
		user = userRepository.save(user);

//		Profile profile = new Profile();
//		profile.setUser(user);
//		profile.setFullName(register.getFullname());
//		profile.setDob(dob);
//		profile.setGender(register.getGender());
//		profile.setDataState(DataState.ACTIVE);
//		profileRepository.save(profile);

	}

}
