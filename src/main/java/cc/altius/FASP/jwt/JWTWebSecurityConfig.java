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
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(1000)
public class JWTWebSecurityConfig {

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

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                .userDetailsService(customUserDetailsService)
//                .passwordEncoder(passwordEncoderBean());
//    }
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
                        .requestMatchers("/actuator/**").permitAll()
                        .requestMatchers("/favicon.ico**").permitAll()
                        .requestMatchers("/actuator**").permitAll()
                        .requestMatchers("/actuator/info").permitAll()
                        .requestMatchers("/browser**").permitAll()
                        .requestMatchers("/file**").permitAll()
                        .requestMatchers("/file/**").permitAll()
                        //                .requestMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources", "/swagger-resources/configuration/security", "/swagger-ui.html**", "/swagger-resources/configuration/ui").permitAll()
                        .requestMatchers("/api/locales/*/**").permitAll()
                        .requestMatchers("/api/forgotPassword/**").permitAll()
                        .requestMatchers("/api/getForgotPasswordToken/**").permitAll()
                        .requestMatchers("/api/confirmForgotPasswordToken/**").permitAll()
                        .requestMatchers("/api/updatePassword/**").permitAll()
                        //                .requestMatchers("/api/user/**").permitAll()
                        .requestMatchers("/api/updateExpiredPassword/**").permitAll()
                        .requestMatchers("/exportSupplyPlan/**").permitAll()
                        .requestMatchers("/exportProgramData/**").permitAll()
                        .requestMatchers("/exportOrderData/**").permitAll()
                        .requestMatchers("/importShipmentData/**").permitAll()
                        .requestMatchers("/importProductCatalog/**").permitAll()
                        .requestMatchers("/api/sync/language/**").permitAll()
                        .requestMatchers("/exportShipmentLinkingData/**").permitAll()
                        .requestMatchers("/jira/syncJiraAccountIds/**").permitAll()
                        .requestMatchers("/api/processCommitRequest/**").permitAll()
                                
                        .requestMatchers(HttpMethod.POST,"/api/budget").hasAnyAuthority("ROLE_BF_ADD_BUDGET")
                        .requestMatchers(HttpMethod.GET,"/api/budget").hasAnyAuthority("ROLE_BF_TICKETING","ROLE_BF_LIST_BUDGET")
                        .requestMatchers("/api/budget/**").hasAnyAuthority("ROLE_BF_EDIT_BUDGET")
                        .requestMatchers(HttpMethod.POST,"/api/country").hasAnyAuthority("ROLE_BF_ADD_COUNTRY")
                        .requestMatchers(HttpMethod.PUT,"/api/country").hasAnyAuthority("ROLE_BF_EDIT_COUNTRY")
                        .requestMatchers(HttpMethod.GET,"/api/country/*").hasAnyAuthority("ROLE_BF_EDIT_COUNTRY")
                        .requestMatchers(HttpMethod.GET,"/api/country").hasAnyAuthority("ROLE_BF_LIST_COUNTRY")
                        .requestMatchers(HttpMethod.GET,"/api/country/all").hasAnyAuthority("ROLE_BF_LIST_COUNTRY","ROLE_BF_ADD_HEALTH_AREA","ROLE_BF_MAP_REALM_COUNTRY","ROLE_BF_LIST_REALM")
                        .requestMatchers(HttpMethod.POST,"/api/currency").hasAnyAuthority("ROLE_BF_ADD_CURRENCY")
                        .requestMatchers(HttpMethod.PUT,"/api/currency").hasAnyAuthority("ROLE_BF_EDIT_CURRENCY")
                        .requestMatchers(HttpMethod.GET,"/api/currency").hasAnyAuthority("ROLE_BF_LIST_CURRENCY","ROLE_BF_ADD_BUDGET","ROLE_BF_SUPPLY_PLAN_VERSION_AND_REVIEW","ROLE_BF_EDIT_COUNTRY","ROLE_BF_ADD_COUNTRY", "ROLE_BF_MAP_REALM_COUNTRY")
                        .requestMatchers(HttpMethod.GET,"api/currency/all").hasAnyAuthority("ROLE_BF_LIST_CURRENCY","ROLE_BF_ADD_COUNTRY","ROLE_BF_EDIT_COUNTRY","ROLE_BF_MAP_REALM_COUNTRY","ROLE_BF_TICKETING")
                        .requestMatchers(HttpMethod.GET,"/api/currency/**").hasAnyAuthority("ROLE_BF_EDIT_CURRENCY")
                        .requestMatchers(HttpMethod.GET,"/api/applicationLevelDashboard").hasAnyAuthority("ROLE_BF_APPLICATION_DASHBOARD")
                        .requestMatchers(HttpMethod.GET,"/api/applicationLevelDashboardUserList").hasAnyAuthority("ROLE_BF_APPLICATION_DASHBOARD")
                        .requestMatchers(HttpMethod.GET,"/api/ticket/openIssues").hasAnyAuthority("ROLE_BF_APPLICATION_DASHBOARD", "ROLE_BF_MASTER_DATA_SYNC")
                        .requestMatchers(HttpMethod.GET,"/api/supplyPlanReviewerLevelDashboard").hasAnyAuthority("ROLE_BF_MASTER_DATA_SYNC")
                        .requestMatchers(HttpMethod.GET,"/api/realmLevelDashboard").hasAnyAuthority("ROLE_BF_APPLICATION_DASHBOARD")
                        .requestMatchers(HttpMethod.GET,"/api/realmLevelDashboardUserList").hasAnyAuthority("ROLE_BF_APPLICATION_DASHBOARD")
                        .requestMatchers(HttpMethod.GET,"/api/dataset").hasAnyAuthority("ROLE_BF_COMPARE_VERSION","ROLE_BF_SUPPLY_PLAN_IMPORT","ROLE_BF_LIST_EQUIVALENCY_UNIT_MAPPING","ROLE_BF_LIST_USAGE_TEMPLATE","ROLE_BF_MODELING_VALIDATION","ROLE_BF_PRODUCT_VALIDATION")
                        .requestMatchers(HttpMethod.GET,"/api/loadDataset").hasAnyAuthority("ROLE_BF_LOAD_DELETE_DATASET")
                        .requestMatchers(HttpMethod.GET,"/api/loadDataset/programId/**").hasAnyAuthority("ROLE_BF_LOAD_DELETE_DATASET")
                        .requestMatchers(HttpMethod.GET,"/api/treeTemplate").hasAnyAuthority("ROLE_BF_TICKETING","ROLE_BF_LIST_TREE_TEMPLATE")
                        .requestMatchers(HttpMethod.GET,"/api/treeTemplate/**").hasAnyAuthority("ROLE_BF_EDIT_TREE_TEMPLATE","ROLE_BF_ADD_TREE_TEMPLATE","ROLE_BF_VIEW_TREE_TEMPLATES","ROLE_BF_LIST_TREE_TEMPLATE")
                        .requestMatchers(HttpMethod.GET,"/api/usageType").hasAnyAuthority("ROLE_BF_EDIT_TREE_TEMPLATE","ROLE_BF_ADD_TREE_TEMPLATE","ROLE_BF_VIEW_TREE_TEMPLATES")
                        .requestMatchers(HttpMethod.POST,"/api/treeTemplate/**").hasAnyAuthority("ROLE_BF_EDIT_TREE_TEMPLATE","ROLE_BF_ADD_TREE_TEMPLATE","ROLE_BF_VIEW_TREE_TEMPLATES","ROLE_BF_LIST_TREE_TEMPLATE")
                        .requestMatchers(HttpMethod.PUT,"/api/treeTemplate/**").hasAnyAuthority("ROLE_BF_EDIT_TREE_TEMPLATE","ROLE_BF_ADD_TREE_TEMPLATE","ROLE_BF_VIEW_TREE_TEMPLATES")
                        .requestMatchers(HttpMethod.PUT,"/api/datasetData/**").hasAnyAuthority("ROLE_BF_COMMIT_DATASET")
                        .requestMatchers(HttpMethod.GET,"/api/datasetData/programId/**").hasAnyAuthority("ROLE_BF_VERSION_SETTINGS","ROLE_BF_LIST_TREE")
                        .requestMatchers(HttpMethod.POST,"/api/dataSource").hasAnyAuthority("ROLE_BF_ADD_DATA_SOURCE")
                        .requestMatchers(HttpMethod.PUT,"/api/dataSource").hasAnyAuthority("ROLE_BF_EDIT_DATA_SOURCE")
                        .requestMatchers(HttpMethod.GET,"/api/dataSource/all").hasAnyAuthority("ROLE_BF_TICKETING","ROLE_BF_LIST_DATA_SOURCE","ROLE_BF_PIPELINE_PROGRAM_IMPORT","ROLE_BF_SUPPLY_PLAN_VERSION_AND_REVIEW")
                        .requestMatchers(HttpMethod.POST,"/api/dataSourceType").hasAnyAuthority("ROLE_BF_ADD_DATA_SOURCE_TYPE")
                        .requestMatchers(HttpMethod.PUT,"/api/dataSourceType").hasAnyAuthority("ROLE_BF_EDIT_DATA_SOURCE_TYPE")
                        .requestMatchers(HttpMethod.GET,"/api/dataSourceType").hasAnyAuthority("ROLE_BF_LIST_DATA_SOURCE_TYPE","ROLE_BF_PIPELINE_PROGRAM_IMPORT","ROLE_BF_EDIT_DATA_SOURCE_TYPE")
                        .requestMatchers(HttpMethod.GET,"/api/dataSourceType/all").hasAnyAuthority("ROLE_BF_LIST_DATA_SOURCE_TYPE","ROLE_BF_LIST_DATA_SOURCE")
                        .requestMatchers(HttpMethod.GET,"/api/dataSourceType/realmId").hasAnyAuthority("ROLE_BF_ADD_DATA_SOURCE")
                        .requestMatchers("/api/equivalencyUnit**").hasAnyAuthority("ROLE_BF_LIST_EQUIVALENCY_UNIT_MAPPING","ROLE_BF_LIST_MONTHLY_FORECAST","ROLE_BF_FORECAST_ERROR_OVER_TIME_REPORT")
                        .requestMatchers(HttpMethod.POST,"/api/forecastingUnit/**").hasAnyAuthority("ROLE_BF_ADD_FORECASTING_UNIT")
                        .requestMatchers(HttpMethod.GET,"/api/forecastingUnit").hasAnyAuthority("ROLE_BF_TICKETING","ROLE_BF_ADD_PLANNING_UNIT")
                        .requestMatchers(HttpMethod.GET,"/api/forecastingUnit/all").hasAnyAuthority("ROLE_BF_LIST_FORECASTING_UNIT")
                        .requestMatchers(HttpMethod.PUT,"/api/forecastingUnit/**").hasAnyAuthority("ROLE_BF_EDIT_FORECASTING_UNIT")
                        .requestMatchers(HttpMethod.GET,"/api/forecastingUnit/**").hasAnyAuthority("ROLE_BF_ADD_PLANNING_UNIT")
                        .requestMatchers(HttpMethod.GET,"/api/forecastingUnit/realmId/**").hasAnyAuthority("ROLE_BF_LIST_FORECASTING_UNIT","ROLE_BF_LIST_PLANNING_UNIT")
                        .requestMatchers(HttpMethod.GET,"/api/forecastingUnit/tracerCategory/**").hasAnyAuthority("ROLE_BF_EDIT_TREE_TEMPLATE","ROLE_BF_ADD_TREE_TEMPLATE","ROLE_BF_VIEW_TREE_TEMPLATES")
                        .requestMatchers(HttpMethod.POST,"/api/forecastingUnit/tracerCategorys**").hasAnyAuthority("ROLE_BF_LIST_EQUIVALENCY_UNIT_MAPPING")
                        .requestMatchers(HttpMethod.GET,"/api/forecastingUnit/programId/**").hasAnyAuthority("ROLE_BF_LIST_MONTHLY_FORECAST")
                        .requestMatchers(HttpMethod.GET,"/api/forecastMethod/all").hasAnyAuthority("ROLE_BF_TICKETING","ROLE_BF_LIST_FORECAST_METHOD")
                        .requestMatchers(HttpMethod.GET,"/api/forecastMethod").hasAnyAuthority("ROLE_BF_EDIT_TREE_TEMPLATE","ROLE_BF_ADD_TREE_TEMPLATE","ROLE_BF_VIEW_TREE_TEMPLATES")
                        .requestMatchers(HttpMethod.POST,"/api/forecastMethod**").hasAnyAuthority("ROLE_BF_LIST_FORECAST_METHOD")
                        .requestMatchers(HttpMethod.GET,"/api/forecastMethodType").hasAnyAuthority("ROLE_BF_TICKETING","ROLE_BF_LIST_FORECAST_METHOD")
                        .requestMatchers(HttpMethod.POST,"/api/fundingSource/**").hasAnyAuthority("ROLE_BF_ADD_FUNDING_SOURCE")
                        .requestMatchers(HttpMethod.GET,"/api/fundingSource").hasAnyAuthority("ROLE_BF_ADD_BUDGET","ROLE_BF_EDIT_BUDGET","ROLE_BF_LIST_BUDGET","ROLE_BF_LIST_FUNDING_SOURCE","ROLE_BF_MANUAL_TAGGING","ROLE_BF_PIPELINE_PROGRAM_IMPORT","ROLE_BF_SUPPLY_PLAN_VERSION_AND_REVIEW","ROLE_BF_SHIPMENT_COST_DETAILS_REPORT","ROLE_BF_SHIPMENT_OVERVIEW_REPORT","ROLE_BF_GLOBAL_DEMAND_REPORT", "ROLE_BF_TICKETING")
                        .requestMatchers("/api/fundingSource/**").hasAnyAuthority("ROLE_BF_EDIT_FUNDING_SOURCE")
                        .requestMatchers(HttpMethod.GET,"/api/fundingSource/getDisplayName/realmId/**").hasAnyAuthority("ROLE_BF_ADD_FUNDING_SOURCE")
                        .requestMatchers(HttpMethod.POST,"/api/healthArea/**").hasAnyAuthority("ROLE_BF_ADD_HEALTH_AREA")
                        .requestMatchers(HttpMethod.PUT,"/api/healthArea/**").hasAnyAuthority("ROLE_BF_EDIT_HEALTH_AREA")
                        .requestMatchers(HttpMethod.GET,"/api/healthArea").hasAnyAuthority("ROLE_BF_TICKETING","ROLE_BF_LIST_HEALTH_AREA","ROLE_BF_EDIT_HEALTH_AREA")
                        .requestMatchers(HttpMethod.GET,"/api/healthArea/**").hasAnyAuthority("ROLE_BF_EDIT_HEALTH_AREA")
                        .requestMatchers(HttpMethod.GET,"/api/healthArea/getDisplayName/realmId/**").hasAnyAuthority("ROLE_BF_ADD_HEALTH_AREA")
                        .requestMatchers(HttpMethod.POST,"/api/integration/**").hasAnyAuthority("ROLE_BF_ADD_INTEGRATION")
                        .requestMatchers(HttpMethod.GET,"/api/integration").hasAnyAuthority("ROLE_BF_LIST_INTEGRATION","ROLE_BF_ADD_INTEGRATION")
                        .requestMatchers(HttpMethod.GET,"/api/integration/viewList").hasAnyAuthority("ROLE_BF_ADD_INTEGRATION","ROLE_BF_EDIT_INTEGRATION")
                        .requestMatchers("/api/integration/**").hasAnyAuthority("ROLE_BF_EDIT_INTEGRATION")
                        .requestMatchers(HttpMethod.PUT,"/api/integrationProgram/**").hasAnyAuthority("ROLE_BF_ADD_INTEGRATION_PROGRAM")
                        .requestMatchers(HttpMethod.GET,"/api/integrationProgram/program/**").hasAnyAuthority("ROLE_BF_ADD_INTEGRATION_PROGRAM")
                        .requestMatchers(HttpMethod.POST,"/api/integrationProgram/manualJson").hasAnyAuthority("ROLE_BF_MANUAL_INTEGRATION")
                        .requestMatchers(HttpMethod.POST,"/api/report/manualJson").hasAnyAuthority("ROLE_BF_MANUAL_INTEGRATION")
                        .requestMatchers(HttpMethod.GET,"/api/businessFunction/**").hasAnyAuthority("ROLE_BF_ADD_ROLE", "ROLE_BF_EDIT_ROLE")
                        .requestMatchers(HttpMethod.GET,"/api/role/**").hasAnyAuthority("ROLE_BF_ADD_ROLE","ROLE_BF_EDIT_ROLE","ROLE_BF_LIST_ROLE","ROLE_BF_ADD_USER","ROLE_BF_EDIT_USER","ROLE_BF_LIST_USER")
                        .requestMatchers(HttpMethod.POST,"/api/role/**").hasAnyAuthority("ROLE_BF_ADD_ROLE")
                        .requestMatchers(HttpMethod.PUT,"/api/role/**").hasAnyAuthority("ROLE_BF_EDIT_ROLE")
                        .requestMatchers(HttpMethod.POST,"/api/user/**").hasAnyAuthority("ROLE_BF_ADD_USER")
                        .requestMatchers(HttpMethod.PUT,"/api/user/**").hasAnyAuthority("ROLE_BF_LIST_USER","ROLE_BF_EDIT_USER")
                        .requestMatchers(HttpMethod.GET,"/api/user").hasAnyAuthority("ROLE_BF_LIST_USER")
                        .requestMatchers(HttpMethod.GET,"/api/user/**").hasAnyAuthority("ROLE_BF_MASTER_DATA_SYNC","ROLE_BF_LIST_USER","ROLE_BF_EDIT_USER")
                        .requestMatchers(HttpMethod.POST,"/api/userManual/uploadUserManual/**").hasAnyAuthority("ROLE_BF_UPLOAD_USER_MANUAL")
                        .requestMatchers(HttpMethod.GET,"/api/usageTemplate/all").hasAnyAuthority("ROLE_BF_LIST_USAGE_TEMPLATE")
                        .requestMatchers(HttpMethod.POST,"/api/usageTemplate/**").hasAnyAuthority("ROLE_BF_LIST_USAGE_TEMPLATE")
                        .requestMatchers(HttpMethod.GET,"/api/usageTemplate/tracerCategory/**").hasAnyAuthority("ROLE_BF_EDIT_TREE_TEMPLATE","ROLE_BF_ADD_TREE_TEMPLATE","ROLE_BF_VIEW_TREE_TEMPLATES")
                        .requestMatchers(HttpMethod.GET,"/api/usagePeriod/all").hasAnyAuthority("ROLE_BF_LIST_USAGE_PERIOD")
                        .requestMatchers(HttpMethod.GET,"/api/usagePeriod/**").hasAnyAuthority("ROLE_BF_EDIT_TREE_TEMPLATE","ROLE_BF_ADD_TREE_TEMPLATE","ROLE_BF_VIEW_TREE_TEMPLATES","ROLE_BF_LIST_USAGE_TEMPLATE","ROLE_BF_LIST_USAGE_PERIOD")
                        .requestMatchers(HttpMethod.POST,"/api/usagePeriod/**").hasAnyAuthority("ROLE_BF_LIST_USAGE_PERIOD")
                        .requestMatchers(HttpMethod.POST,"/api/unit/**").hasAnyAuthority("ROLE_BF_ADD_UNIT")
                        .requestMatchers(HttpMethod.PUT,"/api/unit/**").hasAnyAuthority("ROLE_BF_EDIT_UNIT")
                        .requestMatchers(HttpMethod.POST,"/api/unit/dimension/**").hasAnyAuthority("ROLE_BF_EDIT_TREE_TEMPLATE","ROLE_BF_ADD_TREE_TEMPLATE","ROLE_BF_VIEW_TREE_TEMPLATES","ROLE_BF_LIST_USAGE_TEMPLATE")
                        .requestMatchers(HttpMethod.GET,"/api/unit").hasAnyAuthority("ROLE_BF_TICKETING","ROLE_BF_EDIT_TREE_TEMPLATE","ROLE_BF_ADD_TREE_TEMPLATE","ROLE_BF_VIEW_TREE_TEMPLATES","ROLE_BF_LIST_EQUIVALENCY_UNIT_MAPPING","ROLE_BF_ADD_FORECASTING_UNIT","ROLE_BF_EDIT_FORECASTING_UNIT","ROLE_BF_ADD_PLANNING_UNIT","ROLE_BF_EDIT_PLANNING_UNIT","ROLE_BF_ADD_PROCUREMENT_UNIT","ROLE_BF_EDIT_PROCUREMENT_UNIT","ROLE_BF_MANAGE_REALM_COUNTRY_PLANNING_UNIT","ROLE_BF_LIST_USAGE_TEMPLATE")
                        .requestMatchers(HttpMethod.GET,"/api/tracerCategory").hasAnyAuthority("ROLE_BF_TICKETING", "ROLE_BF_LIST_EQUIVALENCY_UNIT_MAPPING", "ROLE_BF_LIST_PLANNING_UNIT_SETTING", "ROLE_BF_LIST_TRACER_CATEGORY", "ROLE_BF_LIST_USAGE_TEMPLATE", "ROLE_BF_SUPPLY_PLAN_IMPORT")
                        .requestMatchers(HttpMethod.POST,"/api/tracerCategory/**").hasAnyAuthority("ROLE_BF_ADD_TRACER_CATEGORY")
                        .requestMatchers(HttpMethod.PUT,"/api/tracerCategory/**").hasAnyAuthority("ROLE_BF_EDIT_TRACER_CATEGORY")
                        .requestMatchers(HttpMethod.GET,"/api/tracerCategory/realmId/**").hasAnyAuthority("ROLE_BF_LIST_PLANNING_UNIT","ROLE_BF_FORECAST_MATRIX_REPORT","ROLE_BF_PRODUCT_CATALOG_REPORT","ROLE_BF_STOCK_STATUS_GLOBAL_VIEW_REPORT","ROLE_BF_VIEW_STOCK_STATUS_MATRIX")
                        .requestMatchers(HttpMethod.GET,"/api/tracerCategory/**").hasAnyAuthority("ROLE_BF_EDIT_TREE_TEMPLATE","ROLE_BF_ADD_TREE_TEMPLATE","ROLE_BF_VIEW_TREE_TEMPLATES","ROLE_BF_LIST_EQUIVALENCY_UNIT_MAPPING","ROLE_BF_LIST_PLANNING_UNIT_SETTING","ROLE_BF_LIST_TRACER_CATEGORY","ROLE_BF_LIST_USAGE_TEMPLATE","ROLE_BF_EDIT_TRACER_CATEGORY")
                        .requestMatchers(HttpMethod.POST,"/api/supplier/**").hasAnyAuthority("ROLE_BF_ADD_SUPPLIER")
                        .requestMatchers(HttpMethod.GET,"/api/supplier/**").hasAnyAuthority("ROLE_BF_MAP_PLANNING_UNIT_CAPACITY","ROLE_BF_ADD_PROCUREMENT_UNIT","ROLE_BF_LIST_SUPPLIER","ROLE_BF_EDIT_SUPPLIER","ROLE_BF_PIPELINE_PROGRAM_IMPORT")
                        .requestMatchers(HttpMethod.PUT,"/api/supplier/**").hasAnyAuthority("ROLE_BF_EDIT_SUPPLIER")
                        .requestMatchers(HttpMethod.GET,"/api/getShipmentStatusListActive/**").hasAnyAuthority("ROLE_BF_ANNUAL_SHIPMENT_COST_REPORT","ROLE_BF_SUPPLY_PLAN_VERSION_AND_REVIEW","ROLE_BF_SHIPMENT_OVERVIEW_REPORT","ROLE_BF_PIPELINE_PROGRAM_IMPORT")
                        .requestMatchers(HttpMethod.POST,"/api/report/forecastMetricsMonthly/**").hasAnyAuthority("ROLE_BF_FORECAST_ERROR_OVER_TIME_REPORT")
                        .requestMatchers(HttpMethod.POST,"/api/report/globalConsumption/**").hasAnyAuthority("ROLE_BF_CONSUMPTION_GLOBAL_VIEW_REPORT")
                        .requestMatchers(HttpMethod.POST,"/api/report/forecastMetricsComparision/**").hasAnyAuthority("ROLE_BF_FORECAST_MATRIX_REPORT")
                        .requestMatchers(HttpMethod.POST,"/api/report/annualShipmentCost/**").hasAnyAuthority("ROLE_BF_ANNUAL_SHIPMENT_COST_REPORT")
                        .requestMatchers(HttpMethod.POST,"/api/report/stockStatusOverTime/**").hasAnyAuthority("ROLE_BF_STOCK_STATUS_OVER_TIME_REPORT")
                        .requestMatchers(HttpMethod.GET,"/api/programVersion/programId/**").hasAnyAuthority("ROLE_BF_SUPPLY_PLAN_VERSION_AND_REVIEW")
                        .requestMatchers(HttpMethod.POST,"/api/report/costOfInventory/**").hasAnyAuthority("ROLE_BF_COST_OF_INVENTORY_REPORT")
                        .requestMatchers(HttpMethod.POST,"/api/report/stockStatusVertical/**").hasAnyAuthority("ROLE_BF_SUPPLY_PLAN_REPORT")
                        .requestMatchers(HttpMethod.POST,"/api/report/inventoryTurns/**").hasAnyAuthority("ROLE_BF_INVENTORY_TURNS_REPORT")
                        .requestMatchers(HttpMethod.POST,"/api/report/stockAdjustmentReport/**").hasAnyAuthority("ROLE_BF_STOCK_ADJUSTMENT_REPORT")
                        .requestMatchers(HttpMethod.POST,"/api/report/procurementAgentShipmentReport/**").hasAnyAuthority("ROLE_BF_SHIPMENT_COST_DETAILS_REPORT")
                        .requestMatchers(HttpMethod.POST,"/api/report/fundingSourceShipmentReport/**").hasAnyAuthority("ROLE_BF_FUNDER_REPORT","ROLE_BF_SHIPMENT_COST_DETAILS_REPORT")
                        .requestMatchers(HttpMethod.POST,"/api/report/aggregateShipmentByProduct/**").hasAnyAuthority("ROLE_BF_SHIPMENT_COST_DETAILS_REPORT")
                        .requestMatchers(HttpMethod.POST,"/api/report/warehouseCapacityReport/**").hasAnyAuthority("ROLE_BF_WAREHOUSE_CAPACITY_REPORT")
                        .requestMatchers(HttpMethod.POST,"/api/report/stockStatusForProgram/**").hasAnyAuthority("ROLE_BF_STOCK_STATUS_REPORT","ROLE_BF_STOCK_STATUS_GLOBAL_VIEW_REPORT")
                        .requestMatchers(HttpMethod.POST,"/api/report/programProductCatalog/**").hasAnyAuthority("ROLE_BF_PRODUCT_CATALOG_REPORT")
                        .requestMatchers(HttpMethod.POST,"/api/report/shipmentGlobalDemand/**").hasAnyAuthority("ROLE_BF_GLOBAL_DEMAND_REPORT")
                        .requestMatchers(HttpMethod.POST,"/api/report/programLeadTimes/**").hasAnyAuthority("ROLE_BF_PROCUREMENT_AGENT_REPORT")
                        .requestMatchers(HttpMethod.POST,"/api/report/shipmentDetails/**").hasAnyAuthority("ROLE_BF_SHIPMENT_DETAILS_REPORT")
                        .requestMatchers(HttpMethod.POST,"/api/report/shipmentOverview/**").hasAnyAuthority("ROLE_BF_SHIPMENT_OVERVIEW_REPORT")
                        .requestMatchers(HttpMethod.POST,"/api/report/budgetReport/**").hasAnyAuthority("ROLE_BF_BUDGET_REPORT")
                        .requestMatchers(HttpMethod.POST,"/api/report/stockStatusAcrossProducts/**").hasAnyAuthority("ROLE_BF_STOCK_STATUS_GLOBAL_VIEW_REPORT")
                        .requestMatchers(HttpMethod.POST,"/api/report/expiredStock/**").hasAnyAuthority("ROLE_BF_EXPIRIES_REPORT")
                        .requestMatchers(HttpMethod.POST,"/api/report/warehouseByCountry/**").hasAnyAuthority("ROLE_BF_REGION")
                        .requestMatchers(HttpMethod.POST,"/api/report/monthlyForecast/**").hasAnyAuthority("ROLE_BF_SUPPLY_PLAN_IMPORT","ROLE_BF_LIST_MONTHLY_FORECAST")
                        .requestMatchers(HttpMethod.POST,"/api/report/forecastSummary/**").hasAnyAuthority("ROLE_BF_LIST_FORECAST_SUMMARY","ROLE_BF_VIEW_FORECAST_SUMMARY","ROLE_BF_COMPARE_VERSION")
                        .requestMatchers(HttpMethod.POST,"/api/report/forecastError/**").hasAnyAuthority("ROLE_BF_FORECAST_ERROR_OVER_TIME_REPORT")
                        .requestMatchers(HttpMethod.POST,"/api/report/forecastErrorNew/**").hasAnyAuthority("ROLE_BF_FORECAST_ERROR_OVER_TIME_REPORT")
                        .requestMatchers(HttpMethod.GET,"/api/report/updateProgramInfo/programTypeId/**").hasAnyAuthority("ROLE_BF_LIST_DATASET")
                        .requestMatchers(HttpMethod.GET,"/api/region/realmCountryId/**").hasAnyAuthority("ROLE_BF_MAP_REGION","ROLE_BF_ADD_DATASET","ROLE_BF_EDIT_DATASET","ROLE_BF_PIPELINE_PROGRAM_IMPORT","ROLE_BF_CREATE_A_PROGRAM","ROLE_BF_EDIT_PROGRAM","ROLE_BF_SET_UP_PROGRAM")
                        .requestMatchers(HttpMethod.POST,"/api/realm").hasAnyAuthority("ROLE_BF_CREATE_REALM")
                        .requestMatchers(HttpMethod.GET,"/api/realm").hasAnyAuthority("ROLE_BF_LOAD_DELETE_DATASET","ROLE_BF_ADD_DATA_SOURCE","ROLE_BF_LIST_DATA_SOURCE","ROLE_BF_ADD_DATA_SOURCE_TYPE","ROLE_BF_LIST_DATA_SOURCE_TYPE","ROLE_BF_ADD_FORECASTING_UNIT","ROLE_BF_LIST_FORECASTING_UNIT","ROLE_BF_LIST_FORECAST_METHOD","ROLE_BF_ADD_FUNDING_SOURCE","ROLE_BF_LIST_FUNDING_SOURCE","ROLE_BF_LIST_HEALTH_AREA","ROLE_BF_ADD_INTEGRATION_PROGRAM","ROLE_BF_LIST_ORGANIZATION","ROLE_BF_LIST_ORGANIZATION_TYPE","ROLE_BF_LIST_PLANNING_UNIT","ROLE_BF_ADD_PROCUREMENT_AGENT","ROLE_BF_LIST_PROCUREMENT_AGENT","ROLE_BF_LIST_PRODUCT_CATEGORY","ROLE_BF_LIST_REALM","ROLE_BF_LIST_REALM_COUNTRY","ROLE_BF_CONSUMPTION_REPORT","ROLE_BF_ADD_SUPPLIER","ROLE_BF_LIST_SUPPLIER","ROLE_BF_ADD_TRACER_CATEGORY","ROLE_BF_LIST_TRACER_CATEGORY","ROLE_BF_ADD_USER","ROLE_BF_EDIT_USER","ROLE_BF_LIST_USER","ROLE_BF_ADD_DATASET","ROLE_BF_CREATE_A_PROGRAM","ROLE_BF_SET_UP_PROGRAM","ROLE_BF_TICKETING")
                        .requestMatchers(HttpMethod.GET,"/api/realm/**").hasAnyAuthority("ROLE_BF_EDIT_REALM","ROLE_BF_MAP_REALM_COUNTRY")
                        .requestMatchers(HttpMethod.PUT,"/api/realm").hasAnyAuthority("ROLE_BF_EDIT_REALM","ROLE_BF_LIST_REALM")
                        .requestMatchers(HttpMethod.GET,"/api/realmCountry").hasAnyAuthority("ROLE_BF_TICKETING","ROLE_BF_LIST_REALM_COUNTRY")
                        .requestMatchers(HttpMethod.PUT,"/api/realmCountry/planningUnit").hasAnyAuthority("ROLE_BF_LIST_ALTERNATE_REPORTING_UNIT")
                        .requestMatchers(HttpMethod.POST,"/api/realmCountry/programIds/planningUnit").hasAnyAuthority("ROLE_BF_MANUAL_TAGGING","ROLE_BF_LIST_ALTERNATE_REPORTING_UNIT","ROLE_BF_SUPPLY_PLAN")
                        .requestMatchers(HttpMethod.GET,"/api/realmCountry/realmId/**").hasAnyAuthority("ROLE_BF_TICKETING","ROLE_BF_PIPELINE_PROGRAM_IMPORT","ROLE_BF_ADD_HEALTH_AREA","ROLE_BF_EDIT_HEALTH_AREA","ROLE_BF_ADD_DATASET","ROLE_BF_CREATE_A_PROGRAM","ROLE_BF_SET_UP_PROGRAM","ROLE_BF_ADD_ORGANIZATION","ROLE_BF_EDIT_ORGANIZATION","ROLE_BF_LIST_DATASET","ROLE_BF_LIST_PROGRAM","ROLE_BF_MAP_REALM_COUNTRY")
                        .requestMatchers(HttpMethod.GET,"/api/realmCountry/program/realmId/**").hasAnyAuthority("ROLE_BF_TICKETING","ROLE_BF_LOAD_DELETE_DATASET","ROLE_BF_MANUAL_TAGGING","ROLE_BF_REGION","ROLE_BF_FORECAST_MATRIX_REPORT","ROLE_BF_CONSUMPTION_GLOBAL_VIEW_REPORT","ROLE_BF_SHIPMENT_OVERVIEW_REPORT","ROLE_BF_GLOBAL_DEMAND_REPORT","ROLE_BF_STOCK_STATUS_GLOBAL_VIEW_REPORT","ROLE_BF_STOCK_STATUS_OVER_TIME_REPORT","ROLE_BF_SUPPLY_PLAN_VERSION_AND_REVIEW","ROLE_BF_WAREHOUSE_CAPACITY_REPORT","ROLE_BF_DOWNLOAD_PROGARM")
                        .requestMatchers(HttpMethod.POST,"/api/quantimed/quantimedImport/**").hasAnyAuthority("ROLE_BF_QUANTIMED_IMPORT")
                        .requestMatchers(HttpMethod.GET,"/api/programData/programId/**").hasAnyAuthority("ROLE_BF_COMMIT_VERSION","ROLE_BF_SUPPLY_PLAN_VERSION_AND_REVIEW")
                        .requestMatchers(HttpMethod.POST,"/api/programData").hasAnyAuthority("ROLE_BF_DOWNLOAD_PROGARM","ROLE_BF_COMMIT_VERSION")
                        .requestMatchers(HttpMethod.GET,"/api/program").hasAnyAuthority("ROLE_BF_ADD_PROCUREMENT_AGENT","ROLE_BF_EDIT_PROCUREMENT_AGENT","ROLE_BF_FORECAST_ERROR_OVER_TIME_REPORT","ROLE_BF_INVENTORY_TURNS_REPORT", "ROLE_BF_TICKETING")
                        .requestMatchers(HttpMethod.GET,"/api/dataset").hasAnyAuthority("ROLE_BF_COMPARE_VERSION","ROLE_BF_LIST_EQUIVALENCY_UNIT_MAPPING","ROLE_BF_LIST_USAGE_TEMPLATE","ROLE_BF_MODELING_VALIDATION","ROLE_BF_PRODUCT_VALIDATION")
                        .requestMatchers(HttpMethod.GET,"/api/loadProgram/programId/**").hasAnyAuthority("ROLE_BF_DOWNLOAD_PROGARM")
                        .requestMatchers(HttpMethod.POST,"/api/program/**").hasAnyAuthority("ROLE_BF_CREATE_A_PROGRAM","ROLE_BF_ADD_PROGRAM_PRODUCT","ROLE_BF_MAP_PROCUREMENT_AGENT")
                        .requestMatchers(HttpMethod.PUT,"/api/program").hasAnyAuthority("ROLE_BF_EDIT_PROGRAM")
                        .requestMatchers(HttpMethod.GET,"/api/organisation/realmId/**").hasAnyAuthority("ROLE_BF_TICKETING","ROLE_BF_PIPELINE_PROGRAM_IMPORT","ROLE_BF_CREATE_A_PROGRAM")
                        .requestMatchers(HttpMethod.GET,"/api/healthArea/realmId/**").hasAnyAuthority("ROLE_BF_TICKETING","ROLE_BF_PIPELINE_PROGRAM_IMPORT","ROLE_BF_CREATE_A_PROGRAM","ROLE_BF_EDIT_PROGRAM","ROLE_BF_ADD_DATASET","ROLE_BF_EDIT_DATASET")
                        .requestMatchers(HttpMethod.GET,"/api/region/realmCountryId/**").hasAnyAuthority("ROLE_BF_ADD_DATASET","ROLE_BF_EDIT_DATASET","ROLE_BF_PIPELINE_PROGRAM_IMPORT","ROLE_BF_CREATE_A_PROGRAM","ROLE_BF_EDIT_PROGRAM","ROLE_BF_SET_UP_PROGRAM")
                        .requestMatchers(HttpMethod.GET,"/api/program/**").hasAnyAuthority("ROLE_BF_MANUAL_TAGGING","ROLE_BF_MAP_COUNTRY_SPECIFIC_PRICES","ROLE_BF_ADD_PROGRAM_PRODUCT","ROLE_BF_ADD_INTEGRATION_PROGRAM","ROLE_BF_EDIT_PROGRAM","ROLE_BF_FORECAST_ERROR_OVER_TIME_REPORT","ROLE_BF_SUPPLY_PLAN_VERSION_AND_REVIEW","ROLE_BF_EXPIRIES_REPORT","ROLE_BF_SUPPLY_PLAN_REPORT","ROLE_BF_MAP_PROCUREMENT_AGENT")
                        .requestMatchers(HttpMethod.GET,"/api/loadProgram").hasAnyAuthority("ROLE_BF_DOWNLOAD_PROGARM")
                        .requestMatchers(HttpMethod.PUT,"/api/program/planningUnit/**").hasAnyAuthority("ROLE_BF_ADD_PROGRAM_PRODUCT")
                        .requestMatchers(HttpMethod.GET,"/api/user/realmId/**").hasAnyAuthority("ROLE_BF_ADD_DATASET","ROLE_BF_EDIT_DATASET","ROLE_BF_CREATE_A_PROGRAM","ROLE_BF_EDIT_PROGRAM","ROLE_BF_SET_UP_PROGRAM","ROLE_BF_PIPELINE_PROGRAM_IMPORT")
                        .requestMatchers(HttpMethod.PUT,"/api/programData/**").hasAnyAuthority("ROLE_BF_COMMIT_VERSION")
                        .requestMatchers(HttpMethod.POST,"/api/program/initialize/**").hasAnyAuthority("ROLE_BF_SET_UP_PROGRAM")
                        .requestMatchers(HttpMethod.GET,"/api/versionStatus/**").hasAnyAuthority("ROLE_BF_ADD_INTEGRATION_PROGRAM","ROLE_BF_SUPPLY_PLAN_VERSION_AND_REVIEW")
                        .requestMatchers(HttpMethod.PUT,"/api/programVersion/programId/**").hasAnyAuthority("ROLE_BF_SUPPLY_PLAN_VERSION_AND_REVIEW")
                        .requestMatchers(HttpMethod.GET,"/api/versionType/**").hasAnyAuthority("ROLE_BF_SUPPLY_PLAN_VERSION_AND_REVIEW","ROLE_BF_COMMIT_VERSION","ROLE_BF_ADD_INTEGRATION_PROGRAM","ROLE_BF_VERSION_SETTINGS")
                        .requestMatchers(HttpMethod.POST,"/api/programData/checkNewerVersions").hasAnyAuthority("ROLE_BF_MASTER_DATA_SYNC", "ROLE_BF_IMPORT_DATASET","ROLE_BF_LOAD_DELETE_DATASET","ROLE_BF_IMPORT_PROGARM","ROLE_BF_DOWNLOAD_PROGARM")
                        .requestMatchers(HttpMethod.GET,"/api/healthArea/realmCountryId/**").hasAnyAuthority("ROLE_BF_ADD_DATASET","ROLE_BF_EDIT_DATASET","ROLE_BF_EDIT_PROGRAM","ROLE_BF_SET_UP_PROGRAM")
                        .requestMatchers(HttpMethod.GET,"/api/problemStatus/**").hasAnyAuthority("ROLE_BF_SUPPLY_PLAN_VERSION_AND_REVIEW")
                        .requestMatchers(HttpMethod.GET,"/api/programData/getLatestVersionForProgram/**").hasAnyAuthority("ROLE_BF_COMMIT_DATASET","ROLE_BF_COMMIT_VERSION")
                        .requestMatchers(HttpMethod.GET,"/api/programData/getLastModifiedDateForProgram/**").hasAnyAuthority("ROLE_BF_COMMIT_VERSION")
                        .requestMatchers(HttpMethod.POST,"/api/dataset/**").hasAnyAuthority("ROLE_BF_ADD_DATASET")
                        .requestMatchers(HttpMethod.GET,"/api/dataset/**").hasAnyAuthority("ROLE_BF_EDIT_DATASET")
                        .requestMatchers(HttpMethod.PUT,"/api/dataset/**").hasAnyAuthority("ROLE_BF_EDIT_DATASET")
                        .requestMatchers(HttpMethod.POST,"/api/program/actualConsumptionReport/**").hasAnyAuthority("ROLE_BF_LIST_IMPORT_FROM_QAT_SUPPLY_PLAN")
                        .requestMatchers(HttpMethod.GET,"/api/programData/checkIfCommitRequestExistsForProgram//**").hasAnyAuthority("ROLE_BF_COMMIT_VERSION")
                        .requestMatchers(HttpMethod.GET,"/api/sendNotification/**").hasAnyAuthority("ROLE_BF_COMMIT_VERSION","ROLE_BF_COMMIT_DATASET")
                        .requestMatchers(HttpMethod.POST,"/api/erpLinking/otherProgramCheck/**").hasAnyAuthority("ROLE_BF_COMMIT_VERSION")
                        .requestMatchers(HttpMethod.GET,"/api/user/programId/**").hasAnyAuthority("ROLE_BF_EDIT_DATASET","ROLE_BF_EDIT_PROGRAM")
                        .requestMatchers(HttpMethod.POST,"/api/problemReport/createManualProblem/**").hasAnyAuthority("ROLE_BF_SUPPLY_PLAN_VERSION_AND_REVIEW")
                        .requestMatchers(HttpMethod.POST,"/api/dataset/versions/**").hasAnyAuthority("ROLE_BF_VERSION_SETTINGS")
                        .requestMatchers(HttpMethod.POST,"/api/program/realmCountryList").hasAnyAuthority("ROLE_BF_INVENTORY_TURNS_REPORT")
                        .requestMatchers(HttpMethod.POST,"/api/program/productCategoryList").hasAnyAuthority("ROLE_BF_INVENTORY_TURNS_REPORT")
                        .requestMatchers(HttpMethod.POST,"/api/report/consumptionForecastVsActual/**").hasAnyAuthority("ROLE_BF_CONSUMPTION_REPORT")
                        .requestMatchers(HttpMethod.POST,"/api/report/stockStatusMatrix/**").hasAnyAuthority("ROLE_BF_STOCK_STATUS_MATRIX_REPORT")
                        .requestMatchers(HttpMethod.GET,"/api/procurementUnit/all").hasAnyAuthority("ROLE_BF_MAP_PROCUREMENT_UNIT","ROLE_BF_LIST_PROCUREMENT_UNIT")
                        .requestMatchers("/api/procurementUnit/**").hasAnyAuthority("ROLE_BF_EDIT_PROCUREMENT_UNIT")
                        .requestMatchers(HttpMethod.POST,"/api/procurementUnit/**").hasAnyAuthority("ROLE_BF_ADD_PROCUREMENT_UNIT")
                        .requestMatchers(HttpMethod.GET,"/api/procurementAgent").hasAnyAuthority("ROLE_BF_TICKETING","ROLE_BF_LIST_PROCUREMENT_AGENT","ROLE_BF_MAP_PLANNING_UNIT","ROLE_BF_MAP_PROCUREMENT_UNIT","ROLE_BF_MAP_COUNTRY_SPECIFIC_PRICES","ROLE_BF_PIPELINE_PROGRAM_IMPORT","ROLE_BF_MASTER_DATA_SYNC")
                        .requestMatchers(HttpMethod.POST,"/api/procurementAgent/**").hasAnyAuthority("ROLE_BF_ADD_PROCUREMENT_AGENT","ROLE_BF_MAP_PLANNING_UNIT","ROLE_BF_MAP_PROCUREMENT_UNIT")
                        .requestMatchers(HttpMethod.PUT,"/api/procurementAgent/**").hasAnyAuthority("ROLE_BF_EDIT_PROCUREMENT_AGENT","ROLE_BF_MAP_PROCUREMENT_AGENT")
                        .requestMatchers(HttpMethod.GET,"/api/procurementAgent/**").hasAnyAuthority("ROLE_BF_LIST_PROCUREMENT_AGENT","ROLE_BF_MAP_PLANNING_UNIT","ROLE_BF_MAP_PROCUREMENT_UNIT","ROLE_BF_MAP_COUNTRY_SPECIFIC_PRICES","ROLE_BF_EDIT_PROCUREMENT_AGENT")
                        .requestMatchers(HttpMethod.GET,"/api/program/planningUnit/procurementAgent/**").hasAnyAuthority("ROLE_BF_MAP_COUNTRY_SPECIFIC_PRICES")
                        .requestMatchers(HttpMethod.PUT,"/api/procurementAgent/planningUnit/**").hasAnyAuthority("ROLE_BF_MAP_PLANNING_UNIT")
                        .requestMatchers(HttpMethod.PUT,"/api/program/planningingUnit/procurementAgent/**").hasAnyAuthority("ROLE_BF_MAP_COUNTRY_SPECIFIC_PRICES")
                        .requestMatchers(HttpMethod.PUT,"/api/procurementAgent/procurementUnit/**").hasAnyAuthority("ROLE_BF_MAP_PROCUREMENT_UNIT")
                        .requestMatchers("/api/procurementAgent/getDisplayName/realmId/**").hasAnyAuthority("ROLE_BF_ADD_PROCUREMENT_AGENT")
                        .requestMatchers(HttpMethod.POST,"/api/procurementAgentType/**").hasAnyAuthority("ROLE_BF_ADD_PROCUREMENT_AGENT")
                        .requestMatchers(HttpMethod.GET,"/api/procurementAgentType").hasAnyAuthority("ROLE_BF_TICKETING","ROLE_BF_ADD_PROCUREMENT_AGENT","ROLE_BF_EDIT_PROCUREMENT_AGENT","ROLE_BF_LIST_PROCUREMENT_AGENT","ROLE_BF_GLOBAL_DEMAND_REPORT")
                        .requestMatchers(HttpMethod.GET,"/api/procurementAgentType/**").hasAnyAuthority("ROLE_BF_EDIT_PROCUREMENT_AGENT")
                        .requestMatchers(HttpMethod.PUT,"/api/procurementAgentType").hasAnyAuthority("ROLE_BF_EDIT_PROCUREMENT_AGENT")
                        .requestMatchers(HttpMethod.GET,"/api/productCategory/realmId/**").hasAnyAuthority("ROLE_BF_TICKETING","ROLE_BF_ADD_FORECASTING_UNIT","ROLE_BF_LIST_FORECASTING_UNIT","ROLE_BF_MANUAL_TAGGING","ROLE_BF_LIST_PLANNING_UNIT","ROLE_BF_PRODUCT_CATALOG_REPORT","ROLE_BF_GLOBAL_DEMAND_REPORT","ROLE_BF_LIST_PRODUCT_CATEGORY","ROLE_BF_ADD_PROGRAM_PRODUCT","ROLE_BF_INVENTORY_TURNS_REPORT")
                        .requestMatchers(HttpMethod.POST,"/api/report/consumptionForecastVsActual/**").hasAnyAuthority("ROLE_BF_CONSUMPTION_REPORT")
                        .requestMatchers(HttpMethod.POST,"/api/report/stockStatusMatrix/**").hasAnyAuthority("ROLE_BF_STOCK_STATUS_MATRIX_REPORT")
                        .requestMatchers(HttpMethod.PUT,"/api/productCategory/**").hasAnyAuthority("ROLE_BF_LIST_PRODUCT_CATEGORY")
                        .requestMatchers(HttpMethod.POST,"/api/planningUnit/**").hasAnyAuthority("ROLE_BF_ADD_PLANNING_UNIT")
                        .requestMatchers(HttpMethod.GET,"/api/planningUnit/**").hasAnyAuthority("ROLE_BF_EDIT_TREE_TEMPLATE","ROLE_BF_ADD_TREE_TEMPLATE","ROLE_BF_VIEW_TREE_TEMPLATES","ROLE_BF_PIPELINE_PROGRAM_IMPORT","ROLE_BF_SET_UP_PROGRAM","ROLE_BF_EDIT_PLANNING_UNIT","ROLE_BF_LIST_PLANNING_UNIT_SETTING","ROLE_BF_CONSUMPTION_REPORT")
                        .requestMatchers(HttpMethod.PUT,"/api/planningUnit/**").hasAnyAuthority("ROLE_BF_EDIT_PLANNING_UNIT","ROLE_BF_MAP_PLANNING_UNIT_CAPACITY")
                        .requestMatchers(HttpMethod.GET,"/api/planningUnit/all").hasAnyAuthority("ROLE_BF_TICKETING","ROLE_BF_LIST_PLANNING_UNIT_CAPACITY","ROLE_BF_MAP_PLANNING_UNIT","ROLE_BF_ADD_PROGRAM_PRODUCT","ROLE_BF_LIST_ALTERNATE_REPORTING_UNIT","ROLE_BF_PIPELINE_PROGRAM_IMPORT","ROLE_BF_ADD_PROCUREMENT_AGENT")
                        .requestMatchers(HttpMethod.GET,"/api/planningUnit/realmId/**").hasAnyAuthority("ROLE_BF_LIST_PLANNING_UNIT_SETTING","ROLE_BF_LIST_PLANNING_UNIT")
                        .requestMatchers(HttpMethod.POST,"/api/planningUnit/programs/**").hasAnyAuthority("ROLE_BF_LIST_ALTERNATE_REPORTING_UNIT","ROLE_BF_FORECAST_MATRIX_REPORT","ROLE_BF_CONSUMPTION_GLOBAL_VIEW_REPORT","ROLE_BF_SHIPMENT_OVERVIEW_REPORT")
                        .requestMatchers(HttpMethod.GET,"/api/planningUnit/productCategory/**").hasAnyAuthority("ROLE_BF_MANUAL_TAGGING","ROLE_BF_SHIPMENT_OVERVIEW_REPORT","ROLE_BF_GLOBAL_DEMAND_REPORT")
                        .requestMatchers(HttpMethod.POST,"/api/planningUnit/productCategoryList/active/**").hasAnyAuthority("ROLE_BF_MANUAL_TAGGING")
                        .requestMatchers(HttpMethod.GET,"/api/planningUnit/realmCountry/**").hasAnyAuthority("ROLE_BF_MANUAL_TAGGING")
                        .requestMatchers(HttpMethod.GET,"/api/planningUnit/forecastingUnit/**").hasAnyAuthority("ROLE_BF_EDIT_TREE_TEMPLATE","ROLE_BF_ADD_TREE_TEMPLATE","ROLE_BF_VIEW_TREE_TEMPLATES")
                        .requestMatchers(HttpMethod.GET,"/api/planningUnit/programId/**").hasAnyAuthority("ROLE_BF_LIST_MONTHLY_FORECAST","ROLE_BF_LIST_IMPORT_FROM_QAT_SUPPLY_PLAN","ROLE_BF_SUPPLY_PLAN_IMPORT")
                        .requestMatchers(HttpMethod.GET,"/api/planningUnit/basic/**").hasAnyAuthority("ROLE_BF_ADD_PROGRAM_PRODUCT")
                        .requestMatchers(HttpMethod.POST,"/api/planningUnit/byIds").hasAnyAuthority("ROLE_BF_LIST_PLANNING_UNIT_SETTING")
                        .requestMatchers(HttpMethod.POST,"/api/planningUnit/withPrices/byIds").hasAnyAuthority("ROLE_BF_ADD_TREE","ROLE_BF_VIEW_TREE","ROLE_BF_EDIT_TREE","ROLE_BF_EDIT_TREE_TEMPLATE","ROLE_BF_ADD_TREE_TEMPLATE","ROLE_BF_VIEW_TREE_TEMPLATES","ROLE_BF_LIST_TREE","ROLE_BF_LIST_TREE_TEMPLATE","ROLE_BF_LIST_PLANNING_UNIT_SETTING")
                        .requestMatchers(HttpMethod.GET,"/api/planningUnit/capacity/all").hasAnyAuthority("ROLE_BF_LIST_PLANNING_UNIT_CAPACITY")
                        .requestMatchers(HttpMethod.POST,"/api/pipelineJson/**").hasAnyAuthority("ROLE_BF_PIPELINE_PROGRAM_IMPORT")
                        .requestMatchers(HttpMethod.GET,"/api/pipeline/**").hasAnyAuthority("ROLE_BF_PIPELINE_PROGRAM_IMPORT")
                        .requestMatchers(HttpMethod.GET,"/api/pipeline/programInfo/**").hasAnyAuthority("ROLE_BF_PIPELINE_PROGRAM_IMPORT")
                        .requestMatchers(HttpMethod.POST,"/api/qatTemp/program/**").hasAnyAuthority("ROLE_BF_PIPELINE_PROGRAM_IMPORT")
                        .requestMatchers(HttpMethod.GET,"/api/qatTemp/program/**").hasAnyAuthority("ROLE_BF_PIPELINE_PROGRAM_IMPORT")
                        .requestMatchers(HttpMethod.GET,"/api/pipeline/shipment/**").hasAnyAuthority("ROLE_BF_PIPELINE_PROGRAM_IMPORT")
                        .requestMatchers(HttpMethod.PUT,"/api/pipeline/planningUnit/**").hasAnyAuthority("ROLE_BF_PIPELINE_PROGRAM_IMPORT")
                        .requestMatchers(HttpMethod.GET,"/api/qatTemp/planningUnitList/**").hasAnyAuthority("ROLE_BF_PIPELINE_PROGRAM_IMPORT")
                        .requestMatchers(HttpMethod.GET,"/api/qatTemp/regions/**").hasAnyAuthority("ROLE_BF_PIPELINE_PROGRAM_IMPORT")
                        .requestMatchers("/api/pipeline/consumption/**").hasAnyAuthority("ROLE_BF_PIPELINE_PROGRAM_IMPORT")
                        .requestMatchers(HttpMethod.GET,"/api/qatTemp/consumption/**").hasAnyAuthority("ROLE_BF_PIPELINE_PROGRAM_IMPORT")
                        .requestMatchers(HttpMethod.POST,"/api/pipeline/shipment/**").hasAnyAuthority("ROLE_BF_PIPELINE_PROGRAM_IMPORT")
                        .requestMatchers("/api/pipeline/inventory/**").hasAnyAuthority("ROLE_BF_PIPELINE_PROGRAM_IMPORT")
                        .requestMatchers(HttpMethod.GET,"/api/qatTemp/planningUnitListFinalInventry/**").hasAnyAuthority("ROLE_BF_PIPELINE_PROGRAM_IMPORT")
                        .requestMatchers(HttpMethod.POST,"/api/pipeline/programdata/**").hasAnyAuthority("ROLE_BF_PIPELINE_PROGRAM_IMPORT")
                        .requestMatchers(HttpMethod.GET,"/api/qatTemp/datasource/**").hasAnyAuthority("ROLE_BF_PIPELINE_PROGRAM_IMPORT")
                        .requestMatchers(HttpMethod.PUT,"/api/pipeline/datasource/**").hasAnyAuthority("ROLE_BF_PIPELINE_PROGRAM_IMPORT")
                        .requestMatchers(HttpMethod.GET,"/api/qatTemp/fundingsource/**").hasAnyAuthority("ROLE_BF_PIPELINE_PROGRAM_IMPORT")
                        .requestMatchers(HttpMethod.PUT,"/api/pipeline/fundingsource/**").hasAnyAuthority("ROLE_BF_PIPELINE_PROGRAM_IMPORT")
                        .requestMatchers(HttpMethod.GET,"/api/qatTemp/procurementagent/**").hasAnyAuthority("ROLE_BF_PIPELINE_PROGRAM_IMPORT")
                        .requestMatchers(HttpMethod.PUT,"/api/pipeline/procurementagent/**").hasAnyAuthority("ROLE_BF_PIPELINE_PROGRAM_IMPORT")
                        .requestMatchers(HttpMethod.PUT,"/api/pipeline/realmCountryPlanningUnit/**").hasAnyAuthority("ROLE_BF_PIPELINE_PROGRAM_IMPORT")
                        .requestMatchers(HttpMethod.GET,"/api/organisationType/all").hasAnyAuthority("ROLE_BF_TICKETING","ROLE_BF_LIST_ORGANIZATION_TYPE")
                        .requestMatchers(HttpMethod.POST,"/api/organisationType/**").hasAnyAuthority("ROLE_BF_ADD_ORGANIZATION_TYPE")
                        .requestMatchers("/api/organisationType/**").hasAnyAuthority("ROLE_BF_EDIT_ORGANIZATION_TYPE")
                        .requestMatchers(HttpMethod.GET,"/api/organisationType/realmId/**").hasAnyAuthority("ROLE_BF_TICKETING","ROLE_BF_ADD_ORGANIZATION_TYPE","ROLE_BF_EDIT_ORGANIZATION_TYPE")
                        .requestMatchers(HttpMethod.POST,"/api/organisation/**").hasAnyAuthority("ROLE_BF_ADD_ORGANIZATION")
                        .requestMatchers(HttpMethod.GET,"/api/organisation").hasAnyAuthority("ROLE_BF_TICKETING","ROLE_BF_LIST_ORGANIZATION")
                        .requestMatchers(HttpMethod.PUT,"/api/organisation/**").hasAnyAuthority("ROLE_BF_EDIT_ORGANIZATION")
                        .requestMatchers(HttpMethod.GET,"/api/organisation/**").hasAnyAuthority("ROLE_BF_LIST_ORGANIZATION","ROLE_BF_EDIT_ORGANIZATION")
                        .requestMatchers(HttpMethod.GET,"/api/realmCountry/realmId/**").hasAnyAuthority("ROLE_BF_TICKETING","ROLE_BF_MAP_REALM_COUNTRY","ROLE_BF_CREATE_A_PROGRAM","ROLE_BF_PIPELINE_PROGRAM_IMPORT","ROLE_BF_EDIT_HEALTH_AREA","ROLE_BF_ADD_ORGANIZATION","ROLE_BF_EDIT_ORGANIZATION")
                        .requestMatchers(HttpMethod.GET,"/api/organisation/getDisplayName/realmId/**").hasAnyAuthority("ROLE_BF_ADD_ORGANIZATION")
                        .requestMatchers(HttpMethod.GET,"/api/modelingType/all").hasAnyAuthority("ROLE_BF_TICKETING","ROLE_BF_EDIT_TREE_TEMPLATE","ROLE_BF_ADD_TREE_TEMPLATE","ROLE_BF_VIEW_TREE_TEMPLATES","ROLE_BF_LIST_MODELING_TYPE")
                        .requestMatchers(HttpMethod.GET,"/api/modelingType/**").hasAnyAuthority("ROLE_BF_EDIT_TREE_TEMPLATE","ROLE_BF_ADD_TREE_TEMPLATE","ROLE_BF_VIEW_TREE_TEMPLATES")
                        .requestMatchers(HttpMethod.POST,"/api/manualTagging/**").hasAnyAuthority("ROLE_BF_MANUAL_TAGGING","ROLE_BF_DELINKING")
                        .requestMatchers(HttpMethod.GET,"/api/erpLinking/shipmentLinkingNotification/programId/**").hasAnyAuthority("ROLE_BF_MANUAL_TAGGING")
                        .requestMatchers(HttpMethod.PUT,"/api/erpLinking/updateNotification/**").hasAnyAuthority("ROLE_BF_MANUAL_TAGGING")
                        .requestMatchers(HttpMethod.GET,"/api/manualTagging/notLinkedShipments/**").hasAnyAuthority("ROLE_BF_MANUAL_TAGGING","ROLE_BF_DELINKING")
                        .requestMatchers(HttpMethod.GET,"/api/erpLinking/artmisHistory/**").hasAnyAuthority("ROLE_BF_MANUAL_TAGGING","ROLE_BF_DELINKING")
                        .requestMatchers(HttpMethod.GET,"/api/erpLinking/getNotificationSummary/**").hasAnyAuthority("ROLE_BF_MANUAL_TAGGING")
                        .requestMatchers(HttpMethod.POST,"/api/erpLinking/notLinkedQatShipments/programId/**").hasAnyAuthority("ROLE_BF_MANUAL_TAGGING")
                        .requestMatchers(HttpMethod.GET,"/api/erpLinking/autoCompleteOrder/**").hasAnyAuthority("ROLE_BF_MANUAL_TAGGING")
                        .requestMatchers(HttpMethod.POST,"/api/erpLinking/autoCompletePu/**").hasAnyAuthority("ROLE_BF_MANUAL_TAGGING")
                        .requestMatchers(HttpMethod.POST,"/api/erpLinking/notLinkedErpShipments/**").hasAnyAuthority("ROLE_BF_MANUAL_TAGGING")
                        .requestMatchers(HttpMethod.POST,"/api/erpLinking/notLinkedErpShipments/tab3/**").hasAnyAuthority("ROLE_BF_MANUAL_TAGGING")
                        .requestMatchers(HttpMethod.POST,"/api/erpLinking/linkedShipments/programId/**").hasAnyAuthority("ROLE_BF_MANUAL_TAGGING")
                        .requestMatchers(HttpMethod.POST,"/api/erpLinking/batchDetails/**").hasAnyAuthority("ROLE_BF_MANUAL_TAGGING")
                        .requestMatchers(HttpMethod.POST,"/api/language/**").hasAnyAuthority("ROLE_BF_ADD_LANGUAGE")
                        .requestMatchers(HttpMethod.GET,"/api/language").hasAnyAuthority("ROLE_BF_ADD_COUNTRY","ROLE_BF_EDIT_COUNTRY","ROLE_BF_LABEL_TRANSLATIONS","ROLE_BF_ADD_USER","ROLE_BF_EDIT_USER","ROLE_BF_LIST_USER","ROLE_BF_EDIT_LANGUAGE")
                        .requestMatchers(HttpMethod.GET,"/api/language/**").hasAnyAuthority("ROLE_BF_EDIT_LANGUAGE")
                        .requestMatchers(HttpMethod.PUT,"/api/language").hasAnyAuthority("ROLE_BF_EDIT_LANGUAGE")
                        .requestMatchers(HttpMethod.GET,"/api/language/all").hasAnyAuthority("ROLE_BF_ADD_COUNTRY","ROLE_BF_EDIT_COUNTRY","ROLE_BF_LIST_LANGUAGE","ROLE_BF_LABEL_TRANSLATIONS","ROLE_BF_ADD_USER","ROLE_BF_EDIT_USER","ROLE_BF_LIST_USER")
                        .requestMatchers("/api/getDatabaseLabelsListAll").hasAnyAuthority("ROLE_BUSINESS_FUNCTION_EDIT_APPLICATION_LABELS","ROLE_BUSINESS_FUNCTION_EDIT_REALM_LABELS","ROLE_BUSINESS_FUNCTION_EDIT_PROGRAM_LABELS")
                        .requestMatchers("/api/getStaticLabelsListAll").hasAnyAuthority("ROLE_BF_LABEL_TRANSLATIONS")
                        .requestMatchers("/api/saveDatabaseLabels").hasAnyAuthority("ROLE_BUSINESS_FUNCTION_EDIT_APPLICATION_LABELS","ROLE_BUSINESS_FUNCTION_EDIT_REALM_LABELS","ROLE_BUSINESS_FUNCTION_EDIT_PROGRAM_LABELS")
                        .requestMatchers("/api/saveStaticLabels").hasAnyAuthority("ROLE_BF_LABEL_TRANSLATIONS")
                        .requestMatchers("/api/programData/shipmentSync/programId/**").hasAnyAuthority("ROLE_BF_MASTER_DATA_SYNC")
                        .requestMatchers("/api/sync/allMasters/forPrograms/**").hasAnyAuthority("ROLE_BF_MASTER_DATA_SYNC")
                        .requestMatchers("/api/erpLinking/shipmentSync").hasAnyAuthority("ROLE_BF_MASTER_DATA_SYNC")
                        .requestMatchers("/api/getCommitRequest/**").hasAnyAuthority("ROLE_BF_MASTER_DATA_SYNC")
                        .requestMatchers(HttpMethod.GET,"/api/erpLinking/getNotificationCount").hasAnyAuthority("ROLE_BF_MASTER_DATA_SYNC")
                        .requestMatchers(HttpMethod.POST,"/api/programData/getLatestVersionForPrograms").hasAnyAuthority("ROLE_BF_MASTER_DATA_SYNC")
                        .requestMatchers(HttpMethod.POST,"/api/dimension").hasAnyAuthority("ROLE_BF_ADD_DIMENSION")
                        .requestMatchers(HttpMethod.GET,"/api/dimension/all").hasAnyAuthority("ROLE_BF_LIST_DIMENSION", "ROLE_BF_ADD_UNIT", "ROLE_BF_LIST_UNIT")
                        .requestMatchers(HttpMethod.PUT,"/api/dimension").hasAnyAuthority("ROLE_BF_EDIT_DIMENSION")
                        .requestMatchers(HttpMethod.GET,"/api/dimension/**").hasAnyAuthority("ROLE_BF_EDIT_DIMENSION")
                        .requestMatchers(HttpMethod.GET,"/api/realmCountry/**").hasAnyAuthority("ROLE_BF_MAP_REGION","ROLE_BF_PIPELINE_PROGRAM_IMPORT")
                        .anyRequest().authenticated()
                );

        http.authenticationProvider(authenticationProvider());

        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

//    @Override
//    protected void configure(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity
//                .csrf().disable()
//                .exceptionHandling().authenticationEntryPoint(jwtUnAuthorizedResponseAuthenticationEntryPoint).and()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//                .authorizeRequests()
//                //                .antMatchers("/actuator/info").permitAll()
//                //                .antMatchers("/api/healthArea/**").access("hasRole('ROLE_BF_UPDATE_REALM_MASTER')")
//                //                .antMatchers("/api/organisation/**").access("hasRole('ROLE_BF_UPDATE_REALM_MASTER')")
//                //                .antMatchers("/api/unit/**").access("hasRole('ROLE_BF_UPDATE_APPL_MASTER')")
//                //                .antMatchers(HttpMethod.POST, "/api/realm/**").access("hasAnyAuthority('ROLE_BF_UPDATE_APPL_MASTER')")
//                //                .antMatchers(HttpMethod.PUT, "/api/realm/**").access("hasAnyAuthority('ROLE_BF_UPDATE_APPL_MASTER', 'ROLE_BF_UPDATE_REALM_MASTER')")
//                //                .antMatchers("/api/realmCountry/**").access("hasRole('ROLE_BF_UPDATE_REALM_MASTER')")
//                .anyRequest().authenticated();
//
////    @EventListener
////    public void authSuccessEventListener(AuthenticationSuccessEvent authorizedEvent){
////        authorizedEvent.
////    }
////
////    @EventListener
////    public void authFailedEventListener(AbstractAuthenticationFailureEvent oAuth2AuthenticationFailureEvent){
////        
////    }
//    }
}
