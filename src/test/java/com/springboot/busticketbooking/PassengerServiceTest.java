package com.springboot.busticketbooking;

import com.springboot.busticketbooking.dto.request.PassengerSignupDto;
import com.springboot.busticketbooking.dto.request.PassengerUpdateReqDto;
import com.springboot.busticketbooking.dto.response.PassengerRespDto;
import com.springboot.busticketbooking.exception.ResourceNotFoundException;
import com.springboot.busticketbooking.model.Passenger;
import com.springboot.busticketbooking.model.User;
import com.springboot.busticketbooking.repository.PassengerRepository;
import com.springboot.busticketbooking.repository.UserRepository;
import com.springboot.busticketbooking.service.PassengerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PassengerServiceTest {

    @Mock
    private PassengerRepository passengerRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private PassengerService passengerService;

    @Test
    void signupPassengerTest() {
        PassengerSignupDto passengerSignupDto = mock(PassengerSignupDto.class);

        when(passengerSignupDto.username()).thenReturn("passenger1");
        when(passengerSignupDto.password()).thenReturn("password123");
        when(passwordEncoder.encode("password123"))
                .thenReturn("encodedPassword");

        passengerService.signupPassenger(passengerSignupDto);

        verify(passwordEncoder).encode("password123");
        verify(userRepository).save(any(User.class));
        verify(passengerRepository).save(any(Passenger.class));
    }

    @Test
    void getPassengerByIdNotFoundTest() {
        when(passengerRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> passengerService.getPassengerById(1L));

        verify(passengerRepository).findById(1L);
    }

    @Test
    void getPassengerByIdTest() {
        Passenger passenger = mock(Passenger.class);

        when(passengerRepository.findById(1L))
                .thenReturn(Optional.of(passenger));

        PassengerRespDto result = passengerService.getPassengerById(1L);

        assertNotNull(result);
        verify(passengerRepository).findById(1L);
    }

    @Test
    void getAllPassengersTest() {
        Passenger passenger1 = mock(Passenger.class);
        Passenger passenger2 = mock(Passenger.class);

        when(passengerRepository.getAllPassengers(any(Pageable.class)))
                .thenReturn(List.of(passenger1, passenger2));

        List<PassengerRespDto> result =
                passengerService.getAllPassengers(0, 2);

        assertEquals(2, result.size());
        verify(passengerRepository).getAllPassengers(any(Pageable.class));
    }

    @Test
    void updatePassengerNotFoundTest() {
        PassengerUpdateReqDto passengerUpdateReqDto =
                mock(PassengerUpdateReqDto.class);

        when(passengerRepository.findByUsername("passenger1"))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> passengerService.updatePassenger(
                        "passenger1", passengerUpdateReqDto));

        verify(passengerRepository).findByUsername("passenger1");
        verify(passengerRepository, never()).save(any(Passenger.class));
    }

    @Test
    void updatePassengerTest() {
        Passenger passenger = mock(Passenger.class);
        PassengerUpdateReqDto passengerUpdateReqDto =
                mock(PassengerUpdateReqDto.class);

        when(passengerRepository.findByUsername("passenger1"))
                .thenReturn(Optional.of(passenger));

        when(passengerUpdateReqDto.name()).thenReturn("New Name");
        when(passengerUpdateReqDto.email()).thenReturn("new@example.com");
        when(passengerUpdateReqDto.phone()).thenReturn("9876543210");

        passengerService.updatePassenger(
                "passenger1", passengerUpdateReqDto);

        verify(passenger).setName("New Name");
        verify(passenger).setEmail("new@example.com");
        verify(passenger).setPhone("9876543210");
        verify(passengerRepository).save(passenger);
    }
}