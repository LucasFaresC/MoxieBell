package com.fares.MoxieBell.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "musicas")
@Getter
@Setter
@NoArgsConstructor
public class Musica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataAdicionado;

    private Boolean favorito;

    // Storing the file path instead of the File object itself is much safer for databases
    @Column(nullable = false)
    private String fileLocation;

    // This brings all the properties from MetaDataMP3 into this entity!
    @Embedded
    private MetaDataMP3 metaDados;

    public Musica(String fileLocation, MetaDataMP3 metaDados) {
        this.fileLocation = fileLocation;
        this.metaDados = metaDados;
        this.dataAdicionado = LocalDateTime.now();
        this.favorito = false; // Default pra n favorito
    }
}
