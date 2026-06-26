package com.asycoo.library.service;

import com.asycoo.library.dto.LoanRequest;
import com.asycoo.library.dto.MemberCreateRequest;
import com.asycoo.library.dto.MemberResponse;
import com.asycoo.library.dto.ReturnResponse;
import com.asycoo.library.entity.Book;
import com.asycoo.library.entity.Loan;
import com.asycoo.library.entity.Member;
import com.asycoo.library.entity.Role;
import com.asycoo.library.exception.BusinessException;
import com.asycoo.library.repository.BookRepository;
import com.asycoo.library.repository.LoanRepository;
import com.asycoo.library.repository.MemberRepository;
import java.time.LocalDate;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public MemberResponse getMember(String id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new BusinessException("MEMBER_NOT_FOUND", "会员不存在: " + id));
        return toResponse(member);
    }

    @Transactional
    public MemberResponse register(MemberCreateRequest request) {
        if (memberRepository.existsById(request.id())) {
            throw new BusinessException("MEMBER_DUPLICATE", "会员 ID 已存在: " + request.id());
        }
        if (memberRepository.existsByUsername(request.username())) {
            throw new BusinessException("USERNAME_DUPLICATE", "用户名已存在: " + request.username());
        }
        Member member = new Member(
                request.id(),
                request.name(),
                request.username(),
                passwordEncoder.encode(request.password()),
                Role.MEMBER);
        return toResponse(memberRepository.save(member));
    }

    public Member findByUsername(String username) {
        return memberRepository.findByUsername(username)
                .orElseThrow(() -> new BusinessException("AUTH_FAILED", "用户名或密码错误"));
    }

    private static MemberResponse toResponse(Member member) {
        return new MemberResponse(member.getId(), member.getName(), member.getUsername(), member.getRole());
    }
}
