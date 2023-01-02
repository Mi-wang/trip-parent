package cn.wolfcode.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

/**
 * @author wby
 * @version 1.0
 * @date 2023-01-02 002 9:41
 */
@Getter
@Setter
public class Page<T> {

    /** 总数量 */
    private long total;
    /** 结果集 */
    private List<T> records;

    public Page(long total, List<T> records) {
        this.total = total;
        this.records = records;
    }

    public static <T> Page<T> empty() {
        return new Page<>(0, Collections.emptyList());
    }
}

