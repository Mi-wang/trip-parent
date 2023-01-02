package cn.wolfcode.mongo.utils;

import cn.wolfcode.query.BaseQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;


/**
 * @author wby
 * @version 1.0
 * @date 2023-01-02 002 9:43
 */
public class MongoPageTemplate {

    private final MongoTemplate mongoTemplate;

    public MongoPageTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }


    /**
     * 简单的分页查询方法
     *
     * @param baseQuery   分页查询条件封装
     * @param entityClass 实体对象类型
     * @param <T>         分页查询的对象类型
     * @return 分页对象
     */
    public <T> org.springframework.data.domain.Page<T> page(BaseQuery baseQuery, Class<T> entityClass) {
        return this.page(baseQuery, entityClass, new Query());
    }

    /**
     * mongo 通用分页方法
     *
     * @param baseQuery 封装分页查询参数
     * @param query     封装分页条件
     * @param <T>       查询的类型
     * @return 分页对象
     */
    public <T> org.springframework.data.domain.Page<T> page(BaseQuery baseQuery, Class<T> entityClass, Query query) {
        // 1. 查询总数
        long total = mongoTemplate.count(query, entityClass);
        // 2. 判断总数是否有值, 如果没有就返回空分页对象
        if (total == 0) {
            return Page.empty();
        }

        // 3. 查询结果集
        // 封装分页参数
        query.skip(baseQuery.getStart()).limit(baseQuery.getPageSize());
        // 查询结果集
        List<T> list = mongoTemplate.find(query, entityClass);

        // 4. 封装分页对象, 返回结果
        PageRequest pageable = PageRequest.of(baseQuery.getCurrentPage() - 1, baseQuery.getPageSize());
        return new PageImpl<T>(list, pageable, total);
    }
}
