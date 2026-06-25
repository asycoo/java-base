package com.asycoo.library;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void listBooks_应返回3本样例书() throws Exception {
        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data.length()").value(3));
    }

    @Test
    void createBook_应返回201() throws Exception {
        String body = """
                {"id":"B100","title":"Spring实战","author":"Craig","price":99.0}
                """;
        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id").value("B100"));
    }

    @Test
    void createBook_缺少title_应返回400() throws Exception {
        String body = """
                {"id":"B101","author":"Craig","price":99.0}
                """;
        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(4001));
    }

    @Test
    void getBook_不存在_应返回404() throws Exception {
        mockMvc.perform(get("/api/books/NOT_EXIST"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(4041))
                .andExpect(jsonPath("$.message").value("图书不存在: NOT_EXIST"));
    }
}
