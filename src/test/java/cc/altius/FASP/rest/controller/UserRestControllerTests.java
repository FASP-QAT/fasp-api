package cc.altius.FASP;

import cc.altius.FASP.integration.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

}
