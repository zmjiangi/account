package net.aulang.account.configuration;

import org.apereo.cas.services.ServiceRegistryDao;
import org.apereo.cas.services.ServicesManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class JsonServiceRegistryConfiguration {
    @Autowired
    @Qualifier("servicesManager")
    private ServicesManager servicesManager;

    @Autowired
    @Qualifier("serviceRegistryDao")
    private ServiceRegistryDao serviceRegistryDao;

    @Autowired
    @Qualifier("embeddedJsonServiceRegistry")
    private ServiceRegistryDao embeddedJsonServiceRegistry;

    /**
     * 添加了OAuth支持后，CAS不读取json文件，需要手动配置
     */
    @PostConstruct
    public void init() {
        this.embeddedJsonServiceRegistry.load().forEach(e -> this.serviceRegistryDao.save(e));
        servicesManager.load();
    }
}
