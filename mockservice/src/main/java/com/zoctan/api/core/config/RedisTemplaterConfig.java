package com.zoctan.api.core.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@EnableAutoConfiguration
@Slf4j
public class RedisTemplaterConfig {

	@Value("${spring.redis.jedis.pool.max-active}")
    Integer maxActive;
    @Value("${spring.redis.jedis.pool.max-idle}")
    Integer maxIdle;
    @Value("${spring.redis.jedis.pool.max-wait}")
    Integer maxWait;
    @Value("${spring.redis.jedis.pool.min-idle}")
    Integer minIdle;
    @Value("${spring.redis.host}")
    String hostname;
//    @Value("${spring.redis.password}")
//    String password;
    @Value("${spring.redis.port}")
    String port;
    @Value("${spring.redis.database}")
    String database;
    @Value("${spring.redis.timeout}")
    String timeout;

	@Bean
    public JedisPoolConfig getRedisConfig(){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxActive);
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);
		config.setMaxWaitMillis(maxWait);
        return config;
    }

    @Bean
    public JedisConnectionFactory getConnectionFactory(){
        JedisConnectionFactory factory = new JedisConnectionFactory();
        JedisPoolConfig config = getRedisConfig();
        factory.setPoolConfig(config);
        factory.setHostName(hostname);
        //factory.setPassword(password);
        factory.setPort(Integer.parseInt(port));
        factory.setDatabase(Integer.parseInt(database));
        factory.setTimeout(Integer.parseInt(timeout));
        log.info("JedisConnectionFactory bean init success.");
        return factory;
    }


    @Bean
    public RedisTemplate<String, Object> getRedisTemplate(){
    	RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
    	template.setConnectionFactory(getConnectionFactory());
    	template.setKeySerializer(template.getStringSerializer());
    	template.setValueSerializer(template.getStringSerializer());
    	template.setHashKeySerializer(template.getStringSerializer());
    	template.setHashValueSerializer(template.getStringSerializer());
        return template;
    }
}
