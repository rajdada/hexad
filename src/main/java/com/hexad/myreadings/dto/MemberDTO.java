package com.hexad.myreadings.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hexad.myreadings.model.BookStatus;

import java.util.HashSet;
import java.util.Set;

public class MemberDTO
{
    private long memberId;

    private String memberName;

    @JsonBackReference
    private Set<BookStatus> borrowings = new HashSet<>();

    public long getMemberId()
    {
        return memberId;
    }

    public void setMemberId(long memberId)
    {
        this.memberId = memberId;
    }

    public String getMemberName()
    {
        return memberName;
    }

    public void setMemberName(String memberName)
    {
        this.memberName = memberName;
    }

    public Set<BookStatus> getBorrowings()
    {
        return borrowings;
    }

    public void setBorrowings(Set<BookStatus> borrowings)
    {
        this.borrowings = borrowings;
    }
}
