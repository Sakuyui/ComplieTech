package complier.automata;

public class OnMatchedEvent implements Event {
    public int count=0;
    @Override
    public Object doEvent(Object... objs){
        count++;
        return null;
    }
}
