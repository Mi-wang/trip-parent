package cn.wolfcode.filter;

import cn.wolfcode.config.AuthProperties;
import cn.wolfcode.domain.UserInfo;
import cn.wolfcode.service.GatewayTokenService;
import cn.wolfcode.vo.AjaxResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * @author wby
 * @version 1.0
 * @date 2022/12/20 10:21
 */
@Component
public class AuthFilter implements GlobalFilter {

    private final ObjectMapper objectMapper;

    private final GatewayTokenService tokenService;

    private final AuthProperties authProperties;

    public AuthFilter(ObjectMapper objectMapper, GatewayTokenService tokenService, AuthProperties authProperties) {
        this.objectMapper = objectMapper;
        this.tokenService = tokenService;
        this.authProperties = authProperties;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // ServerWebExchange 可以简单把他当做是 Servlet 中的 ServletContext
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        // 1. 判断是否开启认证，如果没有开启就直接放行
        if (authProperties.isEnable()) {

            // 2. 判断当前请求是否在白名单列表中，若果在，直接放行
            String path = request.getPath().value();
            if (!authProperties.getWhiteList().contains(path)) {

                // 1. 从请求对象中获取 token，如果获取不到，认证失败
                // 2. 验证 token 是否有效，如果无效，认证失败
                // 3. 从 token 中获取 uuid
                // 4. 再从 redis 中获取到用户
                UserInfo userInfo = tokenService.getLoginUser(request);
                // 5. 如果用户不存在，认证失败
                if (userInfo == null) {
                    return this.authFailed(response, "用户认证失败");
                }
            }
        }

        // 6. 放行
        return chain.filter(exchange);
    }

    /**
     * 专门处理认证失败的方法
     */
    private Mono<Void> authFailed(ServerHttpResponse resp, String msg) {
        // 设置 http 响应状态码，默认就是 200
        resp.setStatusCode(HttpStatus.OK);
        // 设置响应类型
        resp.getHeaders().add("Content-Type", "application/json;charset=utf-8");

        // 最终响应的对象
        AjaxResult<?> result = AjaxResult.failed(401, msg);
        String json = "";
        try {
            // fastJSON / Jackson => Spring 默认支持的
            // jackson 将对象转换为 json 字符串
            json = objectMapper.writeValueAsString(result);
        } catch (Exception ignored) {
        }

        // 对字符串使用 utf8 字符集进行编码
        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
        // 数据流缓冲对象
        DataBuffer buffer = resp.bufferFactory().wrap(bytes);

        // 响应结果给前端 === HttpServletResponse.getWriter().println(json)
        return resp.writeWith(Mono.just(buffer));
    }
}
