package com.hyunsub.Blog.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob // DB에서는 Long Text 형식으로 저장되게 하는 것.
    private String content;

    public void updateTitle(String title) {
        this.title = title; // 현재 엔티티 인스턴스의 title 필드를 직접 변경
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
