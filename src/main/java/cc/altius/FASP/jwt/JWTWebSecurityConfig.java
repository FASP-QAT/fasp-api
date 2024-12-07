/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.jwt;

import cc.altius.FASP.model.SecurityRequestMatcher;
import cc.altius.FASP.rest.controller.UserRestController;
import cc.altius.FASP.security.CustomUserDetailsService;
import cc.altius.FASP.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@Order(1000)
public class JWTWebSecurityConfig {

    @Autowired
    private JwtUnAuthorizedResponseAuthenticationEntryPoint jwtUnAuthorizedResponseAuthenticationEntryPoint;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenAuthorizationOncePerRequestFilter jwtAuthenticationTokenFilter;

    @Value("${jwt.get.token.uri}")
    private String authenticationPath;
    @Value("${jwt.refresh.token.uri}")
    private String refreshPath;

    private final Logger logger = LoggerFactory.getLogger(UserRestController.class);

    @Bean
    public PasswordEncoder passwordEncoderBean() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoderBean());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .exceptionHandling(exception -> exception.authenticationEntryPoint(jwtUnAuthorizedResponseAuthenticationEntryPoint))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth
                        -> auth
                        .requestMatchers(HttpMethod.POST, authenticationPath).permitAll()
                        .requestMatchers(HttpMethod.GET, refreshPath).permitAll()
                        .requestMatchers(HttpMethod.GET, "/").permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/v3/api-docs/**").permitAll()
                        .requestMatchers("/v3/api-docs.yaml").permitAll()
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/swagger-ui.html").permitAll()
                        .requestMatchers(
                                "/error",
                                "/api/logout",
                                "/actuator/**",
                                "/actuator**",
                                "/actuator/info",
                                "/favicon.ico**",
                                "/browser**",
                                "/file**",
                                "/file/**",
                                "/api/locales/*/**",
                                "/api/user/forgotPassword/**",
                                "/api/user/confirmForgotPasswordToken/**",
                                "/api/user/updatePassword/**",
                                "/api/user/updateExpiredPassword/**",
                                "/exportSupplyPlan/**",
                                "/exportManualJson",
                                "/exportProgramData/**",
                                "/exportOrderData/**",
                                "/importShipmentData/**",
                                "/importProductCatalog/**",
                                "/importProductCatalogLegacy/**",
                                "/api/sync/language/**",
                                "/exportShipmentLinkingData/**",
                                "/jira/syncJiraAccountIds/**",
                                "/api/processCommitRequest/**",
                                "/api/programData/gfpvan/**",
                                "/api/test/**"
                        ).permitAll()
                );
        for (SecurityRequestMatcher security : this.userService.getSecurityList()) {
            buildRequestMatcher(http, security);
        }
        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    private void buildRequestMatcher(HttpSecurity http, SecurityRequestMatcher security) throws Exception {
        HttpMethod method = security.getHttpMethod();
        if (security.getHttpMethod() == null) {
            if (security.getBfList().equals("")) {
                http.authorizeHttpRequests(auth -> auth.requestMatchers(security.getUrlList().split(",")).authenticated());
            } else {
                http.authorizeHttpRequests(auth -> auth.requestMatchers(security.getUrlList().split(",")).hasAnyAuthority(security.getBfList().split(",")));
            }
        } else {
            if (security.getBfList().equals("")) {
                http.authorizeHttpRequests(auth -> auth.requestMatchers(method, security.getUrlList().split(",")).authenticated());
            } else {
                http.authorizeHttpRequests(auth -> auth.requestMatchers(method, security.getUrlList().split(",")).hasAnyAuthority(security.getBfList().split(",")));
            }
        }
    }
}