package com.hexad.myreadings.exception;

import com.hexad.myreadings.constants.Constants;
import com.hexad.myreadings.controllers.MemberController;
import com.hexad.myreadings.services.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MemberController.class)
public class ErrorControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @SpyBean
    ErrorController errorController;

    @MockBean
    private MemberService memberService;


    @Test
    public void testHandleNotFound() throws Exception
    {
        MyLearningsException exception = new MyLearningsException(Constants.ERR_CODE_MEMBER_NOT_FOUND
                , Constants.ERR_MESSAGE_MEMBER_NOT_FOUND);

        doThrow(exception).when(memberService).deleteByMemberName("dummy");

        mockMvc.perform(delete("/member/delete/dummy")
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(memberService).deleteByMemberName("dummy");
    }


    @Test
    public void testHandleInternalError() throws Exception
    {
        Exception exception = new RuntimeException("Unknown error");

        doThrow(exception).when(memberService).deleteByMemberName("generic");

        mockMvc.perform(delete("/member/delete/generic")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());

        verify(memberService).deleteByMemberName("generic");
    }

}
