package com.springboot.busticketbooking.mapper;

import com.springboot.busticketbooking.dto.request.PassengerSignupDto;
import com.springboot.busticketbooking.dto.response.PassengerRespDto;
import com.springboot.busticketbooking.model.Passenger;

public class PassengerMapper {
    public static Passenger mapToEntity(PassengerSignupDto passengerSignupDto) {
        Passenger passenger=new Passenger();
        passenger.setName(passengerSignupDto.name());
        if (passengerSignupDto.email() != null) {
            passenger.setEmail(passengerSignupDto.email());
        }
        passenger.setPhone(passengerSignupDto.phone());
        return passenger;
    }

    public static PassengerRespDto mapToDto(Passenger passenger) {
        PassengerRespDto passengerRespDto;
        passengerRespDto = new PassengerRespDto(passenger.getName(), passenger.getEmail(), passenger.getPhone());
        return passengerRespDto;
    }
}
