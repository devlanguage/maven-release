
import java.util.Map;

/**
 */
// no more Spring imports!
public abstract class CommandService_Lookup {

    public Object businessMethod(Object commandState) {
        System.out.println(this.getClass().getSimpleName() + ".businessMethod(Map commandState() is called");

        // grab a new instance of the appropriate Command interface
        Command command = createCommand();
        // set the state on the (hopefully brand new) Command instance
        command.setState((Map) commandState);
        return command.execute();
    }

    // okay... but where is the implementation of this method?
    protected abstract Command createCommand();
    // �ڰ�����ע�뷽���Ŀͻ�����(�˴���CommandManager)���˷����Ķ�����밴������ʽ���У�
    //
    // <public|protected> [abstract] <return-type> theMethodName(no-arguments);
    // ��������ǳ���ģ���̬���ɵ������ʵ�ָ÷��������򣬶�̬���ɵ�����Ḳ������ľ��巽��

}