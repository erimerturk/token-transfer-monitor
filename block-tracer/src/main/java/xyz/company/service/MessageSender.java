package xyz.company.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import xyz.company.dto.BlockSearchMessage;

@Component
public class MessageSender {

    @Autowired
    private JmsTemplate jmsTemplate;

    public void send(BlockSearchMessage message) {
        jmsTemplate.convertAndSend("block.search.q", message);
    }

}
