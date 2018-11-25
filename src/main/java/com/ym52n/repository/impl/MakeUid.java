package com.ym52n.repository.impl;

import com.ym52n.utils.StringUtil;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;


public class MakeUid implements IdentifierGenerator {
    public MakeUid() {
    }

    @Override
    public Serializable generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException {
        return StringUtil.getUid();
    }
}
