package com.w7.sweatlog_backend.entity.enums;

public enum SearchMode {
    AUTOCOMPLETE,       // PRIMARY prefix (인덱스 타기 쉬움)
    PRIMARY_CONTAINS,   // PRIMARY contains
    EXTENDED_CONTAINS   // PRIMARY or SECONDARY contains
}