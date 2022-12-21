package cn.wolfcode.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wby
 * @version 1.0
 * @date 2022/12/21 18:00
 */
@ConfigurationProperties(prefix = "auth")
public class AuthProperties {

    private boolean enable = true;
    private List<String> whiteList = new ArrayList<>();

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public List<String> getWhiteList() {
        return whiteList;
    }

    public void setWhiteList(List<String> whiteList) {
        this.whiteList = whiteList;
    }
}
