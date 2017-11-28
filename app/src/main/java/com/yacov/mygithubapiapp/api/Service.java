package com.yacov.mygithubapiapp.api;

import android.view.View;
import android.widget.EditText;

import com.yacov.mygithubapiapp.R;
import com.yacov.mygithubapiapp.controlller.MainActivity;
import com.yacov.mygithubapiapp.model.ItemResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


/**
 * Created by YacovR on 23-Nov-17.
 */

public interface Service {
    String loc = "brazil";

    @GET(" /search/users?q=location:brazil")
    Call<ItemResponse> getItems();


};
