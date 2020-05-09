package com.team.app.backend.persistance.model;

public class Option extends OptionObj {

    private Boolean isCorrect;
    private String text;
    private byte[] image;


    public Option() {
    }

    public Option(Boolean isCorrect, String text, byte[] image) {
        //this.id = id;
        this.isCorrect = isCorrect;
        this.text = text;
        this.image = image;
        //this.quest_id = quest_id;
    }

//    public Long getId() {
//        return id;
//    }

    public Boolean getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(Boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

//    public Long getQuest_id() {
//        return quest_id;
//    }
//
//    public void setQuest_id(Long quest_id) {
//        this.quest_id = quest_id;
//    }
}
