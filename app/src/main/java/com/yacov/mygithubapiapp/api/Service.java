package com.yacov.mygithubapiapp.api;

import com.yacov.mygithubapiapp.model.ItemResponse;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by YacovR on 23-Nov-17.
 */

public interface Service {
    String txt = "brazil";

    @GET(" /search/users?q=location:" + txt)
    Call<ItemResponse> getItems();

}
