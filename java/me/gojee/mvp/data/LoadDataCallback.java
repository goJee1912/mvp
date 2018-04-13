package me.gojee.mvp.data;

import java.util.List;

/**
 * @author goJee
 * @since 2018/4/3
 */

public interface LoadDataCallback<Data extends IData> {

    int ERROR_NO_DATA = 1;

    void onLoaded(List<Data> result);

    void onFailure(int errorCode);
}
