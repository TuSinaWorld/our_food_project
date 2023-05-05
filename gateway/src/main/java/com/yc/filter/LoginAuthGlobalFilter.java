package com.yc.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yc.Util.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

/**
 * @Author: 乐哥
 * @Date: 2023/4/28
 * @Time: 20:38
 * @Description:  授权验证全局过滤器
 */
@Component
@Slf4j
public class LoginAuthGlobalFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        List<String> token = exchange.getRequest().getHeaders().get("token");
        try {
            if (token.size() != 1) {
                throw new RuntimeException();
            }
           //TODO:解析token进行验证
            Claims chaims = JwtUtil.parseJWT(token.get(0));

            return chain.filter(exchange);
        }catch (Exception e){
            log.error("token不存在或错误!");
            Map<String,Object> map = new HashMap<>();
            map.put("code",0);
            map.put("msg","token不存在或错误");
            return doResponse(exchange.getResponse(),map);
        }
    }

    private Mono<Void> doResponse(ServerHttpResponse response, Map map){
        response.setStatusCode(UNAUTHORIZED);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(map);
            DataBuffer wrap = response.bufferFactory().wrap(json.getBytes());
            return response.writeWith(Flux.just(wrap));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
