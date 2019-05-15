package xyz.company.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.company.model.BlockTrack;

import java.util.Optional;

public interface BlockTrackRepository extends JpaRepository<BlockTrack, Long> {

    Optional<BlockTrack> findTopBy();
}
