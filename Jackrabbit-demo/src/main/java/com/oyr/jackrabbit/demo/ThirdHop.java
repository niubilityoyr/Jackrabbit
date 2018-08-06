package com.oyr.jackrabbit.demo;

import org.apache.jackrabbit.commons.JcrUtils;

import javax.jcr.*;
import java.io.FileInputStream;

/**
 * Create by 欧阳荣
 * 2018/8/7 0:22
 */
public class ThirdHop {

    public static void main(String[] args) throws Exception {
        Repository repository = JcrUtils.getRepository();
        Session session = repository.login(new SimpleCredentials("admin",
                "admin".toCharArray()));

        //读取文件
        FileInputStream xml = new FileInputStream("src/main/resources/test.xml");
        try {
            //根节点
            Node rootNode = session.getRootNode();
            System.out.println("是否存在 importxml 节点：" + rootNode.hasNode("importxml"));
            if(!rootNode.hasNode("importxml")){
                //不存在进来
                System.out.print("Importing xml... ");
                //创建一个保存xml的一个节点
                Node importxmlNode = rootNode.addNode("importxml", "nt:unstructured");
                //导入xml
                session.importXML(importxmlNode.getPath(), xml, ImportUUIDBehavior.IMPORT_UUID_CREATE_NEW);
                //保存
                session.save();

                System.out.println("dump ...");
                dump(rootNode);
            }
        } finally{
            session.logout();
        }
    }

    /** Recursively outputs the contents of the given node. */
    private static void dump(Node node) throws RepositoryException {
        // First output the node path
        System.out.println(node.getPath());
        // Skip the virtual (and large!) jcr:system subtree
        if (node.getName().equals("jcr:system")) {
            return;
        }

        // Then output the properties
        PropertyIterator properties = node.getProperties();
        while (properties.hasNext()) {
            Property property = properties.nextProperty();
            //判断是否有多重
            if (property.getDefinition().isMultiple()) {
                // 输出所有值
                Value[] values = property.getValues();
                for (int i = 0; i < values.length; i++) {
                    System.out.println(property.getPath() + " = "
                            + values[i].getString());
                }
            } else {
                // 输出值
                System.out.println(property.getPath() + " = "
                        + property.getString());
            }
        }

        // Finally output all the child nodes recursively
        NodeIterator nodes = node.getNodes();
        while (nodes.hasNext()) {
            //如果有子节点，则遍历
            dump(nodes.nextNode());
        }
    }
}
