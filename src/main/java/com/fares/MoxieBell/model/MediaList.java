package com.fares.MoxieBell.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@MappedSuperclass
@Getter
@Setter
public abstract class MediaList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataCriado;
    private LocalDateTime dataUltimaMod;
    private int qntMusicas;

    // Relação muito pra muitos
    @ManyToMany
    @JoinTable(
            name = "list_musicas",
            joinColumns = @JoinColumn(name = "list_id"),
            inverseJoinColumns = @JoinColumn(name = "musica_id")
    )
    protected List<Musica> musicas = new ArrayList<>();

    public MediaList() {
        this.dataCriado = LocalDateTime.now();
        this.dataUltimaMod = LocalDateTime.now();
        this.qntMusicas = 0;
    }

    public void addMusica(Musica musica) {
        this.musicas.add(musica);
        this.qntMusicas = this.musicas.size();
        this.dataUltimaMod = LocalDateTime.now();
    }

    public void removeMusica(Musica musica) {
        this.musicas.remove(musica);
        this.qntMusicas = this.musicas.size();
        this.dataUltimaMod = LocalDateTime.now();
    }
}