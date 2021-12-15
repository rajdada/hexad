package com.hexad.myreadings.controllers;

import com.hexad.myreadings.model.BookStatus;
import com.hexad.myreadings.model.Member;
import com.hexad.myreadings.services.BookStatusService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BookStatusController.class)
public class BookStatusControllerTest
{

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    BookStatusService bookStatusService;

    protected static String bookName = "Java";
    protected static String memberName = "user";

    @Test
    public void testAddBook() throws Exception
    {
        //given
        BookStatus bookStatus = new BookStatus();
        bookStatus.setBookName(bookName);
        bookStatus.setBookStatusId(1);
        bookStatus.setAvailableCount(5);

        when(bookStatusService.addBook(bookName, 5)).thenReturn(bookStatus);

        final String url = String.format("/book/add/%s/%d", bookName, 5);

        //when
        mockMvc.perform(post(url)
                        .content("")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.bookName", is(bookName)));


        //then
        verify(bookStatusService).addBook(bookName, 5);
    }

    @Test
    public void testGetBookStatus() throws Exception
    {
        //given
        BookStatus bookStatus = new BookStatus();
        bookStatus.setBookName(bookName);
        bookStatus.setBookStatusId(1);
        bookStatus.setAvailableCount(5);

        when(bookStatusService.findByBookName(bookName)).thenReturn(bookStatus);

        final String url = String.format("/book/get/%s", bookName);

        //when
        mockMvc.perform(get(url)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                        .andExpect(status().isFound())
                        .andExpect(jsonPath("$.bookName", is(bookName)));

        //then
        verify(bookStatusService).findByBookName(bookName);
    }

    @Test
    public void testBorrowBook() throws Exception
    {
        //given
        BookStatus bookStatus = new BookStatus();
        bookStatus.setBookName(bookName);
        bookStatus.setBookStatusId(1);
        bookStatus.setAvailableCount(5);

        Member member = new Member();
        member.setMemberId(1);
        member.setMemberName(memberName);
        member.getBorrowings().add(bookStatus);

        bookStatus.getBorrowers().add(member);

        when(bookStatusService.borrowBookByName(bookName, memberName)).thenReturn(bookStatus);

        final String url = String.format("/book/borrow/%s/%s", bookName, memberName);

        //when
        mockMvc.perform(post(url)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                        .andExpect(status().isFound())
                        .andExpect(jsonPath("$.bookName", is(bookName)));

        //then
        verify(bookStatusService).borrowBookByName(bookName, memberName);
    }

    @Test
    public void testReturnBook() throws Exception
    {
        //given
        BookStatus bookStatus = new BookStatus();
        bookStatus.setBookName(bookName);
        bookStatus.setBookStatusId(1);
        bookStatus.setAvailableCount(5);

        Member member = new Member();
        member.setMemberId(1);
        member.setMemberName(memberName);
        member.getBorrowings().add(bookStatus);

        bookStatus.getBorrowers().add(member);

        Set<String> bookNames = new HashSet<>();
        bookNames.add(bookName);

        when(bookStatusService.returnBooksToLibrary(bookNames, memberName)).thenReturn(member);

        final String url = String.format("/book/return/%s/%s", bookName, memberName);

        //when
        mockMvc.perform(post(url)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].bookName", is(bookName)));

        //then
        verify(bookStatusService).returnBooksToLibrary(bookNames, memberName);
    }
}
