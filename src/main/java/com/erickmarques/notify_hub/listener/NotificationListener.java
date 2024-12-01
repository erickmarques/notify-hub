package com.erickmarques.notify_hub.listener;

import com.erickmarques.notify_hub.controller.dto.NotificationCreateDto;
import com.erickmarques.notify_hub.service.NotificationServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationListener {

    private final NotificationServiceImpl notificationServiceImpl;
    private final ObjectMapper mapper;

    private static final Logger logger = LoggerFactory.getLogger(NotificationListener.class);

    @RabbitListener(queues = "notification")
    public void receiveMessage(@Payload String payload) {

        try {
            NotificationCreateDto dados = mapper.readValue(payload, NotificationCreateDto.class);

            logger.info("recebendo mensagem da fila {}", dados.toString());

            notificationServiceImpl.create(dados);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

