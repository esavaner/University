package com.example.calendarserver.user;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;

import com.example.calendarserver.user.exceptions.*;

@Service
public class UserService {
	private final UserRepository repository;
	private final BCryptPasswordEncoder encoder;

	@Autowired
	public UserService(UserRepository repository, BCryptPasswordEncoder encoder) {
		this.repository = repository;
		this.encoder = encoder;
	}

	public void register(UserDto user) {
		if (user.getPassword() == null || user.getLogin() == null || user.getLogin().length() == 0
				|| user.getEmail() == null || user.getEmail().length() == 0)
			throw new MissingCredentialsException();
		if (user.getPassword().length() < 8)
			throw new PasswordTooShortException();
		User userEntity = new User(user.getLogin(), encoder.encode(user.getPassword()), user.getEmail());
		if (repository.findByLogin(user.getLogin()).isPresent())
			throw new UserExistsException();
		try {
			this.repository.save(userEntity);
		} catch (TransactionSystemException e) {
			throw new InvalidEmailException();
		}
	}
	
	public Optional<User> getByLogin(String username) {
		return repository.findByLogin(username);
	}
}
