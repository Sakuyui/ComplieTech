package complier.tokenlization;

public class WordTuple {
    int id=-1;
    String word="";




    public WordTuple(int id,String word){
        this.id=id;
        this.word=word;
    }
    public int getId() {
        return id;
    }

    public String getWord() {
        return word;
    }

    @Override
    public String toString(){
        return word;
    }


    public String getTupleString(){
        return "["+id+",'"+word+"']";
    }


    public void setWord(String word) {
        this.word = word;
    }
}
