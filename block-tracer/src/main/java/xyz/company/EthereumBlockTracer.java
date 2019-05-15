package xyz.company;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.protocol.core.methods.response.EthLog;
import xyz.company.dto.BlockSearchMessage;
import xyz.company.model.BlockTrack;
import xyz.company.repository.BlockTrackRepository;
import xyz.company.service.BlockFinderService;
import xyz.company.service.MessageSender;

import java.io.IOException;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Stream;

@Component
public class EthereumBlockTracer {

    private static final Logger logger = LoggerFactory.getLogger(EthereumBlockTracer.class);

    private static final int TOO_MANY_DATA_EXCEPTION = -32005;
    @Autowired private Web3j web3;
    @Autowired private BlockTrackRepository blockTrackRepository;
    @Autowired private MessageSender messageSender;
    @Autowired private BlockFinderService blockFinderService;


    @Scheduled(fixedRate = 30000)
    public void scheduleTaskWithFixedRate() {

        final Optional<BlockTrack> blockTrack = blockTrackRepository.findTopBy();

        try {

            final EthBlockNumber send = web3.ethBlockNumber().send();
            final DefaultBlockParameter endBlock = DefaultBlockParameter.valueOf(send.getBlockNumber());


            final DefaultBlockParameter startBlock = blockTrack.isPresent()
                ? DefaultBlockParameter.valueOf(blockTrack.get().getBlockNumber().add(BigInteger.ONE))
                : endBlock;

            final EthLog ethLog = blockFinderService.findBlock(startBlock, endBlock);

            Optional.ofNullable(ethLog.getLogs())
                .ifPresentOrElse(logs -> {
                    messageSender.send(BlockSearchMessage.builder().start(startBlock.getValue()).end(endBlock.getValue()).build());
                    updateBlockTrackWithNewBlock(blockTrack, send.getBlockNumber());
                }, () -> {
                    if (ethLog.getError() != null && ethLog.getError().getCode() == TOO_MANY_DATA_EXCEPTION) {

                        Stream.iterate(
                            blockTrack.get().getBlockNumber().add(BigInteger.ONE),
                            n -> n.add(BigInteger.ONE))
                            .filter(current -> current.compareTo(send.getBlockNumber()) < 1)
                            .forEach(blockNumber -> {
                                final DefaultBlockParameter searchBlock = DefaultBlockParameter.valueOf(blockNumber);
                                messageSender.send(BlockSearchMessage.builder().start(searchBlock.getValue()).end(searchBlock.getValue()).build());
                                updateBlockTrackWithNewBlock(blockTrack, blockNumber);
                            });
                    }
                });

        } catch (IOException ex) {
            logger.info("Failed :: ", ex);
        }

    }

    private void updateBlockTrackWithNewBlock(Optional<BlockTrack> blockTrack, BigInteger blockNumber) {
        blockTrack.ifPresentOrElse(track -> {
            track.setBlockNumber(blockNumber);
            track.setLastUpdatedTime(LocalDateTime.now());
            blockTrackRepository.save(track);
        }, () -> {
            final BlockTrack track = new BlockTrack();
            track.setBlockNumber(blockNumber);
            track.setLastUpdatedTime(LocalDateTime.now());
            blockTrackRepository.save(track);
        });
    }

}
