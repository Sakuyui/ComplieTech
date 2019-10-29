package complier.automata;

import complier.automata.exception.AutomatonNodeException;

import java.io.Serializable;
import java.util.*;

//基本有限自动机类
public class Automata implements Serializable {
    public Map<Integer,AutomataNode> automatonNodeMap=new HashMap<>();  //自动机结点Map
    public TranstionFunc transitionFuns;  //迁移函数
    public AutomataNode startNode=null;   //起始结点
    public  Automata(){
    }
    //提供节点集，转移函数进行初始化
    public Automata(List<AutomataNode> automatonNodes,TranstionFunc ts) throws AutomatonNodeException {
        //check validity
        //state encoded from 1(not from 0)
        for(AutomataNode node : automatonNodes){
            if(automatonNodeMap.containsKey(node.nodeCode)) throw new AutomatonNodeException("Repeat Automaton Code Number");
            else{
                automatonNodeMap.put(node.nodeCode,node);
            }
        }
        transitionFuns=ts;
        if(automatonNodeMap.containsKey(0)){
            startNode=automatonNodeMap.get(0);
        }
    }

    public Automata(Automata automaton){
        transitionFuns=automaton.transitionFuns;
        startNode=automaton.startNode;
        automatonNodeMap=automaton.automatonNodeMap;
    }

    //节点数
    public int getStateCount(){
        return automatonNodeMap.size();
    }
   /* //自动机编码。（没用上)
    public String getCodedAutomataStr(){
        String s="";
        return transitionFuns.getCodedStr();
    }
*/


    public Event MatchedEvent=null;
    //开始匹配句子
    public Set<AutomataNode> matchingGrammar(String input, IInputable inputmethod, ISetProcess<AutomataNode> returnStrategy , Object... objects){
        int count=0;
        this.transitionFuns.infCollection.cleanStacks();  //清空信息收集器的堆栈
        if(startNode==null) return null;
        //将句子转化为输入项列表
        List<InputItem> is=inputmethod.toInputList(input);

        //当前所在节点
        Set<AutomataNode> cur=new HashSet<>();
        cur.add(startNode);

      /*  //Test
        System.out.print("当前状态集合={");
        for (AutomataNode an:cur){  //遍历当前状态集合，输入
           System.out.print(an.nodeCode+",");
        }
        System.out.println("}");
*/

        for(InputItem i:is){
            //System.out.println("Try input: "+i.content+" ");

            //尝试转移  参数1:开始节点  参数二:输入项 参数三：提供的转移参数
            Set<AutomataNode> tmpNodes=new HashSet<>();
            for (AutomataNode an:cur){  //遍历当前状态集合，输入
                //System.out.println("from node:"+an.nodeCode+" input:"+i);
                Set<AutomataNode> tmpns=transitionFuns.transition(an,i,objects);
                if(tmpns==null || tmpns.size()==0) continue;
                tmpNodes.addAll(tmpns);
            }
            if(tmpNodes==null || tmpNodes.size()==0) return null;
            cur=tmpNodes;



         /*   //Test
            System.out.print("当前状态集合={");
            for (AutomataNode an:cur){  //遍历当前状态集合，输入
                System.out.print(an.nodeCode+",");
            }
            System.out.println("}");*/



            //=null就是无法转移.语法无效
            if(cur==null || cur.size()==0){
                System.out.println("\n----------------------------\n"+"无法识别的语法"+" \n"+"----------------------------");
                //cur=this.startNode;

                //System.out.println(">>>reset");

                //cur=transitionFuns.transition(cur,i,objects);
                //if(cur==null) cur=this.startNode;
                return null;
            }else{
                //System.out.println("-> "+cur.nodeCode);
                /*if(cur.owariNode){
                    System.out.println(">>>>>>>>>>>>>>>>>>>>语法正常识别<<<<<<<<<<<<<<<<<<<");
                    count++;
                    if(MatchedEvent!=null) MatchedEvent.doEvent();
                    cur=this.startNode;
                }*/
            }
        }
        //判断是否是终止结点。不是终止结点说明语义不完整


       // System.out.println("\n----------------------------\n"+"自动机共识别到语法"+count+"次 \n"+"----------------------------");
        /*if(count==0){
            return null;
        }
*/

        for(AutomataNode an:cur){
            if(an.owariNode) return cur;
        }
        return null;
    }






}
