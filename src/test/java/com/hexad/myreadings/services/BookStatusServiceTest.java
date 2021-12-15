package com.hexad.myreadings.services;


import com.hexad.myreadings.constants.Constants;
import com.hexad.myreadings.dao.MemberRepository;
import com.hexad.myreadings.exception.MyLearningsException;
import com.hexad.myreadings.model.BookStatus;
import com.hexad.myreadings.model.Member;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookStatusServiceTest
{

    @SpyBean
    BookStatusService bookStatusService;

    @SpyBean
    MemberRepository memberRepository;

    @Test
    @Order(1)
    public void addBook()
    {
        //given
        String bookName = "Learn Java";
        int count = 5;

        //when
        BookStatus bookStatus = bookStatusService.addBook(bookName, count);

        //then
        assertThat(bookStatus).isNotNull();
        assertThat(bookStatus.getBookName()).isEqualTo(bookName);
        assertThat(bookStatus.getAvailableCount()).isEqualTo(count);
    }

    @Test
    @Order(2)
    public void findByBookName() throws MyLearningsException
    {
        //given
        String bookName = "Learn Java";

        //when
        BookStatus bookStatus = bookStatusService.findByBookName(bookName);

        //then
        assertThat(bookStatus).isNotNull();
        assertThat(bookStatus.getBookName()).isEqualTo(bookName);
    }

    @Test
    @Order(3)
    public void borrowBookByName() throws MyLearningsException
    {
        //given
        String bookName = "Learn Java";
        Member member = new Member();
        member.setMemberName("user");

        memberRepository.save(member);

        //when
        BookStatus bookStatus = bookStatusService.borrowBookByName(bookName, member.getMemberName());

        //then
        assertThat(bookStatus).isNotNull();
        assertThat(bookStatus.getBorrowers()).isNotEmpty();
        assertThat(bookStatus.getBookName().equals("Learn Java")).isEqualTo(true);
    }

    @Test
    @Order(4)
    public void returnBooksToLibrary() throws MyLearningsException
    {
        //given
        Set<String> bookNames = new HashSet<>();
        bookNames.add("Learn Java");
        Optional<Member> memberOp = memberRepository.findByMemberName("user");

        MyLearningsException exception = new MyLearningsException(Constants.ERR_CODE_MEMBER_NOT_FOUND
                , Constants.ERR_MESSAGE_MEMBER_NOT_FOUND);

        if (!memberOp.isPresent())
            throw exception;

        //when
        Member member = bookStatusService.returnBooksToLibrary(bookNames, memberOp.get().getMemberName());

        //then
        assertThat(member).isNotNull();
        assertThat(member.getBorrowings()).isEmpty();
    }
}
