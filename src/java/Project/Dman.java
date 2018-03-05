
package Project;

import java.io.File;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
//Database Manager
public class Dman {

    private String chapter, question, text, choiceA, choiceB, choiceC, choiceD, choiceE, answerKey, hint, message;
    Statement st;
    PreparedStatement pst;
    Connection connection = null;

    public Dman() {
        chapter = question = text = choiceA = choiceB = choiceC = choiceD = choiceE = answerKey = hint = message = null;
                initDB();
            //  populateDB(); Populates database 
    }

    public void initDB() {
        //Load Driver
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            System.out.println("Error in loading jdbc driver");
            System.exit(0);
        }
        System.out.println("Driver Loaded Successfully");
        //Connect to Database
        connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://35.185.94.191:3306/some", "some", "tiger");
        } catch (Exception e) {
            System.out.println("Error connecting to server");
            System.exit(0);
        }
        System.out.println("Database Connected Successfully");

        //Create table intro11equiz if not already created
        try {
            st = connection.createStatement();
            st.execute("create table if not exists intro11equiz("
                    + "chapterNo int(11),"
                    + "questionNo int(11),"
                    + "question text,"
                    + "choiceA varchar(1000),"
                    + "choiceB varchar(1000),"
                    + "choiceC varchar(1000),"
                    + "choiceD varchar(1000),"
                    + "choiceE varchar(1000),"
                    + "answerKey varchar(5),"
                    + "hint text"
                    + ");");
            st.execute("create table if not exists intro11e("
                    + "chapterNo int(11),"
                    + "questionNo int(11),"
                    + "isCorrect bit(1) default 0,"
                    + "time timestamp default current_timestamp,"
                    + "hostname varchar(100),"
                    + "answerA bit(1) default 0,"
                    + "answerB bit(1) default 0,"
                    + "answerC bit(1) default 0,"
                    + "answerD bit(1) default 0,"
                    + "answerE bit(1) default 0,"
                    + "username varchar(100)"
                    + ");");
        } catch (Exception e) {
            System.out.println("Error: Statement Execution Failed");
            System.exit(0);
        }

    }
    
    public void populateDB(){
        List<String> results = new ArrayList<String>();

        File[] files = new File("C:\\Users\\ANSA-HP\\Documents\\NetBeansProjects\\selftest\\src\\java\\selftest\\selftest11e\\").listFiles();
        for (File file : files) {
            if (file.isFile()) {
                results.add(file.getName());                
                Tman q = new Tman(file.getName());
                ArrayList<Bman> e = q.allQuestions();
                for(Bman r : e){
                    insert(r);
                }
            }
        }
    }

    public void insert(Bman q) {        
        //Insert question into intro11equiz starting from chapterNo and questionNo=1
        if (q.getChapter() == 0 || q.getQuestion() == 0) {
            System.out.println("Error: invalid chapter or question");
        } else {
            String st = "Insert into intro11equiz (chapterNo, questionNo, question, choiceA, choiceB, choiceC, choiceD, choiceE, answerKey, hint)"
                    + " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
            this.st = null;
            ResultSet res = null;
            try {
                pst = connection.prepareCall(st);
            } catch (Exception e) {
                message =  "Prepare Statement Error";
            }
            try {
                //Test whether the ID entered is already in use
                this.st = connection.createStatement();
                res = this.st.executeQuery("Select * from intro11equiz where chapterNo = \"" + q.getChapter() + "\" and questionNo = \"" + q.getQuestion() + "\";");
                if (res.isBeforeFirst()) {
                    System.out.println("Chapter " + q.getChapter() + " question" + q.getQuestion() + " already in use in intro11equiz");
                } else {
                    //Insert new row
                    pst.setString(1, "" + q.getChapter());
                    pst.setString(2, "" + q.getQuestion());
                    pst.setString(3, "" + q.getText());
                    pst.setString(4, "" + q.getChoiceA());
                    pst.setString(5, "" + q.getChoiceB());
                    pst.setString(6, "" + q.getChoiceC());
                    pst.setString(7, "" + q.getChoiceD());
                    pst.setString(8, "" + q.getChoiceE());
                    pst.setString(9, "" + q.getAnswerKey());
                    pst.setString(10, "" + q.getHint());
                    pst.execute();
                }
            } catch (Exception e) {
                e.printStackTrace();
                message = "Execution Error: Try again";
            }
            
            //After inserting question into intro11equiz, initialize intro11e
            String s = "";
            try {
                InetAddress ip = InetAddress.getLocalHost();
                String hostName = ip.getHostName();
                s = "Insert into intro11e (chapterNo, questionNo, isCorrect, hostname, answerA, answerB, answerC, answerD, answerE)"
                    + " values (?, ?, 0, \'" + hostName + 
                        "\', 0, 0, 0, 0, 0);";
            } catch (Exception e){
                System.out.println("Error: HostName cannot be retrieved");
                System.exit(0);
            }
                
                this.st = null;
                res = null;
                
                try {
                    pst = connection.prepareCall(s);
                } catch (Exception e) {
                    message =  "Prepare Statement Error";
                }
                
                try {
                //Test whether the ID entered is already in use
                this.st = connection.createStatement();
                res = this.st.executeQuery("Select * from intro11e where chapterNo = \"" + q.getChapter() + "\" and questionNo = \"" + q.getQuestion() + "\";");
                if (res.isBeforeFirst()) {
                    System.out.println("Chapter " + q.getChapter() + " question" + q.getQuestion() + " already in use in intro11e");
                } else {
                    //Insert new row into table)
                    pst.setString(1, "" + q.getChapter());
                    pst.setString(2, "" + q.getQuestion());
                    pst.execute();
                }
                
            } catch (Exception e) {
                e.printStackTrace();
                message = "Execution Error: Try again";
            }
        
        }
    }
    
    public Bman retrieve(int chapterNo, int questionNo) {
        Bman q = new Bman();
        
        String st = "Select * from intro11equiz where chapterNo = ? and questionNo = ?";
        
        try{
            pst = connection.prepareCall(st);
        } catch(Exception e){
            message =  "Prepare Statement Error";
            return q;
        }
        
        ResultSet res = null;
        try{
            pst.setInt(1, chapterNo);
            pst.setInt(2, questionNo);
            res = pst.executeQuery();
            
            if (!res.isBeforeFirst()){
                message = "Chapter " + chapterNo + " and Question " + questionNo + "not found";
            } else {
                res.next();
                q.setChapter(res.getInt(1));
                q.setQuestion(res.getInt(2));
                q.setText(res.getString(3));
                q.setChoiceA(res.getString(4));
                q.setChoiceB(res.getString(5));
                q.setChoiceC(res.getString(6));
                q.setChoiceD(res.getString(7));
                q.setChoiceE(res.getString(8));;
                q.setAnswerKey(res.getString(9));
                q.setHint(res.getString(10));
                
                message = "Question found";
            }
        } catch(Exception e){
            message = "Error in retrieval";
            return q;
        }
        
        return q;
    }
    
    public Rman retrieveRman(int chapterNo, int questionNo){
        Rman a = new Rman();
        
        String st = "Select * from intro11e where chapterNo = ? and questionNo = ?";
        
        try{
            pst = connection.prepareCall(st);
        } catch(Exception e){
            message =  "Prepare Statement Error";
            return a;
        }
        
        ResultSet res = null;
        try{
            pst.setInt(1, chapterNo);
            pst.setInt(2, questionNo);
            res = pst.executeQuery();
            
            if (!res.isBeforeFirst()){
                message = "Chapter " + chapterNo + " and Question " + questionNo + "not found";
            } else {
                res.next();
                a.setChapterNo(res.getInt(1));
                a.setQuestionNo(res.getInt(2));
                a.setIsCorrect(res.getInt(3));
                a.setTimeStamp(res.getString(4));
                a.setHostName(res.getString(5));
                a.setAnswerA(res.getInt(6));
                a.setAnswerB(res.getInt(7));
                a.setAnswerC(res.getInt(8));
                a.setAnswerD(res.getInt(9));
                a.setAnswerE(res.getInt(10));;
                a.setUserName(res.getString(11));
                
                message = "Answer found";
            }
        } catch(Exception e){
            message = "Error in retrieval";
            return a;
        }
        
        return a;
    }
    
    public void updateRman(Rman a){
        String st = "Update intro11e "
                + "set isCorrect = ?, hostname = ?, answerA = ?, answerB = ?, answerC = ?, answerD = ?, answerE = ? "
                + "where chapterNo = ? and questionNo = ?;";
        Statement stmt = null;
        ResultSet res = null;
        
        try{
            pst = connection.prepareCall(st);
        } catch (Exception e){
            message = "Prepare Statement Error";
        }
        
        try {
            //Test whether the ID entered can be updated
            stmt = connection.createStatement();
            res = stmt.executeQuery("Select * from intro11e where chapterNo = " + a.getChapterNo() + " and questionNo = " + a.getQuestionNo() + ";");

            if (!res.isBeforeFirst()){
                message = "Chapter and Question not found";
                return;
            }
            
            //Update the table
            pst.setInt(1, a.getIsCorrect());
            pst.setString(2, a.getHostName());
            pst.setInt(3, a.getAnswerA());
            pst.setInt(4, a.getAnswerB());
            pst.setInt(5, a.getAnswerC());
            pst.setInt(6, a.getAnswerD());
            pst.setInt(7, a.getAnswerE());
            pst.setInt(8, a.getChapterNo());
            pst.setInt(9, a.getQuestionNo());
            
            pst.execute();
            
        } catch (Exception e){
            message = "Error: Update Failed";
        }
    }
}