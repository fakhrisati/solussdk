package com.solus.sdk.model;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;

//////Serivce////
public interface ResponseData {
    public  BaseResponse<?> getResponse( Payment payment) ;
    public boolean isSuccessful();
}


