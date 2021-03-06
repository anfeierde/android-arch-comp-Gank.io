package com.cz1.gank.data.remote;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import com.cz1.gank.AppExecutors;
import com.cz1.gank.api.ApiResponse;

public abstract class NetworkBoundResource<ResultType, RequestType> {
    private final AppExecutors appExecutors;

    private final MediatorLiveData<Resource<ResultType>> result = new MediatorLiveData<>();

    @MainThread
    public NetworkBoundResource(AppExecutors appExecutors) {
        this.appExecutors = appExecutors;
        Resource<ResultType> nullValue = Resource.loading(null);
        result.setValue(nullValue);
        final LiveData<ResultType> dbSource = loadFromDb();
        result.addSource(dbSource, new Observer<ResultType>() {
            @Override
            public void onChanged(@Nullable ResultType data) {
                result.removeSource(dbSource);
                if (shouldFetch(data)) {
                    fetchFromNetwork(dbSource);
                } else {
                    result.addSource(dbSource, new Observer<ResultType>() {
                        @Override
                        public void onChanged(@Nullable ResultType newData) {
                            result.setValue(Resource.success(newData));
                        }
                    });
                }
            }
        });

    }

    private void fetchFromNetwork(final LiveData<ResultType> dbSource) {
        final LiveData<ApiResponse<RequestType>> apiResponse = createCall();
        //we re-attach dbSource as a new source, it will dispatch its latest value quickly.
        result.addSource(dbSource, new Observer<ResultType>() {
            @Override
            public void onChanged(@Nullable ResultType newData) {
                result.setValue(Resource.loading(newData));
            }
        });
        result.addSource(apiResponse, new Observer<ApiResponse<RequestType>>() {
            @Override
            public void onChanged(@Nullable final ApiResponse<RequestType> response) {
                result.removeSource(apiResponse);
                result.removeSource(dbSource);

                if (response.isSuccessful()) {
                    appExecutors.diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            saveCallResult(processResponse(response));
                            appExecutors.mainThread().execute(new Runnable() {
                                @Override
                                public void run() {
                                    result.addSource(loadFromDb(), new Observer<ResultType>() {
                                        @Override
                                        public void onChanged(@Nullable ResultType newData) {
                                            result.setValue(Resource.success(newData));
                                        }
                                    });
                                }
                            });
                        }
                    });
                } else {
                    onFetchFailed();
                    result.addSource(dbSource, new Observer<ResultType>() {
                        @Override
                        public void onChanged(@Nullable ResultType newData) {
                            result.setValue(Resource.error(response.errorMessage, newData));
                        }
                    });
                }

            }
        });

    }

    protected void onFetchFailed() {
    }

    public LiveData<Resource<ResultType>> asLiveData() {
        return result;
    }

    @WorkerThread
    protected RequestType processResponse(ApiResponse<RequestType> response) {
        return response.body;
    }

    // data persistence
    @WorkerThread
    protected abstract void saveCallResult(@NonNull RequestType item);

    // determine send a network request or not according database query result.
    @MainThread
    protected abstract boolean shouldFetch(@Nullable ResultType data);

    // load data from stored database
    @NonNull
    @MainThread
    protected abstract LiveData<ResultType> loadFromDb();

    // if {@code shouldFetch} return {@code true} then fetch from network.
    @NonNull
    @MainThread
    protected abstract LiveData<ApiResponse<RequestType>> createCall();
}