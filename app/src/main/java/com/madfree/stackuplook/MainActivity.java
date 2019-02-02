package com.madfree.stackuplook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Call<StackApiResponse> call =
                RetrofitClient.getsInstance()
                .getApi()
                .getAnswers(1, 50, "stackoverflow");

        call.enqueue(new Callback<StackApiResponse>() {
            @Override
            public void onResponse(Call<StackApiResponse> call, Response<StackApiResponse>
                    response) {
                StackApiResponse stackApiResponse = response.body();

                Toast.makeText(MainActivity.this, String.valueOf(stackApiResponse.items.size()), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<StackApiResponse> call, Throwable t) {

            }
        });
    }
}
