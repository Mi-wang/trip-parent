package cn.wolfcode.controller;

import cn.wolfcode.vo.R;
import org.springframework.http.ResponseEntity;

/**
 * @author wby
 * @version 1.0
 * @date 2022/12/17 15:29
 */
public class BaseController {
    protected <T> ResponseEntity<R<T>> success() {
        return ResponseEntity.ok(R.ok());
    }
    protected <T> ResponseEntity<R<T>> success(String msg) {
        return ResponseEntity.ok(R.ok(msg));
    }
    protected <T> ResponseEntity<R<T>> success(T data) {
        return ResponseEntity.ok(R.ok(data));
    }

    protected <T> ResponseEntity<R<T>> failed(Integer code, String msg) {
        return ResponseEntity.status(code).body(R.err(msg));
    }
}
