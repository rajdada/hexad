package com.hexad.myreadings.services;

import com.hexad.myreadings.constants.Constants;
import com.hexad.myreadings.dao.MemberRepository;
import com.hexad.myreadings.exception.MyLearningsException;
import com.hexad.myreadings.model.BookStatus;
import com.hexad.myreadings.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class MemberService
{
    @Autowired
    MemberRepository memberRepository;

    public Member saveOrUpdate(Member member)
    {
        return memberRepository.save(member);
    }

    public Optional<Member> findByMemberId(long memberId)
    {
        return memberRepository.findByMemberId(memberId);
    }

    public Member findByMemberName(String memberName) throws MyLearningsException
    {
        Optional<Member> memberOp = memberRepository.findByMemberName(memberName);

        if (!memberOp.isPresent())
            throw new MyLearningsException(Constants.ERR_CODE_MEMBER_NOT_FOUND
                    , Constants.ERR_MESSAGE_MEMBER_NOT_FOUND);

        return memberOp.get();
    }

    public int deleteByMemberId(long memberId)
    {
        return memberRepository.deleteByMemberId(memberId);
    }

    public void deleteByMemberName(String memberName) throws MyLearningsException
    {
        int res = memberRepository.deleteByMemberName(memberName);

        if (res == 0)
            throw new MyLearningsException(Constants.ERR_CODE_MEMBER_NOT_FOUND
                    , Constants.ERR_MESSAGE_MEMBER_NOT_FOUND);
    }

    public Set<BookStatus> findBookStatusByMemberNameIgnoreCase(String memberName)
    {
        return memberRepository.findByMemberNameIgnoreCase(memberName);
    }

}
