package me.rothes.jsonconverter;

public class EnLocale {

    public enum LocaleType {
        LANG_KEY,
        STRING
    }

    private String value;
    private LocaleType type;

    public EnLocale(String value, LocaleType type) {
        this.value = value;
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public LocaleType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "EnLocale{" +
                "value='" + value + '\'' +
                ", type=" + type +
                '}';
    }

}
