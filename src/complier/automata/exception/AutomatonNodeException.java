package complier.automata.exception;

import java.io.UncheckedIOException;

public class AutomatonNodeException extends RuntimeException {
    public AutomatonNodeException(String str){
        super(str);
    }
}
