package com.springboot.busticketbooking.controller;

import com.springboot.busticketbooking.dto.request.PassengerSignupDto;
import com.springboot.busticketbooking.dto.request.PassengerUpdateReqDto;
import com.springboot.busticketbooking.dto.response.PassengerRespDto;
import com.springboot.busticketbooking.service.PassengerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PassengerController {
    private final PassengerService passengerService;
    @PostMapping("/api/passenger/signup")
    public void signupPassenger(@RequestBody@Valid PassengerSignupDto passengerSignupDto)
    {
        passengerService.signupPassenger(passengerSignupDto);
    }
    @GetMapping("/api/passenger/{passengerId}")
    public PassengerRespDto getPassengerById(@PathVariable Long passengerId)
    {
        return passengerService.getPassengerById(passengerId);
    }
    @GetMapping("/api/passenger/getAll")
    public List<PassengerRespDto> getAllPassengers(@RequestParam(name="page",required = false,defaultValue = "0")Integer page, @RequestParam(name="size",required = false,defaultValue = "10") Integer size)
    {
        return passengerService.getAllPassengers(page,size);
    }
    @PutMapping("/api/passenger/update")
    public void updatePassenger(@RequestBody PassengerUpdateReqDto passengerUpdateReqDto, Principal principal)
    {
                passengerService.updatePassenger(principal.getName(), passengerUpdateReqDto);
    }
}
