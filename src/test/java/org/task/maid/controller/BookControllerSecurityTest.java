//package org.task.maid.controller;
//
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.test.context.support.WithAnonymousUser;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class BookControllerSecurityTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//
//    @Test
//    @WithMockUser(roles = "USER")
//    void testGetAllBooks_WithAuthenticatedUser() throws Exception {
//        mockMvc.perform(get("/books")
//                        .param("page", "0")
//                        .param("size", "10"))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    @WithMockUser(roles = "USER")
//    void testGetBookById_WithAuthenticatedUser() throws Exception {
//        mockMvc.perform(get("/books/1"))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    @WithMockUser(roles = "USER")
//    void testAddBook_WithAuthenticatedUser() throws Exception {
//        mockMvc.perform(post("/books")
//                        .contentType("application/json")
//                        .content("{\"title\":\"New Book\",\"author\":\"Author Name\"}"))
//                .andExpect(status().isCreated());
//    }
//
//    @Test
//    @WithMockUser(roles = "USER")
//    void testDeleteBook_WithAuthenticatedUser() throws Exception {
//        mockMvc.perform(delete("/books/1"))
//                .andExpect(status().isNoContent());
//    }
//
//
//    @Test
//    @WithAnonymousUser
//    void testGetAllBooks_WithoutAuthentication_ShouldFail() throws Exception {
//        mockMvc.perform(get("/books"))
//                .andExpect(status().isUnauthorized());
//    }
//
//    @Test
//    @WithAnonymousUser
//    void testAddBook_WithoutAuthentication_ShouldFail() throws Exception {
//        mockMvc.perform(post("/books")
//                        .contentType("application/json")
//                        .content("{\"title\":\"New Book\",\"author\":\"Author Name\"}"))
//                .andExpect(status().isUnauthorized());
//    }
//
//
//
//
//
//
//
//
//}
