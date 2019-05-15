package xyz.company.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.company.model.TokenInfo;

import java.util.Optional;

public interface TokenInfoRepository extends JpaRepository<TokenInfo, Long> {

    Optional<TokenInfo> findByAddress(String address);
}
