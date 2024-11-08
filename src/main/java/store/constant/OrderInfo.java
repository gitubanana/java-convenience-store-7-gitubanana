package store.constant;

import java.util.regex.Pattern;

public class OrderInfo {
    public static final String ORDER_DELIMITER = ",";
    public static final Pattern ORDER_PATTERN = Pattern.compile("\\[(.+)-(\\d)]");
    public static final int PRODUCT_NAME_GROUP = 1;
    public static final int PRODUCT_COUNT_GROUP = 2;
}
