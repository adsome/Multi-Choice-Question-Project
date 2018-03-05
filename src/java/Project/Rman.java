
package Project;
// Response Manager
public class Rman {
    private int chapterNo, questionNo, isCorrect, answerA, answerB, answerC, answerD, answerE;
    private String timeStamp, hostName, userName;
    
    Rman(){
        chapterNo = questionNo = isCorrect = answerA = answerB = answerC = answerD = answerE = 0;
        timeStamp = hostName = userName = "";
    }
    
    public int getChapterNo(){
        return chapterNo;
    }
    
    public void setChapterNo(int i){
        chapterNo = i;
    }
    
    public int getQuestionNo(){
        return questionNo;
    }
    
    public void setQuestionNo(int i){
        questionNo = i;
    }
    
    public int getIsCorrect(){
        return isCorrect;
    }
    
    public void setIsCorrect(int i){
        isCorrect = i;
    }
    
    public int getAnswerA(){
        return answerA;
    }
    
    public void setAnswerA(int i){
        answerA = i;
    }
    
    public int getAnswerB(){
        return answerB;
    }
    
    public void setAnswerB(int i){
        answerB = i;
    }
    
    public int getAnswerC(){
        return answerC;
    }
    
    public void setAnswerC(int i){
        answerC = i;
    }
    
    public int getAnswerD(){
        return answerD;
    }
    
    public void setAnswerD(int i){
        answerD = i;
    }
    
    public int getAnswerE(){
        return answerE;
    }
    
    public void setAnswerE(int i){
        answerE = i;
    }
    
    public String getTimeStamp(){
        return timeStamp;
    }
    
    public void setTimeStamp(String s){
        timeStamp = s;
    }
    
    public String getUserName(){
        return userName;
    }
    
    public void setUserName(String s){
        userName = s;
    }
    
    public String getHostName(){
        return hostName;
    }
    
    public void setHostName(String s){
        hostName = s;
    }
}