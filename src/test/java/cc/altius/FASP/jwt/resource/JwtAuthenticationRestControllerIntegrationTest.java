package cc.altius.FASP.jwt.resource;

import cc.altius.FASP.integration.AbstractIntegrationTest;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
public class JwtAuthenticationRestControllerIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private UserService userService;

    @Value("${jwt.get.token.uri}")
    private String tokenUri;

    @Value("${jwt.http.request.header}")
    private String tokenHeader;

    @Test
    public void testCreateAuthenticationToken_Success() throws Exception {

        JwtTokenRequest authRequest = new JwtTokenRequest("alexiodanje@gmail.com", "#@lexio@#", "1", false);
        mockMvc.perform(post(tokenUri)
                    .contentType("application/json")
                    .content(asJsonString(authRequest)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.token").exists())
            .andExpect(jsonPath("$.token").isNotEmpty());
    }

    @Test
    public void testCreateAuthenticationToken_InvalidCredentials() throws Exception {
        JwtTokenRequest authRequest = new JwtTokenRequest("alexiodanje@gmail.com", "wrongpassword", "1", false);
        mockMvc.perform(post(tokenUri)
                        .contentType("application/json")
                        .content(asJsonString(authRequest)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.messageCode").value("static.message.login.invalidCredentials"));
    }

    @Test
    public void testRefreshAndGetAuthenticationToken_ValidToken() throws Exception {
        CustomUserDetails userDetails = userService.getCustomUserByEmailId("alexiodanje@gmail.com");
        String validToken = jwtTokenUtil.generateToken(userDetails);

        mockMvc.perform(get("/refresh")
                        .header(tokenHeader, "Bearer " + validToken))
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.token").isNotEmpty());
    }
}
