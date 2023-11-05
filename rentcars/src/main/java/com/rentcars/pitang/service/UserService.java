package com.rentcars.pitang.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rentcars.pitang.exception.EmailAlreadyExistsException;
import com.rentcars.pitang.exception.LoginAlreadyExistsException;
import com.rentcars.pitang.exception.UserNotFoundException;
import com.rentcars.pitang.model.car.Car;
import com.rentcars.pitang.model.user.ResponseUserDTO;
import com.rentcars.pitang.model.user.User;
import com.rentcars.pitang.model.user.UserDTO;
import com.rentcars.pitang.model.user.mapping.UserMapping;
import com.rentcars.pitang.repository.CarCountRepository;
import com.rentcars.pitang.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {

	private static final ZoneId zone = ZoneId.of("America/Sao_Paulo");
	@Autowired
	ModelMapper modelMapper;

	@Autowired
	UserRepository userRepository;

	@Autowired
	CarCountRepository carCountRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	public List<ResponseUserDTO> getAllUsersResponse() {
		List<User> listaUser = this.userRepository.findAll();
		List<ResponseUserDTO> listaResponseUser = listaUser.stream()
				.map(user -> UserMapping.userToResponseUserDTO(user)).collect(Collectors.toList());
		return listaResponseUser;
	}

	public List<User> getAllUsers() {
		return this.userRepository.findAll();
	}

	public ResponseUserDTO saveUser(UserDTO userDTO) {
		ResponseUserDTO userToResponseUserDTO = new ResponseUserDTO();
		try {
			User user = this.modelMapper.map(userDTO, User.class);
			if(userDTO.getCars() != null) {
				user.setCars(userDTO.getCars().stream().map(carDto -> this.modelMapper.map(carDto, Car.class))
						.collect(Collectors.toList()));
			}
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			user.setCreatedAt(LocalDateTime.now(zone));
			user = this.userRepository.save(user);
			userToResponseUserDTO = UserMapping.userToResponseUserDTO(user);
		} catch (DataIntegrityViolationException e) {
			User user = this.userRepository.findByEmail(userDTO.getEmail());
			UserDetails userDetails = this.userRepository.findByLogin(userDTO.getLogin());
			if (user != null) {
				throw new EmailAlreadyExistsException(e.getLocalizedMessage());
			} else if (userDetails != null) {
				throw new LoginAlreadyExistsException(e.getLocalizedMessage());
			}
		}
		return userToResponseUserDTO;
	}

	public ResponseUserDTO getUser(Long id) {
		User user = this.userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("Chave primaria nao encontrada na tabela USERS."));
		ResponseUserDTO userToResponseUserDTO = this.modelMapper.map(user, ResponseUserDTO.class);
		return userToResponseUserDTO;
	}

	@Transactional
	public void deleteUser(Long id) {
		User usuario = this.userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("Chave primaria nao encontrada na tabela USERS."));
		usuario.getCars().forEach(c -> {
			carCountRepository.deleteById(c.getId());
		});
		this.userRepository.deleteById(id);
	}

	public ResponseUserDTO updateUser(Long id, UserDTO userDTO) {
		ResponseUserDTO userToResponseUserDTO = new ResponseUserDTO();
		try {
			User user = this.modelMapper.map(userDTO, User.class);
			user.setId(id);
			user.setCars(userDTO.getCars().stream().map(carDto -> this.modelMapper.map(carDto, Car.class))
					.collect(Collectors.toList()));
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
			user.setPassword(encoder.encode(userDTO.getPassword()));
			user = this.userRepository.save(user);
			userToResponseUserDTO = UserMapping.userToResponseUserDTO(user);
		} catch (DataIntegrityViolationException e) {
			User user = this.userRepository.findByEmail(userDTO.getEmail());
			UserDetails userDetails = this.userRepository.findByLogin(userDTO.getLogin());
			if (user != null) {
				throw new EmailAlreadyExistsException(e.getLocalizedMessage());
			} else if (userDetails != null) {
				throw new LoginAlreadyExistsException(e.getLocalizedMessage());
			}
		}
		return userToResponseUserDTO;
	}

	public UserDetails findByLogin(String login) {
		UserDetails user = this.userRepository.findByLogin(login);
		return user;
	}

}
