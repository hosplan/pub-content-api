package com.iuni.content.controller;

import com.iuni.content.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/conts/project")
public class ProjectController{
    private final ProjectService projectService;
}
