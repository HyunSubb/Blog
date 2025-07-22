package com.hyunsub.Blog.config.data;

import lombok.Data;

@Data
public class UserSession {
    public Long id;
    public String accessToken;
}
