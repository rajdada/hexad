package com.hexad.myreadings.controllers;

import com.hexad.myreadings.dto.MemberDTO;
import com.hexad.myreadings.exception.MyLearningsException;
import com.hexad.myreadings.model.Member;
import com.hexad.myreadings.services.MemberService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
public class MemberController
{

    @Autowired
    MemberService memberService;

    @Autowired
    ModelMapper modelMapper;

    @PostMapping("/add/{memberName}")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public MemberDTO addMember(@PathVariable String memberName)
    {
        final Member member = new Member();
        member.setMemberName(memberName);

        return convertEntityToDTO(memberService.saveOrUpdate(member));
    }

    @DeleteMapping("/delete/{memberName}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void deleteMemberByName(@PathVariable String memberName) throws MyLearningsException
    {
        memberService.deleteByMemberName(memberName);
    }

    private MemberDTO convertEntityToDTO(Member member)
    {
        return modelMapper.map(member, MemberDTO.class);
    }
}
