package com.aaa.sb.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * className:ShiroConfig
 * discription:配置类
 * author:wzb
 * createTime:2018-11-30 15:46
 */
@Configuration
public class ShiroConfig {

    /**
     * ShiroFilterFactoryBean 处理拦截资源文件问题。
     * 注意：单独一个ShiroFilterFactoryBean配置是或报错的，以为在
     * 初始化ShiroFilterFactoryBean的时候需要注入：SecurityManager
     *
     * Filter Chain定义说明 1、一个URL可以配置多个Filter，使用逗号分隔 2、当设置多个过滤器时，全部验证通过，才视为通过
     * 3、部分过滤器可指定参数，如perms，roles
     *
     */

    //分析Shiro核心API
    //三大核心类

    //Subject 当前用户主体 包含登录，注销，判断授权等方法
    // (关联)把操作交给 SecurityManager

    //SecurityManager 安全的管理器
    // 关联 Realm

    //Realm Shiro 连接数据的桥梁 ，
    //我们的程序需要操作数据库，去查询数据库或其他配置文件，获取用户的信息

    /**
     * 创建ShiroFilterFactoryBean
     */
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        //设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        // shiroFilterFactoryBean.setLoginUrl("/login");
        // 登录成功后要跳转的链接
        // shiroFilterFactoryBean.setSuccessUrl("/index");
        // 未授权界面;
        // shiroFilterFactoryBean.setUnauthorizedUrl("/403");


        //添加Shiro内置过滤器
        //实现权限相关拦截
        //常用过滤器：anon 无需登录 可以访问
        //authc 必须认证才可以访问
        //user 如果使用了rememberMe 的功能可以直接访问
        //perms 该资源必须授于资源权限才可以访问
        //role 该资源必须得到角色权限才可以访问

        //保证顺序用这个LinkedHashMap
        //我认为拦截的是方法
        Map<String,String> filterMap = new LinkedHashMap<String, String>();


        //不拦截该资源(放行)
        //filterMap.put("/user/test","anon");
        filterMap.put("/css/*", "anon");
        filterMap.put("/js/*", "anon");
        filterMap.put("/image/*", "anon");
        filterMap.put("/emp/login","anon");
        filterMap.put("/user/*","anon");
        filterMap.put("/power/*","anon");
        filterMap.put("/stat/*","anon");



        //授权过滤器(顺序，放在拦截页面下面不生效)
        //当授权拦截后，shiro自动跳转到 未授权的页面，(未授权的页面需要设置)
        //filterMap.put("/user/toAdd","perms[user:add]");
        //filterMap.put("/user/toUpd","perms[user:update]");

        //拦截页面
        filterMap.put("/**","authc");

        //设置未授权的页面
        //shiroFilterFactoryBean.setUnauthorizedUrl("/emp/unAuthorization");

        //拦截成功默认跳到 http://localhost:8888/sb/login.jsp
        //修改默认跳转的页面
        shiroFilterFactoryBean.setLoginUrl("/emp/toLogin");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);

        return shiroFilterFactoryBean;

    }




    /**
     * 创建DefaultWebSecuityManager
     */
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //关联Realm   userRealm来自下面的方法
        securityManager.setRealm(userRealm);

        return securityManager;

    }





    /**
     * 创建Realm
     */
    @Bean(name = "userRealm") //方法返回的对象放入spring的环境 以便让上面的方法使用
    public UserRealm getRealm(){
        return new UserRealm();
    }




    /**
     * 配置ShiroDialect 用于thymeleaf和shiro标签的配合使用
     * @return
     */
    @Bean
    public ShiroDialect getShiroDialect(){
        return new ShiroDialect();
    }


}
