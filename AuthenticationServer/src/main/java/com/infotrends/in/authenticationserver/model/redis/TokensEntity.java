package com.infotrends.in.authenticationserver.model.redis;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

@RedisHash(value = "Tokens", timeToLive = 86400)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokensEntity {


    private String id;

    private String username;
    private String authenticationToken;
    private String modifiedBy;
    private LocalDateTime modifiedOn;
    private String createdBy;
    private LocalDateTime createdOn;
}
