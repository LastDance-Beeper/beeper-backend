package com.lastdance.beeper.controller;

import com.lastdance.beeper.data.dto.ResponseDTO;
import com.lastdance.beeper.data.dto.TagDTO;
import com.lastdance.beeper.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tag-select")
@Slf4j
public class TagSelectController {
    TagService tagService;

    public TagSelectController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping("/search/{keyword}")
    public ResponseEntity<ResponseDTO<List<TagDTO.Info>>> searchByName(
            @PathVariable(value = "keyword") String keyword){
        List<TagDTO.Info> infos = tagService.searchByName(keyword);

        return ResponseEntity.ok(ResponseDTO.ofSuccessWithData(infos));
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseDTO<List<TagDTO.Info>>> getAll(){
        List<TagDTO.Info> infos = tagService.findAll();

        return ResponseEntity.ok(ResponseDTO.ofSuccessWithData(infos));
    }

    @PostMapping("/save/list")
    public ResponseEntity<ResponseDTO<List<TagDTO.Info>>> saveList(@RequestBody List<TagDTO.requestList> tags){
        List<TagDTO.Info> infos = tagService.saveList(tags);
        return ResponseEntity.ok(ResponseDTO.ofSuccessWithData(infos));
    }


}
