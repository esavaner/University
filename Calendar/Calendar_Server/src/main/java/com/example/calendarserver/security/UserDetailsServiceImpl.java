package com.example.calendarserver.security;

import static java.util.Collections.emptyList;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.calendarserver.user.User;
import com.example.calendarserver.user.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	private UserRepository userRepository;

	public UserDetailsServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findByLogin(username);
		
		if (user.isPresent()) {
			return new org.springframework.security.core.userdetails.User(user.get().getLogin(), user.get().getPassword(), emptyList());
		} else {
			throw new UsernameNotFoundException(username);
		}
	}
}
