package com.iuni.content.controller;

import com.iuni.content.domain.Task;
import com.iuni.content.helper.jwt.JWT;
import com.iuni.content.service.TrashBasketService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/conts/trashBasket")
public class TrashBasketController {
    private final JWT trashJwt;
    private final TrashBasketService trashBasketService;

    @GetMapping("/all")
    public ArrayList<Task> load(@RequestHeader Map<String, String> headers){
        return this.trashBasketService.load(this.trashJwt.getId(headers.get("authorization")));
    }
}

