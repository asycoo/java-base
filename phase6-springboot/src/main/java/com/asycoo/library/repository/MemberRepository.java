package com.asycoo.library.repository;

import com.asycoo.library.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, String> {

    Optional<Member> findByUsername(String username);

    boolean existsByUsername(String username);
}
