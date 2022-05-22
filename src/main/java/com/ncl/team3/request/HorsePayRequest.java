package com.ncl.team3.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * This class is used to connect with the horsepay API given by university
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HorsePayRequest {

    private String storeID;

    private String customerID;

    private String date;

    private String time;

    private String timeZone;

    private Float transactionAmount;

    private String currencyCode;

    private Boolean forcePaymentStatusReturnType;
}
