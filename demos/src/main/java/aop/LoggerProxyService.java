package aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by jinyancao on 2016/04/05/10/34
 */
public class LoggerProxyService {
    Logger logger= LoggerFactory.getLogger(LoggerProxyService.class);
    //通知
    //todo:打印方法传入参数
    public void beforeMethodLog(String[] args){
        logger.info("before");
        logger.info("------------------------{}",args);
    }


    //todo:打印方法返回参数
    public void beforeMethodReturnLog(){
        logger.info("response");
    }
}
