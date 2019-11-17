package com.jsj.webapi.core.shiro;

/**
 * Created by jinshouji on 2018/5/14.
 */

/**
 * @author ：jinshouji
 * @create ：2018-05-14 13:26
 **/
import com.jsj.webapi.core.redis.RedisCacheManager;
import com.jsj.webapi.core.redis.RedisSessionDAO;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.ShiroHttpSession;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shiro 配置
 *
 Apache Shiro 核心通过 Filter 来实现，就好像SpringMvc 通过DispachServlet 来主控制一样。
 既然是使用 Filter 一般也就能猜到，是通过URL规则来进行过滤和权限校验，所以我们需要定义一系列关于URL的规则和访问权限。
 * 暂时不用Redis 来保存shiro的session
 */
// @Configuration
public class ShiroConfiguration {


    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 凭证匹配器
     * （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了
     * ）
     *
     * @return
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5");//散列算法:这里使用MD5算法;
        hashedCredentialsMatcher.setHashIterations(2);//散列的次数，比如散列两次，相当于 md5(md5(""));
        return hashedCredentialsMatcher;
    }

    /**
     * shiro缓存管理器;
     * 需要注入对应的其它的实体类中：
     * 1、安全管理器：securityManager
     * 可见securityManager是整个shiro的核心；
     * @return
     */
    @Bean(name="ehCacheManager")
    public EhCacheManager ehCacheManager(){
        logger.debug("ShiroConfiguration.ehCacheManager()");
        EhCacheManager cacheManager = new EhCacheManager();
        cacheManager.setCacheManagerConfigFile("classpath:ehcache-shiro.xml");
        return cacheManager;
    }

    @Bean(name = "redisCacheManager")
    public RedisCacheManager redisCacheManager() {
        logger.debug("ShiroConfiguration.redisCacheManager()");
        return new RedisCacheManager();
    }

    @Bean(name = "redisSessionDAO")
    public RedisSessionDAO redisSessionDAO(){
        logger.debug("ShiroConfiguration.redisSessionDAO()");
        return new RedisSessionDAO();
    }

    @Bean(name = "customSessionListener")
    public CustomSessionListener customSessionListener(){
        logger.debug("ShiroConfiguration.customSessionListener()");
        return new CustomSessionListener();
    }

    /**
     * @see DefaultWebSessionManager
     * @return
     */
    @Bean(name="sessionManager")
    public DefaultWebSessionManager defaultWebSessionManager() {
        logger.debug("ShiroConfiguration.defaultWebSessionManager()");
        //DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        MySessionManage sessionManager=new MySessionManage();
        //MySessionManage  extends DefaultWebSessionManager

        //用户信息必须是序列化格式，要不创建用户信息创建不过去，此坑很大，
        sessionManager.setSessionDAO(redisSessionDAO());//如不想使用REDIS可注释此行
        Collection<SessionListener> sessionListeners = new ArrayList<>();
        sessionListeners.add(customSessionListener());
        sessionManager.setSessionListeners(sessionListeners);
        //单位为毫秒（1秒=1000毫秒） 3600000毫秒为1个小时
        sessionManager.setSessionValidationInterval(3600000*12);
        //3600000 milliseconds = 1 hour
        sessionManager.setGlobalSessionTimeout(3600000*12);
        //是否删除无效的，默认也是开启
        sessionManager.setDeleteInvalidSessions(true);
        //是否开启 检测，默认开启
        sessionManager.setSessionValidationSchedulerEnabled(true);

        //创建会话Cookie
        Cookie cookie = new SimpleCookie(ShiroHttpSession.DEFAULT_SESSION_ID_NAME);
        cookie.setName("WEBID");
        cookie.setHttpOnly(true);
        sessionManager.setSessionIdCookie(cookie);
        return sessionManager;
    }

    /**
     * @see org.apache.shiro.mgt.SecurityManager
     * @return
     */
    @Bean(name="securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManage(){

       logger.debug("ShiroConfiguration.getDefaultWebSecurityManage()");
        DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();
        //MySessionManage securityManager=new MySessionManage();

        securityManager.setRealm(myShiroRealm());

        //注入缓存管理器;
        securityManager.setCacheManager(redisCacheManager());
        securityManager.setSessionManager(defaultWebSessionManager());
        return securityManager;
    }

    /**
     * 用户自定义的权限控制 Realm
     * @return
     */
    @Bean
    public MyShiroRealm myShiroRealm() {
        MyShiroRealm myShiroRealm = new MyShiroRealm();
        //myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return myShiroRealm;
    }

    /**
     * 配置访问的目录
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shirFilter(DefaultWebSecurityManager securityManager)
    {
        System.out.println("ShiroConfiguration.shirFilter()");
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        //注意过滤器配置顺序 不能颠倒
        //配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了，登出后跳转配置的loginUrl
        //filterChainDefinitionMap.put("/platform/login/signout", "logout");
        // 配置不会被拦截的链接 顺序判断
        /*
        filterChainDefinitionMap.put("/shirotest/**", "anon"); */

        //登录的页面（允许匿名访问的接口）
        filterChainDefinitionMap.put("/login/platform/**","anon");
        filterChainDefinitionMap.put("/platform/**","anon");
        filterChainDefinitionMap.put("/app/**","anon");
        filterChainDefinitionMap.put("/static/**","anon");
        filterChainDefinitionMap.put("/Apidoc/**", "anon");

        //filterChainDefinitionMap.put("/**", "authc");
        //filterChainDefinitionMap.put("/**", "user");
        //配置shiro默认登录界面地址，前后端分离中登录界面跳转应由前端路由控制，后台仅返回json数据

        shiroFilterFactoryBean.setLoginUrl("/login/platform/unauth");
        // 登录成功后要跳转的链接
//        shiroFilterFactoryBean.setSuccessUrl("/index");
        //未授权界面;
//        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }


    /**
     *  开启shiro aop注解支持.
     *  使用代理方式;所以需要开启代码支持;
     * @param securityManager
     * @return
     */
    @Bean(name="authorizationAttributeSourceAdvisor")
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager){
        logger.debug("ShiroConfiguration.authorizationAttributeSourceAdvisor()");
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * 注入LifecycleBeanPostProcessor
     * @return
     */
    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        logger.debug("ShiroConfiguration.lifecycleBeanPostProcessor()");
        return new LifecycleBeanPostProcessor();
    }

    @ConditionalOnMissingBean
    @Bean(name = "defaultAdvisorAutoProxyCreator")
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        logger.debug("ShiroConfiguration.getDefaultAdvisorAutoProxyCreator()");
        DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();
        daap.setProxyTargetClass(true);
        return daap;
    }
}
