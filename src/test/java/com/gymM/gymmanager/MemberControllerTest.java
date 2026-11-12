package com.gymM.gymmanager;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(MemberController.class)
public class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MemberRepository memberRepository;

    @Test
    public void testGetMemberById_Found() throws Exception {
        // 1. Create a fake Member
        Member fakeMember = new Member();
        fakeMember.setId(1L);
        fakeMember.setFirstName("Kaleb");
        fakeMember.setLastName("Kaz");

        // 2. Tell the Mock: "When the Controller asks for ID 1, give it this fake member"
        Mockito.when(memberRepository.findById(1L)).thenReturn(Optional.of(fakeMember));

        // 3. Simulate a web request to /api/members/1 and check the results
        mockMvc.perform(MockMvcRequestBuilders.get("/api/members/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.firstName").value("Kaleb"));
    }

    @Test
    public void testGetMemberById_NotFound() throws Exception {
        // 1. Tell the Mock: "When the Controller asks for ID 99, return empty (Not Found)"
        Mockito.when(memberRepository.findById(99L)).thenReturn(Optional.empty());

        // 2. Simulate a web request to /api/members/99 and check that it returns a 404 status
        mockMvc.perform(MockMvcRequestBuilders.get("/api/members/99"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}