package com.w7.sweatlog_backend.search.controller;

import com.w7.sweatlog_backend.dto.PostResponse;
import com.w7.sweatlog_backend.entity.Post;
import com.w7.sweatlog_backend.entity.enums.SearchMode;
import com.w7.sweatlog_backend.search.service.PostSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class PostSearchController {

    private final PostSearchService postSearchService;

    @GetMapping("/posts")
    public Page<PostResponse> searchPosts(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "PRIMARY_CONTAINS") SearchMode mode,
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return postSearchService.search(keyword, mode, pageable)
                .map(PostResponse::from);
    }

}
