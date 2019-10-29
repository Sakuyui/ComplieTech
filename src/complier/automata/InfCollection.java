package complier.automata;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

//信息手机去，用来在自动机迁移时收集信息.里面有一堆栈
public class InfCollection implements Serializable {
    public List<Stack<String>> stacks=new ArrayList<Stack<String>>();
    public Object resultObj=null;
    public void cleanStacks(){
        stacks.clear();
        resultObj=null;
    }
}
