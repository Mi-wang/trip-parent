package cn.wolfcode.controller;

import cn.wolfcode.vo.AjaxResult;
import org.springframework.http.ResponseEntity;

/**
 * @author wby
 * @version 1.0
 * @date 2022/12/17 15:29
 */
public class BaseController {
    protected <T> ResponseEntity<AjaxResult<T>> success() {
        return ResponseEntity.ok(AjaxResult.success());
    }
    protected <T> ResponseEntity<AjaxResult<T>> success(String msg) {
        return ResponseEntity.ok(AjaxResult.success(msg));
    }
    protected <T> ResponseEntity<AjaxResult<T>> success(T data) {
        return ResponseEntity.ok(AjaxResult.success(data));
    }

    protected <T> ResponseEntity<AjaxResult<T>> failed(Integer code, String msg) {
        return ResponseEntity.status(code).body(AjaxResult.failed(msg));
    }
}
