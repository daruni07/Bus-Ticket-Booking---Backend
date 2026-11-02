package com.springboot.busticketbooking;

import com.springboot.busticketbooking.dto.request.BusOperatorReqDto;
import com.springboot.busticketbooking.dto.request.BusOperatorUpdateReq;
import com.springboot.busticketbooking.dto.response.BusOperatorRespDto;
import com.springboot.busticketbooking.enums.Role;
import com.springboot.busticketbooking.exception.ResourceNotFoundException;
import com.springboot.busticketbooking.model.BusOperator;
import com.springboot.busticketbooking.model.User;
import com.springboot.busticketbooking.repository.BusOperatorRepository;
import com.springboot.busticketbooking.service.BusOperatorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BusOperatorServiceTest {

    @Mock
    private BusOperatorRepository busOperatorRepository;

    @InjectMocks
    private BusOperatorService busOperatorService;

    @Test
    void insertBusOperatorTest() {
        BusOperatorReqDto busOperatorReqDto = mock(BusOperatorReqDto.class);

        when(busOperatorReqDto.username()).thenReturn("operator1");
        when(busOperatorReqDto.password()).thenReturn("password123");

        busOperatorService.insertBusOperator(busOperatorReqDto);

        verify(busOperatorRepository).save(any(BusOperator.class));
    }

    @Test
    void getBusByIdNotFoundTest() {
        when(busOperatorRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> busOperatorService.getBusById(1L));

        verify(busOperatorRepository).findById(1L);
    }

    @Test
    void getBusByIdTest() {
        BusOperator busOperator = mock(BusOperator.class);

        when(busOperatorRepository.findById(1L))
                .thenReturn(Optional.of(busOperator));

        BusOperatorRespDto result = busOperatorService.getBusById(1L);

        assertNotNull(result);
        verify(busOperatorRepository).findById(1L);
    }

    @Test
    void getAllBusTest() {
        BusOperator busOperator1 = mock(BusOperator.class);
        BusOperator busOperator2 = mock(BusOperator.class);

        when(busOperatorRepository.findAll())
                .thenReturn(List.of(busOperator1, busOperator2));

        List<BusOperatorRespDto> result = busOperatorService.getAllBus();

        assertEquals(2, result.size());
        verify(busOperatorRepository).findAll();
    }

    @Test
    void updateBusOperatorNotFoundTest() {
        BusOperatorUpdateReq busOperatorUpdateReq =
                mock(BusOperatorUpdateReq.class);

        when(busOperatorRepository.findByUserName("operator1"))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> busOperatorService.updateBusOperator(
                        busOperatorUpdateReq, "operator1"));

        verify(busOperatorRepository).findByUserName("operator1");
        verify(busOperatorRepository, never()).save(any(BusOperator.class));
    }

    @Test
    void updateBusOperatorTest() {
        BusOperator busOperator = mock(BusOperator.class);
        BusOperatorUpdateReq busOperatorUpdateReq =
                mock(BusOperatorUpdateReq.class);

        when(busOperatorRepository.findByUserName("operator1"))
                .thenReturn(Optional.of(busOperator));

        when(busOperatorUpdateReq.name()).thenReturn("New Travels");
        when(busOperatorUpdateReq.email()).thenReturn("new@example.com");
        when(busOperatorUpdateReq.phone()).thenReturn("9876543210");

        busOperatorService.updateBusOperator(
                busOperatorUpdateReq, "operator1");

        verify(busOperator).setName("New Travels");
        verify(busOperator).setEmail("new@example.com");
        verify(busOperator).setPhone("9876543210");
        verify(busOperatorRepository).save(busOperator);
    }
}
