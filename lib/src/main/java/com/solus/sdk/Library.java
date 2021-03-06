/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.solus.sdk;

import java.util.List;
import java.util.Map;

import com.solus.sdk.model.BaseResponse;
import com.solus.sdk.model.ErrorResponse;
import com.solus.sdk.model.Payment;
import com.solus.sdk.model.Response;
import com.solus.sdk.model.types;
import com.solus.sdk.noebscall.NOEBSClient;


/*
* How to use noebs SDK
* - special payment:
* use types.SpecialPayment
* - Zain:
* use types.ZainTopUp
* - MTN:
* use types.MTNTopUp
* - Sudani:
* use types.SudaniTopUp
* - NEC:
* use types.NEC
* - E15:
* use types.E15
* */
public class Library {
    public static void main(String[] args) {
		Payment payment = new Payment("gdljdfslkgjf;lgks", "9222081700111111111", "2801", "1111", 1f,"12345678");
		NOEBSClient noebsClient = new NOEBSClient();
		BaseResponse<?> response = noebsClient.getResponse(payment);
		Object object = response.getResponse();
		if(object instanceof Response) {
			System.out.println(true);
			// handle response cases here
		}else if(object instanceof ErrorResponse){
			ErrorResponse err =(ErrorResponse)object;
			System.out.println(err.getMessage());
			 Map<String, String> details = err.getDetails();
			if(!details.isEmpty())///if details not empty show messages
				 for(Map.Entry m:details.entrySet()){  
					   System.out.println(m.getKey()+" : "+m.getValue());  
					  }
			System.out.println(false);
		}else {
			System.out.println("Error");
		}
		

	}
}
