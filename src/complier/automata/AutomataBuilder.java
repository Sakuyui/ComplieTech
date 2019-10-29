package complier.automata;

import java.util.ArrayList;

public class AutomataBuilder {
    public static AutomataNode addletterTrans(Automata automata,AutomataNode from,AutomataNode enode){
        for (char c='a';c<='z';c++){
            automata.transitionFuns.addAtransition(from,enode,TransitionType.STR,String.valueOf(c));
        }
        for (char c='A';c<='Z';c++){
            automata.transitionFuns.addAtransition(from,enode,TransitionType.STR,String.valueOf(c));
        }
        return enode;
    }
    public static AutomataNode addFigureTrans(Automata automata,AutomataNode from,AutomataNode enode){
        for (char c='0';c<='9';c++){
            automata.transitionFuns.addAtransition(from,enode,TransitionType.STR,String.valueOf(c));
        }

        return enode;
    }


    public static Automata getIdentifyWordAutomata(){
        Automata a=new Automata(new ArrayList<>(), new TranstionFunc());
        a.startNode=new AutomataNode(0,false);
        a.automatonNodeMap.put(0,a.startNode);
        AutomataNode n2=new AutomataNode(1,false);
        a.automatonNodeMap.put(1,n2);
        addletterTrans(a,a.startNode,n2).setIsFinshNode(true);
        addFigureTrans(a,n2,n2);
        addletterTrans(a,n2,n2);
        return a;
    }

    public static Automata getIntAutomata(){
        Automata a=new Automata(new ArrayList<>(), new TranstionFunc());
        a.startNode=new AutomataNode(0,false);
        a.automatonNodeMap.put(0,a.startNode);

        AutomataNode n2=new AutomataNode(1,false);
        a.automatonNodeMap.put(1,n2);

       /* AutomataNode n3=new AutomataNode(2,false);
        a.automatonNodeMap.put(2,n3);*/

        addFigureTrans(a,a.startNode,n2);
        addFigureTrans(a,n2,n2).setIsFinshNode(true);
       // a.transitionFuns.addAtransition(n2,n3,TransitionType.STR,"*");
        return a;

    }

    public static Automata getDoubleAutomata(){
        Automata a=new Automata(new ArrayList<>(), new TranstionFunc());
        a.startNode=new AutomataNode(0,false);
        a.automatonNodeMap.put(0,a.startNode);

        AutomataNode n2=new AutomataNode(1,false);
        a.automatonNodeMap.put(1,n2);

        AutomataNode n3=new AutomataNode(2,false);
        a.automatonNodeMap.put(2,n3);

        AutomataNode n4=new AutomataNode(3,false);
        a.automatonNodeMap.put(3,n4);

        AutomataNode n5=new AutomataNode(4,false);
        a.automatonNodeMap.put(4,n5);

        addFigureTrans(a,a.startNode,n2);
        addFigureTrans(a,n2,n2);
        a.transitionFuns.addAtransition(n2,n4,TransitionType.STR,".");
        a.transitionFuns.addAtransition(n2,n3,TransitionType.STR,"E");
        a.transitionFuns.addAtransition(n3,n4,TransitionType.STR,"+");
        a.transitionFuns.addAtransition(n3,n4,TransitionType.STR,"-");
        a.transitionFuns.addAtransition(n3,n4,TransitionType.STR,"");
        a.transitionFuns.addAtransition(n5,n3,TransitionType.STR,"E");
        addFigureTrans(a,n4,n5);
        addFigureTrans(a,n5,n5).setIsFinshNode(true);
        return a;

    }

    public static Automata getCFGRecognizeAutomata(){
        Automata automata=new Automata(new ArrayList<>(), new TranstionFunc());
        return automata;
    }
}
