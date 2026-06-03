package com.fares.MoxieBell.repository;

import com.fares.MoxieBell.model.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
}