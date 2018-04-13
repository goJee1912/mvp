package me.gojee.mvp.data;

/**
 * @author goJee
 * @since 2018/3/29
 */

public interface IData {

    String getUniqueId();

    void generateId();

    boolean isGeneratedId();
}
