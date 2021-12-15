package com.hexad.myreadings.services;

import com.hexad.myreadings.constants.Constants;
import com.hexad.myreadings.dao.BookStatusRepository;
import com.hexad.myreadings.dao.MemberRepository;
import com.hexad.myreadings.exception.MyLearningsException;
import com.hexad.myreadings.model.BookStatus;
import com.hexad.myreadings.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class BookStatusService
{
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    BookStatusRepository bookStatusRepository;

    public BookStatus addBook(String bookName, int count)
    {
        Optional<BookStatus> bookStatusOp = bookStatusRepository.findByBookName(bookName);

        if (bookStatusOp.isPresent())
        {
            final BookStatus bookStatus = bookStatusOp.get();
            bookStatus.setAvailableCount(bookStatus.getAvailableCount() + count);

            return bookStatusRepository.save(bookStatus);
        }
        else
        {
            final BookStatus bookStatus = new BookStatus();
            bookStatus.setBookName(bookName);
            bookStatus.setAvailableCount(count);

            return bookStatusRepository.save(bookStatus);
        }
    }

    public BookStatus findByBookName(String bookName) throws MyLearningsException
    {
        Optional<BookStatus> bookStatusOp = bookStatusRepository.findByBookName(bookName);

        if (!bookStatusOp.isPresent())
            throw new MyLearningsException(Constants.ERR_CODE_BOOK_NOT_EXISTS, Constants.ERR_MESSAGE_BOOK_NOT_EXISTS);

        return bookStatusOp.get();
    }

    public BookStatus borrowBookByName(String bookName, String memberName) throws MyLearningsException
    {
        Optional<Member> memberOp = memberRepository.findByMemberName(memberName);

        if (!memberOp.isPresent())
            throw new MyLearningsException(Constants.ERR_CODE_MEMBER_NOT_FOUND, Constants.ERR_MESSAGE_MEMBER_NOT_FOUND);

        final Optional<BookStatus> bookStatusOp = bookStatusRepository.findByBookName(bookName);

        if (bookStatusOp.isPresent())
        {
            final BookStatus bookStatus = bookStatusOp.get();
            final Member member = memberOp.get();

            if (bookStatus.getBorrowers().contains(member))
                throw new MyLearningsException(Constants.ERR_CODE_ALREADY_BORROWED, Constants.ERR_MESSAGE_ALREADY_BORROWED);

            bookStatus.setAvailableCount(bookStatus.getAvailableCount() - 1);

            member.getBorrowings().add(bookStatus);

            memberRepository.save(member); // adding borrowings per member

            bookStatus.getBorrowers().add(member);

            return bookStatusRepository.save(bookStatus); // adding borrower per book
        }
        else
        {
            throw new MyLearningsException(Constants.ERR_CODE_BOOK_NOT_EXISTS, Constants.ERR_MESSAGE_BOOK_NOT_EXISTS);
        }
    }

    public Member returnBooksToLibrary(Set<String> bookNames, String memberName) throws MyLearningsException
    {

        Optional<Member> memberOp = memberRepository.findByMemberName(memberName);

        if (!memberOp.isPresent())
            throw new MyLearningsException(Constants.ERR_CODE_MEMBER_NOT_FOUND, Constants.ERR_MESSAGE_MEMBER_NOT_FOUND);

        final Member member = memberOp.get();

        final Set<BookStatus> bookStatuses = member.getBorrowings();

        final Set<BookStatus> updatedBookStatuses = new HashSet<>();

        for (BookStatus bookStatus : bookStatuses)
        {
            if (bookNames.contains(bookStatus.getBookName()))
            {
                bookStatus.setAvailableCount(bookStatus.getAvailableCount() + 1);

                boolean resBorrowers = bookStatus.getBorrowers().remove(member);

                boolean resBorrowings = member.getBorrowings().remove(bookStatus);

                if (resBorrowers || resBorrowings)
                    updatedBookStatuses.add(bookStatus);
            }
        }

        bookStatusRepository.saveAll(updatedBookStatuses);

        memberRepository.save(member);

        bookStatusRepository.flush();

        return member;
    }
}
