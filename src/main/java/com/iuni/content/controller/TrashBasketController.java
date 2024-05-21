package com.iuni.content.controller;

import com.iuni.content.domain.Board;
import com.iuni.content.domain.Task;
import com.iuni.content.domain.TrashBasket;
import com.iuni.content.helper.jwt.JWT;
import com.iuni.content.service.BoardService;
import com.iuni.content.service.TaskService;
import com.iuni.content.service.TrashBasketService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/conts/trashBasket")
public class TrashBasketController {
    private final JWT trashJwt;
    private final TrashBasketService trashBasketService;
    private final TaskService taskService;
    private final BoardService boardService;

    @GetMapping("/all")
    public ArrayList<TrashBasket> load(@RequestHeader Map<String, String> headers){
        return this.trashBasketService.load(this.trashJwt.getId(headers.get("authorization")));
    }
    @PatchMapping("/restore")
    public HashMap<String, String> restore(@RequestParam("id") Long id){
        HashMap<String, String> result = new HashMap<>();
        boolean condition = false;
        Optional<TrashBasket> optTrash = this.trashBasketService.trashBasketCheck(id);
        if (!optTrash.isPresent()) {
            result.put("result", "fail");
            return result;
        }
        switch(optTrash.get().getType()){
            case 0 :
                condition = taskService.restore(optTrash.get().getTrashId());
                break;
            case 1 :
                condition = boardService.restore(optTrash.get().getTrashId());
                break;
            default:
                result.put("result", "fail");
                return result;
        }
        if(condition){
            result = this.trashBasketService.remove(id);
            result.put("result", "success");
            return result;
        }
        else{
            result.put("result", "fail");
            return result;
        }
    }
    @PatchMapping("/remove")
    public HashMap<String, String> remove(@RequestParam("id") Long id){ return this.trashBasketService.remove(id); }
}

