package complier.automata;



import java.util.ArrayList;
import java.util.List;

//自动机工具
public class AutomataTools {
    //单例设计模式
    private static AutomataTools instance=null;
    private AutomataTools(){};
    public static AutomataTools getInstance(){
        if(instance==null) instance=new AutomataTools();
        return instance;
    }


}
