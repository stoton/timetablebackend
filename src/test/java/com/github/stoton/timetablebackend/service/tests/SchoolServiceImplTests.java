package com.github.stoton.timetablebackend.service.tests;

import com.github.stoton.timetablebackend.domain.school.Address;
import com.github.stoton.timetablebackend.domain.school.School;
import com.github.stoton.timetablebackend.repository.AddressRepository;
import com.github.stoton.timetablebackend.repository.SchoolRepository;
import com.github.stoton.timetablebackend.service.SchoolServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.Silent.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class SchoolServiceImplTests {

    @Mock
    private SchoolRepository schoolRepository;
    @Mock
    private AddressRepository addressRepository;
    @Mock
    private Logger logger;

    @InjectMocks
    private SchoolServiceImpl schoolService;

    @Test
    public void getAllSchoolsTest() {
        List<School> expected = new ArrayList<>();

        expected.add(new School());
        expected.add(new School());

        Mockito.when(schoolRepository.findAll()).thenReturn(expected);

        List<School> actual = schoolService.getAllSchools();

        assertEquals(expected, actual);
    }

    @Test
    public void getSchoolByIdTest() {
        Optional<School> expected = Optional.of(new School());

        Mockito.when(schoolRepository.findById(1L)).thenReturn(expected);

        Optional<School> actual = schoolService.getSchoolBySchoolId(1L);

        assertEquals(expected, actual);
    }

    @Test
    public void insertSchoolTest() {

        School school = new School();
        Address address = new Address();

        school.setAddress(address);

        schoolService.insertSchool(school);

        Mockito.when(schoolRepository.findByNameAndHrefAndTimetableHrefAndAddress_CityAndAddress_Country(null, null, null, school.getAddress().getCity(), school.getAddress().getCountry())).thenReturn(school);
        Mockito.when(addressRepository.findByCityAndCountry(address.getCity(), address.getCountry())).thenReturn(address);

        Mockito.verify(schoolRepository, Mockito.times(1)).findByNameAndHrefAndTimetableHrefAndAddress_CityAndAddress_Country(null, null, null, school.getAddress().getCity(), school.getAddress().getCountry());
        Mockito.verify(addressRepository, Mockito.times(1)).findByCityAndCountry(address.getCity(), address.getCountry());
        Mockito.verify(addressRepository, Mockito.times(1)).save(school.getAddress());
        Mockito.verify(schoolRepository, Mockito.times(1)).save(school);
    }

    @Test
    public void editSchoolTest() {
        School school = new School();
        Address address = new Address();

        school.setAddress(address);

        Mockito.when(schoolRepository.getOne(1L)).thenReturn(school);

        schoolService.editSchool(1L, school);

        Mockito.verify(addressRepository, Mockito.times(1)).save(school.getAddress());
        Mockito.verify(schoolRepository, Mockito.times(1)).save(school);
    }

}
