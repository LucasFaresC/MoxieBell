package com.fares.MoxieBell.service;

import com.fares.MoxieBell.model.MetaDataMP3;
import com.fares.MoxieBell.model.Musica;
import com.fares.MoxieBell.repository.MusicaRepository;
import org.hibernate.boot.Metadata;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.images.Artwork;
import org.springframework.stereotype.Service;
import java.io.File;
import java.util.List;

public class MusicaService {

    private final MusicaRepository repository;

    public MusicaService(MusicaRepository repository) {
        this.repository = repository;
    }

    // RF04: Remover músicas
    public void deleteMusic(Long id) {
        repository.deleteById(id);
    }

    // RF05: Consultar músicas
    public List<Musica> getAllMusic() {
        return repository.findAll();
    }

    // RF03: Adicionar músicas
    public Musica addMusicFromFile(String filePath) {
        try{
            MetaDataMP3 metaData = extractMetaData(filePath);
            Musica newSong = new Musica(filePath, metaData);
            return repository.save(newSong);

        } catch (Exception e) {
            throw new RuntimeException("ERRO, algo errado com a bomba dos metadados" + e.getMessage());
        }
    }

    public MetaDataMP3 extractMetaData(String filePath) throws Exception {
        File file = new File(filePath);
        AudioFile audio = AudioFileIO.read(file);

        Tag tag = audio.getTag();
        AudioHeader header = audio.getAudioHeader();

        MetaDataMP3 metaData = new MetaDataMP3();

        if(tag != null){
            metaData.setTitulo(tag.getFirst(FieldKey.TITLE));
            metaData.setArtista(tag.getFirst(FieldKey.ARTIST));
            metaData.setAlbum(tag.getFirst(FieldKey.ALBUM));
            metaData.setGenero(tag.getFirst(FieldKey.GENRE));

            String anoStr = tag.getFirst(FieldKey.YEAR);
            if (anoStr != null && !anoStr.isEmpty()) {
                // as vezes o ano vem como "2023-01-01" ai remove esses caracteres extraS
                metaData.setAnoLancamento(Integer.parseInt(anoStr.substring(0, 4)));
            }

            String bpmStr = tag.getFirst(FieldKey.BPM);
            if (bpmStr != null && !bpmStr.isEmpty()) {
                metaData.setBpm(Integer.parseInt(bpmStr));
            }

            // Extrair a arte do álbum (Capa)
            Artwork artwork = tag.getFirstArtwork();
            if (artwork != null) {
                metaData.setArteAlbum(artwork.getBinaryData());
            }

        }

        // A duração da música vem do Header do áudio, não da Tag de texto, QUE ESTÁ em mili, ai tem que converter
        if (header != null) {
            // Convertendo segundos para milissegundos
            metaData.setDuracaoFaixaMillis(header.getTrackLength() * 1000L);
        }

        // Se o título estiver vazio, usamos o nome do ficheiro como backup
        if (metaData.getTitulo() == null || metaData.getTitulo().isEmpty()) {
            metaData.setTitulo(file.getName());
        }

        return metaData;

    }

}