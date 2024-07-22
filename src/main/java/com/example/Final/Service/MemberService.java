package com.example.Final.Service;


import com.example.Final.Repo.MemberRepository;
import com.example.Final.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;

    public Member addMember(Member member) {
         return memberRepository.save(member);
    }

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public Member getMemberById(Long id) {
        Optional<Member> optionalMember = memberRepository.findById(id);
        return optionalMember.orElse(null);
    }

    public Member updateMember(Long id, Member newMember) {
        Optional<Member> optionalMember = memberRepository.findById(id);
        if (optionalMember.isPresent()) {
            Member existingMember = optionalMember.get();
            existingMember.setName(newMember.getName());
            existingMember.setEmail(newMember.getEmail());
            return memberRepository.save(existingMember);
        } else {
            return null;
        }
    }

    public void deleteMember(Long id) {
        memberRepository.deleteById(id);
    }
}
