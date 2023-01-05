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
                .antMatchers(HttpMethod.GET, "/api/treeTemplate/branch/").access("hasRole('ROLE_BF_EDIT_TREE_TEMPLATE','ROLE_BF_ADD_TREE_TEMPLATE','ROLE_BF_VIEW_TREE_TEMPLATES')")
                //Budget
                .antMatchers(HttpMethod.POST, "/api/budget/**").access("hasRole('ROLE_BF_ADD_BUDGET')")
                .antMatchers(HttpMethod.GET, "/api/budget/").fullyAuthenticated()
                .antMatchers(HttpMethod.PUT, "/api/budget/**").access("hasRole('ROLE_BF_EDIT_BUDGET')")
                .antMatchers(HttpMethod.GET, "/api/budget/**").access("hasRole('ROLE_BF_EDIT_BUDGET')")
                //Country
                .antMatchers(HttpMethod.POST, "/api/country/**").access("hasRole('ROLE_BF_ADD_COUNTRY')")
                .antMatchers(HttpMethod.GET, "/api/country/all/").fullyAuthenticated()
                .antMatchers(HttpMethod.GET, "/api/country/").fullyAuthenticated()
                .antMatchers(HttpMethod.PUT, "/api/country/**").access("hasRole('ROLE_BF_EDIT_COUNTRY')")
                .antMatchers(HttpMethod.GET, "/api/country/**").access("hasRole('ROLE_BF_EDIT_COUNTRY')")
                //Currency
                .antMatchers(HttpMethod.POST, "/api/currency/**").access("hasRole('ROLE_BF_ADD_CURRENCY')")
                .antMatchers(HttpMethod.GET, "/api/currency/all/").fullyAuthenticated()
                .antMatchers(HttpMethod.GET, "/api/currency/").fullyAuthenticated()
                .antMatchers(HttpMethod.PUT, "/api/currency/**").access("hasRole('ROLE_BF_EDIT_CURRENCY')")
                .antMatchers(HttpMethod.GET, "/api/currency/**").access("hasRole('ROLE_BF_EDIT_CURRENCY')")
                //Dashboard
                .antMatchers(HttpMethod.GET, "/api/applicationLevelDashboard/").access("hasRole('ROLE_BF_APPLICATION_DASHBOARD')")
                .antMatchers(HttpMethod.GET, "/api/realmLevelDashboard").access("hasRole('ROLE_BF_PROGRAM_DASHBOARD')")
                .antMatchers(HttpMethod.GET, "/api/applicationLevelDashboardUserList/").access("hasRole('ROLE_BF_APPLICATION_DASHBOARD')")
                .antMatchers(HttpMethod.GET, "/api/realmLevelDashboardUserList").access("hasRole('ROLE_BF_PROGRAM_DASHBOARD')")
                .antMatchers(HttpMethod.GET, "/api/ticket/openIssues/").access("hasRole('ROLE_BF_APPLICATION_DASHBOARD')")
                .antMatchers(HttpMethod.GET, "/api/supplyPlanReviewerLevelDashboard/").access("hasRole('ROLE_SUPPLY_PLAN_REVIEWER')")
                //Dataset
                .antMatchers(HttpMethod.GET, "/api/dataset/").access("hasRole('ROLE_BF_MAP_ACCESS_CONTROL','ROLE_BF_ADD_USER','ROLE_BF_EDIT_USER')")
                .antMatchers(HttpMethod.GET, "/api/loadDataset/").access("hasRole('ROLE_BF_LOAD_DELETE_DATASET')")
                .antMatchers(HttpMethod.GET, "/api/loadDataset/programId/**").access("hasRole('ROLE_BF_LOAD_DELETE_DATASET')")
                .antMatchers(HttpMethod.POST, "/api/datasetData/**").fullyAuthenticated()
                //                .antMatchers(HttpMethod.GET, "/api/dataset/*").access("hasRole('')")  -- never used
                .antMatchers(HttpMethod.GET, "/api/treeTemplate/").access("hasRole('ROLE_BF_LIST_TREE_TEMPLATE')")
                .antMatchers(HttpMethod.GET, "/api/treeTemplate/*").access("hasRole('ROLE_BF_LIST_TREE_TEMPLATE')")
                //                .antMatchers(HttpMethod.GET, "/api/nodeType").access("hasRole('')") -- not in use
                .antMatchers(HttpMethod.GET, "/api/usageType").access("hasRole('ROLE_BF_EDIT_TREE_TEMPLATE','ROLE_BF_ADD_TREE_TEMPLATE','ROLE_BF_VIEW_TREE_TEMPLATES')")
                .antMatchers(HttpMethod.POST, "/api/treeTemplate/*").access("hasRole('ROLE_BF_EDIT_TREE_TEMPLATE','ROLE_BF_ADD_TREE_TEMPLATE','ROLE_BF_VIEW_TREE_TEMPLATES')")
                .antMatchers(HttpMethod.PUT, "/api/treeTemplate/*").access("hasRole('ROLE_BF_EDIT_TREE_TEMPLATE','ROLE_BF_ADD_TREE_TEMPLATE','ROLE_BF_VIEW_TREE_TEMPLATES')")
                .antMatchers(HttpMethod.PUT, "/api/datasetData/*").access("hasRole('ROLE_BF_COMMIT_DATASET')")
                .antMatchers(HttpMethod.GET, "/api/datasetData/programId/*").access("hasRole('ROLE_BF_VERSION_SETTINGS','ROLE_BF_LIST_TREE')")
                //DataSource                
                .antMatchers(HttpMethod.POST, "/api/dataSource/**").access("hasRole('ROLE_BF_ADD_DATA_SOURCE')")
                .antMatchers(HttpMethod.GET, "/api/dataSource/").fullyAuthenticated()
                .antMatchers(HttpMethod.GET, "/api/dataSource/all/").fullyAuthenticated()
                .antMatchers(HttpMethod.PUT, "/api/dataSource/**").access("hasRole('ROLE_BF_EDIT_DATA_SOURCE')")
                .antMatchers(HttpMethod.GET, "/api/dataSource/**").access("hasRole('ROLE_BF_EDIT_DATA_SOURCE')")
                //DataSourceType                
                .antMatchers(HttpMethod.POST, "/api/dataSourceType/**").access("hasRole('ROLE_BF_ADD_DATA_SOURCE_TYPE')")
                .antMatchers(HttpMethod.GET, "/api/dataSourceType/all/").fullyAuthenticated()
                .antMatchers(HttpMethod.GET, "/api/dataSourceType/").fullyAuthenticated()
                .antMatchers(HttpMethod.PUT, "/api/dataSourceType/**").access("hasRole('ROLE_BF_EDIT_DATA_SOURCE_TYPE')")
                .antMatchers(HttpMethod.GET, "/api/dataSourceType/**").access("hasRole('ROLE_BF_EDIT_DATA_SOURCE_TYPE')")
                .antMatchers(HttpMethod.GET, "/api/dataSourceType/realmId/**").fullyAuthenticated()
                //dimension    
                .antMatchers(HttpMethod.POST, "/api/dimension/**").access("hasRole('ROLE_BF_ADD_DIMENSION')")
                .antMatchers(HttpMethod.GET, "/api/dimension/all/").fullyAuthenticated()
                .antMatchers(HttpMethod.PUT, "/api/dimension/**").access("hasRole('ROLE_BF_EDIT_DIMENSION')")
                .antMatchers(HttpMethod.GET, "/api/dimension/**").access("hasRole('ROLE_BF_EDIT_DIMENSION')")
                //Equivalency Unit                
                .antMatchers(HttpMethod.GET, "/api/equivalencyUnit/mapping/all").access("hasRole('ROLE_BF_LIST_EQUIVALENCY_UNIT_MAPPING','ROLE_BF_LIST_MONTHLY_FORECAST')")
                .antMatchers(HttpMethod.POST, "/api/equivalencyUnit/mapping**").access("hasRole('ROLE_BF_LIST_EQUIVALENCY_UNIT_MAPPING')")
                .antMatchers(HttpMethod.GET, "/api/equivalencyUnit/all").access("hasRole('ROLE_BF_LIST_EQUIVALENCY_UNIT_MAPPING')")
                .antMatchers(HttpMethod.POST, "/api/equivalencyUnit**").access("hasRole('ROLE_BF_LIST_EQUIVALENCY_UNIT_MAPPING')")
                .antMatchers(HttpMethod.GET, "/api/equivalencyUnit/forecastingUnitId/**").access("hasRole('ROLE_BF_FORECAST_ERROR_OVER_TIME_REPORT')")
                //Extrapolation - these are funtions for calculation do we need to add BF for this ?       
                //                .antMatchers(HttpMethod.POST, "/api/forecastStats/tes**").access("hasRole('')")
                //                .antMatchers(HttpMethod.POST, "/api/forecastStats/arima**").access("hasRole('')")
                //                .antMatchers(HttpMethod.POST, "/api/forecastStats/regression**").access("hasRole('')")
                //Forecasting Unit
                .antMatchers(HttpMethod.POST, "/api/forecastingUnit/**").access("hasRole('ROLE_BF_ADD_FORECASTING_UNIT')")
                .antMatchers(HttpMethod.GET, "/api/forecastingUnit/").access("hasRole('ROLE_BF_ADD_PLANNING_UNIT')")
                .antMatchers(HttpMethod.GET, "/api/forecastingUnit/all/").access("hasRole('ROLE_BF_LIST_EQUIVALENCY_UNIT_MAPPING','ROLE_BF_LIST_FORECASTING_UNIT','ROLE_BF_LIST_USAGE_TEMPLATE')")
                .antMatchers(HttpMethod.PUT, "/api/forecastingUnit/**").access("hasRole('ROLE_BF_EDIT_FORECASTING_UNIT')")
                .antMatchers(HttpMethod.GET, "/api/forecastingUnit/**").access("hasRole('ROLE_BF_LIST_USAGE_TEMPLATE','ROLE_BF_EDIT_FORECASTING_UNIT')")
                .antMatchers(HttpMethod.GET, "/api/forecastingUnit/realmId/**").access("hasRole('ROLE_BF_LIST_FORECASTING_UNIT','ROLE_BF_LIST_PLANNING_UNIT')")
                .antMatchers(HttpMethod.GET, "/api/forecastingUnit/tracerCategory/**").access("hasRole('ROLE_BF_ADD_TREE_TEMPLATE')")
                .antMatchers(HttpMethod.POST, "/api/forecastingUnit/tracerCategorys**").access("hasRole('ROLE_BF_LIST_EQUIVALENCY_UNIT_MAPPING')")
                .antMatchers(HttpMethod.GET, "/api/forecastingUnit/programId/**").access("hasRole('ROLE_BF_LIST_MONTHLY_FORECAST')")
                //Forecast Method
                .antMatchers(HttpMethod.GET, "/api/forecastMethod/all").access("hasRole('ROLE_BF_LIST_FORECAST_METHOD')")
                .antMatchers(HttpMethod.GET, "/api/forecastMethod").access("hasRole('ROLE_BF_EDIT_TREE_TEMPLATE','ROLE_BF_ADD_TREE_TEMPLATE','ROLE_BF_VIEW_TREE_TEMPLATES')")
                .antMatchers(HttpMethod.POST, "/api/forecastMethod**").access("hasRole('ROLE_BF_LIST_FORECAST_METHOD')")
                .antMatchers(HttpMethod.GET, "/api/forecastMethodType").access("hasRole('ROLE_BF_LIST_FORECAST_METHOD')")
                //Funding Source
                .antMatchers(HttpMethod.POST, "/api/fundingSource/**").access("hasRole('ROLE_BF_ADD_FUNDING_SOURCE')")
                .antMatchers(HttpMethod.GET, "/api/fundingSource/").access("hasRole('ROLE_BF_LIST_FUNDING_SOURCE')")
                .antMatchers(HttpMethod.PUT, "/api/fundingSource/**").access("hasRole('ROLE_BF_EDIT_FUNDING_SOURCE')")
                .antMatchers(HttpMethod.GET, "/api/fundingSource/**").access("hasRole('ROLE_BF_EDIT_FUNDING_SOURCE')")
                .antMatchers(HttpMethod.GET, "/api/fundingSource/getDisplayName/realmId/**").access("hasRole('ROLE_BF_ADD_FUNDING_SOURCE')")
                //Health Area
                .antMatchers(HttpMethod.GET, "/api/realm").fullyAuthenticated()
                .antMatchers(HttpMethod.POST, "/api/healthArea/**").access("hasRole('ROLE_BF_LIST_HEALTH_AREA','ROLE_BF_ADD_HEALTH_AREA')")
                .antMatchers(HttpMethod.GET, "/api/healthArea/").fullyAuthenticated()
                .antMatchers(HttpMethod.GET, "/api/realmCountry/realmId/**").fullyAuthenticated()
                .antMatchers(HttpMethod.PUT, "/api/healthArea/**").access("hasRole('ROLE_BF_EDIT_HEALTH_AREA')")
                .antMatchers(HttpMethod.GET, "/api/healthArea/**").access("hasRole('ROLE_BF_EDIT_HEALTH_AREA')")
                //                .antMatchers(HttpMethod.GET, "/api/healthArea/program/realmId/**").access("hasRole('')") //Not Found
                .antMatchers(HttpMethod.GET, "/api/healthArea/getDisplayName/realmId/**").access("hasRole('ROLE_BF_ADD_HEALTH_AREA')")
                //Integration
                .antMatchers(HttpMethod.POST, "/api/integration/**").access("hasRole('ROLE_BF_ADD_INTEGRATION')")
                .antMatchers(HttpMethod.GET, "/api/integration").access("hasRole('ROLE_BF_LIST_INTEGRATION')")
                .antMatchers(HttpMethod.GET, "/api/integration/viewList").access("hasRole('ROLE_BF_ADD_INTEGRATION','ROLE_BF_EDIT_INTEGRATION')")
                .antMatchers(HttpMethod.PUT, "/api/integration/**").access("hasRole('ROLE_BF_EDIT_INTEGRATION')")
                .antMatchers(HttpMethod.GET, "/api/integration/**").access("hasRole('ROLE_BF_EDIT_INTEGRATION')")
                .antMatchers(HttpMethod.PUT, "/api/integrationProgram/**").access("hasRole('ROLE_BF_ADD_INTEGRATION_PROGRAM')")
                .antMatchers(HttpMethod.GET, "/api/integrationProgram/program/**").access("hasRole('ROLE_BF_ADD_INTEGRATION_PROGRAM')")
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
                .and().ignoring().antMatchers("/importShipmentData/**")
                .and().ignoring().antMatchers("/importProductCatalog/**")
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
