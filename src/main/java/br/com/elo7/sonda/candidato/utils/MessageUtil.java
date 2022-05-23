package br.com.elo7.sonda.candidato.utils;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class MessageUtil {

    private MessageSource messageSource;

    public MessageUtil(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getMessage(String key){
        return this.messageSource.getMessage(key, null, null);
    }

}
