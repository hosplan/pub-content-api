package com.iuni.content.service;

import com.iuni.content.domain.Board;
import com.iuni.content.domain.BoardTaskMap;
import com.iuni.content.domain.Task;
import com.iuni.content.domain.TaskMemberMap;
import com.iuni.content.helper.common.ErrorLog;
import com.iuni.content.repository.BoardRepository;
import com.iuni.content.repository.BoardTaskMapRepository;
import com.iuni.content.repository.TaskMemberMapRepository;
import com.iuni.content.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service("boardTaskMapService")
@RequiredArgsConstructor
public class BoardTaskMapService {
    private final BoardTaskMapRepository boardTaskMapRepository;
    private final TaskRepository taskRepository;
    private final BoardRepository boardRepository;
    private final TaskMemberMapRepository taskMemberMapRepository;
    private final ErrorLog errorLog;

    public BoardTaskMap create(BoardTaskMap data){
        try{
            return this.boardTaskMapRepository.save(data);
        }
        catch (Exception e){
            throw e;
        }
    }

    public int updateMove(BoardTaskMap data){
        try{
            return this.boardTaskMapRepository.updateMoveTask(data.getBoardId(), data.getUpdateBoardId(), data.getTaskId());
        }
        catch(Exception e){
            throw e;
        }
    }

    public int updateTaskOrder(ArrayList<BoardTaskMap> dataList){
        try{

            for(BoardTaskMap data : dataList){
                int result = this.boardTaskMapRepository.updateTaskOrder(data.getBoardId(), data.getTaskId(), data.getTaskOrder());
                if(result != 1){
                    return 0;
                }
            }
            return 1;
        }
        catch(Exception e){
            errorLog.write("BoardTaskMapService", "updateTaskOrder", e);
            return 0;
        }
    }
    public Boolean update(BoardTaskMap data){
        try{
            Optional<BoardTaskMap> optUpdateMap = this.boardTaskMapRepository.findById(data.getId());
            if(optUpdateMap.isPresent()){
                BoardTaskMap updateMap = optUpdateMap.get();
                updateMap.setBoard(data.getBoard());
                updateMap.setTask(data.getTask());
                updateMap.setTaskOrder(data.getTaskOrder());
                this.boardTaskMapRepository.save(updateMap);
                return true;
            } else {
                return false;
            }
        }
        catch(Exception e){
            throw e;
        }
    }

    public Boolean remove(Long id){
        try{
            this.boardTaskMapRepository.deleteById(id);
            return true;
        }
        catch(Exception e){
            throw e;
        }
    }

    public int removeByBoard(Long id){
        try{
            System.out.println("id = " + id);
            boardTaskMapRepository.deleteAllByBoardId(id);
            Optional<Board> optBoard = boardRepository.findById(id);
            if(optBoard.isPresent()){
                Board updateBoard = optBoard.get();
                updateBoard.setDelete(true);
                boardRepository.save(updateBoard);
            }
            return 1;
        }
        catch(Exception e){
            return 0;
        }
    }

    public boolean removeByTask(Long id){
        try{
            Optional<BoardTaskMap> optUpdateMap = this.boardTaskMapRepository.findById(id);
            if(optUpdateMap.isEmpty()){
                return false;
            }
            //보드 태스크 연결관계 업데이트
            BoardTaskMap updateMap = optUpdateMap.get();
            updateMap.setBoard(null);
            updateMap.setTask(updateMap.getTask());
            updateMap.setTaskOrder(updateMap.getTaskOrder());
            BoardTaskMap saveBoardTaskMap = this.boardTaskMapRepository.save(updateMap);
            
            //공유 상태 해제, 및 삭제 상태 true
            Task updateTask = saveBoardTaskMap.getTask();
            updateTask.setIsShared(false);
            updateTask.setIsDelete(true);
            Task task = taskRepository.save(updateTask);

            //공유 되어 있는 map 삭제
            ArrayList<TaskMemberMap> optTaskMemberMaps = taskMemberMapRepository.findAllByTaskAndMapType(task, "JOIN").orElse(new ArrayList<>());
            taskMemberMapRepository.deleteAll(optTaskMemberMaps);

            return true;
        }
        catch(Exception e){
            System.out.println("BoardTaskMapService_removeByTask_error = " + e);
            return false;
        }
    }
}

