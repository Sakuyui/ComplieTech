package complier.automata;






import java.io.Serializable;
import java.util.*;

//转移函数
public class TranstionFunc implements Serializable {
    List<TransitionInf> tifs=new ArrayList<>();   //转移函数信息集合,对应自动机上的所有边的集合
    InfCollection infCollection;   //信息收集器
    Automata belongedAutomaton=null;  //所属的自动机(可以为null)


    public TranstionFunc(){
        infCollection=new InfCollection();
    }
    public TranstionFunc(Automata automaton){
        infCollection=new InfCollection();
        belongedAutomaton=automaton;
    }
    public void cleanInfCollection(){
        infCollection=new InfCollection();
    }
    public void addAtransition(AutomataNode from, AutomataNode end, TransitionType t, String input){  //添加一条状态转移信息   从那哪个状态到那个状态，转移类型，以及转移条件
        tifs.add(new TransitionInf(from,end,t,input));
    }
    //添加一条迁移信息（边）
    public void addAtransition(TransitionInf ti){  //添加一条状态转移信息
        tifs.add(ti);
    }   //同样是添加一条转移信息



    //尝试对输入集合转移
    //返回到达的结点 null表示失败
    Set<AutomataNode> transition(AutomataNode from,List<InputItem> input){
        //Set<AutomataNode> cur;
        Set<AutomataNode> result=new HashSet<>();

        for(InputItem s:input) {

            result.addAll(transition(from,s));
        }

        return result;
    }

    public Set<AutomataNode> checkEpisonTrans(Set<AutomataNode> from){
        Set<AutomataNode> nodes=new HashSet<>();


        if(from==null) return null;

        for(AutomataNode node:from){
            nodes.addAll(checkEpisonTrans(node));
        }

        return nodes;
    }
    public Set<AutomataNode> checkEpisonTrans(AutomataNode from){
        Set<AutomataNode> nodes=new HashSet<>();
        nodes.add(from);

        if(from==null) return null;

        AutomataNode cur=from;

        //找出可能的边，尝试转移
        for(int i=0;i<tifs.size();i++){
            TransitionInf tmp_tif=tifs.get(i);
            if(tmp_tif.from.nodeCode!=cur.nodeCode) continue;  //从所有边集合中找出合适的边

            if(tmp_tif.t_content.compareTo("")==0){ //遇到epison转换
               cur=tmp_tif.end;
               nodes.add(cur);
               Set<AutomataNode> t_nodes=checkEpisonTrans(cur);
               nodes.addAll(t_nodes);
            }

        }
        return nodes;
    }
    //对一个输入转移,返回状态集合
    Set<AutomataNode> transition(AutomataNode from,InputItem input,Object... objects){
        AutomataNode cur=from;
        boolean t_exe=false;
        boolean allowany=false;

        TransitionInf tf_OBJany=null;  //任意转移





        //AutomataNode actualFrom= //检查epison转换。获取节点集合
        Set<AutomataNode> nodes=checkEpisonTrans(from);

        Set<AutomataNode> result=new HashSet<>();
        for (AutomataNode node:nodes){
            //找出可能的边，尝试转移
            for(int i=0;i<tifs.size();i++){
                TransitionInf tmp_tif=tifs.get(i);
                if(tmp_tif.from.nodeCode!=cur.nodeCode) continue;

                switch (tmp_tif.type){

                    case STR:
                        if(tmp_tif.t_content.compareTo("*")==0)
                        {
                            allowany=true;
                            tf_OBJany=tifs.get(i); //记录这条任意转换边
                        }

                        if(input.toString().compareTo(tifs.get(i).t_content)==0){
                            //trans
                            //System.out.println("Word Trans(by "+jw.word+"): "+tmp_tif.from.nodeCode+" -> "+tmp_tif.end.nodeCode);
                            cur=tmp_tif.end;
                            result.add(cur);
                            t_exe=true;
                            if(tmp_tif.transMethod!=null) tmp_tif.transMethod.trans(this.infCollection,tmp_tif.t_content);
                            if(cur.owariNode && cur.nodeMethod!=null)
                                cur.exeResult=cur.nodeMethod.doWork(this.infCollection,objects);

                            break;
                        }
                        break;
                }

                if(t_exe) break;

            }
            if(!t_exe){
          /*  //按优先级判断
            if(tf_formTrans!=null){
                //System.out.println("Word Form Trans: "+tf_formTrans.from.nodeCode+" -> "+tf_formTrans.end.nodeCode);
                cur=tf_formTrans.end;
                t_exe=true;
                if(tf_formTrans.transMethod!=null) tf_formTrans.transMethod.trans(this.infCollection,tf_formTrans.t_content);
                if(cur.owariNode && cur.nodeMethod!=null)
                    cur.exeResult=cur.nodeMethod.doWork(this.infCollection,objects);
            }else if(tf_wtTrans!=null){
                //.out.println("Word Type Trans: "+tf_wtTrans.from.nodeCode+" -> "+tf_wtTrans.end.nodeCode);
                            cur=tf_wtTrans.end;
                            t_exe=true;
                            if(tf_wtTrans.transMethod!=null) tf_wtTrans.transMethod.trans(this.infCollection,tf_wtTrans.t_content);
                            if(cur.owariNode && cur.nodeMethod!=null)
                                cur.exeResult=cur.nodeMethod.doWork(this.infCollection,objects);
            }
            else */if(allowany){

                    //System.out.println("* Trans: "+tf_OBJany.from.nodeCode+" -> "+tf_OBJany.end.nodeCode);
                    cur=tf_OBJany.end;
                    t_exe=true;
                    result.add(cur);
                    if(tf_OBJany.transMethod!=null) tf_OBJany.transMethod.trans(this.infCollection,input.toString());
                    if(cur.owariNode)
                        cur.exeResult=cur.nodeMethod.doWork(this.infCollection,objects);
                }else{return null;}


            }
        }



        Set<AutomataNode> actualEnd=checkEpisonTrans(result);
        return actualEnd;

    }
}