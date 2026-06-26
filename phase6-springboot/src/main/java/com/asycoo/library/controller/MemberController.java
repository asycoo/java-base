package com.asycoo.library.controller;

import com.asycoo.library.dto.ApiResponse;
import com.asycoo.library.dto.MemberCreateRequest;
import com.asycoo.library.dto.MemberResponse;
import com.asycoo.library.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/{id}")
    public ApiResponse<MemberResponse> get(@PathVariable String id) {
        return ApiResponse.ok(memberService.getMember(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<MemberResponse> register(@Valid @RequestBody MemberCreateRequest request) {
        return ApiResponse.ok(memberService.register(request));
    }
}
