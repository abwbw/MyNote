package com.example.abwbw.mynote.model;

import android.graphics.Bitmap;

/**
 * Created by abwbw on 15-8-24.
 */
public class NotesDesrcModel {
    private String title;
    private String autor;
    private String desrc;
    private Bitmap desrcPic;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getDesrc() {
        return desrc;
    }

    public void setDesrc(String desrc) {
        this.desrc = desrc;
    }

    public Bitmap getDesrcPic() {
        return desrcPic;
    }

    public void setDesrcPic(Bitmap desrcPic) {
        this.desrcPic = desrcPic;
    }
}
