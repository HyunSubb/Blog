package com.hyunsub.Blog.repository;

import com.hyunsub.Blog.domain.Post;
import com.hyunsub.Blog.domain.QPost;
import com.hyunsub.Blog.request.PostSearch;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory; // QueryDSL 쿼리 생성 팩토리
    private final QPost post = QPost.post; // Post 엔티티에 대한 Q클래스 인스턴스 (QueryDSL에서 Post를 지칭할 때 사용)

    @Override
    public Page<Post> getPostPageBySearchCondition(PostSearch postSearch, Pageable pageable) {

        // 1. 실제 데이터를 가져오는 쿼리 (content 쿼리)
        List<Post> content = jpaQueryFactory
                .selectFrom(post) // 어떤 엔티티에서 선택할지 (QPost 사용)
                // .where(post.title.contains(postSearch.getTitleKeyword())) // 만약 PostSearch에 제목 검색 조건이 추가된다면 이런 식으로 where 절 추가 가능
                .orderBy(post.id.desc()) // ID 내림차순 정렬 (JPA의 Sort 객체 대신 QueryDSL의 orderBy 사용)
                .offset(pageable.getOffset()) // Pageable 객체에서 현재 페이지의 시작 오프셋 계산 (JPA가 알아서 해줌)
                .limit(pageable.getPageSize()) // Pageable 객체에서 페이지당 항목 개수 가져옴
                .fetch(); // 쿼리를 실행하고 결과를 List로 가져옴

        // 2. 전체 개수를 가져오는 쿼리 (count 쿼리)
        // 페이징 결과를 정확히 만들기 위해, 현재 조건에 맞는 전체 게시글 수를 알아야 합니다.
        // content 쿼리와 동일한 where 조건을 적용해야 합니다.
        long totalCount = jpaQueryFactory
                .selectFrom(post)
                // .where(post.title.contains(postSearch.getTitleKeyword())) // content 쿼리와 동일한 조건 적용
                .fetch().size(); // QueryDSL 5.0.0부터 fetchCount()는 Deprecated, fetch().size() 사용 권장

        // Spring Data JPA의 Page 인터페이스 구현체인 PageImpl을 사용하여 Page<Post> 객체를 생성하여 반환합니다.
        return new PageImpl<>(content, pageable, totalCount);
    }
}
