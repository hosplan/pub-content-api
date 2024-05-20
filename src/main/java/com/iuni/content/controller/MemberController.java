package com.iuni.content.controller;

import com.iuni.content.helper.jwt.JWT;
import com.iuni.content.service.BoardService;
import com.iuni.content.service.TaskMemberMapService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/conts/member")
public class MemberController {
    private final TaskMemberMapService taskMemberMapService;
    private final JWT jwt;

    @DeleteMapping("/unscribe")
    public ResponseEntity<Integer> disconnectAll(@RequestHeader Map<String, String> headers){
        return new ResponseEntity<>( taskMemberMapService.removeByMemberId(jwt.getId(headers.get("authorization"))), HttpStatus.OK);
    }
}
