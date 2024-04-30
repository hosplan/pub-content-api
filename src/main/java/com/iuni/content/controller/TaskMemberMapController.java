package com.iuni.content.controller;

import com.iuni.content.domain.Member;
import com.iuni.content.domain.TaskMemberMap;
import com.iuni.content.helper.join.JoinTaskPartyMember;
import com.iuni.content.helper.jwt.JWT;
import com.iuni.content.service.TaskMemberMapService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/conts/taskMemberMap")
public class TaskMemberMapController {
    private final TaskMemberMapService taskMemberMapService;
    private final JWT jwt;

    @GetMapping("/load")
    public ArrayList<TaskMemberMap> load(@RequestParam("taskId") Long taskId){
        return this.taskMemberMapService.load(taskId);
    }

    @GetMapping("/loadMembers")
    public ArrayList<JoinTaskPartyMember> loadMembers(@RequestParam("taskId") Long taskId){
        return this.taskMemberMapService.loadMembers(taskId);
    }
    @PatchMapping("/share")
    public HashMap<String, String> updateShareRequest(@RequestBody TaskMemberMap taskMemberMap){
        HashMap<String, String> result = new HashMap<>();
        result.put("result", taskMemberMapService.updateShareRequest(taskMemberMap));
        return result;
    }

    @PostMapping()
    public TaskMemberMap create(@RequestBody TaskMemberMap data, @RequestHeader Map<String, String> headers){
        data.setMemberId(this.jwt.getId(headers.get("authorization")));
        return this.taskMemberMapService.create(data);
    }

    @PostMapping("/reqShare")
    public HashMap<String, String> reqShareTask(@RequestBody List<TaskMemberMap> data, @RequestHeader Map<String, String> headers){
        HashMap<String, String> result = new HashMap<>();
        result.put("result", taskMemberMapService.reqShareTask(data));
        return result;
    }

    @DeleteMapping()
    public int remove(@RequestBody TaskMemberMap data, @RequestHeader Map<String, String> headers){
        Long memberId = jwt.getId(headers.get("authorization"));
        return this.taskMemberMapService.remove(data.getTaskId(), memberId);
    }

    @DeleteMapping("/deleteTask")
    public int removeByTaskId(@RequestParam("taskId") Long taskId){
        return this.taskMemberMapService.removeByTaskId(taskId);
    }

    @DeleteMapping("/deleteMember")
    public int removeByMemberId(@RequestParam("memberId") Long memberId){
        return this.taskMemberMapService.removeByMemberId(memberId);
    }
}
