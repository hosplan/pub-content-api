package com.iuni.content.controller;

import com.iuni.content.domain.Board;
import com.iuni.content.helper.jwt.JWT;
import com.iuni.content.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/conts/board")
public class BoardController {
    private final BoardService boardService;

    private final JWT jwt;

    @GetMapping()
    public Board get(@RequestParam("id") Long id){
        return this.boardService.get(id);
    }

    @GetMapping("/all")
    public List<Board> load(@RequestHeader Map<String, String> headers){
        return this.boardService.load(this.jwt.getId(headers.get("authorization")));
    }

    @PostMapping("/init")
    public Board init(@RequestBody Board data, @RequestHeader Map<String, String> headers){
        data.setCreator(this.jwt.getId(headers.get("authorization")));
        return this.boardService.create(data);
    }

    @PostMapping()
    public Board create(@RequestBody Board data, @RequestHeader Map<String, String> headers){
        data.setCreator(this.jwt.getId(headers.get("authorization")));
        return this.boardService.create(data);
    }

    @PatchMapping()
    public Boolean patch(@RequestBody Board data){
        return this.boardService.patch(data);
    }

    @PatchMapping("/trash")
    public HashMap<String, String> moveTrash(@RequestBody Board data){ return this.boardService.moveTrash(data); }

    @DeleteMapping()
    public Boolean remove(@RequestParam("id") Long id) {
        return this.boardService.remove(id);
    }
}
