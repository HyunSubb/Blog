package com.hyunsub.Blog.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueryDslConfig {
    private final EntityManager entityManager; // JPA 엔티티 매니저 주입

    public QueryDslConfig(EntityManager entityManager) { // 생성자 주입
        this.entityManager = entityManager;
    }

    @Bean
    public JPAQueryFactory jpaQueryFactory() { // JPAQueryFactory 빈으로 등록
        return new JPAQueryFactory(entityManager);
    }
}
