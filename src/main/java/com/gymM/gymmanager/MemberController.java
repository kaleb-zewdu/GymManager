
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


    @GetMapping("/api/members/{id}")
    public org.springframework.http.ResponseEntity<Member> getMemberById(@PathVariable Long id) {
        return memberRepository.findById(id)
                .map(member -> org.springframework.http.ResponseEntity.ok(member))
                .orElse(org.springframework.http.ResponseEntity.notFound().build());
    }
    @DeleteMapping("/api/members/{id}")
    public void deleteMember(@PathVariable Long id) {
        memberRepository.deleteById(id);
    }
    @PutMapping("/api/members/{id}")
    public Member updateMember(@PathVariable Long id, @RequestBody Member updatedMember) {
        return memberRepository.findById(id)
                .map(member -> {
                    member.setFirstName(updatedMember.getFirstName());
                    member.setLastName(updatedMember.getLastName());
                    member.setPhone(updatedMember.getPhone());
                    member.setPlan(updatedMember.getPlan());
                    return memberRepository.save(member);
                })
                .orElse(null);
    }
}
