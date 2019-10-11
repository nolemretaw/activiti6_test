package com.imooc.activiti.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.debug.ExecutionTreeUtil;
import org.activiti.engine.impl.agenda.AbstractOperation;
import org.activiti.engine.impl.interceptor.DebugCommandInvoker;
import org.activiti.engine.logging.LogMDC;

/**
 * @Author: HuTingrong
 * @Description: MDC拦截器
 * @Date: Created in 11:54 2019/10/11
 * @Modified By:
 */
@Slf4j
public class MDCCommandInvoker extends DebugCommandInvoker {
    @Override
    public void executeOperation(Runnable runnable) {
        boolean mdcEnabled = LogMDC.isMDCEnabled();
        LogMDC.setMDCEnabled(true);//开启MDC
        if (runnable instanceof AbstractOperation) {
            AbstractOperation operation = (AbstractOperation) runnable;
            if (operation.getExecution() != null) {
                LogMDC.putMDCExecution(operation.getExecution());
            }
        }
        super.executeOperation(runnable);
        LogMDC.clear();
        if (!mdcEnabled){
            //还原
            LogMDC.setMDCEnabled(false);
        }
    }

}
