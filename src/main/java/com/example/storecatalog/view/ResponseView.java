package com.example.storecatalog.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ResponseView<T> {
    @JsonProperty
    private T message;
    @JsonProperty
    private int status;

    public ResponseView(T message) {
        this.message = message;
        this.status = HttpStatus.OK.value();
    }

    public ResponseView(T message, int status) {
        this.message = message;
        this.status = status;
    }
}
