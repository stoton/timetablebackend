package com.github.stoton.timetablebackend.repository;

import com.github.stoton.timetablebackend.domain.school.Address;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AddressRepository extends JpaRepository<Address, Long> {

    Address findByCityAndCountry(String city, String country);


}
