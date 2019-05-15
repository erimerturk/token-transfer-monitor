package xyz.company.dto;

import java.io.Serializable;

public class TokenInfoResponse implements Serializable {

    private String decimals;
    private String name;
    private String symbol;
    private String address;

    public TokenInfoResponse() {
    }

    private TokenInfoResponse(Builder builder) {
        setDecimals(builder.decimals);
        setName(builder.name);
        setSymbol(builder.symbol);
        setAddress(builder.address);
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getDecimals() {
        return decimals;
    }

    public void setDecimals(String decimals) {
        this.decimals = decimals;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public static final class Builder {
        private String decimals;
        private String name;
        private String symbol;
        private String address;

        private Builder() {
        }

        public Builder decimals(String decimals) {
            this.decimals = decimals;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder symbol(String symbol) {
            this.symbol = symbol;
            return this;
        }

        public Builder address(String address) {
            this.address = address;
            return this;
        }

        public TokenInfoResponse build() {
            return new TokenInfoResponse(this);
        }
    }
}
