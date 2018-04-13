package me.gojee.mvp.presenter;

/**
 * @author goJee
 * @since 2018/4/3
 */

public class Contract {

    public static
    <Presenter extends IPresenter<Delegate>, Delegate extends IDelegate<Presenter>> void
    contract(Presenter presenter, Delegate delegate) {
        presenter.contract(delegate);
        delegate.contract(presenter);
    }
}
