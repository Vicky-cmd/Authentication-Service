package com.infotrends.in.InfoTrendsIn.config;

import java.time.Duration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisConfiguration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;

import io.lettuce.core.ClientOptions;
import io.lettuce.core.SocketOptions;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Configuration
@ConditionalOnProperty(prefix = "spring.redis", name = "enable", havingValue = "true")
@ConfigurationProperties(prefix = "spring.redis")
@Setter
@Slf4j
@Import({RedisAutoConfiguration.class})
@EnableRedisRepositories
public class RedisConfig {

	 private String host;
	 private int port;
	 private String password;
	 private int ttl;

	    @Bean
	    @Primary
	    public ReactiveRedisConnectionFactory reactiveRedisConnectionFactory(RedisConfiguration defaultRedisConfig) {
	        LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
	                .useSsl().build();
	        log.info("Creating Redis Connection Factory");
	        return new LettuceConnectionFactory(defaultRedisConfig, clientConfig);
	    }
	    
	    @Bean
	    public RedisCacheConfiguration cacheConfiguration() {
	        return RedisCacheConfiguration.defaultCacheConfig()
	          .entryTtl(Duration.ofMinutes(ttl))
	          .disableCachingNullValues()
	          .serializeValuesWith(SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
	    }
	 
//	 	@Bean
//	 	@Primary
//	 	public ReactiveRedisConnectionFactory letuceConnectionFactory() {
//	 		final SocketOptions socketOptions = SocketOptions.builder().connectTimeout(Duration.ofSeconds(5)).build();
//	 		ClientOptions clientOperations = ClientOptions.builder()
//	 						.socketOptions(socketOptions)
//	 						.autoReconnect(true)
//	 						.disconnectedBehavior(ClientOptions.DisconnectedBehavior.REJECT_COMMANDS)
//	 						.build();
//	 		
//	 		LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
//	 						.commandTimeout(Duration.ofSeconds(5))
//	 						.clientOptions(clientOperations)
//	 						.build();
//	 		
//	 		RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration(this.host, this.port);
//	 		redisConfig.setPassword(RedisPassword.of(this.password));
//	 		return new LettuceConnectionFactory(redisConfig, clientConfig);
//	 	}

	    @Bean
	    public RedisConfiguration defaultRedisConfig() {
	        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
	        config.setHostName(host);
	        config.setPassword(RedisPassword.of(password));
	        config.setPort(port);
	        return config;
	    }
}
