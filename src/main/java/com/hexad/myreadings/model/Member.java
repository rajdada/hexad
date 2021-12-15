package com.hexad.myreadings.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "member")
public class Member
{
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private long memberId;

    @Column(name = "member_name", length = 100)
    @NotEmpty
    private String memberName;

    @ManyToMany(mappedBy = "borrowers", fetch = FetchType.LAZY)
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

    @Override
    public String toString()
    {
        return "Member{" +
                "memberId=" + memberId +
                ", memberName='" + memberName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return memberId == member.memberId && memberName.equals(member.memberName);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(memberId, memberName);
    }
}
