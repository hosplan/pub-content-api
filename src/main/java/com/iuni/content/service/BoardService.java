package com.iuni.content.service;

import com.iuni.content.domain.Board;
import com.iuni.content.repository.BoardRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service("boardService")
public class BoardService {
    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository){
        this.boardRepository = boardRepository;
    }
    public List<Board> load(Long userId){
        try{
            System.out.println("userId = " + userId);
            return this.boardRepository.findAllByCreatorAndIsDeleteOrderByBoardOrderDesc(userId, false).orElse(new ArrayList<>());
        }
        catch(Exception e){
            throw e;
        }
    }

    public Board get(Long id){
        try{
            return this.boardRepository.findById(id).orElse(new Board());
        }
        catch(Exception e){
            throw e;
        }
    }

    public Board create(Board board){
        try{
            return this.boardRepository.save(board);
        }
        catch(Exception e){
            throw e;
        }
    }

    public Boolean patch(Board board){
        try{
            Optional<Board> optUpdateBoard = this.boardRepository.findById(board.getId());
            if(optUpdateBoard.isPresent()){
                Board updateBoard = optUpdateBoard.get();
                updateBoard.setName(board.getName());
                updateBoard.setDescription(board.getDescription());
                updateBoard.setProjectId(board.getProjectId());
                this.boardRepository.save(updateBoard);
                return true;
            }
            else {
                return false;
            }
        }
        catch(Exception e){
            throw e;
        }
    }


    public HashMap<String, String> moveTrash(Board data){
        HashMap<String, String> result = new HashMap<>();
        try{
            Optional<Board> optBoard = boardRepository.findById(data.getId());
            if(optBoard.isPresent()){
                Board updateBoard = optBoard.get();
                updateBoard.setDelete(true);
                boardRepository.save(updateBoard);
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

    public Boolean remove(Long id){
        try{
            this.boardRepository.deleteById(id);
            return true;
        }
        catch(Exception e){
            throw e;
        }
    }

}
