package com.hexad.myreadings.dao;

import com.hexad.myreadings.model.BookStatus;
import com.hexad.myreadings.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
@Transactional
public interface MemberRepository extends JpaRepository<Member, Long>
{
    @Query("select m from Member m where m.memberId = ?1")
    Optional<Member> findByMemberId(long memberId);

    @Query("select m from Member m where upper(m.memberName) = upper(?1)")
    Optional<Member> findByMemberName(@NonNull String memberName);

    @Modifying
    @Query("delete from Member m where m.memberId = ?1")
    int deleteByMemberId(long memberId);

    @Modifying
    @Query("delete from Member m where upper(m.memberName) = upper(?1)")
    int deleteByMemberName(@NonNull String memberName);

    @Query("select m.borrowings from Member m where upper(m.memberName) = upper(?1)")
    Set<BookStatus> findByMemberNameIgnoreCase(@NonNull String memberName);
}