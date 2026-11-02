package com.springboot.busticketbooking;

import com.springboot.busticketbooking.dto.request.BusReqDto;
import com.springboot.busticketbooking.dto.request.BusUpdateReqDto;
import com.springboot.busticketbooking.dto.response.BusRespDto;
import com.springboot.busticketbooking.exception.OwnershipException;
import com.springboot.busticketbooking.exception.ResourceNotFoundException;
import com.springboot.busticketbooking.mapper.BusMapper;
import com.springboot.busticketbooking.model.Bus;
import com.springboot.busticketbooking.model.BusOperator;
import com.springboot.busticketbooking.model.User;
import com.springboot.busticketbooking.repository.BusOperatorRepository;
import com.springboot.busticketbooking.repository.BusRepository;
import com.springboot.busticketbooking.service.BusService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BusServiceTest {

    @Mock
    private BusRepository busRepository;

    @Mock
    private BusOperatorRepository busOperatorRepository;

    @InjectMocks
    private BusService busService;

    @Test
    void insertBusInvalidOperatorTest() {
        when(busOperatorRepository.findById(1L))
                .thenReturn(Optional.empty());

        BusReqDto busReqDto = mock(BusReqDto.class);

        assertThrows(ResourceNotFoundException.class,
                () -> busService.insertBus(busReqDto, 1L));

        verify(busOperatorRepository).findById(1L);
        verify(busRepository, never()).save(any(Bus.class));
    }

    @Test
    void insertBusTest() {
        BusOperator busOperator = mock(BusOperator.class);
        BusReqDto busReqDto = mock(BusReqDto.class);

        when(busOperatorRepository.findById(1L))
                .thenReturn(Optional.of(busOperator));

        busService.insertBus(busReqDto, 1L);

        verify(busOperatorRepository).findById(1L);
        verify(busRepository).save(any(Bus.class));
    }

    @Test
    void getAllBusTest() {
        Bus bus1 = mock(Bus.class);
        Bus bus2 = mock(Bus.class);

        when(busRepository.getAllBus(any(Pageable.class)))
                .thenReturn(List.of(bus1, bus2));

        List<BusRespDto> result = busService.getAllBus(0, 2);

        assertEquals(2, result.size());
        verify(busRepository).getAllBus(any(Pageable.class));
    }

    @Test
    void updateBusNotFoundTest() {
        BusUpdateReqDto busUpdateReqDto = mock(BusUpdateReqDto.class);

        when(busRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> busService.updateBus(1L, busUpdateReqDto, "operator1"));

        verify(busRepository).findById(1L);
        verify(busRepository, never()).save(any(Bus.class));
    }

    @Test
    void updateBusWrongOwnerTest() {
        Bus bus = mock(Bus.class);
        BusOperator busOperator = mock(BusOperator.class);
        User user = mock(User.class);
        BusUpdateReqDto busUpdateReqDto = mock(BusUpdateReqDto.class);

        when(busRepository.findById(1L)).thenReturn(Optional.of(bus));
        when(bus.getOperator()).thenReturn(busOperator);
        when(busOperator.getUser()).thenReturn(user);
        when(user.getUsername()).thenReturn("operator1");

        assertThrows(OwnershipException.class,
                () -> busService.updateBus(1L, busUpdateReqDto, "operator2"));

        verify(busRepository).findById(1L);
        verify(busRepository, never()).save(any(Bus.class));
    }

    @Test
    void updateBusTest() {
        Bus bus = mock(Bus.class);
        BusOperator busOperator = mock(BusOperator.class);
        User user = mock(User.class);

        BusUpdateReqDto busUpdateReqDto = mock(BusUpdateReqDto.class);

        when(busRepository.findById(1L)).thenReturn(Optional.of(bus));
        when(bus.getOperator()).thenReturn(busOperator);
        when(busOperator.getUser()).thenReturn(user);
        when(user.getUsername()).thenReturn("operator1");

        when(busUpdateReqDto.busName()).thenReturn("Express Travels");
        when(busUpdateReqDto.busNumber()).thenReturn("TN72AB1234");
        when(busUpdateReqDto.busType()).thenReturn("AC");

        busService.updateBus(1L, busUpdateReqDto, "operator1");

        verify(bus).setBusName("Express Travels");
        verify(bus).setBusNumber("TN72AB1234");
        verify(bus).setBusType("AC");
        verify(busRepository).save(bus);
    }

    @Test
    void getBusNotFoundTest() {
        when(busRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> busService.getBus(1L, "operator1"));

        verify(busRepository).findById(1L);
    }

    @Test
    void getBusWrongOwnerTest() {
        Bus bus = mock(Bus.class);
        BusOperator busOperator = mock(BusOperator.class);
        User user = mock(User.class);

        when(busRepository.findById(1L)).thenReturn(Optional.of(bus));
        when(bus.getOperator()).thenReturn(busOperator);
        when(busOperator.getUser()).thenReturn(user);
        when(user.getUsername()).thenReturn("operator1");

        assertThrows(OwnershipException.class,
                () -> busService.getBus(1L, "operator2"));

        verify(busRepository).findById(1L);
    }

    @Test
    void getBusTest() {
        Bus bus = mock(Bus.class);
        BusOperator busOperator = mock(BusOperator.class);
        User user = mock(User.class);

        when(busRepository.findById(1L)).thenReturn(Optional.of(bus));
        when(bus.getOperator()).thenReturn(busOperator);
        when(busOperator.getUser()).thenReturn(user);
        when(user.getUsername()).thenReturn("operator1");

        BusRespDto result = busService.getBus(1L, "operator1");

        assertNotNull(result);
        verify(busRepository).findById(1L);
    }
}