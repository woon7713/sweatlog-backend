package com.w7.sweatlog_backend.search.service;

import com.w7.sweatlog_backend.entity.Post;
import com.w7.sweatlog_backend.repository.PostRepository;
import com.w7.sweatlog_backend.entity.enums.SearchMode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostSearchService {

    private final PostRepository postRepository;

    @Transactional(readOnly = true)
    public Page<Post> search(String keyword, SearchMode mode, Pageable pageable) {
        String kw = keyword == null ? "" : keyword.trim();

        if (kw.isEmpty()) return Page.empty(pageable); // if kw == "" 일 때, LIKE '%' 가 되어 전체가 나오는걸 방지

        // AUTOCOMPLETE는 항상 첫 페이지 + 작은 크기 권장
        Pageable effective = (mode == SearchMode.AUTOCOMPLETE)
                ? firstPage(pageable, 10)
                : pageable;

        return switch (mode) {
            case AUTOCOMPLETE ->
                    postRepository.findByTitlePrefix(kw, effective);               // PRIMARY prefix
            case PRIMARY_CONTAINS ->
                    postRepository.findByTitleContains(kw, effective);             // PRIMARY contains
            case EXTENDED_CONTAINS ->
                    postRepository.findByTitleOrMemoContains(kw, effective);       // PRIMARY or SECONDARY
        };
    }

    private Pageable firstPage(Pageable pageable, int maxSize) {
        int size = Math.min(pageable.getPageSize(), maxSize);
        Sort sort = pageable.getSort().isUnsorted()
                ? Sort.by(Sort.Direction.ASC, "title")
                : pageable.getSort();
        return PageRequest.of(0, size, sort);
    }
}
