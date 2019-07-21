package org.basic.aop.cglib.BulkBean;

public interface TestCallback {
    String getName();
    TargetBean call(SourceBean source);
}
