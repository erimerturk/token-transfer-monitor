package xyz.company.dto;

import java.io.Serializable;

public class Notification implements Serializable {

    private String address;
    private String name;
    private String value;

    public Notification() {
    }

    private Notification(Builder builder) {
        setAddress(builder.address);
        setName(builder.name);
        setValue(builder.value);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    public static final class Builder {
        private String address;
        private String name;
        private String value;

        private Builder() {
        }

        public Builder address(String address) {
            this.address = address;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder value(String value) {
            this.value = value;
            return this;
        }

        public Notification build() {
            return new Notification(this);
        }
    }
}
