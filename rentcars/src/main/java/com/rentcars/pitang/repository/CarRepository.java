package com.rentcars.pitang.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rentcars.pitang.model.car.Car;

public interface CarRepository extends JpaRepository<Car, Long>{

}
