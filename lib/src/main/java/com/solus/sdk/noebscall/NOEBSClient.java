package com.solus.sdk.noebscall;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.solus.sdk.model.BaseResponse;
import com.solus.sdk.model.ErrorResponse;
import com.solus.sdk.model.IPIN;
import com.solus.sdk.model.Payment;
import com.solus.sdk.model.Request;
import com.solus.sdk.model.Response;
import com.solus.sdk.model.ResponseData;
import com.solus.sdk.model.types;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;


public class NOEBSClient implements ResponseData {

    private String pubKey;
    static String uuid = UUID.randomUUID().toString();

    OkHttpClient client = new OkHttpClient();
    ObjectMapper mapper = new ObjectMapper(); //marshalling and unmarshalling class
    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");

    private final static Logger logger = Logger.getLogger(NOEBSClient.class.getName());

    private String getKey() {
       Request key = new Request();
       key.setUUID(UUID.randomUUID().toString());

        Gson gson = new Gson();
        String json;
        try {
            json = mapper.writeValueAsString(key);
            logger.info("the response is: " + json);

            RequestBody body = RequestBody.create(json, JSON);
            okhttp3.Request httpRequest = new okhttp3.Request.Builder()
                    .url("https://beta.soluspay.net/api/consumer/key")
                    .post(body)
                    .build();

            okhttp3.Response response = client.newCall(httpRequest).execute();
            System.out.println(response.code());
            BaseResponse errorBaseResponse;

            logger.info("the response message is: " + response.message());
            if (response.code() == 200) {
                Type keyType = new TypeToken<Map<String, KeyResponse>>() {
                }.getType();
                Map<String, KeyResponse> responseBody = gson.fromJson(response.body().string(), keyType);
                this.pubKey = responseBody.get("ebs_response").pubKeyValue;
                return responseBody.get("ebs_response").pubKeyValue;

            }else if (response.code() == 400){

                logger.info(response.body().string());
                Type keyType = new TypeToken<Map<String, KeyError>>() {
                }.getType();

                Map<String, KeyResponse> responseBody = gson.fromJson(response.body().string(), keyType);
                this.pubKey = responseBody.get("ebs_response").pubKeyValue;
                return responseBody.get("ebs_response").pubKeyValue;
            }else{
                logger.info(response.body().string());
                logger.info(response.message());
                logger.info(response.body().string());
            }
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            logger.info("why the fuck i'm here!");
            e.printStackTrace();
            logger.info(e.toString());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            logger.info(e.toString());
        } catch (IOException e) {
            e.printStackTrace();
            logger.info(e.toString());
        }
        return "";

    }


    @Override
    public BaseResponse<?> getResponse(Payment paymentData) {
        BaseResponse<Response> successBaseResponse = new BaseResponse<>();
        BaseResponse<ErrorResponse> errorBaseResponse = new BaseResponse<>();
        OkHttpClient client = new OkHttpClient();

        if (this.pubKey == null) {
            this.pubKey = this.getKey();
        }

        logger.info("Example log from {}" + this.pubKey);

        Gson gson = new Gson();
        String ipin = IPIN.getIPINBlock(paymentData.getIpin(), this.pubKey, uuid);
        Request request = new Request();
        request.setPan(paymentData.getPan());
        request.setIPIN(ipin);
        request.setExpDate(paymentData.getExpDate());
        request.setTranAmount(paymentData.getTranAmount());
        request.setUUID(uuid);
        request.setIPIN(ipin);
        request.setPaymentInfo(paymentData.getPaymentInfo());
        request.setPayeeId(paymentData.getPayeeId());
        request.setServiceProviderId("12345678");
        String json;
        try {
            json = gson.toJson(request, Request.class);
            // TRY TO USE DIFFERENT PAYMENT HERE
            logger.info("the request to payment is: " + json);
//            log("EBS request url", serverUrl(paymentData.getBillerId()));
            RequestBody body = RequestBody.create(json, JSON);
            okhttp3.Request httpRequest = new okhttp3.Request.Builder()
                    .url(serverUrl(paymentData.getBillerId()))
                    .post(body)
                    .build();

            okhttp3.Response response = client.newCall(httpRequest).execute();
            System.out.println(response.code());
            if (response.code() == 200) {
                String responseBody = response.body().string();
                Response successRespose = mapper.readValue(responseBody, Response.class);
                successBaseResponse.setResponse(successRespose);
                return successBaseResponse;
            } else if (response.code() == 400) {
                String responseBodey = response.body().string();
                ErrorResponse errorResponse = mapper.readValue(responseBodey, ErrorResponse.class);
                errorBaseResponse.setResponse(errorResponse);
                return errorBaseResponse;
            } else if (response.code() == 404) {
                String responseBodey = response.body().string();

                ErrorResponse errorResponse = mapper.readValue(responseBodey, ErrorResponse.class);
                errorBaseResponse.setResponse(errorResponse);
                return errorBaseResponse;
            }else if (response.code() == 502) { // for ebs error messages
                String responseBodey = response.body().string();

                JsonObject jsonObject = gson.fromJson(responseBodey, JsonObject.class);

                logger.info("gson response " + jsonObject.get("details").toString());
                logger.info("ebs_error_502 is: " + responseBodey);
                ErrorResponse errorResponse = mapper.readValue(jsonObject.get("details").toString(), ErrorResponse.class);
                logger.info("error message: " + errorResponse.getResponseMessage());
                errorBaseResponse.setResponse(errorResponse);
                return errorBaseResponse;
            }

        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        ErrorResponse errorResponse = errorBaseResponse.getResponse();
        errorResponse = new ErrorResponse();
        errorResponse.setResponseCode(0);
        errorResponse.setResponseMessage("SolusSDK error");
        errorBaseResponse.setResponse(errorResponse);
        return errorBaseResponse;
    }

    @Override
    public boolean isSuccessful() {
        // TODO Auto-generated method stub
        return false;
    }

    public String serverUrl(types billerId) {
        String host;
        if (billerId == types.ZainTopUp || billerId == types.MTNTopUp || billerId == types.SudaniTopUp || billerId == types.E15 || billerId == types.NEC) {
            host = "https://beta.soluspay.net/api/v1/purchase";
        } else {
            host = "https://beta.soluspay.net/api/v1/purchase";
        }

        URI builder = URI.create(host);
        return builder.toString();
    }

}



class Key {
    String applicationId;
    String UUID;
    String tranDateTime;

    public Key(String applicationId, String UUID, String tranDateTime) {
        this.applicationId = applicationId;
        this.UUID = UUID;
        this.tranDateTime = tranDateTime;
    }
}

 class KeyResponse {
    String pubKeyValue;
}

 class KeyError {
    String message;
    String code;
}

