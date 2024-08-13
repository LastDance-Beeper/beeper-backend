package com.lastdance.beeper.service;

import com.lastdance.beeper.data.dto.TagDTO;

import java.util.List;

public interface TagService {

    TagDTO.Info save(TagDTO.request request);

    List<TagDTO.Info> findAll();
}
