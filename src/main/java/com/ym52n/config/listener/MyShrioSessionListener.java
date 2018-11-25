package com.ym52n.config.listener;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;


public class MyShrioSessionListener implements SessionListener {
    @Override
    public void onStart(Session session) {
        System.out.println(session.getHost());
    }

    @Override
    public void onStop(Session session) {

    }

    @Override
    public void onExpiration(Session session) {

    }
}
