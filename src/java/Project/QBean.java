
package Project;

import java.util.*;

public class QBean {
    // Question Bean Manager
    private String title, page, Select, selected;
    int chapterNo, questionNo;
    private String[] checked;
    Dman database;

    public QBean() {
        chapterNo = 5;
        questionNo = 2;
        title = "Chapter 5 Question 2";
        page = "";
        Select = "Show";
        database = new Dman();
    }

    public void printPage() {
        page = "";
        Bman q = database.retrieve(chapterNo, questionNo);
        String hint = q.getHint();
        String key = q.getAnswerKey();
        if (q.getAnswerKey() == null) {
            page = "<font color=\"red\"> The requested question could not be found <font/>";
        } else {
            printQuestion(q);
            printChoices(q);

            //Display Select. Print the Check My Rman button
            if (Select.equals("Show")) {
                page = page + "<input type=\"submit\" name=\"Select\" value=\"Check My Answer\" style=\"background-color : green\">";
            } //Check Rman Select. Print answer correctness, hint, and Get Statistics button
            else if (Select.equals("Check My Answer")) {
                try {
                    if (checked.length > 0) {
                        Rman a = updateRman(q);

                        //Correct answer. Inform user
                        if (a.getIsCorrect() == 1) {
                            page = page + "<font color=\"green\"><p>Your answer is correct</font><span ><img src=\"c.jpg\" border=\"0\" width=\"28\" height=\"28\"</img></span></p>";
                         page = page + "<input type=\"button\" value=\"Get Statistics\" style=\"background-color : green\">";
                        }

                        //Incorrect answer. Inform user
                        if (a.getIsCorrect() == 0) {
                            String answers = "";
                            for (String s : checked) {
                                switch (s) {
                                    case "0":
                                        answers += "A";
                                        break;
                                    case "1":
                                        answers += "B";
                                        break;
                                    case "2":
                                        answers += "C";
                                        break;
                                    case "3":
                                        answers += "D";
                                        break;
                                    case "4":
                                        answers += "E";
                                        break;
                                }
                            }

                            page = page + "<p><font color=\"red\">Your answer " + answers + " is incorrect</font><span ><img src=\" w.jpg\" border=\"0\" width=\"28\" height=\"28\"</img></span></p>";
                           page = page + "<font color=\"green\"><p><input type=\"submit\" name=\"Select\" value=\"Click here to show the correct answer and an explanation\" style=\"vertical-align:bottom;overflow:visible; background-color: White; font-size:1em; display:inline;  margin:0; padding:0; border:0; color:blue; cursor:pointer;\"></p></font>";  
                                page = page + "<input type=\"button\" value=\"Get Statistics\" style=\"background-color : green\">";
                        }
                    }
                } catch (Exception e) {
                    //No answer selected. Notify user
                    page = page + "<p>You did not answer this<span ><img src=\"n.jpg\" border=\"0\" width=\"28\" height=\"28\"</img></span></p>";
                    page = page + "<font color=\"green\"><p><input type=\"submit\" name=\"Select\" value=\"Click here to show the correct answer and an explanation\" style=\"vertical-align:bottom;overflow:visible; background-color: White; font-size:1em; display:inline;  margin:0; padding:0; border:0; color:blue; cursor:pointer;\"></p></font>";
               page = page + "<input type=\"button\" value=\"Get Statistics\" style=\"background-color : green\">";
                }

             
            } 
            
            else if (Select.equals("Click here to show the correct answer and an explanation")) {
                 if (hint.equals("null")) {                   
                     page = page + "<font color=\"green\"><p>The correct answer is " + key.toUpperCase(Locale.ITALY) + " </font></p>";
                page = page + "<input type=\"button\" value=\"Get Statistics\" style=\"background-color : green\">";
                 } else {
                     page = page + "<font color=\"green\"><p>The correct answer is " + key.toUpperCase(Locale.ITALY) + " </font></p>";
                    page = page + "<br /><font color=\"blue\">Explanation: " + hint + " </font></p>";
                 page = page + "<input type=\"button\" value=\"Get Statistics\" style=\"background-color : green\">";
                 }  
            }
        }
    }

    private void printQuestion(Bman q) {
        String line = q.getText();
        String first = line.split("\n")[0];
        String rest = line.substring(first.length());
        page = page + first + "<br/>";
        if (!rest.isEmpty()) {
            page += "<pre><code class='java'>" + rest + "</code></pre>";
        }

    }

    private void printChoices(Bman q) {
        //Multiple answers. Checkbox display
        char letter = 'A';
        if (q.getAnswerKey().length() > 1) {
            String[] s = {q.getChoiceA(), q.getChoiceB(), q.getChoiceC(), q.getChoiceD(), q.getChoiceE()};
            for (int i = 0; i < s.length; i++) {
                //If choice is not null, print the choice
                if (!s[i].equals("null") && !s[i].equals(null)) {
                    page = page + " " + letter + ". " + "<input type=\"checkbox\" name=\"choices\" value=\"" + i + "\"> " + s[i] + "<br />";
                    letter = (char) (((int) letter) + 1);
                }
            }
        }

        //Single answer. Radio display
        if (q.getAnswerKey().length() == 1) {
            String[] s = {q.getChoiceA(), q.getChoiceB(), q.getChoiceC(), q.getChoiceD(), q.getChoiceE()};
            for (int i = 0; i < s.length; i++) {
                //If choice is not null, print the choice
                if (!s[i].equals("null") && !s[i].equals(null)) {
                    page = page + " " + letter + ". " + "<input type=\"radio\" name=\"choices\" value=\"" + i + "\"> " + s[i] + "<br />";
                    letter = (char) (((int) letter) + 1);
                }
            }
        }

    }

    private Rman updateRman(Bman q) {
        Rman a = database.retrieveRman(chapterNo, questionNo);
        a.setAnswerA(0);
        a.setAnswerB(0);
        a.setAnswerC(0);
        a.setAnswerD(0);
        a.setAnswerE(0);

        //Set user answers
        for (String s : checked) {
            switch (s) {
                case "0":
                    a.setAnswerA(1);
                    break;
                case "1":
                    a.setAnswerB(1);
                    break;
                case "2":
                    a.setAnswerC(1);
                    break;
                case "3":
                    a.setAnswerD(1);
                    break;
                case "4":
                    a.setAnswerE(1);
                    break;
            }
        }

        //Check correctness
        String key = q.getAnswerKey();
        String answers = "";
        boolean correct = true;
        for (String s : checked) {
            answers += s;
        }

        //Different lengths means user answer is incorrect
        if (key.length() != answers.length()) {
            correct = false;
        } else {
            if (key.contains("a") && !answers.contains("0")) {
                correct = false;
            }
            if (key.contains("b") && !answers.contains("1")) {
                correct = false;
            }
            if (key.contains("c") && !answers.contains("2")) {
                correct = false;
            }
            if (key.contains("d") && !answers.contains("3")) {
                correct = false;
            }
            if (key.contains("e") && !answers.contains("4")) {
                correct = false;
            }
        }

        if (correct) {
            a.setIsCorrect(1);
        } else {
            a.setIsCorrect(0);
        }

        database.updateRman(a);
        return a;
    }

    /* Getters and Setters */
    public int getChapterNo() {
        return chapterNo;
    }

    public void setChapterNo(int i) {
        chapterNo = i;
    }

    public int getQuestionNo() {
        return questionNo;
    }

    public void setQuestionNo(int i) {
        questionNo = i;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String i) {
        title = i;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String i) {
        page = i;
    }

    public String getSelect() {
        return Select;
    }

    public void setSelect(String i) {
        Select = i;
    }

    public String getSelected() {
        return selected;
    }

    public void setSelected(String i) {
        selected = i;
    }

    public String[] getChecked() {
        return checked;
    }

    public void setChecked(String[] i) {
        checked = i;
    }
}
