package com.example.series.model;

public class Serie {
    private String key;
    private String nome;
    private Integer temporada;
    private Integer episodio;
    private String diaSemana;
    private String plataforma;

    public Serie() {
    }

    public Serie(String nome, Integer temporada, Integer episodio, String diaSemana, String plataforma) {
        this.nome = nome;
        this.temporada = temporada;
        this.episodio = episodio;
        this.diaSemana = diaSemana;
        this.plataforma = plataforma;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getTemporada() {
        return temporada;
    }

    public void setTemporada(Integer temporada) {
        this.temporada = temporada;
    }

    public Integer getEpisodio() {
        return episodio;
    }

    public void setEpisodio(Integer episodio) {
        this.episodio = episodio;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public String getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }
}
