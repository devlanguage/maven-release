package org.third.bytecode.cglib.BulkBean;

public interface TestCallback {
    String getName();
    TargetBean call(SourceBean source);
}
