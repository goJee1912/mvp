package me.gojee.mvp.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import me.gojee.mvp.data.filter.Filter;

import static me.gojee.mvp.util.Preconditions.checkNotNull;

/**
 * @author goJee
 * @since 2018/4/3
 */

public abstract class DataRepository<Data extends IData> implements DataEntry<Data> {

    private final DataEntry<Data> remote;
    private final DataEntry<Data> disk;

    private Map<String, LinkedHashMap<String, Data>> mCache = new HashMap<>();

    private boolean isRefreshCache = true;

    protected DataRepository(DataEntry<Data> remote, DataEntry<Data> disk) {
        this.remote = remote;
        this.disk = disk;
    }

    @Override
    public void add(Data data) {
        checkNotNull(data);

        remote.add(data);
        disk.add(data);
    }

    @Override
    public void batchAdd(List<Data> dataSet) {
        checkNotNull(dataSet);

        remote.batchAdd(dataSet);
        disk.batchAdd(dataSet);
    }

    @Override
    public void delete(Data data) {
        checkNotNull(data);

        remote.delete(data);
        disk.delete(data);
    }

    @Override
    public void batchDelete(List<Data> dataSet) {
        checkNotNull(dataSet);

        remote.batchDelete(dataSet);
        disk.batchDelete(dataSet);
    }

    @Override
    public void update(String id, Data newData) {
        checkNotNull(newData);

        remote.update(id, newData);
        disk.update(id, newData);
    }

    @Override
    public void load(Filter filter, LoadDataCallback<Data> callback) {
        checkNotNull(filter);
        checkNotNull(callback);

        if (isPriorityRemote()) {
            loadFromRemoteEntry(filter, callback);
        } else {
            loadFromDiskEntry(filter, callback);
        }
    }

    @Override
    public void refreshCache(boolean refresh) {
        isRefreshCache = refresh;
    }

    @Override
    public boolean isPriorityRemote() {
        return false;
    }

    private void loadFromRemoteEntry(final Filter filter, final LoadDataCallback<Data> callback) {
        if (isRefreshCache) {
            mCache.clear();

            remote.load(filter, new LoadDataCallback<Data>() {
                @Override
                public void onLoaded(List<Data> result) {
                    callback.onLoaded(result);

                    isRefreshCache = false;
                    cacheData(filter, result);
                }

                @Override
                public void onFailure(int errorCode) {
                    callback.onFailure(errorCode);
                }
            });
        } else {
            loadFromCache(filter, callback);
        }
    }

    private void loadFromDiskEntry(final Filter filter, final LoadDataCallback<Data> callback) {
        if (isRefreshCache) {
            mCache.clear();

            disk.load(filter, new LoadDataCallback<Data>() {
                @Override
                public void onLoaded(List<Data> result) {
                    callback.onLoaded(result);

                    isRefreshCache = false;
                    cacheData(filter, result);
                }

                @Override
                public void onFailure(int errorCode) {
                    callback.onFailure(errorCode);
                }
            });
        } else {
            loadFromCache(filter, callback);
        }
    }

    private void loadFromCache(Filter filter, LoadDataCallback<Data> callback) {
        LinkedHashMap<String, Data> linked = mCache.get(filter.getOperator());
        if (linked == null || linked.isEmpty()) {
            callback.onFailure(LoadDataCallback.ERROR_NO_DATA);
        } else {
            List<Data> result = new ArrayList<>();
            for (Map.Entry<String, Data> entry:  linked.entrySet()) {
                result.add(entry.getValue());
            }
            callback.onLoaded(result);
        }
    }

    private void cacheData(Filter filter, List<Data> dataSet) {
        LinkedHashMap<String, Data> linked = new LinkedHashMap<>();

        for (Data data: dataSet) {
            linked.put(data.getUniqueId(), data);
        }

        mCache.put(filter.getOperator(), linked);
    }
}
