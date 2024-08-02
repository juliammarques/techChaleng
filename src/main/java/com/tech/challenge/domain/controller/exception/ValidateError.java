package com.tech.challenge.domain.controller.exception;

import java.util.ArrayList;
import java.util.List;

public class ValidateError extends StandardError{
    private List<ValidateMessage> listaMensagens = new ArrayList<ValidateMessage>() {};

    public List<ValidateMessage> getListaMensagens(){
        return listaMensagens;
    }

    public void addMensagem(String campo,String mensagem){
        listaMensagens.add(new ValidateMessage(campo,mensagem));
    }
}
