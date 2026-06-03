package com.fares.MoxieBell.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Lob;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class MetaDataMP3 {

    private String titulo;
    private String artista;
    private String album;
    private String genero;
    private int anoLancamento;
    private int bpm;
    private long duracaoFaixaMillis;

    // @Lob é pro bd se preparar pq a metadata vai ser um registro grande
    // LONGBLOB allows you to store the image bytes directly in the DB
    @Lob
    @Column(columnDefinition="LONGBLOB")
    private byte[] arteAlbum;
}