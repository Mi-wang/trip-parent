package cn.wolfcode.service;

/**
 * @author wby
 * @version 1.0
 * @date 2022/12/17 17:13
 */
public interface ISmsService {

    void send(String phone, String type);
}
