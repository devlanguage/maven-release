package org.third.test.jmock;

interface MessageProvider {
    // 获取数据信息
    public String getMessage();
}

// 好，下来就是打印类，该打印类先通过MessageProvider获取到数据再打印，代码如下：
class MsgPrint {
    String message = "";

    public MsgPrint(MessageProvider provider) {
        this.message = provider.getMessage();
    }

    public void print() {
        // 打印
        System.out.println(message);
    }
}

public class JMockTest extends junit.framework.TestCase {
    org.jmock.Mockery context = new org.jmock.Mockery();
    final MessageProvider provider = context.mock(MessageProvider.class);
    final String message = "message";

    @org.junit.Before
    // ///使用了该元数据的方法在每个测试方法执行之前都要执行一次。, replace the setup();
    protected void setUp() throws Exception {
        super.setUp();
        // expectations
        context.checking(new org.jmock.Expectations() {
            {
                oneOf(provider).getMessage(); // when call getMessage()
                will(returnValue(message)); // will return message
            }
        });
    }

    @org.junit.After
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    @org.junit.Test
    public void testGetMessage() {
        assertEquals(message, provider.getMessage());
        // MsgPrint printer = new MsgPrint(provider);
        // printer.print();
    }
}
