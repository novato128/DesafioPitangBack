package com.rentcars.pitang.model.user.mapping;

import org.modelmapper.ModelMapper;

import com.rentcars.pitang.model.user.ResponseUserDTO;
import com.rentcars.pitang.model.user.User;

public class UserMapping {

	public static ResponseUserDTO userToResponseUserDTO(User user) {
		ModelMapper modelMapper = new ModelMapper();;
		
		ResponseUserDTO responseUserDTO = new ResponseUserDTO();
		responseUserDTO = modelMapper.map(user, ResponseUserDTO.class);
		responseUserDTO.setCars(user.getCars());
		
		return responseUserDTO;
	}
}
