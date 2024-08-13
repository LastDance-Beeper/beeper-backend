package com.lastdance.beeper.service.impl;

import com.lastdance.beeper.data.domain.Tag;
import com.lastdance.beeper.data.dto.TagDTO;
import com.lastdance.beeper.data.repository.TagRepository;
import com.lastdance.beeper.service.TagService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class TagServiceImpl implements TagService {
    private TagRepository tagRepository;

    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public List<TagDTO.Info> searchByName(String keyword) {
        List<Tag> tagList = tagRepository.findByNameContaining(keyword);

        List<TagDTO.Info> tagDTOList = new ArrayList<>();
        for (Tag tag : tagList) {
            TagDTO.Info tagDTO = new TagDTO.Info();
        }

        return tagDTOList;
    }

    @Override
    public List<TagDTO.Info> findAll() {
        List<Tag> tagList = tagRepository.findAll();
        List<TagDTO.Info> tagDTOList = new ArrayList<>();
        for (Tag tag : tagList) {
            TagDTO.Info tagDTO = new TagDTO.Info();
        }
        return tagDTOList;
    }

    @Override
    public List<TagDTO.Info> saveList(List<TagDTO.requestList> tagList) {

        List<TagDTO.Info> tagDTOList = new ArrayList<>();

        for(TagDTO.requestList tagDTO : tagList) {
            Tag tag = tagRepository.getById(tagDTO.getId());

            TagDTO.Info tagDTOInfo = new TagDTO.Info(tagRepository.save(tag));

            tagDTOList.add(tagDTOInfo);
        }

        return tagDTOList;
    }

    @Override
    public TagDTO.Info save(TagDTO.request request) {
        Tag tag = Tag.builder().name(request.getName()).build();

        TagDTO.Info tagDTOInfo = new TagDTO.Info(tagRepository.save(tag));
        return tagDTOInfo;
    }
}
