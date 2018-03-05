
package Project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
// Text Editor Manager
public class Tman {
    Scanner in;
    String folder, file;
    
    public Tman(){
        folder = "C:\\Users\\ANSA-HP\\Documents\\NetBeansProjects\\selftest\\src\\java\\selftest\\selftest11e\\";
        file = "chapter1.txt";
        String path = folder + file;
        try{
            in = new Scanner(new File(path), "UTF-8");
        } catch (FileNotFoundException e){
            in = new Scanner("");
            System.out.println("The file could not be found");
        }
    }
    
    public Tman(String file){
        folder = "C:\\Users\\ANSA-HP\\Documents\\NetBeansProjects\\selftest\\src\\java\\selftest\\selftest11e\\";
        this.file = file;
        String path = folder + file;
        try{
            in = new Scanner(new File(path));
        } catch (FileNotFoundException e){
            in = new Scanner("");
            System.out.println("The file could not be found");
        }
    }
    
    public Tman(String folder, String file){
        this.folder = folder;
        this.file = file;
        String path = folder + file;
        try{
            in = new Scanner(new File(path));
        } catch (FileNotFoundException e){
            in = new Scanner("");
            System.out.println("The file could not be found");
        }
    }
    
    public void print(){
        while(in.hasNext()){
            System.out.println(in.nextLine());
        }
    }
    
    public ArrayList<Bman> allQuestions(){
        ArrayList<Bman> que = new ArrayList<>();
        String s = in.nextLine();
        int chapter = 0, question = 1;
        
        //First line - set Chapter #
                String t = s.substring(8).trim();
                String u = "";
                while(t.charAt(0) != ' '){
                    u = u + t.charAt(0);
                    t = t.substring(1);

                }
                chapter = Integer.parseInt(u);
                s = in.nextLine();
            
        while (in.hasNext()){
            que.add(oneQuestion(s, chapter, question));
            question++;
        }
        
        return que;
    }
    
    private Bman oneQuestion(String s, int chapter, int question){
        Bman q = new Bman();
        
        //Set Chapter and Bman #
        q.setChapter(chapter);
        q.setQuestion(question);
        while (!s.startsWith("#")){
            
            
            
            //Set text for question
            if (!s.isEmpty() && !isNumeric(s.charAt(0))){
                s = setQuestionText(q, s);
            }
            
            //Set text for answer choices
            if (!s.isEmpty() && isChoice(s.substring(0, 2))){
                char a = s.charAt(0);
                try{
                s = s.substring(s.indexOf("\t"));
                
                } catch(Exception e){
                s = s.substring(s.indexOf(" "));
                }

                s = s.trim();
                
                switch(a){
                    case 'a': q.setChoiceA(s); break;
                    case 'b': q.setChoiceB(s); break;
                    case 'c': q.setChoiceC(s); break;
                    case 'd': q.setChoiceD(s); break;
                    case 'e': q.setChoiceE(s); break;
                    default: break;
                }
            }
            
            //Set answer key and hint
            if (!s.isEmpty() && s.toUpperCase().startsWith("KEY:")){
                String t = s.substring(4);
                String answers = "";
                while(!t.isEmpty() && (t.charAt(0) != ' ')){
                    answers = answers + t.charAt(0); //Add each individual answer to the answwer key
                    t = t.substring(1);
                }
                q.setAnswerKey(answers);
                
                t = t.trim();
                if (!t.isEmpty()){
                    q.setHint(t); // If any more words remain, they are the hint
                }
            }
            if (!s.equals("#")){
                try{
                    s = in.nextLine();
                    s = s.trim();
                } catch (Exception e){
                    break;
                }
            }
        }
        
        return q;
    }
    
    public boolean isNumeric(char a){
        if ((a >= 'a' && a <= 'z' ) || (a >= 'A' && a <= 'Z' )){
            return true;
        }
        
        return false;
    }
    
    public boolean isChoice(String s){
        return (s.equals("a.") || s.equals("b.") || s.equals("c.") ||s.equals("d.") || s.equals("e."));
    }
    
    public String setQuestionText(Bman q, String s){
        //Remove number from beginning of string
        try{
            s = s.substring(s.indexOf("\t"));
                
            } catch(Exception e){
            s = s.substring(s.indexOf(" "));
            }
        
        s = s.trim(); // Remove leading whitespace
        
        String set = q.getQuestion() + ". " + s; // First line of question
        s = in.nextLine();
        
        while (!s.startsWith("a.")){ // Add subsequent lines to question text
            set = set + "\n" + s;
            s = in.nextLine();

        }
        
        q.setText(set); //Set the text in Bman class
        return s; // Return altered string
    }
    
    
}