package com.asycoo.library;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class LoanControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private static final String LOAN_BODY = """
            {"bookId":"B002","memberId":"M001"}
            """;

    @Test
    void borrowBook_应返回201() throws Exception {
        mockMvc.perform(post("/api/loans")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(LOAN_BODY))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.bookId").value("B002"));
    }

    @Test
    void borrowBook_重复借阅_应返回400() throws Exception {
        mockMvc.perform(post("/api/loans")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(LOAN_BODY))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/api/loans")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(LOAN_BODY))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(4002));
    }

    @Test
    void returnBook_应返回罚款0() throws Exception {
        mockMvc.perform(post("/api/loans")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"bookId":"B003","memberId":"M002"}
                                """))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/api/loans/return")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"bookId":"B003","memberId":"M002"}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.fine").value(0.0))
                .andExpect(jsonPath("$.data.loan.returnDate").exists());
    }

    @Test
    void listActiveLoans_借书后应有记录() throws Exception {
        mockMvc.perform(post("/api/loans")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"bookId":"B001","memberId":"M001"}
                                """))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/loans"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[?(@.bookId=='B001')]").exists());
    }
}
