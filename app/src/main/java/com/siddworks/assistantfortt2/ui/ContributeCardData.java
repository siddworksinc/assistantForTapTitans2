package com.siddworks.assistantfortt2.ui;

/**
 * Created by SIDD on 12-Oct-16.
 */
public class ContributeCardData {
    private final int color;
    String title;
    String desc;
    int icon;

    public int getColor() {
        return color;
    }

    public String getDesc() {
        return desc;
    }

    public int getIcon() {
        return icon;
    }

    public ContributeCardData(String title_, String desc_, int icon_, int color_) {
        this.title = title_;
        this.desc = desc_;
        this.icon = icon_;
        this.color = color_;
    }

    public String getTitle() {
        return title;
    }
}
