package com.solus.sdk.noebscall;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.solus.sdk.model.BaseResponse;
import com.solus.sdk.model.ErrorResponse;
import com.solus.sdk.model.IPIN;
import com.solus.sdk.model.Payment;
import com.solus.sdk.model.Request;
import com.solus.sdk.model.Response;
import com.solus.sdk.model.ResponseData;
import com.solus.sdk.model.types;
import com.solus.sdk.util.SdkUtil;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;


public class NOEBSClient implements ResponseData {

	private String pubKey;
    static String uuid = SdkUtil.getUuid();

    OkHttpClient client = new OkHttpClient();
    ObjectMapper mapper = new ObjectMapper(); //marshalling and unmarshalling class
    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");
    
    
    
    
    private String getKey() {
    	Key k = new Key();
        k.setApplicationId("ACTSCon");
        k.setTranDateTime(SdkUtil.getDate());
        k.setUUID(uuid);
        
        Gson gson = new Gson();
        String json;
        try {
            json = mapper.writeValueAsString(k);

            RequestBody body = RequestBody.create(json, JSON);
            okhttp3.Request httpRequest = new okhttp3.Request.Builder()
                    .url("https://beta.soluspay.net/api/consumer/key")
                    .post(body)
                    .build();

            okhttp3.Response response = client.newCall(httpRequest).execute();
            System.out.println(response.code());
          

            if (response.code() == 200) {
                Type keyType = new TypeToken<Map<String, KeyResponse>>() {
                }.getType();

                Map<String, KeyResponse> responseBody = gson.fromJson(response.body().string(), keyType);

                pubKey = responseBody.get("ebs_response").pubKeyValue;

                System.out.println(pubKey);
                
                return responseBody.get("ebs_response").pubKeyValue;

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
        return "";

    }


    @Override
    public BaseResponse<?> getResponse(Payment paymentData) {
        BaseResponse<Response> successBaseResponse = new BaseResponse<>();
        BaseResponse<ErrorResponse> errorBaseResponse = new BaseResponse<>();
        OkHttpClient client = new OkHttpClient();
        pubKey =  getKey();
        if (pubKey != null && !pubKey.isEmpty()) {
           
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
        request.setServiceProviderId(paymentData.getServiceProviderId());
        String json;
        try {
            json = gson.toJson(request, Request.class);

            // TRY TO USE DIFFERENT PAYMENT HERE
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
            }else if (response.code() == 502) {
                String responseBodey = response.body().string();
                ErrorResponse errorResponse = mapper.readValue(responseBodey, ErrorResponse.class);
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
        errorResponse.setCode(0);
        errorResponse.setMessage("SolusSDK error");
        errorBaseResponse.setResponse(errorResponse);
        return errorBaseResponse;
        }else {
        	ErrorResponse errorResponse = errorBaseResponse.getResponse();
        	 errorResponse = new ErrorResponse();
             errorResponse.setCode(0);
             errorResponse.setMessage("EBS Key error");
             errorBaseResponse.setResponse(errorResponse);
             return errorBaseResponse;
        	
        }
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

    private class Key {
        String applicationId;
        String UUID;
        String tranDateTime;
		public String getApplicationId() {
			return applicationId;
		}
		public void setApplicationId(String applicationId) {
			this.applicationId = applicationId;
		}
		public String getUUID() {
			return UUID;
		}
		public void setUUID(String uUID) {
			UUID = uUID;
		}
		public String getTranDateTime() {
			return tranDateTime;
		}
		public void setTranDateTime(String tranDateTime) {
			this.tranDateTime = tranDateTime;
		}
        
    }

    static class KeyResponse {
        String pubKeyValue;

    }

}

