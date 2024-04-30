package com.iuni.content.controller;

import com.iuni.content.domain.Comment;
import com.iuni.content.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@RequestMapping("/conts/comment")
public class CommentController {
    private final CommentService commentService;

    @PostMapping()
    public Comment create(@RequestBody Comment comment){
        return commentService.create(comment);
    }

    @GetMapping("/load")
    public ArrayList<Comment> loadByTaskId(@RequestParam("taskId") Long taskId){
        return commentService.loadByTaskId(taskId);
    }

    @GetMapping()
    public Comment getById(@RequestParam("id") Long id){
        return commentService.getById(id);
    }

    @PatchMapping()
    public HashMap<String, String> update(@RequestBody Comment comment){
        HashMap<String, String> result = new HashMap<>();
        result.put("result", commentService.update(comment));
        return result;
    }

    @DeleteMapping()
    public HashMap<String, String> delete(@RequestBody Comment comment){
        HashMap<String, String> result = new HashMap<>();
        result.put("result", commentService.delete(comment));
        return result;
    }
}