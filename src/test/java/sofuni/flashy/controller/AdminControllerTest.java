package sofuni.flashy.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AdminControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "admin@example.com", roles = {"USER", "ADMIN"})
    public void testStatsAccess() throws Exception
    {
        mockMvc.perform(get("/admin/stats")).
                andExpect(status().isOk()).
                andExpect(view().name("stats")).
                andExpect(model().attributeExists("requestCount", "startedOn"));
    }

    @Test
    @WithMockUser(username = "pesho@example.com", roles = {"USER"})
    public void testStatsAccessDeniedForNormalUser() throws Exception
    {
        mockMvc.perform(get("/admin/stats")).
                andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin@example.com", roles = {"USER", "ADMIN"})
    public void testPlayerAccess() throws Exception
    {
        mockMvc.perform(get("/admin/players")).
                andExpect(status().isOk()).
                andExpect(view().name("players")).
                andExpect(model().attributeExists("players"));
    }

    @Test
    @WithMockUser(username = "pesho@example.com", roles = {"USER"})
    public void testPlayersAccessDeniedForNormalUser() throws Exception
    {
        mockMvc.perform(get("/admin/players")).
                andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "pesho@example.com", roles = {"USER"})
    public void testDeleteAccessDeniedForNormalUser() throws Exception
    {
        mockMvc.perform(get("/admin/delete")).
                andExpect(status().is4xxClientError());
    }
}
