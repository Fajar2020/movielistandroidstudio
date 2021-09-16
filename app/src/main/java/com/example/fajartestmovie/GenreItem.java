package com.example.fajartestmovie;

public class GenreItem {
    private int id;
//    private String imgUrl;
    private String nameGenre;

//    public GenreItem(int id, String imgUrl, String nameGenre){
//        this.id=id;
//        this.imgUrl=imgUrl;
//        this.nameGenre=nameGenre;
//    }

    public GenreItem(int id, String nameGenre) {
        this.id = id;
        this.nameGenre = nameGenre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameGenre() {
        return nameGenre;
    }

    public void setNameGenre(String nameGenre) {
        this.nameGenre = nameGenre;
    }
}
