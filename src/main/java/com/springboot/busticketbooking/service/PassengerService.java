package com.springboot.busticketbooking.service;

import com.springboot.busticketbooking.dto.request.PassengerSignupDto;
import com.springboot.busticketbooking.dto.request.PassengerUpdateReqDto;
import com.springboot.busticketbooking.dto.response.PassengerRespDto;
import com.springboot.busticketbooking.enums.Role;
import com.springboot.busticketbooking.exception.ResourceNotFoundException;
import com.springboot.busticketbooking.mapper.PassengerMapper;
import com.springboot.busticketbooking.model.Passenger;
import com.springboot.busticketbooking.model.User;
import com.springboot.busticketbooking.repository.PassengerRepository;
import com.springboot.busticketbooking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.RecognitionException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PassengerService {
    private final PassengerRepository passengerRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public void signupPassenger(PassengerSignupDto passengerSignupDto) {
        Passenger passenger= PassengerMapper.mapToEntity(passengerSignupDto);
        User user=new User();
        user.setUsername(passengerSignupDto.username());
        user.setPassword(passwordEncoder.encode(passengerSignupDto.password()));
        user.setRole(Role.PASSENGER);
        userRepository.save(user);
        passenger.setUser(user);
        passengerRepository.save(passenger);
    }

    public PassengerRespDto getPassengerById(Long passengerId) {
            Passenger passenger=passengerRepository.findById(passengerId).orElseThrow(()->new ResourceNotFoundException("invalid passenger id"));
            return PassengerMapper.mapToDto(passenger);
    }

    public List<PassengerRespDto> getAllPassengers(Integer page, Integer size) {
        Pageable pageable= PageRequest.of(page,size);
        List<Passenger> passengerList=passengerRepository.getAllPassengers(pageable);
        return passengerList.stream().map(PassengerMapper::mapToDto).toList();
    }

    public void updatePassenger(String username, PassengerUpdateReqDto passengerUpdateReqDto) {

        Passenger passenger = passengerRepository
                .findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Passenger not found"));
        if(passengerUpdateReqDto.name()!=null)
        {
            passenger.setName(passengerUpdateReqDto.name());
        }
        if(passengerUpdateReqDto.email()!=null)
        {
        passenger.setEmail(passengerUpdateReqDto.email());
        }
        if (passengerUpdateReqDto.phone()!=null)
        {
            passenger.setPhone(passengerUpdateReqDto.phone());
        }
        passengerRepository.save(passenger);
    }
}
