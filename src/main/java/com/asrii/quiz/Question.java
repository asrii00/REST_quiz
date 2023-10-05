package com.asrii.quiz;


import java.util.List;

public class Question {
    
    private Integer id;
    private String questionText;
    private List<String> options;
    private String correctOption;

    public Question(Integer id, String questionText, List<String> options, String correctOption) {
        this.id = id;
        this.questionText = questionText;
        this.options = options;
        this.correctOption = correctOption;
    }

    @Override
    public String toString(){
        return this.questionText + "<br>" + options;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestionText() {
        return this.questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public List<String> getOptions() {
        return this.options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public String getCorrectOption() {
        return this.correctOption;
    }

    public void setCorrectOption(String correctOption) {
        this.correctOption = correctOption;
    }
   
}