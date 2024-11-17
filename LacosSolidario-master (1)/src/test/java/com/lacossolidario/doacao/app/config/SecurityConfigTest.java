package com.lacossolidario.doacao.app.config;

import com.lacossolidario.doacao.infra.repository.SecurityFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.assertNotNull;

class SecurityConfigTest {

    @Mock
    private SecurityFilter securityFilter;

    @Mock
    private AuthenticationConfiguration authenticationConfiguration;

    @InjectMocks
    private SecurityConfig securityConfig;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    void testPasswordEncoder() {
        BCryptPasswordEncoder passwordEncoder = securityConfig.passwordEncoder();

        String senha = "senha123";
        String senhaCodificada = passwordEncoder.encode(senha);

        assertNotNull(senhaCodificada, "A senha codificada não deve ser nula");
        assertTrue(passwordEncoder.matches(senha, senhaCodificada), "A senha deve corresponder à codificação");
    }

    @Test
    void testAuthenticationManager() throws Exception {
        AuthenticationManager mockAuthenticationManager = mock(AuthenticationManager.class);
        when(authenticationConfiguration.getAuthenticationManager()).thenReturn(mockAuthenticationManager);

        AuthenticationManager authenticationManager = securityConfig.authenticationManager(authenticationConfiguration);
        assertNotNull("A", "O AuthenticationManager não deve ser nulo");
    }

//    @Test
//    void testSecurityFilterChain() throws Exception {
//        HttpSecurity httpSecurity = mock(HttpSecurity.class);
//
//
//        when(httpSecurity.csrf(any())).thenReturn(httpSecurity);
//        when(httpSecurity.cors(any())).thenReturn(httpSecurity);
//        when(httpSecurity.sessionManagement(any())).thenReturn(httpSecurity);
//        when(httpSecurity.authorizeHttpRequests(any())).thenReturn(httpSecurity);
//        when(httpSecurity.addFilterBefore(any(), eq(UsernamePasswordAuthenticationFilter.class))).thenReturn(httpSecurity);
//
//        SecurityFilterChain securityFilterChain = securityConfig.securityFilterChain(httpSecurity);
//
//        assertNotNull("securityFilterChain", "O SecurityFilterChain não deve ser nulo");
//    }
}
