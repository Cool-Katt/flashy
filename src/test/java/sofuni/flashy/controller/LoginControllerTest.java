package sofuni.flashy.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "admin@example.com", roles = {"USER", "ADMIN"})
    public void testLoginAccess() throws Exception
    {
        mockMvc.perform(get("/login")).
                andExpect(status().isOk()).
                andExpect(view().name("login")).
                andExpect(model().attributeExists("formData"));
    }

    @Test
    @WithMockUser(username = "admin@example.com", roles = {"USER", "ADMIN"})
    public void testRegistrationAccess() throws Exception
    {
        mockMvc.perform(get("/registration")).
                andExpect(status().isOk()).
                andExpect(view().name("registration")).
                andExpect(model().attributeExists("formData"));
    }
}
