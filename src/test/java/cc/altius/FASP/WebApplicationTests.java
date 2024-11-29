package cc.altius.FASP;

import cc.altius.FASP.integration.AbstractIntegrationTest;
import cc.altius.FASP.model.Password;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class WebApplicationTests extends AbstractIntegrationTest {

    @Test
    void contextLoads() {
    }

    @Test
    @WithUserDetails("alexiodanje@gmail.com")
    public void testGetRoleById() throws Exception {
        String roleId = "ROLE_APPLICATION_ADMIN";
        mockMvc.perform(get("/api/role/{roleId}", roleId))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("alexiodanje@gmail.com")
    public void testGetBusinessFunctionList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/businessFunction"))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("alexiodanje@gmail.com")
    public void testUpdateExpiredPassword() throws Exception {
        Password password = new Password();
        password.setUserId(2100);
        password.setEmailId("alexiodanje@gmail.com");
        password.setOldPassword("#@ruth@#");
        password.setNewPassword("#@danje@#");

        mockMvc.perform(post("/api/updateExpiredPassword")
                        .contentType("application/json")
                        .content(asJsonString(password)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.token").isNotEmpty());
    }

}
