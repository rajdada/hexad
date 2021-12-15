package com.hexad.myreadings.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "book_status")
public class BookStatus
{

    @Id
    @GeneratedValue
    @Column(name = "book_status_id")
    private int bookStatusId;

    @Column(name = "book_name", length = 100)
    @NotEmpty
    private String bookName;

    @Column(name = "available_count")
    private int availableCount;

    @ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
    @JoinTable(
            name = "book_status_member",
            joinColumns = { @JoinColumn(name = "book_status_id") },
            inverseJoinColumns = { @JoinColumn(name = "member_id") }
    )
    private Set<Member> borrowers = new HashSet<>();


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

    public Set<Member> getBorrowers()
    {
        return borrowers;
    }

    public void setBorrowers(Set<Member> borrowers)
    {
        this.borrowers = borrowers;
    }

    @Override
    public String toString()
    {
        return "BookStatus{" +
                "bookStatusId=" + bookStatusId +
                ", bookName='" + bookName + '\'' +
                ", availableCount=" + availableCount +
                '}';
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookStatus that = (BookStatus) o;
        return bookStatusId == that.bookStatusId && bookName.equals(that.bookName);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(bookStatusId, bookName);
    }
}
