package com.springboot.busticketbooking.config;

import com.springboot.busticketbooking.service.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import static java.util.Base64.getEncoder;

@EnableTransactionManagement
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {
    private final JwtFilter jwtFilter;
    private final MyUserDetailsService myUserDetailsService;
    @Bean
    public SecurityFilterChain defaultFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST,"/api/passenger/signup").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/passenger/getAll").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/passenger/{passengerId}").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/auth/login").authenticated()
                        .requestMatchers(HttpMethod.GET,"/api/booking/{bookingId}").hasAuthority("PASSENGER")
                        .requestMatchers(HttpMethod.GET,"/api/booking/history").hasAuthority("PASSENGER")
                        .requestMatchers(HttpMethod.GET, "/api/schedules/search").hasAnyAuthority("ADMIN", "BUS_OPERATOR", "PASSENGER")
                        .requestMatchers(HttpMethod.POST,"/api/bus/add/{busOperatorId}").hasAuthority("BUS_OPERATOR")
                        .requestMatchers(HttpMethod.POST,"/api/admin/add").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/refund/add/{paymentId}").hasAuthority("PASSENGER")
                        .requestMatchers(HttpMethod.PUT,"/api/bookings/cancel/{bookingId}").hasAuthority("PASSENGER")
                        .requestMatchers(HttpMethod.POST,"/api/booking/add").hasAuthority("PASSENGER")
                        .requestMatchers(HttpMethod.POST,"/api/busOperator/add").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/payment/add/{bookingId}").hasAuthority("PASSENGER")
                        .requestMatchers(HttpMethod.GET,"/api/refund/getRefunds").hasAuthority("BUS_OPERATOR")
                        .requestMatchers(HttpMethod.PUT,"api/refund/complete/{refundId}").hasAuthority("BUS_OPERATOR")
                        .requestMatchers(HttpMethod.POST,"/api/add/user").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/review/add").hasAuthority("PASSENGER")
                        .requestMatchers(HttpMethod.GET,"/api/review/bus/{busId}").hasAnyAuthority("PASSENGER","BUS_OPERATOR")
                        .requestMatchers(HttpMethod.GET,"/api/review/user").hasAuthority("PASSENGER")
                        .requestMatchers(HttpMethod.PUT,"/api/passenger/update").hasAuthority("PASSENGER")
                        .requestMatchers(HttpMethod.PUT,"/api/bus/update/{busId}").hasAuthority("BUS_OPERATOR")
                        .requestMatchers(HttpMethod.PUT,"/api/busOperator/update/me").hasAuthority("BUS_OPERATOR")
                        .requestMatchers(HttpMethod.PUT,"/api/schedules/update/{scheduleId}").hasAuthority("BUS_OPERATOR")
                        .requestMatchers(HttpMethod.GET,"/api/schedule/seats/{scheduleId}").hasAnyAuthority("BUS_OPERATOR","")
                        .requestMatchers(HttpMethod.GET,"/api/schedule/{scheduleId}").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/route/{routeId}").hasAnyAuthority("ADMIN","BUS_OPERATOR")
                        .requestMatchers(HttpMethod.GET,"/api/stop/getAll").hasAnyAuthority("ADMIN","BUS_OPERATOR","PASSENGER")
                        .requestMatchers(HttpMethod.GET,"/api/payment/booking/{bookingId}").hasAuthority("PASSENGER")
                        .requestMatchers(HttpMethod.PUT,"/api/route/update/{routeId}").hasAuthority("BUS_OPERATOR")
                        .requestMatchers(HttpMethod.PUT,"/api/routeStop/update/{routeStopId}").hasAuthority("BUS_OPERATOR")
                        .requestMatchers(HttpMethod.PUT,"/api/stop/update/{stopId}").hasAuthority("BUS_OPERATOR")
                        .requestMatchers(HttpMethod.GET,"/api/payment/operator/filter").hasAuthority("BUS_OPERATOR")
                        .requestMatchers(HttpMethod.GET,"/api/scheduleFares").hasAnyAuthority("BUS_OPERATOR","ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/auth/user-details").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/eticket/{bookingId}").hasAuthority("PASSENGER")
                        .requestMatchers(HttpMethod.GET, "/api/booking/operator").hasAuthority("BUS_OPERATOR")
                        .requestMatchers(HttpMethod.GET,"/api/routeStop/route/{routeId}").hasAnyAuthority("ADMIN","BUS_OPERATOR")
                        .requestMatchers(HttpMethod.GET,"/api/route/getAll").hasAnyAuthority("ADMIN","BUS_OPERATOR")
                        .requestMatchers(HttpMethod.GET,"/api/bus/operator").hasAuthority("BUS_OPERATOR")
                        .requestMatchers(HttpMethod.POST, "/api/scheduleFare/add").hasAnyAuthority("ADMIN", "BUS_OPERATOR")
                        .requestMatchers(HttpMethod.GET,"/api/schedule/getAll").hasAuthority("ADMIN")

                        .anyRequest().permitAll()
                );
        http.addFilterBefore(jwtFilter , UsernamePasswordAuthenticationFilter.class);
        http.httpBasic(Customizer.withDefaults());
        return http.build();
    }
    @Bean
    public AuthenticationProvider getProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(myUserDetailsService);
        authenticationProvider.setPasswordEncoder(getEncoder());
        return authenticationProvider;
    }
    @Bean
    public PasswordEncoder getEncoder()
    {
        return new BCryptPasswordEncoder();
    }
}
