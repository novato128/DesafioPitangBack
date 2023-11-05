package com.rentcars.pitang.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rentcars.pitang.model.car.CarCount;

public interface CarCountRepository extends JpaRepository<CarCount, Long>{
	
	Optional<CarCount> findByCar_Id(Long carId);

}
