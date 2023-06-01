package com.jd.demo.util;

import com.jd.jim.cli.Cluster;
import com.jd.jim.cli.ReloadableJimClientFactory;

import com.jd.jim.cli.config.client.ConfigClient;
import com.jd.jim.cli.config.client.ConfigLongPollingClientFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * jimdb配置
 *
 * @author lihaohao13
 */
@Configuration
public class JimdbConfig {

    /**
     * jimdb地址
     */
    private final String url;

    /**
     * jimdb客户端
     */
    private final String configId;

    /**
     * 初始化缓存参数
     *
     * @param url      url
     * @param configId configId
     */
    public JimdbConfig(@Value("${jimp.uri}") String url, @Value("${jimp.configId}") String configId) {
        this.url = url;
        this.configId = configId;
    }

    /**
     * 初始化bean
     *
     * @return Cluster
     */
    @Bean
    public Cluster createJimdbCluster() {
        ConfigLongPollingClientFactory configLongPollingClientFactory = new ConfigLongPollingClientFactory();
        ConfigClient configClient = configLongPollingClientFactory.create();
        ReloadableJimClientFactory reloadableJimClientFactory = new ReloadableJimClientFactory();
        reloadableJimClientFactory.setConfigClient(configClient);
        reloadableJimClientFactory.setJimUrl(url);
        reloadableJimClientFactory.setConfigId(configId);
        return reloadableJimClientFactory.getClient();
    }
}
