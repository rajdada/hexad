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
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MemberServiceTest
{
    @SpyBean
    MemberService memberService;

    @SpyBean
    MemberRepository memberRepository;

    @Test
    @Order(1)
    public void testSaveOrUpdate()
    {
        //given
        Member member = new Member();
        member.setMemberName("user");

        //when
        member = memberService.saveOrUpdate(member);

        //then
        assertThat(member).isNotNull();
        assertThat(member.getMemberName()).isEqualTo("user");
    }

    @Test
    @Order(2)
    public void findByMemberName() throws MyLearningsException
    {
        //given
        String memberName = "user";

        //when
        Member member = memberService.findByMemberName(memberName);

        //then
        assertThat(member).isNotNull();
        assertThat(member.getMemberName()).isEqualTo("user");
    }

    @Test
    @Order(3)
    public void testFindBookStatusByMemberNameIgnoreCase() throws MyLearningsException
    {
        //given
        String memberName = "user";
        String bookName = "Learn Java";
        Member member = memberService.findByMemberName(memberName);
        if (member == null)
            throw new MyLearningsException(Constants.ERR_CODE_MEMBER_NOT_FOUND, Constants.ERR_MESSAGE_MEMBER_NOT_FOUND);

        Set<Member> borrowers = new HashSet<>();
        borrowers.add(member);

        BookStatus bookStatus = new BookStatus();
        bookStatus.setBookStatusId(1);
        bookStatus.setBookName(bookName);
        bookStatus.setAvailableCount(4);
        bookStatus.setBorrowers(borrowers);

        member.getBorrowings().add(bookStatus);

        when(memberRepository.findByMemberNameIgnoreCase(memberName)).thenReturn(member.getBorrowings());

        //when
        Set<BookStatus> books = memberService.findBookStatusByMemberNameIgnoreCase(memberName);

        //then
        assertThat(books).isNotNull();
        assertThat(books.isEmpty()).isEqualTo(false);
        assertThat(books.size()).isEqualTo(1);
        assertThat(books.stream().findFirst().get().getBookName()).isEqualTo(bookName);
    }

    @Test
    @Order(4)
    public void testDeleteByMemberName() throws MyLearningsException
    {
        //given
        String memberName = "user";

        //when
        memberService.deleteByMemberName(memberName);

        verify(memberService).deleteByMemberName(memberName);
    }
}
