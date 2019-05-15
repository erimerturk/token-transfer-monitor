package xyz.company.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import xyz.company.dto.TokenInfoResponse;
import xyz.company.model.TokenInfo;
import xyz.company.service.TokenInfoService;

import java.util.Optional;

@RestController
public class TokenInfoController {

    private final RestTemplate restTemplate;
    private final TokenInfoService tokenInfoService;

    @Autowired
    public TokenInfoController(RestTemplateBuilder restTemplateBuilder, TokenInfoService tokenInfoService) {
        this.restTemplate = restTemplateBuilder.build();
        this.tokenInfoService = tokenInfoService;
    }

    @GetMapping("/token/{address}")
    public TokenInfoResponse findToken(@PathVariable String address) {

        final Optional<TokenInfo> fromDB = tokenInfoService.findByAddress(address);

        if (fromDB.isPresent()) {

            final TokenInfo tokenInfo = fromDB.get();
            return TokenInfoResponse.builder()
                .address(tokenInfo.getAddress())
                .decimals(tokenInfo.getDecimals())
                .name(tokenInfo.getName())
                .symbol(tokenInfo.getSymbol())
                .build();
        } else {
            final TokenInfoResponse fromEthplorer = getTokenInfo(address);

            final TokenInfo toDB = TokenInfo.builder()
                .address(fromEthplorer.getAddress())
                .decimals(fromEthplorer.getDecimals())
                .name(fromEthplorer.getName())
                .symbol(fromEthplorer.getSymbol())
                .build();

            try {
                tokenInfoService.save(toDB);
            } catch (Exception ex) {
            }
            return fromEthplorer;

        }
    }

    private TokenInfoResponse getTokenInfo(String address) {
        final String uri = String.format("http://api.ethplorer.io/getTokenInfo/%s?apiKey=freekey", address);
        return restTemplate.getForObject(uri, TokenInfoResponse.class);
    }
}

