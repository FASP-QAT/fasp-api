package cc.altius.FASP.integration;

import cc.altius.FASP.model.*;
import com.google.gson.Gson;
import jakarta.annotation.PostConstruct;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.SecurityContext;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserRestControllerIntegrationTest extends AbstractIntegrationTest {

    @PostConstruct
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }
    @Test
    @WithUserDetails("alexiodanje@gmail.com")
    public void shouldReturnUserWhenUserIdExists() throws Exception {
        int userId = 1;
        String jwtToken = jwtTokenUtil.generateToken((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        mockMvc.perform(get("/api/user/{userId}", userId)
                .header(tokenHeader, "Bearer " + jwtToken)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("alexiodanje@gmail.com")
    public void editUser_shouldUpdateUserSuccessfully() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(getUser())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.messageCode").value("static.message.updateSuccess"));
    }

    @Test
    @WithUserDetails("alexiodanje@gmail.com")
    public void logout_shouldSucceedWithValidToken() throws Exception {
        String jwtToken = jwtTokenUtil.generateToken((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/logout")
                        .header(tokenHeader, "Bearer " + jwtToken))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("alexiodanje@gmail.com")
    public void shouldReturnUserDetails() throws Exception {
        String jwtToken = jwtTokenUtil.generateToken((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/details")
                        .accept(MediaType.APPLICATION_JSON)
                        .header(tokenHeader, "Bearer " + jwtToken))
                .andExpect(status().isInternalServerError());
    }

}