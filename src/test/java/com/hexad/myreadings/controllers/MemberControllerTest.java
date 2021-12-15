package com.hexad.myreadings.controllers;

import com.hexad.myreadings.dto.MemberDTO;
import com.hexad.myreadings.model.Member;
import com.hexad.myreadings.services.MemberService;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MemberController.class)
public class MemberControllerTest
{

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    @MockBean
    ModelMapper modelMapper;

    protected static String memberName = "user";

    @Test
    public void testAddMember() throws Exception
    {
        //given
        Member member = new Member();
        member.setMemberName(memberName);
        member.setMemberId(0);

        when(memberService.saveOrUpdate(any(Member.class))).thenReturn(member);

        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setMemberId(1);
        memberDTO.setMemberName(memberName);

        when(modelMapper.map(member, MemberDTO.class)).thenReturn(memberDTO);

        final String url = String.format("/member/add/%s", memberName);

        //when
        mockMvc.perform(post(url)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.memberName", is(memberName)));

        //then
        verify(memberService).saveOrUpdate(member);
        verify(modelMapper).map(member, MemberDTO.class);
    }

    @Test
    public void testDeleteMemberByName() throws Exception
    {
        //given
        doNothing().when(memberService).deleteByMemberName(memberName);

        final String url = String.format("/member/delete/%s", memberName);

        //when
        mockMvc.perform(delete(url)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());

        //then
        verify(memberService).deleteByMemberName(memberName);
    }
}
