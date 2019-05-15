package xyz.company.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.company.model.TokenInfo;
import xyz.company.repository.TokenInfoRepository;

import java.util.Optional;

@Service
public class TokenInfoService {

    @Autowired private TokenInfoRepository tokenInfoRepository;

    public Optional<TokenInfo> findByAddress(String address) {
        return tokenInfoRepository.findByAddress(address);
    }

    @Transactional
    public void save(TokenInfo toDB) {
        tokenInfoRepository.save(toDB);
    }

}
