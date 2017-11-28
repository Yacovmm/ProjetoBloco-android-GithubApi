package com.yacov.mygithubapiapp.controlller;

import android.app.ProgressDialog;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yacov.mygithubapiapp.ItemAdapter;
import com.yacov.mygithubapiapp.R;
import com.yacov.mygithubapiapp.api.Client;
import com.yacov.mygithubapiapp.api.Service;
import com.yacov.mygithubapiapp.model.Item;
import com.yacov.mygithubapiapp.model.ItemResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity{

    private RecyclerView recyclerView;
    private TextView Disconnected;
    private Item item;
    ProgressDialog pd;
    private SwipeRefreshLayout swipeContainer;
    private EditText search;
    public String loca = "brazil";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();


        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainerID);

        swipeContainer.setColorSchemeResources(android.R.color.holo_orange_dark);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadJson();
                Toast.makeText(MainActivity.this, "Github Data Refresh", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initViews(){
        pd = new ProgressDialog(this);
        pd.setMessage("Fetching Data");
        pd.setCancelable(false);
        pd.show();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewID);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.smoothScrollToPosition(0);
        loadJson();
    }

    private void loadJson(){
        Disconnected = (TextView) findViewById(R.id.disconnectedID);
        try {

            Client client = new Client();
            Service apiservice =
                    client.getClient().create(Service.class);
            Call<ItemResponse> call = apiservice.getItems();
            call.enqueue(new Callback<ItemResponse>() {
                @Override
                public void onResponse(Call<ItemResponse> call, Response<ItemResponse> response) {

                    List<Item> items = response.body().getItems();
                    recyclerView.setAdapter(new ItemAdapter(getApplicationContext(), items));
                    recyclerView.smoothScrollToPosition(0);
                    swipeContainer.setRefreshing(false);
                    pd.hide();
                }

                @Override
                public void onFailure(Call<ItemResponse> call, Throwable t) {
                    Log.d("Error", t.getMessage());
                    Toast.makeText(MainActivity.this, "Error Fetching Data!", Toast.LENGTH_SHORT).show();
                    Disconnected.setVisibility(View.VISIBLE);
                    pd.hide();
                }
            });


        }catch (Exception e){
            Log.d("Error", e.getMessage());
            Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();

        }

    }

    public void searchClick(View view){

        search = (EditText) findViewById(R.id.edtSearchID);
        loca = search.getText().toString();
        loadJson();

    }

}
