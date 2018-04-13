package me.gojee.mvp.presenter;

/**
 * @author goJee
 * @since 2018/4/3
 */

public interface IPresenter<Delegate extends IDelegate> {

    void contract(Delegate delegate);

    void start();
}
