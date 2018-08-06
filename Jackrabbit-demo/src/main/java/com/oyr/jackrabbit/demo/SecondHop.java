package com.oyr.jackrabbit.demo;

import org.apache.jackrabbit.commons.JcrUtils;

import javax.jcr.*;

/**
 * Create by 欧阳荣
 * 2018/8/7 0:08
 */
public class SecondHop {

    public static void main(String[] args) throws RepositoryException {
        //获取仓库
        Repository repository = JcrUtils.getRepository();
        //获取session
        Session session = repository.login(new SimpleCredentials("admin",
                "admin".toCharArray()));

        try {
            //获取根节点
            Node root = session.getRootNode();
            // 添加节点数据（仔细看hello节点和world节点的组织方式，可以发现它是一颗树形结构）
            Node hello = root.addNode("hello");
            Node world = hello.addNode("world");
            world.setProperty("message", "my is world node");
            //保存
            session.save();

            //数据查询
            Node node = root.getNode("hello/world");
            System.out.println(node.getPath());
            System.out.println(node.getProperty("message").getString());

            //数据删除
            root.getNode("hello").remove();
            session.save();
        } finally{
            session.logout();
        }
    }

}
