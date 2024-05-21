package com.iuni.content.service;



import com.iuni.content.domain.Board;
import com.iuni.content.domain.Task;
import com.iuni.content.domain.TaskMemberMap;
import com.iuni.content.helper.common.ErrorLog;
import com.iuni.content.helper.join.JoinTask;
import com.iuni.content.repository.TaskMemberMapRepository;
import com.iuni.content.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

@Service("taskService")
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMemberMapRepository taskMemberMapRepository;
    private TrashBasketService trashBasketService;

    private final ErrorLog errorLog;

    public Task get(Long id){
        try{
            return taskRepository.findById(id).orElse(new Task());
        }
        catch(Exception e) {
            throw e;
        }
    }
    public ArrayList<Task> loadShareTask(Long memberId){
        try{
            ArrayList<TaskMemberMap> joinTaskMemberMaps = taskMemberMapRepository.findByMemberIdAndMapType(memberId, "JOIN").orElse(new ArrayList<>());
            ArrayList<TaskMemberMap> editorTaskMemberMaps = taskMemberMapRepository.findByMemberIdAndMapType(memberId, "editor").orElse(new ArrayList<>());

            ArrayList<Task> result = new ArrayList<>();
            for(TaskMemberMap taskMemberMap : joinTaskMemberMaps){
                result.add(taskMemberMap.getTask());
            }

            for(TaskMemberMap taskMemberMap : editorTaskMemberMaps){
                result.add(taskMemberMap.getTask());
            }

            return result;
        }
        catch(Exception e){
            System.out.println("TaskService_Error_loadShareTask = " + e);
            return new ArrayList<>();
        }
    }
    public ArrayList<Task> load(Long boardId){
        try{
            ArrayList<JoinTask> dataList = this.taskRepository.findAllByBoardId(boardId);
            ArrayList<Task> result = new ArrayList<>();
            
            for(JoinTask data : dataList){
                
                System.out.println(data.getStartDate().orElse(null));
                Task task = new Task();
                task.setId(data.getId());
                task.setMapId(data.getMapId());
                task.setTaskOrder(data.getTaskOrder());
                task.setStartDate(data.getStartDate().orElse(null));
                task.setCreateDate(data.getCreateDate().orElse(null));
                task.setDescription(data.getDescription());
                task.setDueDate(data.getDueDate().orElse(null));
                task.setCreatorNickName(data.getCreatorNickName());
                task.setEditorNickName(data.getEditorNickName());
                task.setEndDate(data.getEndDate().orElse(null));
                task.setMajorVersion(data.getMajorVersion());
                task.setMinorVersion(data.getMinorVersion());
                task.setName(data.getName());
                task.setStatusId(data.getStatusId());
                task.setStatusName(data.getStatusName());
                task.setPoint(data.getPoint());
                task.setIsShared(data.getIsShared());

                result.add(task);
            }
            return result;
        }
        catch(Exception e){
            throw e;
        }
    }

    public Task create(Task task, Long memberId){
        try{
            task.setIsDelete(false);
            Task result = this.taskRepository.save(task);
            TaskMemberMap taskMemberMap = new TaskMemberMap();
            taskMemberMap.setTask(result);
            taskMemberMap.setMemberId(memberId);
            taskMemberMap.setMapType("owner");
            this.taskMemberMapRepository.save(taskMemberMap);

            return result;
        }
        catch(Exception e){
            throw e;
        }
    }

    public Boolean patch(Task task, String nickName, Long memberId){
        try{
            Optional<Task> opt_updateTask = this.taskRepository.findById(task.getId());

            if(opt_updateTask.isEmpty()){
                return false;
            }

            Task updateTask = opt_updateTask.get();
            updateTask.setName(task.getName());
            updateTask.setDescription(task.getDescription());
            updateTask.setDueDate(task.getDueDate());
            updateTask.setStatus(task.getStatus());
            updateTask.setEndDate(task.getEndDate());
            updateTask.setStartDate(task.getStartDate());
            updateTask.setMinorVersion(task.getMinorVersion()+1);
            updateTask.setEditorNickName(nickName);
            taskRepository.save(updateTask);


            taskMemberMapRepository.updatePreviousEditor("JOIN", task.getId());

            taskMemberMapRepository.updateNewEditor("editor", task.getId(), memberId);

            return true;
        }
        catch(Exception e){
            errorLog.write("TaskService", "patch", e);
            return false;
        }
    }

    public Boolean remove(Long id){
        try{
            this.taskRepository.deleteById(id);
            return true;
        }
        catch(Exception e){
            throw e;
        }
    }


    public HashMap<String, String> moveTrash(Long id){
        HashMap<String, String> result = new HashMap<>();
        try{
            Optional<Task> optTask = taskRepository.findById(id);
            if(optTask.isPresent()){
                Task updateTask = optTask.get();
                updateTask.setIsDelete(true);
                taskRepository.save(updateTask);
                result.put("result", "success");
                return result;
            }
            result.put("result", "fail");
            return result;
        }
        catch(Exception e){
            result.put("result", "fail");
            return result;
        }
    }
    public Boolean restore(Long id){
        try{
            Optional<Task> optTask = taskRepository.findById(id);
            if(optTask.isPresent()){
                Task updateTask = optTask.get();
                updateTask.setIsDelete(false);
                taskRepository.save(updateTask);
                return true;
            }
            return false;
        }
        catch(Exception e){
            return false;
        }
    }
}
