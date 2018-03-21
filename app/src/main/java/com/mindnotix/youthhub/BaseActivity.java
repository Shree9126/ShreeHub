package com.mindnotix.youthhub;

import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mindnotix.network.IBaseResponseListener;

/**
 * Created by Admin on 1/20/2018.
 */

public class BaseActivity extends BaseAppCompactActivity implements IBaseResponseListener {

    private static final String TAG = BaseActivity.class.getSimpleName();
    /**
     * BaseActivity implements IBaseResponseListener
     * Server error from the ResponseListener wil e handled globally - not to implement in every activity.
     * Activities should extend BaseActivity
     */

    private Gson mGson;

    protected Gson getGson(){
        if(mGson == null){
            mGson = new Gson();
        }
        return mGson;
    }

    protected void showToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void serverError(String serverResponse, int paramInt, int errorCode) {
        showToast(serverResponse + " Internal server error." );
    }

    /**
     * By implementing this method in BaseActivity will by default shows the server api call failure message
     * If needed , this method can be overridden in your own activity
     *
     * @param t - Server error message
     * @param requestId - Shows ID of the requested ApiCall
     */

    @Override
    public void onFailure(Throwable t, int requestId) {

        t.printStackTrace();
        //showToast(t.getLocalizedMessage());
    }

    @Override
    public void onSuccess(String completed, int i) {
       // showToast(completed);
        Log.d(TAG, "onSuccess: "+completed);

    }

}