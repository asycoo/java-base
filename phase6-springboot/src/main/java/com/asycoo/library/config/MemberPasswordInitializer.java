package com.asycoo.library.config;

import com.asycoo.library.entity.Member;
import com.asycoo.library.repository.MemberRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 为 seed 会员写入 BCrypt 密码（默认 123456）
 */
@Component
public class MemberPasswordInitializer implements ApplicationRunner {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberPasswordInitializer(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) {
        for (Member member : memberRepository.findAll()) {
            if ("PLACEHOLDER".equals(member.getPassword())) {
                member.setPassword(passwordEncoder.encode("123456"));
                memberRepository.save(member);
            }
        }
    }
}
