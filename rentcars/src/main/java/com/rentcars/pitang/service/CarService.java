package com.rentcars.pitang.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.rentcars.pitang.model.car.Car;
import com.rentcars.pitang.model.car.CarByUsage;
import com.rentcars.pitang.model.car.CarCount;
import com.rentcars.pitang.model.car.CarDTO;
import com.rentcars.pitang.model.user.CarByUser;
import com.rentcars.pitang.model.user.User;
import com.rentcars.pitang.repository.CarCountRepository;
import com.rentcars.pitang.repository.CarRepository;
import com.rentcars.pitang.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class CarService {

	@Autowired
	ModelMapper modelMapper;
	@Autowired
	UserRepository userRepository;
	@Autowired
	CarRepository carRepository;
	@Autowired
	CarCountRepository carCountRepository;

	public List<Car> getAllcars(String login) {
		User user = (User) userRepository.findByLogin(login);
		return user.getCars();
	}

	public void saveCar(CarDTO carDTO, String login) throws DataIntegrityViolationException {
		User user = (User) userRepository.findByLogin(login);
		Car car = modelMapper.map(carDTO, Car.class);
		user.getCars().add(car);
		userRepository.save(user);
	}

	public Car getCar(Long id, String login) {
		User user = (User) userRepository.findByLogin(login);
		Car car = user.getCars().stream().filter(c -> id.equals(c.getId())).findAny().orElseThrow(null);
		CarCount carCount = this.carCountRepository.findByCar_Id(car.getId()).orElse(new CarCount());
		carCount.setCount(carCount.getCount() + 1);
		carCount.setUserLogin(user.getLogin());
		this.carCountRepository.save(carCount);
		return car;
	}

	@Transactional
	public void deleteCar(Long id, String login) {
		User user = (User) userRepository.findByLogin(login);
		user.getCars().removeIf(c -> c.getId() == id);
		userRepository.save(user);
		CarCount carCount = this.carCountRepository.findByCar_Id(id).orElse(null);
		if(carCount != null) {
			this.carCountRepository.deleteById(carCount.getId());
		}
		carRepository.deleteById(id);
	}

	public void updateCar(Long id, CarDTO carDTO, String login) {
		User user = (User) userRepository.findByLogin(login);
		Car car = user.getCars().stream().filter(c -> id.equals(c.getId())).findAny().orElseThrow(null);
		car = modelMapper.map(carDTO, Car.class);
		car.setId(id);
		carRepository.save(car);
	}

	public List<CarByUser> getCarCount() {
		List<CarCount> listCarCount = this.carCountRepository.findAll();
		List<CarByUser> listaRetorno = new ArrayList<>();
		for (CarCount carCount : listCarCount) {
			boolean userFound = false;
			for (CarByUser carByUser : listaRetorno) {
				if(carCount.getUserLogin().equals(carByUser.getLogin())) {
					CarByUsage carByUsage = new CarByUsage();
					carByUsage.setModel(carCount.getCar().getModel());
					carByUsage.setUsage(carCount.getCount());
					carByUser.getListaCarro().add(carByUsage);
					carByUser.setTotalUsages(carByUser.getTotalUsages() + carCount.getCount());
					userFound = true;
					break;
				}
			}
			if(!userFound) {
				CarByUser carByUser = new CarByUser();
				CarByUsage carByUsage = new CarByUsage();
				List<CarByUsage> listaCarro = new ArrayList<>();
				carByUsage.setModel(carCount.getCar().getModel());
				carByUsage.setUsage(carCount.getCount());
				carByUser.setLogin(carCount.getUserLogin());
				listaCarro.add(carByUsage);
				carByUser.setListaCarro(listaCarro);
				carByUser.setTotalUsages(carCount.getCount());
				listaRetorno.add(carByUser);
			}
			
		}
		for (CarByUser carByUser : listaRetorno) {
			carByUser.setListaCarro(carByUser.getListaCarro().stream().sorted((car1, car2) -> car2.getUsage().compareTo(car1.getUsage())).collect(Collectors.toList()));
		}
		listaRetorno.forEach(carByUser -> {
			carByUser.getListaCarro().stream().sorted((car1, car2) -> car2.getUsage().compareTo(car1.getUsage())).collect(Collectors.toList());
		});
		listaRetorno = listaRetorno.stream().sorted((usr1, usr2) -> usr1.getLogin().compareTo(usr2.getLogin()))
											.sorted((usr1, usr2) -> usr2.getTotalUsages().compareTo(usr1.getTotalUsages()))
											.collect(Collectors.toList());
		return listaRetorno;
	}
	
}
