package tiramisudelemon.restaurand.app.restaurants;


public enum RRType {

    EMPTY("empty"),
    DEFAULT("default");

    private final String mValue;

    RRType(String value) {
        this.mValue = value;
    }

    public static RRType forValue(String value) {
        for (RRType type : RRType.values()) {
            if (type.mValue.equalsIgnoreCase(value)) {
                return type;
            }
        }
        // return at least default
        return DEFAULT;
    }

    public String getValue() {
        return mValue;
    }
}
