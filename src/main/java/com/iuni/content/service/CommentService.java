package com.iuni.content.service;

import com.iuni.content.domain.Comment;
import com.iuni.content.domain.Task;
import com.iuni.content.helper.common.ErrorLog;
import com.iuni.content.repository.CommentRepository;
import com.iuni.content.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@Service("commentService")
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;
    private final ErrorLog errorLog;

    //댓글 저장
    public Comment create(Comment comment){
        try{
            comment.setCreateDate(new Date());
            return commentRepository.save(comment);
        }
        catch(Exception e){
            errorLog.write("CommentService","create", e);
            return new Comment();
        }
    }

    //태스크의 코멘트 불러오기
    public ArrayList<Comment> loadByTaskId(Long taskId){
        try{
            Task task = taskRepository.findById(taskId).orElse(new Task());
            return commentRepository.findAllByTask(task).orElse(new ArrayList<>());
        }
        catch(Exception e){
            errorLog.write("CommentService", "loadByTaskId", e);
            return new ArrayList<>();
        }
    }

    public String update(Comment comment){
        try{
            Optional<Comment> optComment = commentRepository.findById(comment.getId());
            if(optComment.isEmpty()){
                return "false";
            }
            Comment updateComment = optComment.get();
            updateComment.setUpdateDate(new Date());
            updateComment.setDescription(comment.getDescription());
            Comment saveComment =  commentRepository.save(updateComment);

            return saveComment.getId() != null ? "success" : "false";
        }
        catch(Exception e){
            errorLog.write("CommentService", "update", e);
            return "false";
        }
    }

    public Comment getById(Long id){
        try{
            return commentRepository.findById(id).orElse(new Comment());
        }
        catch (Exception e){
            errorLog.write("CommentService", "getById", e);
            return new Comment();
        }
    }

    public String delete(Comment comment){
        try{
            commentRepository.deleteById(comment.getId());
            return "success";
        }
        catch(Exception e){
            errorLog.write("CommentService", "delete", e);
            return "false";
        }
    }
}
