package complier.tokenlization;
import complier.automata.*;

import java.util.*;

public class tokenlizer {
    private static Map<String,Integer> keyWordMap=null;
    private static Automata IDAutomata=null;
    private static Automata IntAutomata=null;
    private static Automata DoubleAutomata=null;
    public static void init(){
        keyWordMap=new HashMap<>();
        keyWordMap.put("for",1);
        keyWordMap.put("if",2);
        keyWordMap.put("then",3);
        keyWordMap.put("else",4);
        keyWordMap.put("while",5);
        keyWordMap.put("do",6);
        //keyWordMap.put("")  //合法标识符
        //keyWordMap.put("")  //合法数值常量
        keyWordMap.put("+",13);
        keyWordMap.put("-",14);
        keyWordMap.put("*",15);
        keyWordMap.put("/",16);
        keyWordMap.put("until",29);
        keyWordMap.put("input",31);
        keyWordMap.put(":",17);
        keyWordMap.put(":=",18);
        keyWordMap.put("<",20);
        keyWordMap.put("<>",21);
        keyWordMap.put("<=",22);
        keyWordMap.put(">",23);
        keyWordMap.put(">=",24);
        keyWordMap.put("=",25);
        keyWordMap.put(";",26);
        keyWordMap.put("(",27);
        keyWordMap.put(")",28);
        keyWordMap.put("#",0);
        keyWordMap.put("int",30);
        keyWordMap.put("output",32);


        IDAutomata=AutomataBuilder.getIdentifyWordAutomata();  //识别标识符的自动机
        IntAutomata=AutomataBuilder.getIntAutomata(); //识别整数的自动机
        DoubleAutomata=AutomataBuilder.getDoubleAutomata(); //识别浮点数的自动机
    }



    public static Map<String,List<String>> CFGAnalyze(String cfg){
        Map<String,List<String>> cfgmap=new HashMap<>();
        //System.out.println("/////////////////Input S/////////////////");
        //System.out.println(cfg);
        //System.out.println("/////////////////Input E/////////////////");
        String[] cfgs=cfg.split("\n");
        for(int i=0;i<cfgs.length;i++){
            //System.out.println("====Process S====");
            //System.out.println(cfgs[i]);
            String[] rl=cfgs[i].split("->");
            if(rl.length!=2){
                rl=cfgs[i].split("→");
            }
            if(rl.length!=2) throw new RuntimeException();

            String[] rs=rl[1].split("\\|");
          //  System.out.print(rl[0]+",");

            if(cfgmap.containsKey(rl[0])) throw new RuntimeException();
            List<String> rarr=new ArrayList<>();
            for(String r:rs){
               rarr.add(r);
                //System.out.print(" "+r);
            }
            cfgmap.put(rl[0],rarr);
            //System.out.println();




            //System.out.println("====Process E====");
        }
        return cfgmap;
    }

    public static Set<String> getFirst(String input,String S){
        Map<String,List<String>> cfgmap=CFGAnalyze(input);
        Set<String> first=new HashSet<>();
        if(!cfgmap.containsKey(S)) throw new RuntimeException();
        List<String> cfgDeduction=cfgmap.get(S);
        for(String cfgItem:cfgDeduction){
            System.out.println("process: "+cfgItem);
            if(cfgItem.compareTo("ε")==0){
                first.add("ε");
                System.out.println("put ε");
                continue;
            }
            String mostLeft=cfgItem.substring(0,1);

            if('A'<=mostLeft.charAt(0) && mostLeft.charAt(0)<='Z' ){
                if(cfgItem.charAt(1)!='\''){
                    System.out.println("Found "+mostLeft);
                    first.addAll(getFirst(input,mostLeft));
                }else{
                    System.out.println("Found "+mostLeft+'\'');
                    first.addAll(getFirst(input,mostLeft+"'"));
                }
            }else{
                //最左不是终结符
                if(mostLeft.charAt(0)<='z' && mostLeft.charAt(0)>='a'){
                    for(int i=1;i<cfgItem.length();i++){
                        //System.out.println("curr="+mostLeft);
                        if(cfgItem.charAt(i)>'z' || cfgItem.charAt(i)<'a'){
                            break;
                        }else{
                            mostLeft+=String.valueOf(cfgItem.charAt(i));
                          //  System.out.println("curr="+mostLeft);
                        }

                    }
                    System.out.println("put "+mostLeft);
                    first.add(mostLeft);
                }else{
                    System.out.println("put "+mostLeft);
                    first.add(mostLeft);
                }
            }

        }

        return first;
    }


    public static  Set<Integer>  getReconigzedSet(String s){
        Set<Integer> idset=new HashSet<>();
        if(keyWordMap.containsKey(s)){
            idset.add(keyWordMap.get(s));
        }
        if(IDAutomata.matchingGrammar(s,CharInputStartegy.getInstance(),ValidFinalAutomataDodeSetReturnStrategy.getInstance())!=null) idset.add(10);
        if(IntAutomata.matchingGrammar(s,CharInputStartegy.getInstance(),ValidFinalAutomataDodeSetReturnStrategy.getInstance())!=null) idset.add(11);
        return idset;
    }


    public static List<InputItem> tokenlize(String inputStr){
        if(keyWordMap==null || IDAutomata==null) init();
        String curr="";
        InputItem pre=null;
        Set<Integer> preCodeset=null;
        Set<Integer> curCodeset=null;
        List<InputItem> re=new ArrayList();
        for(int i=0;i<inputStr.length();i++){
            char in=inputStr.charAt(i);
            curr+=in;
            //System.out.println(curr);

            preCodeset=curCodeset;
            curCodeset=getReconigzedSet(curr);

            System.out.println("上次匹配结果集合:"+preCodeset+" 本次:"+curCodeset);
            if(preCodeset!=null && preCodeset.size()>0 && curCodeset.size()==0){
                System.out.print("record:["+curr.substring(0,curr.length()-1)+"]  ");
                String w=curr.substring(0,curr.length()-1);

                if(preCodeset.size()==1){
                    System.out.println("["+preCodeset.toArray()[0]+"]");
                    re.add(new InputItem(new WordTuple((Integer) preCodeset.toArray()[0],w)));
                }else{
                    if(preCodeset.size()==2 && preCodeset.contains(10)){
                        preCodeset.remove(10);
                    }
                    System.out.println("["+preCodeset.toArray()[0]+"]");
                    re.add(new InputItem(new WordTuple((Integer) preCodeset.toArray()[0],w)));
                }
            }


            if(curCodeset.size()==0) {
                curr="";
                System.out.println("重置");
                if(in!=' ')
                {
                   i--;
                }
            }

        }

        if(!("".equals(curr))){
            if(curCodeset.size()!=0){
                System.out.println("record:["+curr+"]");
                curCodeset=getReconigzedSet(curr);
                if(curCodeset.size()==0) re.add(new InputItem(new WordTuple(-1,curr)));
                else if(curCodeset.size()==1) re.add(new InputItem(new WordTuple((Integer) curCodeset.toArray()[0],curr)));
               else{
                    curCodeset.remove(10);
                    re.add(new InputItem(new WordTuple((Integer) curCodeset.toArray()[0],curr)));
                }
            }
        }
        return re;
    }

    public static void main(String[] args){
        System.out.println(getFirst("E→TE'\nE'→+TE'|ε\nT→FT'\nT'→*FT'|ε\nF→(E)|id","E"));

        /*init();

        //System.out.println(DoubleAutomata.matchingGrammar("32.",CharInputStartegy.getInstance(),ValidFinalAutomataDodeSetReturnStrategy.getInstance()));
        List<InputItem> items=tokenlize("int x:=5;  if (x >0)  then  x:=2*x+1/3;  else  x:=2/x;  #");
        for (InputItem item:items){
            System.out.println(((WordTuple)item.getTransContent()).getTupleString());
        }*/
    }
}