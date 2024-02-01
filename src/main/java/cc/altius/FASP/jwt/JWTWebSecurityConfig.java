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
                        .requestMatchers(HttpMethod.POST,"/api/budget/").hasAnyRole("ROLE_BF_ADD_BUDGET")
                        .requestMatchers("/api/budget/**").hasAnyRole("ROLE_BF_EDIT_BUDGET")
                        .requestMatchers(HttpMethod.GET,"/api/budget/").hasAnyRole("ROLE_BF_LIST_BUDGET")
                        .requestMatchers(HttpMethod.POST,"/api/country/").hasAnyRole("ROLE_BF_ADD_COUNTRY")
                        .requestMatchers(HttpMethod.PUT,"/api/country/").hasAnyRole("ROLE_BF_EDIT_COUNTRY")
                        .requestMatchers(HttpMethod.GET,"/api/country/").hasAnyRole("ROLE_BF_LIST_COUNTRY")
                        .requestMatchers(HttpMethod.POST,"/api/currency/").hasAnyRole("ROLE_BF_ADD_CURRENCY")
                        .requestMatchers(HttpMethod.PUT,"/api/currency/").hasAnyRole("ROLE_BF_EDIT_CURRENCY")
                        .requestMatchers(HttpMethod.GET,"/api/currency/").hasAnyRole("ROLE_BF_LIST_CURRENCY","ROLE_BF_ADD_BUDGET","ROLE_BF_SUPPLY_PLAN_VERSION_AND_REVIEW")
                        .requestMatchers(HttpMethod.GET,"api/currency/all/").hasAnyRole("ROLE_BF_LIST_CURRENCY","ROLE_BF_ADD_COUNTRY","ROLE_BF_EDIT_COUNTRY","ROLE_BF_MAP_REALM_COUNTRY")
                        .requestMatchers(HttpMethod.GET,"/api/applicationLevelDashboard/").hasAnyRole("ROLE_BF_APPLICATION_DASHBOARD")
                        .requestMatchers(HttpMethod.GET,"/api/applicationLevelDashboardUserList").hasAnyRole("ROLE_BF_APPLICATION_DASHBOARD")
                        .requestMatchers(HttpMethod.GET,"/api/ticket/openIssues").hasAnyRole("ROLE_BF_APPLICATION_DASHBOARD")
                        .requestMatchers(HttpMethod.GET,"/api/supplyPlanReviewerLevelDashboard").hasAnyRole("ROLE_SUPPLY_PLAN_REVIEWER")
                        .requestMatchers(HttpMethod.GET,"/api/realmLevelDashboard").hasAnyRole("ROLE_BF_APPLICATION_DASHBOARD")
                        .requestMatchers(HttpMethod.GET,"/api/realmLevelDashboardUserList").hasAnyRole("ROLE_BF_APPLICATION_DASHBOARD")
                        .requestMatchers(HttpMethod.GET,"/api/dataset").hasAnyRole("ROLE_BF_COMPARE_VERSION","ROLE_BF_SUPPLY_PLAN_IMPORT","ROLE_BF_LIST_EQUIVALENCY_UNIT_MAPPING","ROLE_BF_LIST_USAGE_TEMPLATE","ROLE_BF_MODELING_VALIDATION","ROLE_BF_PRODUCT_VALIDATION")
                        .requestMatchers(HttpMethod.GET,"/api/loadDataset").hasAnyRole("ROLE_BF_LOAD_DELETE_DATASET")
                        .requestMatchers(HttpMethod.GET,"/api/loadDataset/programId/**").hasAnyRole("ROLE_BF_LOAD_DELETE_DATASET")
                        .requestMatchers(HttpMethod.GET,"/api/treeTemplate").hasAnyRole("ROLE_BF_LIST_TREE_TEMPLATE")
                        .requestMatchers(HttpMethod.GET,"/api/treeTemplate/**").hasAnyRole("ROLE_BF_EDIT_TREE_TEMPLATE","ROLE_BF_ADD_TREE_TEMPLATE","ROLE_BF_VIEW_TREE_TEMPLATES","ROLE_BF_LIST_TREE_TEMPLATE")
                        .requestMatchers(HttpMethod.GET,"/api/usageType").hasAnyRole("ROLE_BF_EDIT_TREE_TEMPLATE","ROLE_BF_ADD_TREE_TEMPLATE","ROLE_BF_VIEW_TREE_TEMPLATES")
                        .requestMatchers(HttpMethod.POST,"/api/treeTemplate/**").hasAnyRole("ROLE_BF_EDIT_TREE_TEMPLATE","ROLE_BF_ADD_TREE_TEMPLATE","ROLE_BF_VIEW_TREE_TEMPLATES","ROLE_BF_LIST_TREE_TEMPLATE")
                        .requestMatchers(HttpMethod.PUT,"/api/treeTemplate/**").hasAnyRole("ROLE_BF_EDIT_TREE_TEMPLATE","ROLE_BF_ADD_TREE_TEMPLATE","ROLE_BF_VIEW_TREE_TEMPLATES")
                        .requestMatchers(HttpMethod.PUT,"/api/datasetData/**").hasAnyRole("ROLE_BF_COMMIT_DATASET")
                        .requestMatchers(HttpMethod.GET,"/api/datasetData/programId/**").hasAnyRole("ROLE_BF_VERSION_SETTINGS","ROLE_BF_LIST_TREE")
                        .requestMatchers(HttpMethod.POST,"/api/dataSource/").hasAnyRole("ROLE_BF_ADD_DATA_SOURCE")
                        .requestMatchers(HttpMethod.PUT,"/api/dataSource/").hasAnyRole("ROLE_BF_EDIT_DATA_SOURCE")
                        .requestMatchers(HttpMethod.GET,"/api/dataSource/all").hasAnyRole("ROLE_BF_LIST_DATA_SOURCE","ROLE_BF_PIPELINE_PROGRAM_IMPORT","ROLE_BF_SUPPLY_PLAN_VERSION_AND_REVIEW")
                        .requestMatchers(HttpMethod.POST,"/api/dataSourceType/").hasAnyRole("ROLE_BF_ADD_DATA_SOURCE_TYPE")
                        .requestMatchers(HttpMethod.PUT,"/api/dataSourceType/").hasAnyRole("ROLE_BF_EDIT_DATA_SOURCE_TYPE")
                        .requestMatchers(HttpMethod.GET,"/api/dataSourceType/").hasAnyRole("ROLE_BF_LIST_DATA_SOURCE_TYPE","ROLE_BF_PIPELINE_PROGRAM_IMPORT","ROLE_BF_EDIT_DATA_SOURCE_TYPE")
                        .requestMatchers(HttpMethod.GET,"/api/dataSourceType/all/").hasAnyRole("ROLE_BF_LIST_DATA_SOURCE_TYPE","ROLE_BF_LIST_DATA_SOURCE")
                        .requestMatchers(HttpMethod.GET,"/api/dataSourceType/realmId/").hasAnyRole("ROLE_BF_ADD_DATA_SOURCE")
                        .requestMatchers("/api/equivalencyUnit**").hasAnyRole("ROLE_BF_LIST_EQUIVALENCY_UNIT_MAPPING","ROLE_BF_LIST_MONTHLY_FORECAST","ROLE_BF_FORECAST_ERROR_OVER_TIME_REPORT")
                        .requestMatchers(HttpMethod.POST,"/api/forecastingUnit/**").hasAnyRole("ROLE_BF_ADD_FORECASTING_UNIT")
                        .requestMatchers(HttpMethod.GET,"/api/forecastingUnit/").hasAnyRole("ROLE_BF_ADD_PLANNING_UNIT")
                        .requestMatchers(HttpMethod.GET,"/api/forecastingUnit/all").hasAnyRole("ROLE_BF_LIST_FORECASTING_UNIT")
                        .requestMatchers(HttpMethod.PUT,"/api/forecastingUnit/**").hasAnyRole("ROLE_BF_EDIT_FORECASTING_UNIT")
                        .requestMatchers(HttpMethod.GET,"/api/forecastingUnit/**").hasAnyRole("ROLE_BF_ADD_PLANNING_UNIT")
                        .requestMatchers(HttpMethod.GET,"/api/forecastingUnit/realmId/**").hasAnyRole("ROLE_BF_LIST_FORECASTING_UNIT","ROLE_BF_LIST_PLANNING_UNIT")
                        .requestMatchers(HttpMethod.GET,"/api/forecastingUnit/tracerCategory/**").hasAnyRole("ROLE_BF_EDIT_TREE_TEMPLATE","ROLE_BF_ADD_TREE_TEMPLATE","ROLE_BF_VIEW_TREE_TEMPLATES")
                        .requestMatchers(HttpMethod.POST,"/api/forecastingUnit/tracerCategorys**").hasAnyRole("ROLE_BF_LIST_EQUIVALENCY_UNIT_MAPPING")
                        .requestMatchers(HttpMethod.GET,"/api/forecastingUnit/programId/**").hasAnyRole("ROLE_BF_LIST_MONTHLY_FORECAST")
                        .requestMatchers(HttpMethod.GET,"/api/forecastMethod/all").hasAnyRole("ROLE_BF_LIST_FORECAST_METHOD")
                        .requestMatchers(HttpMethod.GET,"/api/forecastMethod").hasAnyRole("ROLE_BF_EDIT_TREE_TEMPLATE","ROLE_BF_ADD_TREE_TEMPLATE","ROLE_BF_VIEW_TREE_TEMPLATES")
                        .requestMatchers(HttpMethod.POST,"/api/forecastMethod**").hasAnyRole("ROLE_BF_LIST_FORECAST_METHOD")
                        .requestMatchers(HttpMethod.GET,"/api/forecastMethodType").hasAnyRole("ROLE_BF_LIST_FORECAST_METHOD")
                        .requestMatchers(HttpMethod.POST,"/api/fundingSource/**").hasAnyRole("ROLE_BF_ADD_FUNDING_SOURCE")
                        .requestMatchers(HttpMethod.GET,"/api/fundingSource").hasAnyRole("ROLE_BF_ADD_BUDGET","ROLE_BF_EDIT_BUDGET","ROLE_BF_LIST_BUDGET","ROLE_BF_LIST_FUNDING_SOURCE","ROLE_BF_MANUAL_TAGGING","ROLE_BF_PIPELINE_PROGRAM_IMPORT","ROLE_BF_SUPPLY_PLAN_VERSION_AND_REVIEW","ROLE_BF_SHIPMENT_COST_DETAILS_REPORT","ROLE_BF_SHIPMENT_OVERVIEW_REPORT","ROLE_BF_GLOBAL_DEMAND_REPORT")
                        .requestMatchers("/api/fundingSource/**").hasAnyRole("ROLE_BF_EDIT_FUNDING_SOURCE")
                        .requestMatchers(HttpMethod.GET,"/api/fundingSource/getDisplayName/realmId/**").hasAnyRole("ROLE_BF_ADD_FUNDING_SOURCE")
                        .requestMatchers(HttpMethod.POST,"/api/healthArea/**").hasAnyRole("ROLE_BF_ADD_HEALTH_AREA")
                        .requestMatchers(HttpMethod.PUT,"/api/healthArea/**").hasAnyRole("ROLE_BF_EDIT_HEALTH_AREA")
                        .requestMatchers(HttpMethod.GET,"/api/healthArea/**").hasAnyRole("ROLE_BF_LIST_HEALTH_AREA","ROLE_BF_EDIT_HEALTH_AREA")
                        .requestMatchers(HttpMethod.GET,"/api/healthArea/getDisplayName/realmId/**").hasAnyRole("ROLE_BF_ADD_HEALTH_AREA")
                        .requestMatchers(HttpMethod.POST,"/api/integration/**").hasAnyRole("ROLE_BF_ADD_INTEGRATION")
                        .requestMatchers(HttpMethod.GET,"/api/integration").hasAnyRole("ROLE_BF_LIST_INTEGRATION","ROLE_BF_ADD_INTEGRATION")
                        .requestMatchers(HttpMethod.GET,"/api/integration/viewList").hasAnyRole("ROLE_BF_ADD_INTEGRATION","ROLE_BF_EDIT_INTEGRATION")
                        .requestMatchers("/api/integration/**").hasAnyRole("ROLE_BF_EDIT_INTEGRATION")
                        .requestMatchers(HttpMethod.PUT,"/api/integrationProgram/**").hasAnyRole("ROLE_BF_ADD_INTEGRATION_PROGRAM")
                        .requestMatchers(HttpMethod.GET,"/api/integrationProgram/program/**").hasAnyRole("ROLE_BF_ADD_INTEGRATION_PROGRAM")
                        .requestMatchers(HttpMethod.POST,"/api/integrationProgram/manualJson/").hasAnyRole("ROLE_BF_MANUAL_INTEGRATION")
                        .requestMatchers(HttpMethod.POST,"/api/report/manualJson/").hasAnyRole("ROLE_BF_MANUAL_INTEGRATION")
                        .requestMatchers(HttpMethod.GET,"/api/businessFunction/**").hasAnyRole("ROLE_BF_ADD_ROLE", "ROLE_BF_EDIT_ROLE")
                        .requestMatchers(HttpMethod.GET,"/api/role/**").hasAnyRole("ROLE_BF_ADD_ROLE","ROLE_BF_EDIT_ROLE","ROLE_BF_LIST_ROLE","ROLE_BF_ADD_USER","ROLE_BF_EDIT_USER","ROLE_BF_LIST_USER")
                        .requestMatchers(HttpMethod.POST,"/api/role/**").hasAnyRole("ROLE_BF_ADD_ROLE")
                        .requestMatchers(HttpMethod.PUT,"/api/role/**").hasAnyRole("ROLE_BF_EDIT_ROLE")
                        .requestMatchers(HttpMethod.POST,"/api/user/**").hasAnyRole("ROLE_BF_ADD_USER")
                        .requestMatchers(HttpMethod.PUT,"/api/user/**").hasAnyRole("ROLE_BF_LIST_USER","ROLE_BF_EDIT_USER")
                        .requestMatchers(HttpMethod.GET,"/api/user/**").hasAnyRole("ROLE_BF_LIST_USER","ROLE_BF_EDIT_USER")
                        .requestMatchers(HttpMethod.POST,"/api/userManual/uploadUserManual/**").hasAnyRole("ROLE_BF_UPLOAD_USER_MANUAL")
                        .requestMatchers(HttpMethod.GET,"/api/usageTemplate/all/**").hasAnyRole("ROLE_BF_LIST_USAGE_TEMPLATE")
                        .requestMatchers(HttpMethod.POST,"/api/usageTemplate/**").hasAnyRole("ROLE_BF_LIST_USAGE_TEMPLATE")
                        .requestMatchers(HttpMethod.GET,"/api/usageTemplate/tracerCategory/**").hasAnyRole("ROLE_BF_EDIT_TREE_TEMPLATE","ROLE_BF_ADD_TREE_TEMPLATE","ROLE_BF_VIEW_TREE_TEMPLATES")
                        .requestMatchers(HttpMethod.GET,"/api/usagePeriod/all/**").hasAnyRole("ROLE_BF_LIST_USAGE_PERIOD")
                        .requestMatchers(HttpMethod.GET,"/api/usagePeriod/**").hasAnyRole("ROLE_BF_EDIT_TREE_TEMPLATE","ROLE_BF_ADD_TREE_TEMPLATE","ROLE_BF_VIEW_TREE_TEMPLATES","ROLE_BF_LIST_USAGE_TEMPLATE","ROLE_BF_LIST_USAGE_PERIOD")
                        .requestMatchers(HttpMethod.POST,"/api/usagePeriod/**").hasAnyRole("ROLE_BF_LIST_USAGE_PERIOD")
                        .requestMatchers(HttpMethod.POST,"/api/unit/**").hasAnyRole("ROLE_BF_ADD_UNIT")
                        .requestMatchers(HttpMethod.PUT,"/api/unit/**").hasAnyRole("ROLE_BF_EDIT_UNIT")
                        .requestMatchers(HttpMethod.POST,"/api/unit/dimension/**").hasAnyRole("ROLE_BF_EDIT_TREE_TEMPLATE","ROLE_BF_ADD_TREE_TEMPLATE","ROLE_BF_VIEW_TREE_TEMPLATES","ROLE_BF_LIST_USAGE_TEMPLATE")
                        .requestMatchers(HttpMethod.GET,"/api/unit/").hasAnyRole("ROLE_BF_EDIT_TREE_TEMPLATE","ROLE_BF_ADD_TREE_TEMPLATE","ROLE_BF_VIEW_TREE_TEMPLATES","ROLE_BF_LIST_EQUIVALENCY_UNIT_MAPPING","ROLE_BF_ADD_FORECASTING_UNIT","ROLE_BF_EDIT_FORECASTING_UNIT","ROLE_BF_ADD_PLANNING_UNIT","ROLE_BF_EDIT_PLANNING_UNIT","ROLE_BF_ADD_PROCUREMENT_UNIT","ROLE_BF_EDIT_PROCUREMENT_UNIT","ROLE_BF_MANAGE_REALM_COUNTRY_PLANNING_UNIT","ROLE_BF_LIST_USAGE_TEMPLATE")
                        .requestMatchers(HttpMethod.POST,"/api/tracerCategory/**").hasAnyRole("ROLE_BF_ADD_TRACER_CATEGORY")
                        .requestMatchers(HttpMethod.PUT,"/api/tracerCategory/**").hasAnyRole("ROLE_BF_EDIT_TRACER_CATEGORY")
                        .requestMatchers(HttpMethod.GET,"/api/tracerCategory/realmId/**").hasAnyRole("ROLE_BF_LIST_PLANNING_UNIT","ROLE_BF_FORECAST_MATRIX_REPORT","ROLE_BF_PRODUCT_CATALOG_REPORT","ROLE_BF_STOCK_STATUS_GLOBAL_VIEW_REPORT","ROLE_BF_VIEW_STOCK_STATUS_MATRIX")
                        .requestMatchers(HttpMethod.GET,"/api/tracerCategory/**").hasAnyRole("ROLE_BF_EDIT_TREE_TEMPLATE","ROLE_BF_ADD_TREE_TEMPLATE","ROLE_BF_VIEW_TREE_TEMPLATES","ROLE_BF_LIST_EQUIVALENCY_UNIT_MAPPING","ROLE_BF_LIST_PLANNING_UNIT_SETTING","ROLE_BF_LIST_TRACER_CATEGORY","ROLE_BF_LIST_USAGE_TEMPLATE","ROLE_BF_EDIT_TRACER_CATEGORY")
                        .requestMatchers(HttpMethod.POST,"/api/supplier/**").hasAnyRole("ROLE_BF_ADD_SUPPLIER")
                        .requestMatchers(HttpMethod.GET,"/api/supplier/**").hasAnyRole("ROLE_BF_MAP_PLANNING_UNIT_CAPACITY","ROLE_BF_ADD_PROCUREMENT_UNIT","ROLE_BF_LIST_SUPPLIER","ROLE_BF_EDIT_SUPPLIER","ROLE_BF_PIPELINE_PROGRAM_IMPORT")
                        .requestMatchers(HttpMethod.PUT,"/api/supplier/**").hasAnyRole("ROLE_BF_EDIT_SUPPLIER")
                        .requestMatchers(HttpMethod.GET,"/api/getShipmentStatusListActive/**").hasAnyRole("ROLE_BF_ANNUAL_SHIPMENT_COST_REPORT","ROLE_BF_SUPPLY_PLAN_VERSION_AND_REVIEW","ROLE_BF_SHIPMENT_OVERVIEW_REPORT","ROLE_BF_PIPELINE_PROGRAM_IMPORT")
                        .requestMatchers(HttpMethod.POST,"/api/report/forecastMetricsMonthly/**").hasAnyRole("ROLE_BF_FORECAST_ERROR_OVER_TIME_REPORT")
                        .requestMatchers(HttpMethod.POST,"/api/report/globalConsumption/**").hasAnyRole("ROLE_BF_CONSUMPTION_GLOBAL_VIEW_REPORT")
                        .requestMatchers(HttpMethod.POST,"/api/report/forecastMetricsComparision/**").hasAnyRole("ROLE_BF_FORECAST_MATRIX_REPORT")
                        .requestMatchers(HttpMethod.POST,"/api/report/annualShipmentCost/**").hasAnyRole("ROLE_BF_ANNUAL_SHIPMENT_COST_REPORT")
                        .requestMatchers(HttpMethod.POST,"/api/report/stockStatusOverTime/**").hasAnyRole("ROLE_BF_STOCK_STATUS_OVER_TIME_REPORT")
                        .requestMatchers(HttpMethod.GET,"/api/programVersion/programId/**").hasAnyRole("ROLE_BF_SUPPLY_PLAN_VERSION_AND_REVIEW")
                        .requestMatchers(HttpMethod.POST,"/api/report/costOfInventory/**").hasAnyRole("ROLE_BF_COST_OF_INVENTORY_REPORT")
                        .requestMatchers(HttpMethod.POST,"/api/report/stockStatusVertical/**").hasAnyRole("ROLE_BF_SUPPLY_PLAN_REPORT")
                        .requestMatchers(HttpMethod.POST,"/api/report/inventoryTurns/**").hasAnyRole("ROLE_BF_INVENTORY_TURNS_REPORT")
                        .requestMatchers(HttpMethod.POST,"/api/report/stockAdjustmentReport/**").hasAnyRole("ROLE_BF_STOCK_ADJUSTMENT_REPORT")
                        .requestMatchers(HttpMethod.POST,"/api/report/procurementAgentShipmentReport/**").hasAnyRole("ROLE_BF_SHIPMENT_COST_DETAILS_REPORT")
                        .requestMatchers(HttpMethod.POST,"/api/report/fundingSourceShipmentReport/**").hasAnyRole("ROLE_BF_FUNDER_REPORT","ROLE_BF_SHIPMENT_COST_DETAILS_REPORT")
                        .requestMatchers(HttpMethod.POST,"/api/report/aggregateShipmentByProduct/**").hasAnyRole("ROLE_BF_SHIPMENT_COST_DETAILS_REPORT")
                        .requestMatchers(HttpMethod.POST,"/api/report/warehouseCapacityReport/**").hasAnyRole("ROLE_BF_WAREHOUSE_CAPACITY_REPORT")
                        .requestMatchers(HttpMethod.POST,"/api/report/stockStatusForProgram/**").hasAnyRole("ROLE_BF_STOCK_STATUS_REPORT","ROLE_BF_STOCK_STATUS_GLOBAL_VIEW_REPORT")
                        .requestMatchers(HttpMethod.POST,"/api/report/programProductCatalog/**").hasAnyRole("ROLE_BF_PRODUCT_CATALOG_REPORT")
                        .requestMatchers(HttpMethod.POST,"/api/report/shipmentGlobalDemand/**").hasAnyRole("ROLE_BF_GLOBAL_DEMAND_REPORT")
                        .requestMatchers(HttpMethod.POST,"/api/report/programLeadTimes/**").hasAnyRole("ROLE_BF_PROCUREMENT_AGENT_REPORT")
                        .requestMatchers(HttpMethod.POST,"/api/report/shipmentDetails/**").hasAnyRole("ROLE_BF_SHIPMENT_DETAILS_REPORT")
                        .requestMatchers(HttpMethod.POST,"/api/report/shipmentOverview/**").hasAnyRole("ROLE_BF_SHIPMENT_OVERVIEW_REPORT")
                        .requestMatchers(HttpMethod.POST,"/api/report/budgetReport/**").hasAnyRole("ROLE_BF_BUDGET_REPORT")
                        .requestMatchers(HttpMethod.POST,"/api/report/stockStatusAcrossProducts/**").hasAnyRole("ROLE_BF_STOCK_STATUS_GLOBAL_VIEW_REPORT")
                        .requestMatchers(HttpMethod.POST,"/api/report/expiredStock/**").hasAnyRole("ROLE_BF_EXPIRIES_REPORT")
                        .requestMatchers(HttpMethod.POST,"/api/report/warehouseByCountry/**").hasAnyRole("ROLE_BF_REGION")
                        .requestMatchers(HttpMethod.POST,"/api/report/monthlyForecast/**").hasAnyRole("ROLE_BF_SUPPLY_PLAN_IMPORT","ROLE_BF_LIST_MONTHLY_FORECAST")
                        .requestMatchers(HttpMethod.POST,"/api/report/forecastSummary/**").hasAnyRole("ROLE_BF_LIST_FORECAST_SUMMARY","ROLE_BF_VIEW_FORECAST_SUMMARY","ROLE_BF_COMPARE_VERSION")
                        .requestMatchers(HttpMethod.POST,"/api/report/forecastError/**").hasAnyRole("ROLE_BF_FORECAST_ERROR_OVER_TIME_REPORT")
                        .requestMatchers(HttpMethod.POST,"/api/report/forecastErrorNew/**").hasAnyRole("ROLE_BF_FORECAST_ERROR_OVER_TIME_REPORT")
                        .requestMatchers(HttpMethod.GET,"/api/report/updateProgramInfo/programTypeId/**").hasAnyRole("ROLE_BF_LIST_DATASET")
                        .requestMatchers(HttpMethod.GET,"/api/region/realmCountryId/**").hasAnyRole("ROLE_BF_MAP_REGION","ROLE_BF_ADD_DATASET","ROLE_BF_EDIT_DATASET","ROLE_BF_PIPELINE_PROGRAM_IMPORT","ROLE_BF_CREATE_A_PROGRAM","ROLE_BF_EDIT_PROGRAM","ROLE_BF_SET_UP_PROGRAM")
                        .requestMatchers(HttpMethod.POST,"/api/realm/").hasAnyRole("ROLE_BF_CREATE_REALM")
                        .requestMatchers(HttpMethod.GET,"/api/realm/").hasAnyRole("ROLE_BF_LOAD_DELETE_DATASET","ROLE_BF_ADD_DATA_SOURCE","ROLE_BF_LIST_DATA_SOURCE","ROLE_BF_ADD_DATA_SOURCE_TYPE","ROLE_BF_LIST_DATA_SOURCE_TYPE","ROLE_BF_ADD_FORECASTING_UNIT","ROLE_BF_LIST_FORECASTING_UNIT","ROLE_BF_LIST_FORECAST_METHOD","ROLE_BF_ADD_FUNDING_SOURCE","ROLE_BF_LIST_FUNDING_SOURCE","ROLE_BF_LIST_HEALTH_AREA","ROLE_BF_ADD_INTEGRATION_PROGRAM","ROLE_BF_LIST_ORGANIZATION","ROLE_BF_LIST_ORGANIZATION_TYPE","ROLE_BF_LIST_PLANNING_UNIT","ROLE_BF_ADD_PROCUREMENT_AGENT","ROLE_BF_LIST_PROCUREMENT_AGENT","ROLE_BF_LIST_PRODUCT_CATEGORY","ROLE_BF_LIST_REALM","ROLE_BF_LIST_REALM_COUNTRY","ROLE_BF_CONSUMPTION_REPORT","ROLE_BF_ADD_SUPPLIER","ROLE_BF_LIST_SUPPLIER","ROLE_BF_ADD_TRACER_CATEGORY","ROLE_BF_LIST_TRACER_CATEGORY","ROLE_BF_ADD_USER","ROLE_BF_EDIT_USER","ROLE_BF_LIST_USER","ROLE_BF_ADD_DATASET","ROLE_BF_CREATE_A_PROGRAM","ROLE_BF_SET_UP_PROGRAM")
                        .requestMatchers(HttpMethod.GET,"/api/realm/**").hasAnyRole("ROLE_BF_EDIT_REALM","ROLE_BF_MAP_REALM_COUNTRY")
                        .requestMatchers(HttpMethod.PUT,"/api/realm/").hasAnyRole("ROLE_BF_EDIT_REALM","ROLE_BF_LIST_REALM")
                        .requestMatchers(HttpMethod.GET,"/api/realmCountry/").hasAnyRole("ROLE_BF_LIST_REALM_COUNTRY")
                        .requestMatchers(HttpMethod.GET,"/api/realmCountry/**").hasAnyRole("ROLE_BF_MAP_REGION","ROLE_BF_PIPELINE_PROGRAM_IMPORT")
                        .requestMatchers(HttpMethod.PUT,"/api/realmCountry/planningUnit/").hasAnyRole("ROLE_BF_LIST_ALTERNATE_REPORTING_UNIT")
                        .requestMatchers(HttpMethod.POST,"/api/realmCountry/programIds/planningUnit/").hasAnyRole("ROLE_BF_MANUAL_TAGGING","ROLE_BF_LIST_ALTERNATE_REPORTING_UNIT","ROLE_BF_SUPPLY_PLAN")
                        .requestMatchers(HttpMethod.GET,"/api/realmCountry/realmId/**").hasAnyRole("ROLE_BF_ADD_HEALTH_AREA","ROLE_BF_EDIT_HEALTH_AREA","ROLE_BF_ADD_DATASET","ROLE_BF_CREATE_A_PROGRAM","ROLE_BF_SET_UP_PROGRAM","ROLE_BF_ADD_ORGANIZATION","ROLE_BF_EDIT_ORGANIZATION","ROLE_BF_LIST_DATASET","ROLE_BF_LIST_PROGRAM","ROLE_BF_MAP_REALM_COUNTRY")
                        .requestMatchers(HttpMethod.GET,"/api/realmCountry/program/realmId/**").hasAnyRole("ROLE_BF_LOAD_DELETE_DATASET","ROLE_BF_MANUAL_TAGGING","ROLE_BF_REGION","ROLE_BF_FORECAST_MATRIX_REPORT","ROLE_BF_CONSUMPTION_GLOBAL_VIEW_REPORT","ROLE_BF_SHIPMENT_OVERVIEW_REPORT","ROLE_BF_GLOBAL_DEMAND_REPORT","ROLE_BF_STOCK_STATUS_GLOBAL_VIEW_REPORT","ROLE_BF_STOCK_STATUS_OVER_TIME_REPORT","ROLE_BF_SUPPLY_PLAN_VERSION_AND_REVIEW","ROLE_BF_WAREHOUSE_CAPACITY_REPORT")
                        .requestMatchers(HttpMethod.POST,"/api/quantimed/quantimedImport/**").hasAnyRole("ROLE_BF_QUANTIMED_IMPORT")
                        .requestMatchers(HttpMethod.GET,"/api/programData/programId/**").hasAnyRole("ROLE_BF_COMMIT_VERSION","ROLE_BF_SUPPLY_PLAN_VERSION_AND_REVIEW")
                        .requestMatchers(HttpMethod.POST,"/api/programData/").hasAnyRole("ROLE_BF_DOWNLOAD_PROGARM","ROLE_BF_COMMIT_VERSION")
                        .requestMatchers(HttpMethod.GET,"/api/program/").hasAnyRole("ROLE_BF_ADD_PROCUREMENT_AGENT","ROLE_BF_EDIT_PROCUREMENT_AGENT","ROLE_BF_FORECAST_ERROR_OVER_TIME_REPORT","ROLE_BF_INVENTORY_TURNS_REPORT")
                        .requestMatchers(HttpMethod.GET,"/api/dataset/").hasAnyRole("ROLE_BF_COMPARE_VERSION","ROLE_BF_LIST_EQUIVALENCY_UNIT_MAPPING","ROLE_BF_LIST_USAGE_TEMPLATE","ROLE_BF_MODELING_VALIDATION","ROLE_BF_PRODUCT_VALIDATION")
                        .requestMatchers(HttpMethod.GET,"/api/loadProgram/programId/**").hasAnyRole("ROLE_BF_DOWNLOAD_PROGARM")
                        .requestMatchers(HttpMethod.POST,"/api/program/**").hasAnyRole("ROLE_BF_CREATE_A_PROGRAM","ROLE_BF_ADD_PROGRAM_PRODUCT","ROLE_BF_MAP_PROCUREMENT_AGENT")
                        .requestMatchers(HttpMethod.PUT,"/api/program/").hasAnyRole("ROLE_BF_EDIT_PROGRAM")
                        .requestMatchers(HttpMethod.GET,"/api/realmCountry/realmId/**").hasAnyRole("ROLE_BF_PIPELINE_PROGRAM_IMPORT","ROLE_BF_CREATE_A_PROGRAM")
                        .requestMatchers(HttpMethod.GET,"/api/organisation/realmId/**").hasAnyRole("ROLE_BF_PIPELINE_PROGRAM_IMPORT","ROLE_BF_CREATE_A_PROGRAM")
                        .requestMatchers(HttpMethod.GET,"/api/healthArea/realmId/**").hasAnyRole("ROLE_BF_PIPELINE_PROGRAM_IMPORT","ROLE_BF_CREATE_A_PROGRAM","ROLE_BF_EDIT_PROGRAM","ROLE_BF_ADD_DATASET","ROLE_BF_EDIT_DATASET")
                        .requestMatchers(HttpMethod.GET,"/api/region/realmCountryId/**").hasAnyRole("ROLE_BF_ADD_DATASET","ROLE_BF_EDIT_DATASET","ROLE_BF_PIPELINE_PROGRAM_IMPORT","ROLE_BF_CREATE_A_PROGRAM","ROLE_BF_EDIT_PROGRAM","ROLE_BF_SET_UP_PROGRAM")
                        .requestMatchers(HttpMethod.GET,"/api/program/**").hasAnyRole("ROLE_BF_MANUAL_TAGGING","ROLE_BF_MAP_COUNTRY_SPECIFIC_PRICES","ROLE_BF_ADD_PROGRAM_PRODUCT","ROLE_BF_ADD_INTEGRATION_PROGRAM","ROLE_BF_EDIT_PROGRAM","ROLE_BF_FORECAST_ERROR_OVER_TIME_REPORT","ROLE_BF_SUPPLY_PLAN_VERSION_AND_REVIEW","ROLE_BF_EXPIRIES_REPORT","ROLE_BF_SUPPLY_PLAN_REPORT","ROLE_BF_MAP_PROCUREMENT_AGENT")
                        .requestMatchers(HttpMethod.GET,"/api/loadProgram/").hasAnyRole("ROLE_BF_DOWNLOAD_PROGARM")
                        .requestMatchers(HttpMethod.PUT,"/api/program/planningUnit/**").hasAnyRole("ROLE_BF_ADD_PROGRAM_PRODUCT")
                        .requestMatchers(HttpMethod.GET,"/api/user/realmId/**").hasAnyRole("ROLE_BF_ADD_DATASET","ROLE_BF_EDIT_DATASET","ROLE_BF_CREATE_A_PROGRAM","ROLE_BF_EDIT_PROGRAM","ROLE_BF_SET_UP_PROGRAM","ROLE_BF_PIPELINE_PROGRAM_IMPORT")
                        .requestMatchers(HttpMethod.PUT,"/api/programData/**").hasAnyRole("ROLE_BF_COMMIT_VERSION")
                        .requestMatchers(HttpMethod.POST,"/api/program/initialize/**").hasAnyRole("ROLE_BF_SET_UP_PROGRAM")
                        .requestMatchers(HttpMethod.GET,"/api/versionStatus/**").hasAnyRole("ROLE_BF_ADD_INTEGRATION_PROGRAM","ROLE_BF_SUPPLY_PLAN_VERSION_AND_REVIEW")
                        .requestMatchers(HttpMethod.PUT,"/api/programVersion/programId/**").hasAnyRole("ROLE_BF_SUPPLY_PLAN_VERSION_AND_REVIEW")
                        .requestMatchers(HttpMethod.GET,"/api/versionType/**").hasAnyRole("ROLE_BF_SUPPLY_PLAN_VERSION_AND_REVIEW","ROLE_BF_COMMIT_VERSION","ROLE_BF_ADD_INTEGRATION_PROGRAM","ROLE_BF_VERSION_SETTINGS")
                        .requestMatchers(HttpMethod.POST,"/api/programData/checkNewerVersions/").hasAnyRole("ROLE_BF_IMPORT_DATASET","ROLE_BF_LOAD_DELETE_DATASET","ROLE_BF_IMPORT_PROGARM","ROLE_BF_DOWNLOAD_PROGARM")
                        .requestMatchers(HttpMethod.GET,"/api/healthArea/realmCountryId/**").hasAnyRole("ROLE_BF_ADD_DATASET","ROLE_BF_EDIT_DATASET","ROLE_BF_EDIT_PROGRAM","ROLE_BF_SET_UP_PROGRAM")
                        .requestMatchers(HttpMethod.GET,"/api/problemStatus/**").hasAnyRole("ROLE_BF_SUPPLY_PLAN_VERSION_AND_REVIEW")
                        .requestMatchers(HttpMethod.GET,"/api/programData/getLatestVersionForProgram/**").hasAnyRole("ROLE_BF_COMMIT_DATASET","ROLE_BF_COMMIT_VERSION")
                        .requestMatchers(HttpMethod.GET,"/api/programData/getLastModifiedDateForProgram/**").hasAnyRole("ROLE_BF_COMMIT_VERSION")
                        .requestMatchers(HttpMethod.POST,"/api/dataset/**").hasAnyRole("ROLE_BF_ADD_DATASET")
                        .requestMatchers(HttpMethod.GET,"/api/dataset/**").hasAnyRole("ROLE_BF_EDIT_DATASET")
                        .requestMatchers(HttpMethod.PUT,"/api/dataset/**").hasAnyRole("ROLE_BF_EDIT_DATASET")
                        .requestMatchers(HttpMethod.POST,"/api/program/actualConsumptionReport/**").hasAnyRole("ROLE_BF_LIST_IMPORT_FROM_QAT_SUPPLY_PLAN")
                        .requestMatchers(HttpMethod.GET,"/api/programData/checkIfCommitRequestExistsForProgram//**").hasAnyRole("ROLE_BF_COMMIT_VERSION")
                        .requestMatchers(HttpMethod.GET,"/api/sendNotification/**").hasAnyRole("ROLE_BF_COMMIT_VERSION","ROLE_BF_COMMIT_DATASET")
                        .requestMatchers(HttpMethod.POST,"/api/erpLinking/otherProgramCheck/**").hasAnyRole("ROLE_BF_COMMIT_VERSION")
                        .requestMatchers(HttpMethod.GET,"/api/user/programId/**").hasAnyRole("ROLE_BF_EDIT_DATASET","ROLE_BF_EDIT_PROGRAM")
                        .requestMatchers(HttpMethod.POST,"/api/problemReport/createManualProblem/**").hasAnyRole("ROLE_BF_SUPPLY_PLAN_VERSION_AND_REVIEW")
                        .requestMatchers(HttpMethod.POST,"/api/dataset/versions/**").hasAnyRole("ROLE_BF_VERSION_SETTINGS")
                        .requestMatchers(HttpMethod.POST,"/api/program/realmCountryList/").hasAnyRole("ROLE_BF_INVENTORY_TURNS_REPORT")
                        .requestMatchers(HttpMethod.POST,"/api/program/productCategoryList/").hasAnyRole("ROLE_BF_INVENTORY_TURNS_REPORT")
                        .requestMatchers(HttpMethod.POST,"/api/report/consumptionForecastVsActual/**").hasAnyRole("ROLE_BF_CONSUMPTION_REPORT")
                        .requestMatchers(HttpMethod.POST,"/api/report/stockStatusMatrix/**").hasAnyRole("ROLE_BF_STOCK_STATUS_MATRIX_REPORT")
                        .requestMatchers(HttpMethod.GET,"/api/procurementUnit/all/**").hasAnyRole("ROLE_BF_MAP_PROCUREMENT_UNIT","ROLE_BF_LIST_PROCUREMENT_UNIT")
                        .requestMatchers("/api/procurementUnit/**").hasAnyRole("ROLE_BF_EDIT_PROCUREMENT_UNIT")
                        .requestMatchers(HttpMethod.POST,"/api/procurementUnit/**").hasAnyRole("ROLE_BF_ADD_PROCUREMENT_UNIT")
                        .requestMatchers(HttpMethod.POST,"/api/procurementAgent/**").hasAnyRole("ROLE_BF_ADD_PROCUREMENT_AGENT","ROLE_BF_MAP_PLANNING_UNIT","ROLE_BF_MAP_PROCUREMENT_UNIT")
                        .requestMatchers(HttpMethod.PUT,"/api/procurementAgent/**").hasAnyRole("ROLE_BF_EDIT_PROCUREMENT_AGENT","ROLE_BF_MAP_PROCUREMENT_AGENT")
                        .requestMatchers(HttpMethod.GET,"/api/procurementAgent/**").hasAnyRole("ROLE_BF_LIST_PROCUREMENT_AGENT","ROLE_BF_MAP_PLANNING_UNIT","ROLE_BF_MAP_PROCUREMENT_UNIT","ROLE_BF_MAP_COUNTRY_SPECIFIC_PRICES","ROLE_BF_EDIT_PROCUREMENT_AGENT")
                        .requestMatchers(HttpMethod.GET,"/api/program/planningUnit/procurementAgent/**").hasAnyRole("ROLE_BF_MAP_COUNTRY_SPECIFIC_PRICES")
                        .requestMatchers(HttpMethod.PUT,"/api/procurementAgent/planningUnit/**").hasAnyRole("ROLE_BF_MAP_PLANNING_UNIT")
                        .requestMatchers(HttpMethod.PUT,"/api/program/planningingUnit/procurementAgent/**").hasAnyRole("ROLE_BF_MAP_COUNTRY_SPECIFIC_PRICES")
                        .requestMatchers(HttpMethod.PUT,"/api/procurementAgent/procurementUnit/**").hasAnyRole("ROLE_BF_MAP_PROCUREMENT_UNIT")
                        .requestMatchers("/api/procurementAgent/getDisplayName/realmId/**").hasAnyRole("ROLE_BF_ADD_PROCUREMENT_AGENT")
                        .requestMatchers(HttpMethod.POST,"/api/procurementAgentType/**").hasAnyRole("ROLE_BF_ADD_PROCUREMENT_AGENT")
                        .requestMatchers(HttpMethod.GET,"/api/procurementAgentType/**").hasAnyRole("ROLE_BF_ADD_PROCUREMENT_AGENT","ROLE_BF_EDIT_PROCUREMENT_AGENT","ROLE_BF_LIST_PROCUREMENT_AGENT","ROLE_BF_GLOBAL_DEMAND_REPORT")
                        .requestMatchers(HttpMethod.PUT,"/api/procurementAgentType/**").hasAnyRole("ROLE_BF_EDIT_PROCUREMENT_AGENT")
                        .requestMatchers(HttpMethod.GET,"/api/productCategory/realmId/**").hasAnyRole("ROLE_BF_ADD_FORECASTING_UNIT","ROLE_BF_LIST_FORECASTING_UNIT","ROLE_BF_MANUAL_TAGGING","ROLE_BF_LIST_PLANNING_UNIT","ROLE_BF_PRODUCT_CATALOG_REPORT","ROLE_BF_GLOBAL_DEMAND_REPORT","ROLE_BF_LIST_PRODUCT_CATEGORY","ROLE_BF_ADD_PROGRAM_PRODUCT","ROLE_BF_INVENTORY_TURNS_REPORT")
                        .requestMatchers(HttpMethod.POST,"/api/report/consumptionForecastVsActual/**").hasAnyRole("ROLE_BF_CONSUMPTION_REPORT")
                        .requestMatchers(HttpMethod.POST,"/api/report/stockStatusMatrix/**").hasAnyRole("ROLE_BF_STOCK_STATUS_MATRIX_REPORT")
                        .requestMatchers(HttpMethod.PUT,"/api/productCategory/**").hasAnyRole("ROLE_BF_LIST_PRODUCT_CATEGORY")
                        .requestMatchers(HttpMethod.POST,"/api/planningUnit/**").hasAnyRole("ROLE_BF_ADD_PLANNING_UNIT")
                        .requestMatchers(HttpMethod.GET,"/api/planningUnit/**").hasAnyRole("ROLE_BF_EDIT_TREE_TEMPLATE","ROLE_BF_ADD_TREE_TEMPLATE","ROLE_BF_VIEW_TREE_TEMPLATES","ROLE_BF_PIPELINE_PROGRAM_IMPORT","ROLE_BF_SET_UP_PROGRAM","ROLE_BF_EDIT_PLANNING_UNIT","ROLE_BF_LIST_PLANNING_UNIT_SETTING","ROLE_BF_CONSUMPTION_REPORT")
                        .requestMatchers(HttpMethod.PUT,"/api/planningUnit/**").hasAnyRole("ROLE_BF_EDIT_PLANNING_UNIT","ROLE_BF_MAP_PLANNING_UNIT_CAPACITY")
                        .requestMatchers(HttpMethod.GET,"/api/planningUnit/all/**").hasAnyRole("ROLE_BF_LIST_PLANNING_UNIT_CAPACITY","ROLE_BF_MAP_PLANNING_UNIT","ROLE_BF_ADD_PROGRAM_PRODUCT","ROLE_BF_LIST_ALTERNATE_REPORTING_UNIT","ROLE_BF_PIPELINE_PROGRAM_IMPORT","ROLE_BF_ADD_PROCUREMENT_AGENT")
                        .requestMatchers(HttpMethod.GET,"/api/planningUnit/realmId/**").hasAnyRole("ROLE_BF_LIST_PLANNING_UNIT_SETTING","ROLE_BF_LIST_PLANNING_UNIT")
                        .requestMatchers(HttpMethod.POST,"/api/planningUnit/programs/**").hasAnyRole("ROLE_BF_LIST_ALTERNATE_REPORTING_UNIT","ROLE_BF_FORECAST_MATRIX_REPORT","ROLE_BF_CONSUMPTION_GLOBAL_VIEW_REPORT","ROLE_BF_SHIPMENT_OVERVIEW_REPORT")
                        .requestMatchers(HttpMethod.GET,"/api/planningUnit/productCategory/**").hasAnyRole("ROLE_BF_MANUAL_TAGGING","ROLE_BF_SHIPMENT_OVERVIEW_REPORT","ROLE_BF_GLOBAL_DEMAND_REPORT")
                        .requestMatchers(HttpMethod.POST,"/api/planningUnit/productCategoryList/active/**").hasAnyRole("ROLE_BF_MANUAL_TAGGING")
                        .requestMatchers(HttpMethod.GET,"/api/planningUnit/realmCountry/**").hasAnyRole("ROLE_BF_MANUAL_TAGGING")
                        .requestMatchers(HttpMethod.GET,"/api/planningUnit/forecastingUnit/**").hasAnyRole("ROLE_BF_EDIT_TREE_TEMPLATE","ROLE_BF_ADD_TREE_TEMPLATE","ROLE_BF_VIEW_TREE_TEMPLATES")
                        .requestMatchers(HttpMethod.GET,"/api/planningUnit/programId/**").hasAnyRole("ROLE_BF_LIST_MONTHLY_FORECAST","ROLE_BF_LIST_IMPORT_FROM_QAT_SUPPLY_PLAN","ROLE_BF_SUPPLY_PLAN_IMPORT")
                        .requestMatchers(HttpMethod.GET,"/api/planningUnit/basic/**").hasAnyRole("ROLE_BF_ADD_PROGRAM_PRODUCT")
                        .requestMatchers(HttpMethod.POST,"/api/planningUnit/byIds").hasAnyRole("ROLE_BF_LIST_PLANNING_UNIT_SETTING")
                        .requestMatchers(HttpMethod.POST,"/api/planningUnit/withPrices/byIds").hasAnyRole("ROLE_BF_ADD_TREE","ROLE_BF_VIEW_TREE","ROLE_BF_EDIT_TREE","ROLE_BF_EDIT_TREE_TEMPLATE","ROLE_BF_ADD_TREE_TEMPLATE","ROLE_BF_VIEW_TREE_TEMPLATES","ROLE_BF_LIST_TREE","ROLE_BF_LIST_TREE_TEMPLATE","ROLE_BF_LIST_PLANNING_UNIT_SETTING")
                        .requestMatchers(HttpMethod.GET,"/api/planningUnit/capacity/all/**").hasAnyRole("ROLE_BF_LIST_PLANNING_UNIT_CAPACITY")
                        .requestMatchers(HttpMethod.POST,"/api/pipelineJson/**").hasAnyRole("ROLE_BF_PIPELINE_PROGRAM_IMPORT")
                        .requestMatchers(HttpMethod.GET,"/api/pipeline/**").hasAnyRole("ROLE_BF_PIPELINE_PROGRAM_IMPORT")
                        .requestMatchers(HttpMethod.GET,"/api/pipeline/programInfo/**").hasAnyRole("ROLE_BF_PIPELINE_PROGRAM_IMPORT")
                        .requestMatchers(HttpMethod.POST,"/api/qatTemp/program/**").hasAnyRole("ROLE_BF_PIPELINE_PROGRAM_IMPORT")
                        .requestMatchers(HttpMethod.GET,"/api/qatTemp/program/**").hasAnyRole("ROLE_BF_PIPELINE_PROGRAM_IMPORT")
                        .requestMatchers(HttpMethod.GET,"/api/pipeline/shipment/**").hasAnyRole("ROLE_BF_PIPELINE_PROGRAM_IMPORT")
                        .requestMatchers(HttpMethod.PUT,"/api/pipeline/planningUnit/**").hasAnyRole("ROLE_BF_PIPELINE_PROGRAM_IMPORT")
                        .requestMatchers(HttpMethod.GET,"/api/qatTemp/planningUnitList/**").hasAnyRole("ROLE_BF_PIPELINE_PROGRAM_IMPORT")
                        .requestMatchers(HttpMethod.GET,"/api/qatTemp/regions/**").hasAnyRole("ROLE_BF_PIPELINE_PROGRAM_IMPORT")
                        .requestMatchers("/api/pipeline/consumption/**").hasAnyRole("ROLE_BF_PIPELINE_PROGRAM_IMPORT")
                        .requestMatchers(HttpMethod.GET,"/api/qatTemp/consumption/**").hasAnyRole("ROLE_BF_PIPELINE_PROGRAM_IMPORT")
                        .requestMatchers(HttpMethod.POST,"/api/pipeline/shipment/**").hasAnyRole("ROLE_BF_PIPELINE_PROGRAM_IMPORT")
                        .requestMatchers("/api/pipeline/inventory/**").hasAnyRole("ROLE_BF_PIPELINE_PROGRAM_IMPORT")
                        .requestMatchers(HttpMethod.GET,"/api/qatTemp/planningUnitListFinalInventry/**").hasAnyRole("ROLE_BF_PIPELINE_PROGRAM_IMPORT")
                        .requestMatchers(HttpMethod.POST,"/api/pipeline/programdata/**").hasAnyRole("ROLE_BF_PIPELINE_PROGRAM_IMPORT")
                        .requestMatchers(HttpMethod.GET,"/api/qatTemp/datasource/**").hasAnyRole("ROLE_BF_PIPELINE_PROGRAM_IMPORT")
                        .requestMatchers(HttpMethod.PUT,"/api/pipeline/datasource/**").hasAnyRole("ROLE_BF_PIPELINE_PROGRAM_IMPORT")
                        .requestMatchers(HttpMethod.GET,"/api/qatTemp/fundingsource/**").hasAnyRole("ROLE_BF_PIPELINE_PROGRAM_IMPORT")
                        .requestMatchers(HttpMethod.PUT,"/api/pipeline/fundingsource/**").hasAnyRole("ROLE_BF_PIPELINE_PROGRAM_IMPORT")
                        .requestMatchers(HttpMethod.GET,"/api/qatTemp/procurementagent/**").hasAnyRole("ROLE_BF_PIPELINE_PROGRAM_IMPORT")
                        .requestMatchers(HttpMethod.PUT,"/api/pipeline/procurementagent/**").hasAnyRole("ROLE_BF_PIPELINE_PROGRAM_IMPORT")
                        .requestMatchers(HttpMethod.PUT,"/api/pipeline/realmCountryPlanningUnit/**").hasAnyRole("ROLE_BF_PIPELINE_PROGRAM_IMPORT")
                        .requestMatchers(HttpMethod.POST,"/api/organisationType/**").hasAnyRole("ROLE_BF_ADD_ORGANIZATION_TYPE")
                        .requestMatchers("/api/organisationType/**").hasAnyRole("ROLE_BF_EDIT_ORGANIZATION_TYPE")
                        .requestMatchers(HttpMethod.GET,"/api/organisationType/all/**").hasAnyRole("ROLE_BF_LIST_ORGANIZATION_TYPE")
                        .requestMatchers(HttpMethod.GET,"/api/organisationType/realmId/**").hasAnyRole("ROLE_BF_ADD_ORGANIZATION_TYPE","ROLE_BF_EDIT_ORGANIZATION_TYPE")
                        .requestMatchers(HttpMethod.POST,"/api/organisation/**").hasAnyRole("ROLE_BF_ADD_ORGANIZATION")
                        .requestMatchers(HttpMethod.PUT,"/api/organisation/**").hasAnyRole("ROLE_BF_EDIT_ORGANIZATION")
                        .requestMatchers(HttpMethod.GET,"/api/organisation/**").hasAnyRole("ROLE_BF_LIST_ORGANIZATION")
                        .requestMatchers(HttpMethod.GET,"/api/realmCountry/realmId/**").hasAnyRole("ROLE_BF_MAP_REALM_COUNTRY","ROLE_BF_CREATE_A_PROGRAM","ROLE_BF_PIPELINE_PROGRAM_IMPORT","ROLE_BF_EDIT_HEALTH_AREA","ROLE_BF_ADD_ORGANIZATION","ROLE_BF_EDIT_ORGANIZATION")
                        .requestMatchers(HttpMethod.GET,"/api/organisation/getDisplayName/realmId/**").hasAnyRole("ROLE_BF_ADD_ORGANIZATION")
                        .requestMatchers(HttpMethod.GET,"/api/modelingType/**").hasAnyRole("ROLE_BF_EDIT_TREE_TEMPLATE","ROLE_BF_ADD_TREE_TEMPLATE","ROLE_BF_VIEW_TREE_TEMPLATES")
                        .requestMatchers(HttpMethod.GET,"/api/modelingType/all/**").hasAnyRole("ROLE_BF_EDIT_TREE_TEMPLATE","ROLE_BF_ADD_TREE_TEMPLATE","ROLE_BF_VIEW_TREE_TEMPLATES","ROLE_BF_LIST_MODELING_TYPE")
                        .requestMatchers(HttpMethod.POST,"/api/manualTagging/**").hasAnyRole("ROLE_BF_MANUAL_TAGGING","ROLE_BF_DELINKING")
                        .requestMatchers(HttpMethod.GET,"/api/erpLinking/shipmentLinkingNotification/programId/**").hasAnyRole("ROLE_BF_MANUAL_TAGGING")
                        .requestMatchers(HttpMethod.PUT,"/api/erpLinking/updateNotification/**").hasAnyRole("ROLE_BF_MANUAL_TAGGING")
                        .requestMatchers(HttpMethod.GET,"/api/manualTagging/notLinkedShipments/**").hasAnyRole("ROLE_BF_MANUAL_TAGGING","ROLE_BF_DELINKING")
                        .requestMatchers(HttpMethod.GET,"/api/erpLinking/artmisHistory/**").hasAnyRole("ROLE_BF_MANUAL_TAGGING","ROLE_BF_DELINKING")
                        .requestMatchers(HttpMethod.GET,"/api/erpLinking/getNotificationSummary/**").hasAnyRole("ROLE_BF_MANUAL_TAGGING")
                        .requestMatchers(HttpMethod.POST,"/api/erpLinking/notLinkedQatShipments/programId/**").hasAnyRole("ROLE_BF_MANUAL_TAGGING")
                        .requestMatchers(HttpMethod.GET,"/api/erpLinking/autoCompleteOrder/**").hasAnyRole("ROLE_BF_MANUAL_TAGGING")
                        .requestMatchers(HttpMethod.POST,"/api/erpLinking/autoCompletePu/**").hasAnyRole("ROLE_BF_MANUAL_TAGGING")
                        .requestMatchers(HttpMethod.POST,"/api/erpLinking/notLinkedErpShipments/**").hasAnyRole("ROLE_BF_MANUAL_TAGGING")
                        .requestMatchers(HttpMethod.POST,"/api/erpLinking/notLinkedErpShipments/tab3/**").hasAnyRole("ROLE_BF_MANUAL_TAGGING")
                        .requestMatchers(HttpMethod.POST,"/api/erpLinking/linkedShipments/programId/**").hasAnyRole("ROLE_BF_MANUAL_TAGGING")
                        .requestMatchers(HttpMethod.POST,"/api/erpLinking/batchDetails/**").hasAnyRole("ROLE_BF_MANUAL_TAGGING")
                        .requestMatchers(HttpMethod.POST,"/api/language/**").hasAnyRole("ROLE_BF_ADD_LANGUAGE")
                        .requestMatchers(HttpMethod.GET,"/api/language/**").hasAnyRole("ROLE_BF_ADD_COUNTRY","ROLE_BF_EDIT_COUNTRY","ROLE_BF_LABEL_TRANSLATIONS","ROLE_BF_ADD_USER","ROLE_BF_EDIT_USER","ROLE_BF_LIST_USER","ROLE_BF_EDIT_LANGUAGE")
                        .requestMatchers(HttpMethod.PUT,"/api/language/").hasAnyRole("ROLE_BF_EDIT_LANGUAGE")
                        .requestMatchers(HttpMethod.GET,"/api/language/all/**").hasAnyRole("ROLE_BF_ADD_COUNTRY","ROLE_BF_EDIT_COUNTRY","ROLE_BF_LIST_LANGUAGE","ROLE_BF_LABEL_TRANSLATIONS","ROLE_BF_ADD_USER","ROLE_BF_EDIT_USER","ROLE_BF_LIST_USER")
                        .requestMatchers("/api/getDatabaseLabelsListAll/").hasAnyRole("ROLE_BF_LABEL_TRANSLATIONS")
                        .requestMatchers("/api/getStaticLabelsListAll/").hasAnyRole("ROLE_BF_LABEL_TRANSLATIONS")
                        .requestMatchers("/api/saveDatabaseLabels/").hasAnyRole("ROLE_BUSINESS_FUNCTION_EDIT_APPLICATION_LABELS","ROLE_BUSINESS_FUNCTION_EDIT_REALM_LABELS","ROLE_BUSINESS_FUNCTION_EDIT_PROGRAM_LABELS")
                        .requestMatchers("/api/saveStaticLabels/").hasAnyRole("ROLE_BF_LABEL_TRANSLATIONS")
                        .requestMatchers("/api/programData/shipmentSync/programId/**").hasAnyRole("ROLE_BF_MASTER_DATA_SYNC")
                        .requestMatchers("/api/sync/allMasters/forPrograms/**").hasAnyRole("ROLE_BF_MASTER_DATA_SYNC")
                        .requestMatchers("/api/erpLinking/shipmentSync/").hasAnyRole("ROLE_BF_MASTER_DATA_SYNC")
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
//                //                .antMatchers(HttpMethod.POST, "/api/realm/**").access("hasAnyRole('ROLE_BF_UPDATE_APPL_MASTER')")
//                //                .antMatchers(HttpMethod.PUT, "/api/realm/**").access("hasAnyRole('ROLE_BF_UPDATE_APPL_MASTER', 'ROLE_BF_UPDATE_REALM_MASTER')")
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
