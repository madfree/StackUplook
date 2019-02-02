package com.madfree.stackuplook;

import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemDataSource extends PageKeyedDataSource<Integer, Item> {

    public static final int PAGE_SIZE = 50;
    public static final int FIRST_PAGE = 1;
    public static final String SITE = "stackoverlow";

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull final
    LoadInitialCallback<Integer, Item> callback) {

        RetrofitClient.getsInstance()
                .getApi()
                .getAnswers(FIRST_PAGE, PAGE_SIZE, SITE)
                .enqueue(new Callback<StackApiResponse>() {
                    @Override
                    public void onResponse(Call<StackApiResponse> call, Response<StackApiResponse
                                                > response) {
                        if (response.body() != null) {
                            callback.onResult(response.body().items, null, FIRST_PAGE +1);
                        }
                    }

                    @Override
                    public void onFailure(Call<StackApiResponse> call, Throwable t) {

                    }
                });

    }

    @Override
    public void loadBefore(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Item> callback) {

        RetrofitClient.getsInstance()
                .getApi()
                .getAnswers(params.key, PAGE_SIZE, SITE)
                .enqueue(new Callback<StackApiResponse>() {
                    @Override
                    public void onResponse(Call<StackApiResponse> call,
                                           Response<StackApiResponse> response) {

                        Integer key = (params.key > 1) ? params.key-1 : null;

                        if (response.body() != null) {
                            callback.onResult(response.body().items, key);
                        }

                    }

                    @Override
                    public void onFailure(Call<StackApiResponse> call, Throwable t) {

                    }
                });
    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Item> callback) {

        RetrofitClient.getsInstance()
                .getApi()
                .getAnswers(params.key, PAGE_SIZE, SITE)
                .enqueue(new Callback<StackApiResponse>() {
                    @Override
                    public void onResponse(Call<StackApiResponse> call,
                                           Response<StackApiResponse> response) {
                        Integer key = response.body().has_more ? params.key+1 : null;

                        if (response.body() != null) {
                            callback.onResult(response.body().items, key);
                        }
                    }

                    @Override
                    public void onFailure(Call<StackApiResponse> call, Throwable t) {

                    }
                });

    }
}
