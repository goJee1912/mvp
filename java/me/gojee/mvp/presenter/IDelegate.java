package me.gojee.mvp.presenter;

/**
 * @author goJee
 * @since 2018/4/3
 */

public interface IDelegate<Presenter extends IPresenter> {

    void contract(Presenter presenter);

    boolean isActive();
}
