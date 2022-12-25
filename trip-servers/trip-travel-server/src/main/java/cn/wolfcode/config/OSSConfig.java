package cn.wolfcode.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wby
 * @version 1.0
 * @date 2022/12/25 15:57
 */
@Configuration
@EnableConfigurationProperties(OSSProperties.class)
public class OSSConfig {

    @Autowired
    private OSSProperties properties;

    @Bean(destroyMethod = "shutdown")
    public OSS ossClient() {
        return new OSSClient(properties.getEndpoint(), properties.getAccessKeyId(), properties.getAccessKeySecret());
    }
}

