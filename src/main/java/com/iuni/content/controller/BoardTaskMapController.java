package com.iuni.content.controller;

import com.iuni.content.domain.BoardTaskMap;
import com.iuni.content.service.BoardTaskMapService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
@RequestMapping("/conts/boardTaskMap")
public class BoardTaskMapController {
    private final BoardTaskMapService boardTaskMapService;

    @PostMapping()
    public BoardTaskMap create(@RequestBody BoardTaskMap data){
        return this.boardTaskMapService.create(data);
    }

    @PatchMapping("/move")
    public int updateMove(@RequestBody BoardTaskMap data) { return this.boardTaskMapService.updateMove(data); }

    @PatchMapping("/order")
    public int updateTaskOrder(@RequestBody ArrayList<BoardTaskMap> data) { return this.boardTaskMapService.updateTaskOrder(data); }

    @PatchMapping()
    public Boolean update(@RequestBody BoardTaskMap data){
        return this.boardTaskMapService.update(data);
    }

    @DeleteMapping()
    public Boolean remove(@RequestParam("id") Long id){
        return this.boardTaskMapService.remove(id);
    }

    @DeleteMapping("/board")
    public int removeByBoard(@RequestParam("id") Long id) {
        return this.boardTaskMapService.removeByBoard(id);
    }

    @DeleteMapping("/task")
    public boolean removeByTask(@RequestParam("id") Long id){
        return this.boardTaskMapService.removeByTask(id);
    }
}
