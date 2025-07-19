package com.hyunsub.Blog.repository;

import com.hyunsub.Blog.domain.Post;
import com.hyunsub.Blog.request.PostSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostRepositoryCustom {
    // PostSearch 조건과 Pageable을 받아 Page<Post>를 반환하는 메서드를 정의
    // JPA에 없는 기능을 내가 커스텀해서 추가하겠다는 의미다.
    Page<Post> getPostPageBySearchCondition(PostSearch postSearch, Pageable pageable);
}