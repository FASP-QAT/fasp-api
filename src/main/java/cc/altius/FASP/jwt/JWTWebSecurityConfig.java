/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.jwt;

import cc.altius.FASP.rest.controller.UserRestController;
import cc.altius.FASP.security.CustomUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(1000)
public class JWTWebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtUnAuthorizedResponseAuthenticationEntryPoint jwtUnAuthorizedResponseAuthenticationEntryPoint;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtTokenAuthorizationOncePerRequestFilter jwtAuthenticationTokenFilter;

    @Value("${jwt.get.token.uri}")
    private String authenticationPath;
    @Value("${jwt.refresh.token.uri}")
    private String refreshPath;

    private final Logger logger = LoggerFactory.getLogger(UserRestController.class);

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoderBean());
    }

    @Bean
    public PasswordEncoder passwordEncoderBean() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(jwtUnAuthorizedResponseAuthenticationEntryPoint).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
//                .antMatchers("/actuator/info").permitAll()
                //                .antMatchers("/api/healthArea/**").access("hasRole('ROLE_BF_UPDATE_REALM_MASTER')")
                //                .antMatchers("/api/organisation/**").access("hasRole('ROLE_BF_UPDATE_REALM_MASTER')")
                //                .antMatchers("/api/unit/**").access("hasRole('ROLE_BF_UPDATE_APPL_MASTER')")
                //                .antMatchers(HttpMethod.POST, "/api/realm/**").access("hasAnyRole('ROLE_BF_UPDATE_APPL_MASTER')")
                //                .antMatchers(HttpMethod.PUT, "/api/realm/**").access("hasAnyRole('ROLE_BF_UPDATE_APPL_MASTER', 'ROLE_BF_UPDATE_REALM_MASTER')")
                //                .antMatchers("/api/realmCountry/**").access("hasRole('ROLE_BF_UPDATE_REALM_MASTER')")
                .anyRequest().authenticated();

        httpSecurity
                .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        httpSecurity
                .headers()
                .cacheControl(); //disable caching
    }

    @Override
    public void configure(WebSecurity webSecurity) throws Exception {
        webSecurity
                .ignoring()
                .antMatchers(HttpMethod.POST, authenticationPath)
                .antMatchers(HttpMethod.GET, refreshPath)
                .antMatchers(HttpMethod.OPTIONS, "/**")
                .and()
                .ignoring()
                .antMatchers(HttpMethod.GET, "/")
                //Other Stuff You want to Ignore
                .and().ignoring().antMatchers("/actuator/**")
                .and().ignoring().antMatchers("/favicon.ico**")
                .and().ignoring().antMatchers("/actuator**")
                .and().ignoring().antMatchers("/actuator/info")
                .and().ignoring().antMatchers("/browser**")
                .and().ignoring().antMatchers("/file**")
                .and().ignoring().antMatchers("/file/**")
                //                .and().ignoring().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources", "/swagger-resources/configuration/security", "/swagger-ui.html**", "/swagger-resources/configuration/ui")
                .and().ignoring().antMatchers("/api/locales/*/**")
                .and().ignoring().antMatchers("/api/forgotPassword/**")
                .and().ignoring().antMatchers("/api/coreui/version/**")
                .and().ignoring().antMatchers("/api/getForgotPasswordToken/**")
                .and().ignoring().antMatchers("/api/confirmForgotPasswordToken/**")
                .and().ignoring().antMatchers("/api/updatePassword/**")
                //                .and().ignoring().antMatchers("/api/user/**")
                .and().ignoring().antMatchers("/api/updateExpiredPassword/**")
                .and().ignoring().antMatchers("/exportSupplyPlan/**")
                .and().ignoring().antMatchers("/exportProgramData/**")
                .and().ignoring().antMatchers("/exportOrderData/**")
                .and().ignoring().antMatchers("/exportManualJson/**")
                .and().ignoring().antMatchers("/importShipmentData/**")
                .and().ignoring().antMatchers("/importProductCatalog/**")
                .and().ignoring().antMatchers("/importProductCatalogLegacy/**")
                .and().ignoring().antMatchers("/api/sync/language/**")
                .and().ignoring().antMatchers("/exportShipmentLinkingData/**")
                .and().ignoring().antMatchers("/jira/syncJiraAccountIds/**")
                .and().ignoring().antMatchers("/api/processCommitRequest/**");
    }

//    @EventListener
//    public void authSuccessEventListener(AuthenticationSuccessEvent authorizedEvent){
//        authorizedEvent.
//    }
//
//    @EventListener
//    public void authFailedEventListener(AbstractAuthenticationFailureEvent oAuth2AuthenticationFailureEvent){
//        
//    }
}
