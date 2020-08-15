package sofuni.flashy.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class FlashcardControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "admin@example.com", roles = {"USER", "ADMIN"})
    public void testFlashcardAccess() throws Exception
    {
        mockMvc.perform(get("/flashcard/add")).
                andExpect(status().isOk()).
                andExpect(view().name("new")).
                andExpect(model().attributeExists("formData"));
    }

    @Test
    @WithMockUser(username = "admin@example.com", roles = {"USER", "ADMIN"})
    public void testFlashcardListAllAccess() throws Exception
    {
        mockMvc.perform(get("/flashcard/list")).
                andExpect(status().isOk()).
                andExpect(view().name("list")).
                andExpect(model().attributeExists("formDataAll"));
    }

    @Test
    @WithMockUser(username = "pesho", roles = {"USER"})
    public void testFlashcardDeleteAccess() throws Exception
    {
        mockMvc.perform(delete("/flashcard/delete")).
                andExpect(status().isForbidden());
    }
}
