package xyz.company.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "token_info")
public class TokenInfo {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
    @Column(name = "decimals")
    private String decimals;
    @Column(name = "name")
    private String name;
    @Column(name = "symbol")
    private String symbol;

    @Column(name = "address", unique = true)
    private String address;

    public TokenInfo() {
    }

    private TokenInfo(Builder builder) {
        setDecimals(builder.decimals);
        setName(builder.name);
        setSymbol(builder.symbol);
        setAddress(builder.address);
    }

    public static Builder builder() {
        return new Builder();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

        public TokenInfo build() {
            return new TokenInfo(this);
        }
    }
}
