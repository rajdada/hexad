package com.hexad.myreadings.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hexad.myreadings.model.Member;

import java.util.HashSet;
import java.util.Set;

public class BookStatusDTO
{
    private int bookStatusId;

    private String bookName;

    private int availableCount;

    @JsonIgnore
    private Set<MemberDTO> borrowers = new HashSet<>();

    public int getBookStatusId()
    {
        return bookStatusId;
    }

    public void setBookStatusId(int bookStatusId)
    {
        this.bookStatusId = bookStatusId;
    }

    public String getBookName()
    {
        return bookName;
    }

    public void setBookName(String bookName)
    {
        this.bookName = bookName;
    }

    public int getAvailableCount()
    {
        return availableCount;
    }

    public void setAvailableCount(int availableCount)
    {
        this.availableCount = availableCount;
    }

    public Set<MemberDTO> getBorrowers()
    {
        return borrowers;
    }

    public void setBorrowers(Set<MemberDTO> borrowers)
    {
        this.borrowers = borrowers;
    }
}
