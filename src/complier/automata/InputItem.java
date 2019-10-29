package complier.automata;

import java.io.Serializable;

//输入项。能让自动机迁移的东西。。。
public class InputItem implements Serializable {
    Object content;
    TransitionType inputtype;   //没用上。本来考虑到输入项也是有类型的。后来发现在本项目中不用考虑这个

    public InputItem(Object c){
        content=c;
        //this.inputtype=inputtype;
    }
    @Override
    public String toString(){
        return content.toString();
    }

    public Object getTransContent(){
        return content;
    }

    public TransitionType getInputtype(){
        return inputtype;
    }



}