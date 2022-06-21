package com.infotrends.in.authenticationserver.dao.redis;

import com.infotrends.in.authenticationserver.model.redis.TokensEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokensRedisRepository extends CrudRepository<TokensEntity, String> {
}
