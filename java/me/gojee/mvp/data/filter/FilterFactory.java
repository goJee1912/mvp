package me.gojee.mvp.data.filter;

/**
 * @author goJee
 * @since 2018/4/3
 */

public class FilterFactory {

    public static Filter createFilter(final String operator) {
        return new Filter() {
            @Override
            public String getOperator() {
                return operator;
            }
        };
    }
}
