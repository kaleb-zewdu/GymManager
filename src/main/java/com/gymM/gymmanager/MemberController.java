
package com.gymM.gymmanager;

import org.springframework.web.bind.annotation.*;
        import java.util.List;

@RestController
public class MemberController {

    private final MemberRepository memberRepository;

    public MemberController(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // 1. GET: Ask the database for all members
    @GetMapping("/api/members")
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    // 2. POST: Send a new member to the database
    @PostMapping("/api/members")
    public Member addMember(@RequestBody Member member) {
        return memberRepository.save(member);
    }
}