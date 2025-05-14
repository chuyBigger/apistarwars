package com.aluracrsos.java.modelos;


import com.google.gson.annotations.SerializedName;

public class Peliculas {

    @SerializedName("title")
    private String nombre;

    @SerializedName("episode_id")
    private int episodio;

    @SerializedName("release_date")
    private String fechaDeLanzamiento;

    @SerializedName("opening_crawl")
    private String sinopsis;

    @SerializedName("director")
    private String director;

    // Getters y Setters

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEpisodio() {
        return episodio;
    }

    public void setEpisodio(int episodio) {
        this.episodio = episodio;
    }

    public String getFechaDeLanzamiento() {
        return fechaDeLanzamiento;
    }

    public void setFechaDeLanzamiento(String fechaDeLanzamiento) {
        this.fechaDeLanzamiento = fechaDeLanzamiento;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    // to string para consola y Json

    @Override
    public String toString() {

    }
}
