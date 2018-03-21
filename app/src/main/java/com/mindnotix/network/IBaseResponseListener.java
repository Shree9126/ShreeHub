package com.mindnotix.network;

/**
 * Created by Admin on 1/20/2018.
 */

public abstract interface IBaseResponseListener {


    //apart from api call error, server communication errors
    void serverError(String stringFromByte, int paramInt, int errorCode);

    //api call failure response
    void onFailure(Throwable t, int requestId);

    void onSuccess(String completed, int i);
}
