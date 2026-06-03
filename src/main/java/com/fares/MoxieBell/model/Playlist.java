package com.fares.MoxieBell.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "playlists")
@Getter
@Setter
@NoArgsConstructor
public class Playlist extends MediaList {

    @Column(nullable = false)
    private String nome;
    private String descricao;
    private boolean fixada;

    // relação n pra 1 usuario
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Playlist(String nome, String descricao, User user) {
        super();
        this.nome = nome;
        this.descricao = descricao;
        this.user = user;
        this.fixada = false;
    }
}