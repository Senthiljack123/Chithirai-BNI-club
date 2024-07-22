package com.example.Final.Repo;

 import com.example.Final.model.Member;
 import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    // You can add custom methods if needed
}
