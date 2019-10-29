package complier.automata;

public class AutomataNode {
    public int nodeCode=-1;   //结点编号
    public boolean owariNode=false;  //是否是一个终止结点
    public INodeFunc nodeMethod=null;  //结点处理函数.如果是终止结点，到达该节点后将会执行实现了这个接口的函数。返回值会放在底下的exeResult
    public Object exeResult=null;     //处理函数执行结果

    public AutomataNode(int nodeCode,boolean owariNode){
        this.nodeCode=nodeCode;
        this.owariNode=owariNode;
    }

    public AutomataNode(int nodeCode,boolean owariNode,INodeFunc method){
        this.nodeCode=nodeCode;
        this.owariNode=owariNode;
        nodeMethod=method;
    }

    public void setIsFinshNode(Boolean b){
        this.owariNode=b;
    }

    @Override
    public boolean equals(Object o){
        if(!(o instanceof AutomataNode)) return false;
        if(((AutomataNode) o).nodeCode==this.nodeCode) return true;
        return false;
    }
    @Override
    public int hashCode() {
        //0
        int result = this.nodeCode;
        return result;
    }
}
