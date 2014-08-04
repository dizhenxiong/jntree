package com.lenovo.vctl.dal.dao.id.util;

/**
 * @author arthurkang
 */
import java.io.Serializable;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.type.Type;
import org.springframework.stereotype.Component;

@Component("idGenerator")
public class IdGenerator implements IdentifierGenerator, Configurable {

    private static final Log log = LogFactory.getLog(IdGenerator.class);

    public String sequenceName = null;

    public static AtomicInteger createCnt = new AtomicInteger(0);

    /**
     * @return 根据Sequence名字，返回唯一的Sequence id.类似于Oracle的sequence机制
     * @param session:
     *            这个参数没有用到
     * @param arg1:
     *            这个参数没有用到
     * 
     */
    public Serializable generate(SessionImplementor session, Object arg1) throws HibernateException {
        if (StringUtils.isEmpty(sequenceName)) {
            log.warn("Attention : The sequence for idcenter is null\r\n");
            return null;
        }
        Long id = SequenceService.getInstance().nextValue(sequenceName);
        return id;
    }

    /**
     * 对于一个数据库，每次仅创建一个sequence,这是Hibernate的机制
     */
    public void configure(Type arg0, Properties props, Dialect arg2) {
        if (StringUtils.isEmpty(sequenceName)) {
            sequenceName = props.getProperty(IdCenterHelper.getSeqParamName());
            if (StringUtils.isEmpty(sequenceName)) {
                log.warn("Warn: The user donesn't supply the <sequence> name when using idcenter\r\n");
                return;
            }
            if (log.isDebugEnabled()) {
                log.debug("Debug : The squence --" + sequenceName + " is created\r\n");
                //用于判断Sequence创建的次数
                createCnt.incrementAndGet();
            }
        }
    }
}
