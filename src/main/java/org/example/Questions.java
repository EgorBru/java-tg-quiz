package org.example;

import java.util.ArrayList;
import java.util.Collections;

public class Questions {
    private String question;
    private String answer;
    private String answer1;
    private String answer2;
    private String answer3;

    public Questions(){
    }

    public Questions(String question, String answer, String answer1, String answer2, String answer3) {
        this.question = question;
        this.answer = answer;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
    }

    public ArrayList<String> shuffleAnswers() {
        ArrayList<String> answers = new ArrayList<>() {{
            add(answer);
            add(answer1);
            add(answer2);
            add(answer3);
        }};
        Collections.shuffle(answers);
        return answers;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }
}
