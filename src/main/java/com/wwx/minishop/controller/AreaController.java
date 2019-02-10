package com.wwx.minishop.controller;

import com.wwx.minishop.beans.Msg;
import com.wwx.minishop.entity.Area;
import com.wwx.minishop.repository.AreaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AreaController {

    @Autowired
    AreaRepository repository;

    @GetMapping("/areas")
    public Msg getAllArea(){
        List<Area> areas = repository.findAll();
        return Msg.success().add("areas",areas);
    }
}
