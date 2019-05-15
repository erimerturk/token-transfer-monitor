package xyz.company.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.abi.EventEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.EthLog;

import java.util.ArrayList;

import static xyz.company.service.Constants.TRANSFER_EVENT;

@Service
public class BlockFinderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BlockEventParseService.class);

    @Autowired private Web3j web3;

    public EthLog findBlock(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        try {
            EthFilter filter = new EthFilter(startBlock, endBlock, new ArrayList<>());
            filter.addSingleTopic(EventEncoder.encode(TRANSFER_EVENT));
            return web3.ethGetLogs(filter).send();
        } catch (Exception ex) {
            LOGGER.info("Exception Occured :: ", ex);
            throw new RuntimeException("Failed to find block events");
        }
    }
}
