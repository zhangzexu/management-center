package com.ym52n.config.shrioException;

import org.apache.shiro.authc.AuthenticationException;

public class MoreAccountException extends AuthenticationException {

    public MoreAccountException() {
        super();
    }
    public MoreAccountException(String msg) {
        super(msg);
    }

}
