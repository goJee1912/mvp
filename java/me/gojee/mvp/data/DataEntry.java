package me.gojee.mvp.data;

import java.util.List;

import me.gojee.mvp.data.filter.Filter;

/**
 * @author goJee
 * @since 2018/3/29
 */

public interface DataEntry<Data extends IData> {

    void add(Data data);

    void batchAdd(List<Data> dataSet);

    void delete(Data data);

    void batchDelete(List<Data> dataSet);

    void update(String id, Data newData);

    void load(Filter filter, LoadDataCallback<Data> callback);

    void refreshCache(boolean refresh);

    boolean isPriorityRemote();

}
