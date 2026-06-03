package com.fares.MoxieBell.controller;

import com.fares.MoxieBell.model.Musica;
import com.fares.MoxieBell.service.MusicaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/musicas")
public class MusicaController {

    private final MusicaService musicaService;

    // RF04: Remover músicas (DELETE http://localhost:8080/api/musicas/{id})
    @DeleteMapping("/{id}")
    public void deleteMusic(@PathVariable Long id) {
        musicaService.deleteMusic(id);
    }

    public MusicaController(MusicaService musicaService) {
        this.musicaService = musicaService;
    }

    // RF05: Consultar músicas
    @GetMapping
    public List<Musica> getAll() {
        return musicaService.getAllMusic();
    }

    // RF03: Adicionar músicas
    // We expect the front-end to send a raw String containing the file path
    @PostMapping
    public Musica addMusic(@RequestBody String filePath) {
        return musicaService.addMusicFromFile(filePath);
    }
}