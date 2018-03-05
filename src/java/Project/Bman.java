
package Project;
// Question Bean Manager
public class Bman {
    private int chapter, question;
    private String text, choiceA, choiceB, choiceC, choiceD, choiceE, answerKey, hint;
    
    Bman(){
        chapter = question = 0;
        text = choiceA = choiceB = choiceC = choiceD = choiceE = answerKey = hint = null;
    }
    
    Bman(int chap, int ques, String txt, String a, String b, String c, String d, String e, String an, String h){
        chapter = chap; question = ques; text = txt; choiceA = a; choiceB = b;
        choiceC = c; choiceD = d; choiceE = e; answerKey = an; hint = h;
        
    }
    
   public void print(){
        System.out.print(chapter + "\n" +  question + "\n" + text + "\n" + 
                         choiceA + "\n" + choiceB + "\n" +  choiceC + "\n" + 
                         choiceD + "\n" +  choiceE + "\n" + answerKey + "\n" + hint + "\n");
    }
    
    
    /* Getters and Setters */
    public int getChapter(){
        return chapter;
    }
    
    public void setChapter(int i){
        chapter = i;
    }
    
    public int getQuestion(){
        return question;
    }
    
    public void setQuestion(int i){
        question = i;
    }
    
    public String getText(){
        return text;
    }
    
    public void setText(String s){
        text = s;
    }
    
    public String getChoiceA(){
        return choiceA;
    }
    
    public void setChoiceA(String s){
        choiceA = s;
    }
    
    public String getChoiceB(){
        return choiceB;
    }
    
    public void setChoiceB(String s){
        choiceB = s;
    }
    
    public String getChoiceC(){
        return choiceC;
    }
    
    public void setChoiceC(String s){
        choiceC = s;
    }
    
    public String getChoiceD(){
        return choiceD;
    }
    
    public void setChoiceD(String s){
        choiceD = s;
    }
    
    public String getChoiceE(){
        return choiceE;
    }
    
    public void setChoiceE(String s){
        choiceE = s;
    }
    
    public String getAnswerKey(){
        return answerKey;
    }
    
    public void setAnswerKey(String s){
        answerKey = s;
    }
    
    public String getHint(){
        return hint;
    }
    
    public void setHint(String s){
        hint = s;
    }
}
