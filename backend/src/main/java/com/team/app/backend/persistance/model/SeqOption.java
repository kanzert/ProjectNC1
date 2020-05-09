package com.team.app.backend.persistance.model;

public class SeqOption extends OptionObj {
    private Integer serialNum;
    private String text;
    private byte[] image;


    public SeqOption() {

    }

    public SeqOption(Long id, Integer serialNum, String text, byte[] image, Long quest_id) {
        super(id, quest_id);
        this.serialNum = serialNum;
        this.text = text;
        this.image = image;
    }

    public Integer getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(Integer serialNum) {
        this.serialNum = serialNum;
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
}
