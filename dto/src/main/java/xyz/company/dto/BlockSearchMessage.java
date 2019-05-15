package xyz.company.dto;

import java.io.Serializable;

public class BlockSearchMessage implements Serializable {
    private String start;
    private String end;

    public BlockSearchMessage() {
    }

    private BlockSearchMessage(Builder builder) {
        setStart(builder.start);
        setEnd(builder.end);
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }


    public static final class Builder {
        private String start;
        private String end;

        private Builder() {
        }

        public Builder start(String start) {
            this.start = start;
            return this;
        }

        public Builder end(String end) {
            this.end = end;
            return this;
        }

        public BlockSearchMessage build() {
            return new BlockSearchMessage(this);
        }
    }
}
