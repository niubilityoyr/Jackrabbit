package com.oyr.jackrabbit.demo;

import org.apache.jackrabbit.commons.JcrUtils;

import javax.jcr.GuestCredentials;
import javax.jcr.Repository;
import javax.jcr.Session;

/**
 * Create by 欧阳荣
 * 2018/8/6 23:59
 */
public class FirstHop {

    public static void main(String[] args) throws Exception {
        Repository repository = JcrUtils.getRepository();
        Session session = repository.login(new GuestCredentials());
        try {
            String user = session.getUserID();
            String name = repository.getDescriptor(Repository.REP_NAME_DESC);
            System.out.println("Logged in as " + user + " to a " + name + " repository.");
        } finally {
            session.logout();
        }
    }

}
