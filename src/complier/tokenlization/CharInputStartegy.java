package complier.tokenlization;

import complier.automata.IInputable;
import complier.automata.InputItem;

import java.util.ArrayList;
import java.util.List;

public class CharInputStartegy implements IInputable {
    private static CharInputStartegy instance=null;
    public static CharInputStartegy getInstance(){
        return instance==null?(instance=new CharInputStartegy()):instance;
    }
    private CharInputStartegy(){}

    @Override
    public List<InputItem> toInputList(String input){
        List<InputItem> items=new ArrayList<>();
        for(int i=0;i<input.length();i++){
            items.add(new InputItem(input.charAt(i)));
        }
        return items;
    }
}
