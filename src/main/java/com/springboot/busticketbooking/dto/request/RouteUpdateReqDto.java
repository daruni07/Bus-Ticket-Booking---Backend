package com.springboot.busticketbooking.dto.request;

public record RouteUpdateReqDto( String source,
                                 String destination,
                                 Double distance) {
}
