package com.solutis.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.solutis.DTO.Mensagem;

@Component
public class MessageConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageConsumer.class);
    
    private final int NUMERO_THREADS = 5;

    private final ThreadPool threadPool;

    public MessageConsumer(){

        this.threadPool = new ThreadPool(NUMERO_THREADS);
    }
    
    @RabbitListener(queues = {"${rabbit.exclusao.queue}"})
    public void consumer(Mensagem mensagem) throws InterruptedException {
        LOGGER.info("Iniciando o processamento da mensagem");
        threadPool.execute(() -> {
        	LOGGER.info("Recebi uma mensagem!");
//        	FirebaseService.enviarMensagem(mensagem);
            threadPool.shutdown();
        });

        while(threadPool.waitThread()){
            Thread.sleep(100);
        }
    }

}

