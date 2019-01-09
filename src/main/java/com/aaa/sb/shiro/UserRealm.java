package com.aaa.sb.shiro;

import com.aaa.sb.service.EmpService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * className:UserRealm
 * discription:自定义Realm
 * author:wzb
 * createTime:2018-11-30 15:50
 */
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private EmpService empService;

    /**
     * 执行授权逻辑
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行授权逻辑");

        //给资源进行授权
        //SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //添加资源的授权字符串
        //info.addStringPermission("user:add");

        //到数据库查询当前登录用户的授权字符串
        //获取当前登录用户

        //实体
        /*Subject subject = SecurityUtils.getSubject();
        Emp emp = (Emp) subject.getPrincipal();
        Emp newEmp = empService.findByUserId(emp.getId());
        info.addStringPermission(newEmp.getUserRole());*/

        //Map
        //Subject subject = SecurityUtils.getSubject();
        //getPrincipal() 得到此登录用户的principal，里面放的userName
        //String userName = (String) subject.getPrincipal();
        //根据所登录的用户名获取该用户的权限字符串
        //String userRole = empService.selByUserName(userName).get(0).get("USERROLE")+"";

        //info.addStringPermission(userRole);

        return null;
    }

    /**
     * 去执行一些认证的逻辑
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("执行一些认证的逻辑");
        //令牌
        UsernamePasswordToken token = (UsernamePasswordToken)authenticationToken;
        //判断用户名 密码是否正确

        //实体
        //Emp user = empService.findByUserName(token.getUsername());
        /*if(user==null){
            //用户名不存在
            return null; //shiro抛出UnknownAccountException异常
        }*/

        //1 需要返回login方法的数据 2 数据库的密码，shiro自行判断 3 shiro名字
        //return new SimpleAuthenticationInfo(user,user.getPassWord(),"");

        String userName = token.getUsername();
        //System.out.println(userName);
        //Map
        List<Map> userList = empService.selByUserName(userName);
        System.out.println(userList);
        if(userList==null||userList.size()==0){
            //用户名不存在
            return null; //shiro抛出UnknownAccountException异常
        }

        //1 principal 需要返回login方法的数据 2 数据库的密码，shiro自行判断 3 shiro名字
        //System.out.println(11111111);
        //System.out.println(userName);
        //System.out.println(userList.get(0).get("PASSWORD"));
        return new SimpleAuthenticationInfo("",userList.get(0).get("PASSWORD"),"");


    }
}
