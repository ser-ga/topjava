package ru.javawebinar.topjava.web;


import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ResourceControllerTest extends AbstractControllerTest {

    @Test
    void testCss() throws Exception {
        mockMvc.perform(get("/resources/css/style.css"))
                .andExpect(content().contentType(MediaType.parseMediaType("text/css")))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
