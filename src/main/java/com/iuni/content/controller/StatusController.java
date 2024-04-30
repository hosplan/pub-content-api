package com.iuni.content.controller;

import com.iuni.content.domain.Status;
import com.iuni.content.service.StatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/conts/status")
public class StatusController {
    private final StatusService statusService;

    @GetMapping()
    public Status get(@RequestParam("id") Long id) {
        return this.statusService.get(id);
    }

    @GetMapping("/type")
    public List<Status> loadByType(@RequestParam("type") String type){
        return this.statusService.loadByType(type);
    }
    @GetMapping("/all")
    public List<Status> load(){
        return this.statusService.load();
    }

    @PostMapping()
    public Status create(@RequestBody Status data){
        return this.statusService.create(data);
    }

    @PatchMapping()
    public Boolean update(@RequestBody Status data){
        return this.statusService.update(data);
    }

    @DeleteMapping()
    public Boolean remove(@RequestParam("id") Long id){
        return this.statusService.remove(id);
    }
}
