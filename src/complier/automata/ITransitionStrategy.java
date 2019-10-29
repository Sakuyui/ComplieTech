package complier.automata;

public interface ITransitionStrategy {
    boolean tryTrans(TransitionInf ti,InputItem inputItem);
}
