package complier.tokenlization;

import complier.automata.Automata;
import complier.automata.AutomataNode;
import complier.automata.ISetProcess;

import java.util.Set;

public class ValidFinalAutomataDodeSetReturnStrategy implements ISetProcess<AutomataNode> {
    private static ValidFinalAutomataDodeSetReturnStrategy instance=null;
    private ValidFinalAutomataDodeSetReturnStrategy(){}
    public static ValidFinalAutomataDodeSetReturnStrategy getInstance(){
        return instance==null?(instance=new ValidFinalAutomataDodeSetReturnStrategy()):instance;
    }
    @Override
    public Set<AutomataNode> processSet(Set<AutomataNode> s){
        for(AutomataNode an:s){
            if(an.owariNode) return s;
        }
        return null;
    }
}
