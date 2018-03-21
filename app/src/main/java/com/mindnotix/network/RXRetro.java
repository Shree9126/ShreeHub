package com.mindnotix.network;


import android.util.Log;

import com.mindnotix.utils.Constant;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import okhttp3.ResponseBody;
import retrofit2.Response;
//import rx.Observable;
//import rx.Subscriber;
//import rx.android.schedulers.AndroidSchedulers;
//import rx.schedulers.Schedulers;
import com.mindnotix.network.IBaseResponseListener;

/**
 * Created by Admin on 1/20/2018.
 */

public class RXRetro {

    private static String TAG = RXRetro.class.getSimpleName();

    private static RXRetro rxRetro;

    public static RXRetro getInstance() {
        if (rxRetro == null) {
            rxRetro = new RXRetro();
        }
        return rxRetro;
    }


    private String getStringFromByte(InputStream paramInputStream) {
        StringBuilder localStringBuilder = new StringBuilder();
        BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(paramInputStream));
        try {
            while (true) {
                String str = localBufferedReader.readLine();
                if (str == null)
                    break;
                localStringBuilder.append(str);
            }
        } catch (IOException localIOException) {
            localIOException.printStackTrace();
        }
        return localStringBuilder.toString();
    }




    /*public void retrofitEnque(Observable<Response<ResponseBody>> call, final IBaseResponseListener iResponseListener, final int requestId) {

        call
                .subscribeOn(Schedulers.newThread())
              //  .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response<ResponseBody>>() {
                    @Override
                    public void onCompleted() {
                        if (iResponseListener != null) {
                            iResponseListener.onSuccess("Completed", 0);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (iResponseListener != null) {
                            iResponseListener.onFailure(e, 0);
                        }
                    }

                    @Override
                    public void onNext(Response<ResponseBody> paramResponse) {

                        Log.d(TAG, "RESPONSE CODE - " + paramResponse.raw().code());
                        Log.d(TAG, "RAW RESPONSE - " + paramResponse.raw());

                        Object localObject = paramResponse.body();
                        String str = null;
                        if (localObject != null) {
                            str = getStringFromByte(getByteStream(paramResponse.body()));
                            Log.d(TAG, "=" + str);
                        }

                        switch (paramResponse.code()) {
                            case Constant.httpcodes.STATUS_OK:
                                iResponseListener.onSuccess(str, requestId);
                                break;
                            case Constant.httpcodes.STATUS_CREATED:
                                iResponseListener.onSuccess(str, requestId);
                                break;
                            case Constant.httpcodes.STATUS_BAD_REQUEST:
                                iResponseListener.serverError(getStringFromByte(getByteStream(paramResponse.errorBody())), requestId, Constant.httpcodes.STATUS_OK);
                                break;
                            case Constant.httpcodes.STATUS_UNAUTHORIZED:
                                iResponseListener.serverError(getStringFromByte(getByteStream(paramResponse.errorBody())), requestId, Constant.httpcodes.STATUS_UNAUTHORIZED);
                                break;
                            case Constant.httpcodes.STATUS_FORBITTEN:
                                iResponseListener.serverError(paramResponse.raw().message(), requestId, Constant.httpcodes.STATUS_FORBITTEN);
                                break;
                            case Constant.httpcodes.STATUS_NOT_FOUND:
                                iResponseListener.serverError(paramResponse.raw().message(), requestId, Constant.httpcodes.STATUS_NOT_FOUND);
                                break;
                            case Constant.httpcodes.STATUS_SERVER_ERROR:
                                iResponseListener.serverError(getStringFromByte(getByteStream(paramResponse.errorBody())), requestId, Constant.httpcodes.STATUS_SERVER_ERROR);
                                break;
                            case Constant.httpcodes.STATUS_NO_CONTENT:
                                iResponseListener.serverError(getStringFromByte(getByteStream(paramResponse.errorBody())), requestId, Constant.httpcodes.STATUS_NO_CONTENT);
                                break;
                        }

                    }

                });

    }*/

    private InputStream getByteStream(ResponseBody paramResponse) {
        return paramResponse.byteStream();
    }
}



