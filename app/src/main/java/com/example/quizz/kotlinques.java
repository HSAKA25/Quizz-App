package com.example.quizz.model;

import java.util.List;

public class kotlinques {
    private String question;
    private List<String> options;
    private String answer;

    public kotlinques() {} // required empty constructor for Firebase

    public String getQuestion() { return question; }
    public List<String> getOptions() { return options; }
    public String getAnswer() { return answer; }
}

