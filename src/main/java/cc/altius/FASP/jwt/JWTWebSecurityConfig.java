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
                
                //UserService

                .antMatchers(HttpMethod.GET, "/api/getLanguageList/**").fullyAuthenticated()
                .antMatchers(HttpMethod.GET, "/api/businessFunction/**").access("hasRole('ROLE_BF_ADD_ROLE', 'ROLE_BF_EDIT_ROLE')")
                .antMatchers(HttpMethod.GET, "/api/realm/**").fullyAuthenticated()
                .antMatchers(HttpMethod.GET, "/api/role/**").access("hasRole('ROLE_BF_ADD_ROLE','ROLE_BF_EDIT_ROLE'.'ROLE_BF_LIST_ROLE','ROLE_BF_ADD_USER','ROLE_BF_EDIT_USER','ROLE_BF_LIST_USER')")
                .antMatchers(HttpMethod.POST, "/api/role/**").access("hasRole('ROLE_BF_ADD_ROLE')")
                .antMatchers(HttpMethod.PUT, "/api/role/**").access("hasRole('ROLE_BF_EDIT_ROLE')")
                .antMatchers(HttpMethod.GET, "/api/user/**").access("hasRole('ROLE_BF_LIST_USER')")
                .antMatchers(HttpMethod.POST, "/api/user/**").access("hasRole('ROLE_BF_ADD_USER')")
                .antMatchers(HttpMethod.PUT, "/api/user/**").access("hasRole('ROLE_BF_LIST_USER','ROLE_BF_EDIT_USER')")
                //.antMatchers(HttpMethod.PUT, "/api/unlockAccount/**").access("hasRole(')")
                .antMatchers(HttpMethod.POST, "/api/updateExpiredPassword/**").fullyAuthenticated()
                .antMatchers(HttpMethod.POST, "/api/changePassword/**").fullyAuthenticated()
                .antMatchers(HttpMethod.POST, "/api/forgotPassword/**").fullyAuthenticated()
                .antMatchers(HttpMethod.POST, "/api/confirmForgotPasswordToken/**").fullyAuthenticated()
                .antMatchers(HttpMethod.POST, "/api/updatePassword/**").fullyAuthenticated()
                .antMatchers(HttpMethod.PUT, "/api/accessControls/**").fullyAuthenticated()
                .antMatchers(HttpMethod.POST, "/api/user/language/**").fullyAuthenticated()
                .antMatchers(HttpMethod.POST, "/api/user/agreement/**").fullyAuthenticated()
                .antMatchers(HttpMethod.POST, "/api/user/module/**").fullyAuthenticated()

                .antMatchers(HttpMethod.POST, "/api/userManual/uploadUserManual/**").access("hasRole('ROLE_BF_UPLOAD_USER_MANUAL')")

                .antMatchers(HttpMethod.GET, "/api/usageTemplate/all/**").access("hasRole('ROLE_BF_LIST_USAGE_TEMPLATE')")
                .antMatchers(HttpMethod.GET, "/api/usageTemplate/**").access("hasRole('ROLE_BF_LIST_USAGE_TEMPLATE')")
                .antMatchers(HttpMethod.POST, "/api/usageTemplate/**").access("hasRole('ROLE_BF_LIST_USAGE_TEMPLATE')")
                .antMatchers(HttpMethod.GET, "/api/usageTemplate/tracerCategory/**").access("hasRole('ROLE_BF_EDIT_TREE_TEMPLATE','ROLE_BF_ADD_TREE_TEMPLATE','ROLE_BF_VIEW_TREE_TEMPLATES')")
                //UsagePeriodService
                .antMatchers(HttpMethod.GET, "/api/usagePeriod/all/**").access("hasRole('ROLE_BF_LIST_USAGE_PERIOD)")
                .antMatchers(HttpMethod.GET, "/api/usagePeriod/**").access("hasRole('ROLE_BF_EDIT_TREE_TEMPLATE','ROLE_BF_ADD_TREE_TEMPLATE','ROLE_BF_VIEW_TREE_TEMPLATES','ROLE_BF_LIST_USAGE_TEMPLATE')")
                .antMatchers(HttpMethod.POST, "/api/usagePeriod/**").access("hasRole('ROLE_BF_LIST_USAGE_PERIOD')")
                //UnitService
                .antMatchers(HttpMethod.POST, "/api/unit/**").access("hasRole('ROLE_BF_ADD_UNIT')")
                .antMatchers(HttpMethod.GET, "/api/unit/**").fullyAuthenticated()
                .antMatchers(HttpMethod.PUT, "/api/unit/**").access("hasRole('ROLE_BF_EDIT_UNIT')")
                .antMatchers(HttpMethod.POST, "/api/unit/dimension/**").access("hasRole('ROLE_BF_EDIT_TREE_TEMPLATE','ROLE_BF_ADD_TREE_TEMPLATE','ROLE_BF_VIEW_TREE_TEMPLATES')")
                //TracerCategoryService
                .antMatchers(HttpMethod.GET, "/api/tracerCategory/**").fullyAuthenticated()
                .antMatchers(HttpMethod.POST, "/api/tracerCategory/**").access("hasRole('ROLE_BF_ADD_TRACER_CATEGORY')")
                .antMatchers(HttpMethod.PUT, "/api/tracerCategory/**").access("hasRole('ROLE_BF_EDIT_TRACER_CATEGORY')")
                .antMatchers(HttpMethod.GET, "/api/tracerCategory/realmId/**").access("hasRole('ROLE_BF_LIST_PLANNING_UNIT','ROLE_BF_FORECAST_MATRIX_REPORT','ROLE_BF_PRODUCT_CATALOG_REPORT','ROLE_BF_STOCK_STATUS_GLOBAL_VIEW_REPORT','ROLE_BF_VIEW_STOCK_STATUS_MATRIX')")
                .antMatchers(HttpMethod.GET, "/api/planningUnit/tracerCategory/**").access("hasRole('ROLE_BF_LIST_PLANNING_UNIT_SETTING')")
                //TicketTypeService
                //.antMatchers(HttpMethod.GET, "/api/ticketType/**").access("hasRole(')")
                //TicketStatusService
                //.antMatchers(HttpMethod.GET, "/api/ticketStatus/**").access("hasRole(')")
                //TicketService
                //.antMatchers(HttpMethod.GET, "/api/ticket/**").access("hasRole(')")
                //.antMatchers(HttpMethod.PUT, "/api/ticket/**").access("hasRole(')")
                //SupplierService
                .antMatchers(HttpMethod.POST, "/api/supplier/**").access("hasRole('ROLE_BF_ADD_SUPPLIER')")
                .antMatchers(HttpMethod.GET, "/api/supplier/**").access("hasRole('ROLE_BF_MAP_PLANNING_UNIT_CAPACITY','ROLE_BF_ADD_PROCUREMENT_UNIT','ROLE_BF_LIST_SUPPLIER','ROLE_BF_EDIT_SUPPLIER','ROLE_BF_PIPELINE_PROGRAM_IMPORT')")
                .antMatchers(HttpMethod.PUT, "/api/supplier/**").access("hasRole('ROLE_BF_EDIT_SUPPLIER')")
                //subFundingSourceService
                //.antMatchers(HttpMethod.POST, "/api/subFundingSource/**").access("hasRole(')")
                //.antMatchers(HttpMethod.GET, "/api/subFundingSource/**").access("hasRole(')")
                //.antMatchers(HttpMethod.PUT, "/api/subFundingSource/**").access("hasRole(')")
                //SignupService
                .antMatchers(HttpMethod.GET, "/api/getCountryList/**").fullyAuthenticated()
                .antMatchers(HttpMethod.GET, "/api/getStateListByCountryId/**").fullyAuthenticated()
                .antMatchers(HttpMethod.GET, "/api/getStateList/**").fullyAuthenticated()
                .antMatchers(HttpMethod.GET, "/api/getCityList/**").fullyAuthenticated()
                .antMatchers(HttpMethod.GET, "/api/getCityList/**").fullyAuthenticated()
                //.antMatchers(HttpMethod.PUT, "/api/saveRegistration/**").access("hasRole(')")
                //ShipmentStatusService
                //.antMatchers(HttpMethod.PUT, "/api/addShipmentStatus/**").access("hasRole(')")
                //.antMatchers(HttpMethod.GET, "/api/getShipmentStatusListAll/**").access("hasRole(')")
                .antMatchers(HttpMethod.GET, "/api/getShipmentStatusListActive/**").access("hasRole('ROLE_BF_ANNUAL_SHIPMENT_COST_REPORT','ROLE_BF_SUPPLY_PLAN_VERSION_AND_REVIEW','ROLE_BF_SHIPMENT_OVERVIEW_REPORT','ROLE_BF_PIPELINE_PROGRAM_IMPORT')")
                //.antMatchers(HttpMethod.PUT, "/api/editShipmentStatus/**").access("hasRole(')")
                //ReportService
                .antMatchers(HttpMethod.POST, "/api/report/forecastMetricsMonthly/**").access("hasRole('ROLE_BF_FORECAST_ERROR_OVER_TIME_REPORT')")
                .antMatchers(HttpMethod.POST, "/api/report/globalConsumption/**").access("hasRole('ROLE_BF_CONSUMPTION_GLOBAL_VIEW_REPORT')")
                .antMatchers(HttpMethod.POST, "/api/report/forecastMetricsComparision/**").access("hasRole('ROLE_BF_FORECAST_MATRIX_REPORT')")
                //.antMatchers(HttpMethod.POST, "/api/budget/programIds/**").access("hasRole(')")
                .antMatchers(HttpMethod.POST, "/api/program/programIds/**").access("hasRole('ROLE_BF_PROCUREMENT_AGENT_REPORT')")
                .antMatchers(HttpMethod.POST, "/api/report/annualShipmentCost/**").access("hasRole('ROLE_BF_ANNUAL_SHIPMENT_COST_REPORT')")
                .antMatchers(HttpMethod.POST, "/api/report/stockStatusOverTime/**").access("hasRole('ROLE_BF_STOCK_STATUS_OVER_TIME_REPORT')")
                .antMatchers(HttpMethod.GET, "/api/programVersion/programId/**").access("hasRole('ROLE_BF_SUPPLY_PLAN_VERSION_AND_REVIEW')")
                .antMatchers(HttpMethod.POST, "/api/report/costOfInventory/**").access("hasRole('ROLE_BF_COST_OF_INVENTORY_REPORT')")
                .antMatchers(HttpMethod.POST, "/api/report/stockStatusVertical/**").access("hasRole('ROLE_BF_SUPPLY_PLAN_REPORT')")
                .antMatchers(HttpMethod.POST, "/api/report/inventoryTurns/**").access("hasRole('ROLE_BF_INVENTORY_TURNS_REPORT')")
                .antMatchers(HttpMethod.POST, "/api/report/stockAdjustmentReport/**").access("hasRole('ROLE_BF_STOCK_ADJUSTMENT_REPORT')")
                .antMatchers(HttpMethod.POST, "/api/report/procurementAgentShipmentReport/**").access("hasRole('ROLE_BF_SHIPMENT_COST_DETAILS_REPORT')")
                .antMatchers(HttpMethod.POST, "/api/report/fundingSourceShipmentReport/**").access("hasRole('ROLE_BF_FUNDER_REPORT','ROLE_BF_SHIPMENT_COST_DETAILS_REPORT')")
                .antMatchers(HttpMethod.POST, "/api/report/aggregateShipmentByProduct/**").access("hasRole('ROLE_BF_SHIPMENT_COST_DETAILS_REPORT')")
                .antMatchers(HttpMethod.POST, "/api/report/warehouseCapacityReport/**").access("hasRole('ROLE_BF_WAREHOUSE_CAPACITY_REPORT')")
                .antMatchers(HttpMethod.POST, "/api/report/stockStatusForProgram/**").access("hasRole('ROLE_BF_STOCK_STATUS_REPORT')")
                .antMatchers(HttpMethod.POST, "/api/report/programProductCatalog/**").access("hasRole('ROLE_BF_PRODUCT_CATALOG_REPORT')")
                .antMatchers(HttpMethod.POST, "/api/report/shipmentGlobalDemand/**").access("hasRole('ROLE_BF_GLOBAL_DEMAND_REPORT)")
                .antMatchers(HttpMethod.POST, "/api/report/programLeadTimes/**").access("hasRole('ROLE_BF_PROCUREMENT_AGENT_REPORT')")
                .antMatchers(HttpMethod.POST, "/api/report/shipmentDetails/**").access("hasRole('ROLE_BF_SHIPMENT_DETAILS_REPORT')")
                .antMatchers(HttpMethod.POST, "/api/report/shipmentOverview/**").access("hasRole('ROLE_BF_SHIPMENT_OVERVIEW_REPORT')")
                .antMatchers(HttpMethod.POST, "/api/report/budgetReport/**").access("hasRole('ROLE_BF_BUDGET_REPORT')")
                .antMatchers(HttpMethod.POST, "/api/report/stockStatusAcrossProducts/**").access("hasRole('ROLE_BF_STOCK_STATUS_GLOBAL_VIEW_REPORT')")
                .antMatchers(HttpMethod.POST, "/api/report/expiredStock/**").access("hasRole('ROLE_BF_EXPIRIES_REPORT')")
                .antMatchers(HttpMethod.POST, "/api/report/warehouseByCountry/**").access("hasRole('ROLE_BF_REGION')")
                .antMatchers(HttpMethod.POST, "/api/report/monthlyForecast/**").access("hasRole('ROLE_BF_SUPPLY_PLAN_IMPORT','ROLE_BF_LIST_MONTHLY_FORECAST')")
                .antMatchers(HttpMethod.POST, "/api/report/forecastSummary/**").access("hasRole('ROLE_BF_LIST_FORECAST_SUMMARY','ROLE_BF_VIEW_FORECAST_SUMMARY','ROLE_BF_COMPARE_VERSION')")
                .antMatchers(HttpMethod.POST, "/api/report/forecastError/**").access("hasRole('ROLE_BF_FORECAST_ERROR_OVER_TIME_REPORT')")
                //RegistrationService
                .antMatchers(HttpMethod.GET, "/api/getCountryList/**").access("hasRole(')")
                .antMatchers(HttpMethod.GET, "/api/getStateList/**").access("hasRole(')")
                .antMatchers(HttpMethod.GET, "/api/getCityList/**").access("hasRole(')")
                //.antMatchers(HttpMethod.PUT, "/api/saveRegistration/**").access("hasRole(')")
                //.antMatchers(HttpMethod.GET, "/api/getUserApprovalList/**").access("hasRole(')")
                //.antMatchers(HttpMethod.PUT, "/api/saveApproval/**").access("hasRole(')")
                //RegionService
                .antMatchers(HttpMethod.PUT, "/api/region//**").fullyAuthenticated()
                .antMatchers(HttpMethod.GET, "/api/region//**").fullyAuthenticated()
                .antMatchers(HttpMethod.GET, "/api/region/realmCountryId//**").access("hasRole('ROLE_BF_MAP_REGION','ROLE_BF_ADD_DATASET','ROLE_BF_EDIT_DATASET','ROLE_BF_PIPELINE_PROGRAM_IMPORT','ROLE_BF_CREATE_A_PROGRAM','ROLE_BF_EDIT_PROGRAM','ROLE_BF_SET_UP_PROGRAM')")
                //RealmService
                .antMatchers(HttpMethod.POST, "/api/realm/**").fullyAuthenticated()
                .antMatchers(HttpMethod.GET, "/api/realm/**").fullyAuthenticated()
                .antMatchers(HttpMethod.PUT, "/api/realm/**").access("hasRole('ROLE_BF_EDIT_REALM','ROLE_BF_LIST_REALM')")
                //RealmCountryService
                .antMatchers(HttpMethod.POST, "/api/realmCountry/**").access("hasRole('ROLE_BF_LIST_REALM_COUNTRY')")
                .antMatchers(HttpMethod.GET, "/api/realmCountry/**").fullyAuthenticated()
                //.antMatchers(HttpMethod.PUT, "/api/realmCountry/**").access("hasRole(')")
                .antMatchers(HttpMethod.GET, "/api/realmCountry/realmId/**").access("hasRole('ROLE_BF_ADD_HEALTH_AREA','ROLE_BF_EDIT_HEALTH_AREA','ROLE_BF_ADD_DATASET','ROLE_BF_CREATE_A_PROGRAM','ROLE_BF_SET_UP_PROGRAM','ROLE_BF_ADD_ORGANIZATION','ROLE_BF_EDIT_ORGANIZATION','ROLE_BF_LIST_DATASET','ROLE_BF_LIST_PROGRAM','ROLE_BF_MAP_REALM_COUNTRY')")
                .antMatchers(HttpMethod.GET, "/api/realmCountry/planningUnit/**").access("hasRole('ROLE_BF_MANAGE_REALM_COUNTRY_PLANNING_UNIT','ROLE_BF_LIST_ALTERNATE_REPORTING_UNIT')")
                .antMatchers(HttpMethod.PUT, "/api/realmCountry/programIds/planningUnit/**").access("hasRole('ROLE_BF_MANUAL_TAGGING','ROLE_BF_LIST_ALTERNATE_REPORTING_UNIT','ROLE_BF_SUPPLY_PLAN_VERSION_AND_REVIEW')")
                .antMatchers(HttpMethod.GET, "/api/realmCountry/program/realmId/**").access("hasRole('ROLE_BF_LOAD_DELETE_DATASET','ROLE_BF_MANUAL_TAGGING','ROLE_BF_REGION','ROLE_BF_FORECAST_MATRIX_REPORT','ROLE_BF_CONSUMPTION_GLOBAL_VIEW_REPORT','ROLE_BF_SHIPMENT_OVERVIEW_REPORT','ROLE_BF_GLOBAL_DEMAND_REPORT','ROLE_BF_STOCK_STATUS_GLOBAL_VIEW_REPORT','ROLE_BF_STOCK_STATUS_OVER_TIME_REPORT','ROLE_BF_SUPPLY_PLAN_VERSION_AND_REVIEW','ROLE_BF_WAREHOUSE_CAPACITY_REPORT')")
                //.antMatchers(HttpMethod.GET, "/api/realmCountry/program/**").access("hasRole(')")
                //QuantimedImportService
                .antMatchers(HttpMethod.POST, "/api/quantimed/quantimedImport/**").access("hasRole('ROLE_BF_QUANTIMED_IMPORT')")
                //ProgramService
                .antMatchers(HttpMethod.GET, "/api/programData/programId/**").access("hasRole('ROLE_BF_COMMIT_VERSION','ROLE_BF_SUPPLY_PLAN_VERSION_AND_REVIEW')")
                .antMatchers(HttpMethod.POST, "/api/programData/**").fullyAuthenticated()
                .antMatchers(HttpMethod.GET, "/api/program/**").fullyAuthenticated()
                .antMatchers(HttpMethod.POST, "/api/program/**").fullyAuthenticated()
                .antMatchers(HttpMethod.PUT, "/api/program/**").fullyAuthenticated()
                .antMatchers(HttpMethod.GET, "/api/dataset/**").fullyAuthenticated()
                .antMatchers(HttpMethod.GET, "/api/program/all/**").access("hasRole('ROLE_BF_LIST_PROGRAM')")
                .antMatchers(HttpMethod.GET, "/api/dataset/all/**").access("hasRole('ROLE_BF_LIST_DATASET','ROLE_BF_LIST_TREE','ROLE_BF_CONSUMPTION_FORECAST_ERROR','ROLE_BF_LIST_MONTHLY_FORECAST','ROLE_BF_LIST_FORECAST_SUMMARY','ROLE_BF_VIEW_FORECAST_SUMMARY')")
                //.antMatchers(HttpMethod.GET, "/api/loadProgram/**").access("hasRole(')")
                //.antMatchers(HttpMethod.GET, "/api/loadProgram/programId/**").access("hasRole(')")
                //.antMatchers(HttpMethod.GET, "/api/getProgramList/**").access("hasRole(')")
                .antMatchers(HttpMethod.GET, "/api/realmCountry/realmId/**").fullyAuthenticated()
                .antMatchers(HttpMethod.GET, "/api/organisation/realmId/**").fullyAuthenticated()
                .antMatchers(HttpMethod.GET, "/api/healthArea/realmId/**").fullyAuthenticated()
                .antMatchers(HttpMethod.GET, "/api/region/realmCountryId/**").fullyAuthenticated()
                //.antMatchers(HttpMethod.GET, "/api/programProduct/**").access("hasRole(')")
                //.antMatchers(HttpMethod.PUT, "/api/programProduct/**").access("hasRole(')")
                .antMatchers(HttpMethod.PUT, "/api/program/planningUnit/**").access("hasRole('ROLE_BF_ADD_PROGRAM_PRODUCT')")
                .antMatchers(HttpMethod.GET, "/api/user/realmId/**").access("hasRole('ROLE_BF_ADD_DATASET','ROLE_BF_EDIT_DATASET','ROLE_BF_CREATE_A_PROGRAM','ROLE_BF_EDIT_PROGRAM','ROLE_BF_SET_UP_PROGRAM','ROLE_BF_PIPELINE_PROGRAM_IMPORT')")
                .antMatchers(HttpMethod.GET, "/api/program/realmId/**").access("hasRole('ROLE_BF_PRODUCT_CATALOG_REPORT','ROLE_BF_EDIT_PROCUREMENT_AGENT','ROLE_BF_ADD_PROCUREMENT_AGENT','ROLE_BF_ADD_DATA_SOURCE')")
                .antMatchers(HttpMethod.PUT, "/api/programData/**").access("hasRole('ROLE_BF_COMMIT_VERSION')")
                .antMatchers(HttpMethod.POST, "/api/program/initialize/**").access("hasRole('ROLE_BF_SET_UP_PROGRAM')")
                //.antMatchers(HttpMethod.POST, "/api/pipeline/programsetup/**").access("hasRole(')")
                .antMatchers(HttpMethod.GET, "/api/versionStatus/**").access("hasRole('ROLE_BF_ADD_INTEGRATION_PROGRAM','ROLE_BF_SUPPLY_PLAN_VERSION_AND_REVIEW','ROLE_BF_SUPPLY_PLAN_VERSION_AND_REVIEW')")
                .antMatchers(HttpMethod.PUT, "/api/programVersion/programId/**").access("hasRole('ROLE_BF_SUPPLY_PLAN_VERSION_AND_REVIEW')")
                .antMatchers(HttpMethod.GET, "/api/versionType/**").access("hasRole('ROLE_BF_SUPPLY_PLAN_VERSION_AND_REVIEW','ROLE_BF_COMMIT_VERSION','ROLE_BF_ADD_INTEGRATION_PROGRAM','ROLE_BF_VERSION_SETTINGS')")
                //.antMatchers(HttpMethod.GET, "/api/programData/checkErpOrder/orderNo/**").access("hasRole(')")
                //.antMatchers(HttpMethod.GET, "/api/program/validate/realmId/**").access("hasRole(')")
                .antMatchers(HttpMethod.POST, "/api/programData/checkNewerVersions/**").fullyAuthenticated()
                .antMatchers(HttpMethod.GET, "/api/healthArea/realmCountryId/**").access("hasRole('ROLE_BF_ADD_DATASET','ROLE_BF_EDIT_DATASET','ROLE_BF_EDIT_PROGRAM','ROLE_BF_SET_UP_PROGRAM')")
                .antMatchers(HttpMethod.GET, "/api/organisation/realmCountryId/**").access("hasRole('ROLE_BF_ADD_DATASET','ROLE_BF_EDIT_DATASET','ROLE_BF_EDIT_PROGRAM','ROLE_BF_QUANTIMED_IMPORT','ROLE_BF_SET_UP_PROGRAM')")
                .antMatchers(HttpMethod.GET, "/api/problemStatus/**").access("hasRole('ROLE_BF_SUPPLY_PLAN_VERSION_AND_REVIEW')")
                .antMatchers(HttpMethod.GET, "/api/programData/getLatestVersionForProgram/**").fullyAuthenticated()
                .antMatchers(HttpMethod.POST, "/api/programData/getLatestVersionForPrograms/**").fullyAuthenticated()
                .antMatchers(HttpMethod.GET, "/api/programData/getLastModifiedDateForProgram/**").access("hasRole('ROLE_BF_COMMIT_VERSION')")
                .antMatchers(HttpMethod.POST, "/api/dataset/**").access("hasRole('ROLE_BF_ADD_DATASET)")
                .antMatchers(HttpMethod.PUT, "/api/dataset/**").access("hasRole('ROLE_BF_EDIT_DATASET')")
                .antMatchers(HttpMethod.GET, "/api/dataset/**").fullyAuthenticated()
                .antMatchers(HttpMethod.POST, "/api/program/actualConsumptionReport/**").access("hasRole('ROLE_BF_LIST_IMPORT_FROM_QAT_SUPPLY_PLAN')")
                .antMatchers(HttpMethod.GET, "/api/programData/checkIfCommitRequestExistsForProgram//**").access("hasRole('ROLE_BF_COMMIT_VERSION')")
                //.antMatchers(HttpMethod.POST, "/api/getCommitRequest/**").access("hasRole(')")
                .antMatchers(HttpMethod.GET, "/api/sendNotification/**").access("hasRole('ROLE_BF_COMMIT_VERSION','ROLE_BF_COMMIT_DATASET')")
                .antMatchers(HttpMethod.POST, "/api/erpLinking/otherProgramCheck/**").access("hasRole('ROLE_BF_COMMIT_VERSION')")
                .antMatchers(HttpMethod.GET, "/api/user/programId/**").access("hasRole('ROLE_BF_EDIT_DATASET','ROLE_BF_EDIT_PROGRAM')")
                .antMatchers(HttpMethod.POST, "/api/problemReport/createManualProblem/**").access("hasRole('ROLE_BF_SUPPLY_PLAN_VERSION_AND_REVIEW')")
                .antMatchers(HttpMethod.POST, "/api/dataset/versions/**").access("hasRole('ROLE_BF_VERSION_SETTINGS')")
                .antMatchers(HttpMethod.GET, "/api/programForDropDown/programType/**").access("hasRole('ROLE_BF_ADD_PROGRAM_PRODUCT')")
                //ProductService
                //.antMatchers(HttpMethod.POST, "/api/product/**").access("hasRole(')")
                //.antMatchers(HttpMethod.GET, "/api/product/**").access("hasRole(')")
                //.antMatchers(HttpMethod.PUT, "/api/product/**").access("hasRole(')")
                //.antMatchers(HttpMethod.GET, "/api/productCategory/realmId/**").access("hasRole(')")
                .antMatchers(HttpMethod.POST, "/api/report/consumptionForecastVsActual/**").access("hasRole('ROLE_BF_CONSUMPTION_REPORT')")
                .antMatchers(HttpMethod.POST, "/api/report/stockStatusMatrix/**").access("hasRole('ROLE_BF_STOCK_STATUS_MATRIX_REPORT')")
                //ProcurementUnitservice
                .antMatchers(HttpMethod.GET, "/api/procurementUnit/all/**").access("hasRole('ROLE_BF_MAP_PROCUREMENT_UNIT','ROLE_BF_LIST_PROCUREMENT_UNIT')")
                .antMatchers(HttpMethod.GET, "/api/procurementUnit/**").access("hasRole('ROLE_BF_EDIT_PROCUREMENT_UNIT')")
                .antMatchers(HttpMethod.POST, "/api/procurementUnit/**").access("hasRole('ROLE_BF_ADD_PROCUREMENT_UNIT')")
                .antMatchers(HttpMethod.PUT, "/api/procurementUnit/**").access("hasRole('ROLE_BF_EDIT_PROCUREMENT_UNIT')")
                //.antMatchers(HttpMethod.GET, "/api/procurementUnit/realmId/**").access("hasRole(')")
                //ProcurementAgentService
                .antMatchers(HttpMethod.POST, "/api/procurementAgent/**").access("hasRole('ROLE_BF_ADD_PROCUREMENT_AGENT')")
                .antMatchers(HttpMethod.GET, "/api/procurementAgent/**").fullyAuthenticated()
                .antMatchers(HttpMethod.PUT, "/api/procurementAgent/**").access("hasRole('ROLE_BF_EDIT_PROCUREMENT_AGENT')")
                .antMatchers(HttpMethod.GET, "/api/program/planningUnit/procurementAgent/**").access("hasRole('ROLE_BF_MAP_COUNTRY_SPECIFIC_PRICES')")
                .antMatchers(HttpMethod.PUT, "/api/procurementAgent/planningUnit/**").access("hasRole('ROLE_BF_MAP_PLANNING_UNIT')")
                .antMatchers(HttpMethod.PUT, "/api/program/planningingUnit/procurementAgent/**").access("hasRole('ROLE_BF_MAP_COUNTRY_SPECIFIC_PRICES')")
                .antMatchers(HttpMethod.PUT, "/api/procurementAgent/procurementUnit/**").access("hasRole('ROLE_BF_MAP_PROCUREMENT_UNIT')")
                .antMatchers(HttpMethod.POST, "/api/procurementAgentType/**").access("hasRole('ROLE_BF_ADD_PROCUREMENT_AGENT')")
                .antMatchers(HttpMethod.GET, "/api/procurementAgentType/**").access("hasRole('ROLE_BF_ADD_PROCUREMENT_AGENT','ROLE_BF_EDIT_PROCUREMENT_AGENT','ROLE_BF_LIST_PROCUREMENT_AGENT','ROLE_BF_GLOBAL_DEMAND_REPORT')")
                .antMatchers(HttpMethod.PUT, "/api/procurementAgentType/**").access("hasRole('ROLE_BF_EDIT_PROCUREMENT_AGENT')")
                //ProductCategoryService
                //.antMatchers(HttpMethod.GET, "/api/productCategory/realmId/**").access("hasRole(')")
                .antMatchers(HttpMethod.PUT, "/api/productCategory/**").access("hasRole('ROLE_BF_LIST_PRODUCT_CATEGORY)")
                .antMatchers(HttpMethod.POST, "/api/planningUnit/**").access("hasRole('ROLE_BF_ADD_PLANNING_UNIT')")
                .antMatchers(HttpMethod.GET, "/api/planningUnit/**").fullyAuthenticated()
                .antMatchers(HttpMethod.PUT, "/api/planningUnit/**").access("hasRole('ROLE_BF_EDIT_PLANNING_UNIT','ROLE_BF_MAP_PLANNING_UNIT_CAPACITY')")
                .antMatchers(HttpMethod.GET, "/api/planningUnit/all/**").access("hasRole('ROLE_BF_LIST_PLANNING_UNIT_CAPACITY','ROLE_BF_MAP_PLANNING_UNIT','ROLE_BF_ADD_PROGRAM_PRODUCT','ROLE_BF_LIST_ALTERNATE_REPORTING_UNIT','ROLE_BF_PIPELINE_PROGRAM_IMPORT')")
                .antMatchers(HttpMethod.GET, "/api/planningUnit/realmId/**").access("hasRole('ROLE_BF_LIST_PLANNING_UNIT_SETTING','ROLE_BF_LIST_PLANNING_UNIT')")
                .antMatchers(HttpMethod.GET, "/api/planningUnit/capacity/**").access("hasRole('ROLE_BF_MAP_PLANNING_UNIT_CAPACITY')")
                .antMatchers(HttpMethod.PUT, "/api/planningUnit/capacity/**").access("hasRole('ROLE_BF_MAP_PLANNING_UNIT_CAPACITY')")
                .antMatchers(HttpMethod.POST, "/api/planningUnit/programs/**").access("hasRole('ROLE_BF_LIST_ALTERNATE_REPORTING_UNIT,'ROLE_BF_FORECAST_MATRIX_REPORT','ROLE_BF_CONSUMPTION_GLOBAL_VIEW_REPORT','ROLE_BF_SHIPMENT_OVERVIEW_REPORT')")
                .antMatchers(HttpMethod.GET, "/api/planningUnit/productCategory/**").access("hasRole('ROLE_BF_MANUAL_TAGGING','ROLE_BF_SHIPMENT_OVERVIEW_REPORT','ROLE_BF_GLOBAL_DEMAND_REPORT')")
                .antMatchers(HttpMethod.POST, "/api/planningUnit/productCategoryList/active/**").access("hasRole('ROLE_BF_MANUAL_TAGGING')")
                .antMatchers(HttpMethod.GET, "/api/planningUnit/realmCountry/**").access("hasRole('ROLE_BF_MANUAL_TAGGING')")
                .antMatchers(HttpMethod.GET, "/api/planningUnit/forecastingUnit/**").access("hasRole('ROLE_BF_EDIT_TREE_TEMPLATE','ROLE_BF_ADD_TREE_TEMPLATE','ROLE_BF_VIEW_TREE_TEMPLATES')")
                .antMatchers(HttpMethod.POST, "/api/planningUnit/tracerCategory/program/**").access("hasRole('ROLE_BF_FORECAST_MATRIX_REPORT')")
                .antMatchers(HttpMethod.POST, "/api/procurementAgent/planningUnits/**").access("hasRole('ROLE_BF_LIST_PLANNING_UNIT_SETTING')")

                .antMatchers(HttpMethod.GET, "/api/planningUnit/programId/**").access("hasRole('ROLE_BF_LIST_MONTHLY_FORECAST','ROLE_BF_LIST_IMPORT_FROM_QAT_SUPPLY_PLAN','ROLE_BF_SUPPLY_PLAN_IMPORT')")
                .antMatchers(HttpMethod.GET, "/api/planningUnit/withPricing/productCategory/**").access("hasRole('ROLE_BF_LIST_PLANNING_UNIT_SETTING')")
                .antMatchers(HttpMethod.GET, "/api/planningUnit/basic/**").access("hasRole('ROLE_BF_ADD_PROGRAM_PRODUCT')")
                .antMatchers(HttpMethod.GET, "/api/planningUnit/capacity/all/**").access("hasRole('ROLE_BF_LIST_PLANNING_UNIT_CAPACITY')")
                .antMatchers(HttpMethod.POST, "/api/pipelineJson/**").access("hasRole('ROLE_BF_PIPELINE_PROGRAM_IMPORT')")
                .antMatchers(HttpMethod.GET, "/api/pipeline/**").access("hasRole('ROLE_BF_PIPELINE_PROGRAM_IMPORT')")
                .antMatchers(HttpMethod.POST, "/api/qatTemp/program/**").access("hasRole('ROLE_BF_PIPELINE_PROGRAM_IMPORT')")
                .antMatchers(HttpMethod.GET, "/api/qatTemp/program/**").access("hasRole('ROLE_BF_PIPELINE_PROGRAM_IMPORT')")
                .antMatchers(HttpMethod.GET, "/api/pipeline/product/**").access("hasRole('ROLE_BF_PIPELINE_PROGRAM_IMPORT')")
                .antMatchers(HttpMethod.GET, "/api/pipeline/shipment/**").access("hasRole('ROLE_BF_PIPELINE_PROGRAM_IMPORT')")
                .antMatchers(HttpMethod.PUT, "/api/pipeline/planningUnit/**").access("hasRole('ROLE_BF_PIPELINE_PROGRAM_IMPORT')")
                .antMatchers(HttpMethod.GET, "/api/qatTemp/planningUnitList/**").access("hasRole('ROLE_BF_PIPELINE_PROGRAM_IMPORT')")
                .antMatchers(HttpMethod.GET, "/api/pipeline/consumption/**").access("hasRole('ROLE_BF_PIPELINE_PROGRAM_IMPORT')")
                .antMatchers(HttpMethod.GET, "/api/qatTemp/regions/**").access("hasRole('ROLE_BF_PIPELINE_PROGRAM_IMPORT')")
                .antMatchers(HttpMethod.PUT, "/api/pipeline/consumption/**").access("hasRole('ROLE_BF_PIPELINE_PROGRAM_IMPORT')")
                .antMatchers(HttpMethod.GET, "/api/qatTemp/consumption/**").access("hasRole('ROLE_BF_PIPELINE_PROGRAM_IMPORT')")
                .antMatchers(HttpMethod.POST, "/api/pipeline/shipment/**").access("hasRole('ROLE_BF_PIPELINE_PROGRAM_IMPORT')")
                .antMatchers(HttpMethod.GET, "/api/pipeline/inventory/**").access("hasRole('ROLE_BF_PIPELINE_PROGRAM_IMPORT')")
                .antMatchers(HttpMethod.PUT, "/api/pipeline/inventory/**").access("hasRole('ROLE_BF_PIPELINE_PROGRAM_IMPORT')")
                .antMatchers(HttpMethod.GET, "/api/qatTemp/planningUnitListFinalInventry/**").access("hasRole('ROLE_BF_PIPELINE_PROGRAM_IMPORT')")
                .antMatchers(HttpMethod.POST, "/api/pipeline/programdata/**").access("hasRole('ROLE_BF_PIPELINE_PROGRAM_IMPORT')")
                .antMatchers(HttpMethod.GET, "/api/qatTemp/datasource/**").access("hasRole('ROLE_BF_PIPELINE_PROGRAM_IMPORT')")
                .antMatchers(HttpMethod.PUT, "/api/pipeline/datasource/**").access("hasRole('ROLE_BF_PIPELINE_PROGRAM_IMPORT')")
                .antMatchers(HttpMethod.GET, "/api/qatTemp/fundingsource/**").access("hasRole('ROLE_BF_PIPELINE_PROGRAM_IMPORT')")
                .antMatchers(HttpMethod.PUT, "/api/pipeline/fundingsource/**").access("hasRole('ROLE_BF_PIPELINE_PROGRAM_IMPORT')")
                .antMatchers(HttpMethod.GET, "/api/qatTemp/procurementagent/**").access("hasRole('ROLE_BF_PIPELINE_PROGRAM_IMPORT')")
                .antMatchers(HttpMethod.PUT, "/api/pipeline/procurementagent/**").access("hasRole('ROLE_BF_PIPELINE_PROGRAM_IMPORT')")
                .antMatchers(HttpMethod.PUT, "/api/pipeline/realmCountryPlanningUnit/**").access("hasRole('ROLE_BF_PIPELINE_PROGRAM_IMPORT')")
                //OrganisationTypeService
                .antMatchers(HttpMethod.POST, "/api/organisationType/**").access("hasRole('ROLE_BF_ADD_ORGANIZATION_TYPE')")
                .antMatchers(HttpMethod.GET, "/api/organisationType/**").access("hasRole('ROLE_BF_EDIT_ORGANIZATION_TYPE')")
                .antMatchers(HttpMethod.PUT, "/api/organisationType/**").access("hasRole('ROLE_BF_EDIT_ORGANIZATION_TYPE')")
                .antMatchers(HttpMethod.GET, "/api/organisationType/all/**").access("hasRole('ROLE_BF_LIST_ORGANIZATION_TYPE')")
                .antMatchers(HttpMethod.GET, "/api/organisationType/realmId/**").access("hasRole('ROLE_BF_ADD_ORGANIZATION_TYPE','ROLE_BF_EDIT_ORGANIZATION_TYPE')")
                .antMatchers(HttpMethod.POST, "/api/organisation/**").access("hasRole('ROLE_BF_ADD_ORGANIZATION')")
                .antMatchers(HttpMethod.GET, "/api/organisation/**").fullyAuthenticated()
                .antMatchers(HttpMethod.PUT, "/api/organisation/**").access("hasRole('ROLE_BF_EDIT_ORGANIZATION')")
                .antMatchers(HttpMethod.GET, "/api/realmCountry/realmId/**").access("hasRole('ROLE_BF_ADD_ORGANIZATION','ROLE_BF_EDIT_ORGANIZATION')")
                .antMatchers(HttpMethod.GET, "/api/organisation/getDisplayName/realmId/**").access("hasRole('ROLE_BF_ADD_ORGANIZATION')")
                .antMatchers(HttpMethod.GET, "/api/modelingType/**").access("hasRole('ROLE_BF_EDIT_TREE_TEMPLATE','ROLE_BF_ADD_TREE_TEMPLATE','ROLE_BF_VIEW_TREE_TEMPLATES')")
                .antMatchers(HttpMethod.POST, "/api/modelingType/**").access("hasRole('ROLE_BF_LIST_MODELING_TYPE')")
                .antMatchers(HttpMethod.GET, "/api/modelingType/all/**").access("hasRole('ROLE_BF_EDIT_TREE_TEMPLATE','ROLE_BF_ADD_TREE_TEMPLATE','ROLE_BF_VIEW_TREE_TEMPLATES','ROLE_BF_LIST_MODELING_TYPE')")
                //MasterSyncService
                //.antMatchers(HttpMethod.GET, "/api/sync/language/**").access("hasRole(')")
                //.antMatchers(HttpMethod.GET, "/api/sync/country/**").access("hasRole(')")
                //.antMatchers(HttpMethod.GET, "/api/sync/budget/**").access("hasRole(')")
                //.antMatchers(HttpMethod.GET, "/api/sync/currency/**").access("hasRole(')")
                //.antMatchers(HttpMethod.GET, "/api/sync/unit/**").access("hasRole(')")
                //.antMatchers(HttpMethod.GET, "/api/sync/organisation/**").access("hasRole(')")
                //.antMatchers(HttpMethod.GET, "/api/sync/procurementAgent/**").access("hasRole(')")
                //.antMatchers(HttpMethod.GET, "/api/sync/healthArea/**").access("hasRole(')")
                //.antMatchers(HttpMethod.GET, "/api/sync/region/**").access("hasRole(')")
                //.antMatchers(HttpMethod.GET, "/api/sync/fundingSource/**").access("hasRole(')")
                //.antMatchers(HttpMethod.GET, "/api/sync/subFundingSource/**").access("hasRole(')")
                //.antMatchers(HttpMethod.GET, "/api/sync/supplier/**").access("hasRole(')")
                //.antMatchers(HttpMethod.GET, "/api/sync/tracerCategory/**").access("hasRole(')")
                //.antMatchers(HttpMethod.GET, "/api/sync/productCategory/**").access("hasRole(')")
                //.antMatchers(HttpMethod.GET, "/api/sync/program/**").access("hasRole(')")
                //.antMatchers(HttpMethod.GET, "/api/sync/realmCountry/**").access("hasRole(')")
                //.antMatchers(HttpMethod.GET, "/api/sync/realm/**").access("hasRole(')")
                //.antMatchers(HttpMethod.GET, "/api/sync/dataSource/**").access("hasRole(')")
                //.antMatchers(HttpMethod.GET, "/api/sync/dimension/**").access("hasRole(')")
                //.antMatchers(HttpMethod.GET, "/api/sync/dataSourceType/**").access("hasRole(')")
                //.antMatchers(HttpMethod.GET, "/api/sync/procurementUnit/**").access("hasRole(')")
                //.antMatchers(HttpMethod.GET, "/api/sync/planningUnit/**").access("hasRole(')")
                //.antMatchers(HttpMethod.GET, "/api/sync/forecastingUnit/**").access("hasRole(')")
                //.antMatchers(HttpMethod.GET, "/api/sync/realmCountryPlanningUnit/**").access("hasRole(')")
                //.antMatchers(HttpMethod.GET, "/api/sync/programPlanningUnit/**").access("hasRole(')")
                //.antMatchers(HttpMethod.GET, "/api/sync/procurementAgent/planningUnit/**").access("hasRole(')")
                //.antMatchers(HttpMethod.GET, "/api/sync/procurementAgent/procurementUnit/**").access("hasRole(')")
                //.antMatchers(HttpMethod.GET, "/api/sync/shipmentStatus/**").access("hasRole(')")
                //.antMatchers(HttpMethod.GET, "/api/sync/problem/lastSyncDate/**").access("hasRole(')")
                //.antMatchers(HttpMethod.GET, "/api/sync/problemStatus/**").access("hasRole(')")
                //.antMatchers(HttpMethod.GET, "/api/sync/problemCategory/**").access("hasRole(')")
                //.antMatchers(HttpMethod.GET, "/api/programData/shipmentSync/programId/**").access("hasRole(')")
                //.antMatchers(HttpMethod.GET, "/api/sync/allMasters/**").access("hasRole(')")
                //.antMatchers(HttpMethod.POST, "/api/sync/allMasters/forPrograms/**").access("hasRole(')")
                //.antMatchers(HttpMethod.POST, "/api/erpLinking/shipmentSync/**").access("hasRole(')")
                //ManualTaggingService
                .antMatchers(HttpMethod.POST, "/api/manualTagging/**").access("hasRole('ROLE_BF_MANUAL_TAGGING','ROLE_BF_DELINKING')")
                .antMatchers(HttpMethod.GET, "/api/erpLinking/shipmentLinkingNotification/programId/**").access("hasRole('ROLE_BF_MANUAL_TAGGING')")
                .antMatchers(HttpMethod.PUT, "/api/erpLinking/updateNotification/**").access("hasRole('ROLE_BF_MANUAL_TAGGING')")
                .antMatchers(HttpMethod.GET, "/api/manualTagging/notLinkedShipments/**").access("hasRole('ROLE_BF_MANUAL_TAGGING','ROLE_BF_DELINKING')")
                .antMatchers(HttpMethod.GET, "/api/erpLinking/artmisHistory/**").access("hasRole('ROLE_BF_MANUAL_TAGGING','ROLE_BF_DELINKING')")
                .antMatchers(HttpMethod.GET, "/api/erpLinking/getNotificationCount/**").access("hasRole('ROLE_BF_MANUAL_TAGGING','ROLE_BF_DELINKING')")
                .antMatchers(HttpMethod.GET, "/api/orderDetails/**").access("hasRole('ROLE_BF_MANUAL_TAGGING','ROLE_BF_DELINKING')")
                .antMatchers(HttpMethod.POST, "/api/linkShipmentWithARTMIS/**").access("hasRole('ROLE_BF_MANUAL_TAGGING','ROLE_BF_DELINKING')")
                .antMatchers(HttpMethod.POST, "/api/delinkShipment/**").access("hasRole('ROLE_BF_DELINKING')")
                .antMatchers(HttpMethod.GET, "/api/shipmentListForDelinking/**").access("hasRole('ROLE_BF_DELINKING')")
                //.antMatchers(HttpMethod.GET, "/api/searchErpOrderData/**").access("hasRole(')")
                .antMatchers(HttpMethod.POST, "/api/getShipmentDetailsByParentShipmentId/**").access("hasRole('ROLE_BF_MANUAL_TAGGING','ROLE_BF_DELINKING')")
                .antMatchers(HttpMethod.GET, "/api/erpLinking/getNotificationSummary/**").access("hasRole('ROLE_BF_MANUAL_TAGGING')")
                .antMatchers(HttpMethod.POST, "/api/erpLinking/notLinkedQatShipments/programId/**").access("hasRole('ROLE_BF_MANUAL_TAGGING')")
                .antMatchers(HttpMethod.GET, "/api/erpLinking/autoCompleteOrder/**").access("hasRole('ROLE_BF_MANUAL_TAGGING')")
                .antMatchers(HttpMethod.POST, "/api/erpLinking/autoCompletePu/**").access("hasRole('ROLE_BF_MANUAL_TAGGING')")
                .antMatchers(HttpMethod.POST, "/api/erpLinking/notLinkedErpShipments/**").access("hasRole('ROLE_BF_MANUAL_TAGGING')")
                .antMatchers(HttpMethod.POST, "/api/erpLinking/notLinkedErpShipments/tab3/**").access("hasRole('ROLE_BF_MANUAL_TAGGING')")
                .antMatchers(HttpMethod.POST, "/api/erpLinking/linkedShipments/programId/**").access("hasRole('ROLE_BF_MANUAL_TAGGING')")
                .antMatchers(HttpMethod.POST, "/api/erpLinking/batchDetails/**").access("hasRole('ROLE_BF_MANUAL_TAGGING')")
                //.antMatchers(HttpMethod.GET, "/api/logout/**").access("hasRole()")
                //.antMatchers(HttpMethod.POST, "/authenticate/**").access("hasRole()")
                //.antMatchers(HttpMethod.GET, "//actuator/info/**").access("hasRole()")
                //LanguageService
                .antMatchers(HttpMethod.POST, "/api/language/**").access("hasRole('ROLE_BF_ADD_LANGUAGE')")
                .antMatchers(HttpMethod.GET, "/api/language/**").access("hasRole('ROLE_BF_ADD_COUNTRY','ROLE_BF_EDIT_COUNTRY','ROLE_BF_LABEL_TRANSLATIONS','ROLE_BF_ADD_USER','ROLE_BF_EDIT_USER','ROLE_BF_LIST_USER','ROLE_BF_EDIT_LANGUAGE')")
                .antMatchers(HttpMethod.GET, "/api/language/**").access("hasRole('ROLE_BF_EDIT_LANGUAGE')")
                .antMatchers(HttpMethod.GET, "/api/language/all/**").access("hasRole('ROLE_BF_ADD_COUNTRY','ROLE_BF_EDIT_COUNTRY','ROLE_BF_LIST_LANGUAGE','ROLE_BF_LABEL_TRANSLATIONS','ROLE_BF_ADD_USER','ROLE_BF_EDIT_USER','ROLE_BF_LIST_USER')")
                //.antMatchers(HttpMethod.GET, "/api/getDatabaseLabelsListAll/**").access("hasRole()")
                //.antMatchers(HttpMethod.GET, "/api/getStaticLabelsListAll/**").access("hasRole()")
                //.antMatchers(HttpMethod.PUT, "/api/saveDatabaseLabels/**").access("hasRole()")
                //.antMatchers(HttpMethod.PUT, "/api/saveStaticLabels/**").access("hasRole()")
                
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
