package xyz.company.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import xyz.company.dto.Notification;

@RestController
public class NotifyController {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotifyController.class);

    @PostMapping("/notify")
    public void notification(@RequestBody Notification notification) {

        LOGGER.info(
            "to : {} name : {} value : {} ",
            notification.getAddress(),
            notification.getName(),
            notification.getValue()
        );
    }


}
