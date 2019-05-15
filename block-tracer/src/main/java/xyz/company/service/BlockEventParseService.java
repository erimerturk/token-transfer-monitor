package xyz.company.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.web3j.abi.EventValues;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthLog;
import org.web3j.tx.Contract;
import org.web3j.utils.Numeric;
import xyz.company.dto.BlockSearchMessage;
import xyz.company.dto.Notification;
import xyz.company.dto.TokenInfoResponse;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static xyz.company.service.Constants.DECIMAL_FORMAT;
import static xyz.company.service.Constants.TRANSFER_EVENT;

@Component
public class BlockEventParseService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BlockEventParseService.class);

    private List<String> BLOCK_PARAMETER_NAMES = Stream.of(DefaultBlockParameterName.values())
        .map(DefaultBlockParameterName::getValue)
        .collect(Collectors.toList());

    private RestTemplate restTemplate;
    private BlockFinderService blockFinderService;

    @Value("${token.info.provider.base.url}")
    private String tokenInfoProviderBaseUrl;
    @Value("${transaction.notify.url}")
    private String transactionNotifyUrl;


    @Autowired
    public BlockEventParseService(RestTemplateBuilder restTemplateBuilder, BlockFinderService blockFinderService) {
        this.restTemplate = restTemplateBuilder.build();
        this.blockFinderService = blockFinderService;
    }

    @JmsListener(destination = "block.search.q")
    public void receive(BlockSearchMessage message) {

        final EthLog ethLog = blockFinderService.findBlock(getBlockParameter(message.getStart()), getBlockParameter(message.getEnd()));

        ethLog.getLogs().stream().forEach(logResult -> {

            try {
                final EthLog.LogObject log = (EthLog.LogObject) logResult.get();
                final EventValues eventValues = Contract.staticExtractEventParameters(TRANSFER_EVENT, log);
                TokenInfoResponse tokenInfo = restTemplate.getForObject(tokenInfoProviderBaseUrl + "/token/" + log.getAddress(), TokenInfoResponse.class);

                final Notification body = Notification.newBuilder()
                    .address((String) eventValues.getIndexedValues().get(1).getValue())
                    .name(tokenInfo.getName())
                    .value(DECIMAL_FORMAT.format(amount((BigInteger) eventValues.getNonIndexedValues().get(0).getValue(), tokenInfo.getDecimals())))
                    .build();

                restTemplate.postForEntity(transactionNotifyUrl, body, Void.class);

            } catch (Exception ex) {
                LOGGER.info("Exception Occured :: ", ex);
            }

        });
    }

    private DefaultBlockParameter getBlockParameter(String start) {
        return BLOCK_PARAMETER_NAMES.contains(start) ? DefaultBlockParameterName.valueOf(start) : DefaultBlockParameter.valueOf(Numeric.decodeQuantity(start));
    }

    private Double amount(BigInteger value, String decimals) {
        return (value.doubleValue() / Math.pow(10, Double.valueOf(decimals)));
    }


}
