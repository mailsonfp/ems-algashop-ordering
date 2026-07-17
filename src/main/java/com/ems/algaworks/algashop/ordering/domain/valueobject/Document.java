package com.ems.algaworks.algashop.ordering.domain.valueobject;

import java.util.Objects;

public record Document(String document){
    public Document(String document) {
        Objects.requireNonNull(document);
        if (document.isBlank()) {
            throw new IllegalArgumentException("Document ID cannot be blank.");
        }
        this.document = document;
    }

    @Override
    public String toString() {
        return document;
    }
}
