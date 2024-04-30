package com.iuni.content.controller;

import com.iuni.content.domain.Task;
import com.iuni.content.helper.jwt.JWT;
import com.iuni.content.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/conts/task")
public class TaskController{
    private final TaskService taskService;
    private final JWT jwt;

    @GetMapping()
    public Task get(@RequestParam("task") Long id){
        return this.taskService.get(id);
    }
    @GetMapping("/share")
    public ArrayList<Task> loadShareTask(@RequestHeader Map<String, String> headers){
        return taskService.loadShareTask(jwt.getId(headers.get("authorization")));
    }
    @PostMapping()
    public Task create(@RequestBody Task data,  @RequestHeader Map<String, String> headers){
        return taskService.create(data, this.jwt.getId(headers.get("authorization")));
    }

    @GetMapping("/all")
    public ArrayList<Task> load(@RequestParam("board") Long boardId){
        return taskService.load(boardId);
    }

    @PatchMapping()
    public Boolean patch(@RequestBody Task data, @RequestHeader Map<String, String> headers){
        return taskService.patch(data, jwt.getNickName(headers.get("authorization")), jwt.getId(headers.get("authorization")));
    }

    @DeleteMapping()
    public Boolean remove(@RequestParam("id") Long id){
        return taskService.remove(id);
    }
}
