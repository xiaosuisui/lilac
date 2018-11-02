package io.github.isliqian.cache.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
/**
 * @author wxt.liqian
 * @version 2018/11/1
 * @desc
 */
@Configuration
public class JedisRedisConfigure {
    private  String host="127.0.0.1";

    private  String password=null;

    private  int port=6379;

    private  int timeout=3600;

    private int maxIdle=20;

    private long maxWaitMillis=60000;
    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setHostName(host);
        factory.setPort(port);
        factory.setTimeout(timeout);
        factory.setPassword(password);
        return factory;
    }
    @Bean
    public Jedis redisPoolFactory() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        JedisPool jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout, password);
        return jedisPool.getResource();
    }

}
