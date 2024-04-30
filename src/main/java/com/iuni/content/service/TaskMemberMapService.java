package com.iuni.content.service;

import com.iuni.content.domain.Member;
import com.iuni.content.domain.Task;
import com.iuni.content.domain.TaskMemberMap;
import com.iuni.content.helper.join.JoinTaskMemberMap;
import com.iuni.content.helper.join.JoinTaskPartyMember;
import com.iuni.content.repository.TaskMemberMapRepository;
import com.iuni.content.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service("taskMemberMapService")
@RequiredArgsConstructor
public class TaskMemberMapService {
    private final TaskMemberMapRepository taskMemberMapRepository;
    private final TaskRepository taskRepository;

    public ArrayList<TaskMemberMap> load(Long taskId){
        try{
            ArrayList<JoinTaskMemberMap> dataList = this.taskMemberMapRepository.load(taskId);
            ArrayList<TaskMemberMap> result = new ArrayList<>();
            for(JoinTaskMemberMap data : dataList){
                TaskMemberMap taskMemberMap = new TaskMemberMap();
                taskMemberMap.setMapType(data.getMapType());
                taskMemberMap.setNickName(data.getNickName());
                taskMemberMap.setName(data.getName());
                taskMemberMap.setMemberId(data.getMemberId());
                taskMemberMap.setTaskId(taskId);
                result.add(taskMemberMap);
            }
            return result;
        }
        catch(Exception e){
            throw e;
        }
    }

    public ArrayList<JoinTaskPartyMember> loadMembers(Long taskId){
        try{
            return taskMemberMapRepository.findAllPartyMember(taskId);
        }
        catch(Exception e){
            throw e;
        }
    }
    public TaskMemberMap create(TaskMemberMap data){
        try{
            return this.taskMemberMapRepository.save(data);
        }
        catch(Exception e){
            throw e;
        }
    }

    public String reqShareTask(List<TaskMemberMap> dataList){
        try{
            Task task = taskRepository.findById(dataList.get(0).getTaskId()).orElse(new Task());
            for(TaskMemberMap data : dataList){
                data.setTask(task);
            }
            List<TaskMemberMap> results = this.taskMemberMapRepository.saveAll(dataList);
            return results.isEmpty() ? "fail" : "success";
        }
        catch(Exception e){
            System.out.println("reqShareTaskError = " + e);
            return "serviceError";
        }
    }
    //공유하기 업데이트
    public String updateShareRequest(TaskMemberMap taskMemberMap) {
        try {
            int result = taskMemberMapRepository.updateShareRequest(taskMemberMap.getMemberId(), taskMemberMap.getTaskId(), taskMemberMap.getMapType());
            //업데이트에 성공하면 태스크의 공유여부도 활성화(true)시킨다.
            if (result == 1) {
                Optional<Task> optTask = taskRepository.findById(taskMemberMap.getTaskId());
                if (!optTask.isEmpty()) {
                    Task updateTask = optTask.get();
                    updateTask.setIsShared(true);
                    Task task = taskRepository.save(updateTask);
                    return task.getId() != null ? "success" : "fail";
                }
                return "fail";
            } else {
                return "fail";
            }
        } catch (Exception e) {
            System.out.println("TaskMemberMapService_updateShareRequest_Error_ = " + e);
            return "fail";
        }
    }


    public int remove(Long taskId, Long memberId){
        try{
            int result = taskMemberMapRepository.deleteMap(taskId, memberId);

            //지우고 난뒤 멤버가 한명(owner)인 경우 해당 공유 태스크는 자동으로 공유 안됨으로 변경
            ArrayList<TaskMemberMap> dataList = taskMemberMapRepository.findAllById(taskId).orElse(new ArrayList<TaskMemberMap>());
            if(dataList.size() == 1 && dataList.get(0).getMapType().equals("owner")){
                return taskRepository.updateShare(taskId, false);
            }
            return result;
        }
        catch(Exception e){
            System.out.println("TaskMemberMapSerivce_remove_error = " + e);
            return 0;
        }
    }

    public int removeByTaskId(Long taskId){
        try{
            return this.taskMemberMapRepository.deleteMapByTaskId(taskId);
        }
        catch(Exception e){
            throw e;
        }
    }

    public int removeByMemberId(Long memberId){
        try{
            return this.taskMemberMapRepository.deleteMapByMemberId(memberId);
        }
        catch(Exception e){
            throw e;
        }
    }
}
