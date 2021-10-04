package com.pepsidrc.callbacks;


import com.pepsidrc.network.RestResponse;

public interface APIResponseCallback<T> {

    void onResponse(RestResponse<T> response);
}
