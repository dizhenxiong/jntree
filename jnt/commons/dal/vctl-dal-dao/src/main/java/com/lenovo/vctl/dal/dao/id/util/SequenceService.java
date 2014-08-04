package com.lenovo.vctl.dal.dao.id.util;

/**
 * @author arthurkang
 */
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lenovo.vctl.dal.dao.config.helper.GroupHelper;
import com.lenovo.vctl.dal.dao.id.dao.SequenceDao;
import com.lenovo.vctl.dal.dao.util.Constants;

public class SequenceService {

    private Log log = LogFactory.getLog(SequenceService.class);

    protected SequenceDao dao;;
    private static SequenceService service;
    protected Map< String, ConcurrentLinkedQueue< Long >> queueMap;
    protected ConcurrentHashMap< String, Integer> spanMap; 

    /**
     * 根据应用初始化Dao
     */
    private SequenceService() {
        dao = new SequenceDao(GroupHelper.getDataSource(IdCenterHelper.getIDCenterDataSourceName()));
        queueMap = new HashMap< String, ConcurrentLinkedQueue< Long >>();
        spanMap  = new ConcurrentHashMap<String, Integer>();
    }

    /**
     * @return 单例模式创建一个SequenceService
     */
    public static SequenceService getInstance() {
        if (null == service) {
            synchronized (SequenceService.class) {
                if (null == service) {
                    service = new SequenceService();
                }
            }
        }
        return service;
    }

    /**
     * 暴露给外面使用的接口，用于得到某个Sequence当前可用的id
     * 
     * @param sequence
     * @return
     */
    public synchronized Long nextValue(String seqName) {
        if (null == seqName || StringUtils.isEmpty(seqName)) {
            log.info("Attention: Sequence's name is null\r\n");
            return null;
        }
        ConcurrentLinkedQueue< Long > queue = getQueue(seqName);
        int size = queue.size();
        if (size <= Constants.SeqIdCapacity) {
            Map< String, Long > ids = dao.getSeqIds(seqName);
            if (null != ids && ids.size() > 0) {
            	Integer span = spanMap.get(seqName);
            	if(null == span){
            		synchronized (seqName) {
        				span = dao.getSeqSpan(seqName);
        				spanMap.put(seqName, span);
        			}
            	}
            	Random random = new Random();
                Long minId = ids.get(Constants.MinId);
                Long maxId = ids.get(Constants.MaxId);
                if (log.isInfoEnabled()) {
                    log.info("MinId :" + minId);
                    log.info("MaxId :" + maxId);
                }
                for (long i = minId.longValue();;) {
                	i +=(random.nextInt(span)+1);
                	if(i > maxId.longValue()){
                		break;
                	}
                    queue.add(Long.valueOf(i));
                }
            } else {
                log.error("Error: Cant' get sequence : "+seqName+" from DB \r\n");
            }
        }
        Long returnId = null;
        returnId = queue.poll();
        if(null == returnId){
            log.error("Error : Failed to get sequence :"+seqName+" from queue \r\n");
        }
        return returnId;
    }

    /**
     * 得到和某个sequence对应的 queue
     * 
     * @param sequence
     * @return
     */
    public ConcurrentLinkedQueue<Long> getQueue(String seqName) {
        ConcurrentLinkedQueue< Long > queue = queueMap.get(seqName);
        if (null == queue) {
                queue = new ConcurrentLinkedQueue< Long >();
                queueMap.put(seqName, queue);
        }
        return queue;
    }

}
