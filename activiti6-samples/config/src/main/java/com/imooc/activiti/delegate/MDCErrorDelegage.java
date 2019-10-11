package com.imooc.activiti.delegate;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

/**
 * @Author: HuTingrong
 * @Description: test
 * @Date: Created in 11:47 2019/10/11
 * @Modified By:
 */
@Slf4j
public class MDCErrorDelegage implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) {
      log.info("run MDCErrorDelegage");
      throw new RuntimeException("only test");
    }
}
