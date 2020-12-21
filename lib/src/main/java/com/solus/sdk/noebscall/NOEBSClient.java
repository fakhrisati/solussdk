package com.solus.sdk.noebscall;
import java.io.IOException;
import java.net.URI;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solus.sdk.model.BaseResponse;
import com.solus.sdk.model.ErrorResponse;
import com.solus.sdk.model.IPIN;
import com.solus.sdk.model.Payment;
import com.solus.sdk.model.Request;
import com.solus.sdk.model.Response;
import com.solus.sdk.model.ResponseData;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;




public class NOEBSClient implements ResponseData {
	public static final MediaType JSON
    = MediaType.get("application/json; charset=utf-8");

	@Override
	public BaseResponse<?> getResponse(Payment paymentData)
			 {

        BaseResponse<Response> successBaseResponse = new BaseResponse<>();
        BaseResponse<ErrorResponse> errorBaseResponse = new BaseResponse<>();
        OkHttpClient client = new OkHttpClient();
        ObjectMapper mapper = new ObjectMapper(); //marshalling and unmarshalling class
        
        
        Request request = new Request();
       // String ipinBlock = IPIN.getIPINBlock(paymentData.getIpin(), paymentData.getApiKey(), request.getUuid());
        request.setPan(paymentData.getPan());
        request.setIPIN(paymentData.getIpin());
        request.setExpDate(paymentData.getExpDate());
        request.setTranAmount(paymentData.getTranAmount());
        
        
        
        String json;
		try {
			json = mapper.writeValueAsString(request);
		
        RequestBody body = RequestBody.create(json, JSON);
        okhttp3.Request httpRequest = new okhttp3.Request.Builder()
        	      .url(serverUrl())
        	      .post(body)
        	      .build();
        
			okhttp3.Response response = client.newCall(httpRequest).execute();
			System.out.println(response.code());
			if(response.code()==200)
				
					{
						
						String responseBody = response.body().string();
						Response successRespose = mapper.readValue(responseBody, Response.class);
						successBaseResponse.setResponse(successRespose);
						return successBaseResponse;
					}
				
			else if(response.code()==400) {
				
				String responseBodey = response.body().string();
				ErrorResponse errorResponse = mapper.readValue(responseBodey, ErrorResponse.class);
				errorBaseResponse.setResponse(errorResponse);
				return errorBaseResponse;
			}
             else if(response.code()==404) {
				
				String responseBodey = response.body().string();
				ErrorResponse errorResponse = mapper.readValue(responseBodey, ErrorResponse.class);
				errorBaseResponse.setResponse(errorResponse);
				return errorBaseResponse;
			}
			
				
			
		
		}
		
		
		catch (JsonMappingException e) {
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
		errorResponse= new ErrorResponse();
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
	
	
	


    public String serverUrl() {
        String host = "https://beta.soluspay.net/api/consumer/";
        URI builder = URI.create(host);
        return builder.toString();
    }



}
