package com.example.demo.enums;

public enum PropertyKey {

    PROPERTIES_NICKNAME("propertes.nickname"),
    PROPERTIES_PROFILE_IMAGE("properties.profile_image"),
    PROPERTIES_THUMBNAIL_IMAGE("properties.thumbnail_image"),
    KAKAO_ACCOUNT_PROFILE("kakao_account.profile"),
    KAKAO_ACCOUNT_EMAIL("kakao_account.email"),
    KAKAO_ACCOUNT_AGE_RANGE("kakao_account.age_range"),
    KAKAO_ACCOUNT_BIRTHDAY("kakao_account.birthday"),
    KAKAO_ACCOUNT_GENDER("kakao_account.gender");

    private final String property;

    PropertyKey(String property) {
        this.property = property;
    }

    public String value() {
        return this.property;
    }

    public String convertToString(String text) {
        for (PropertyKey propertyKey : PropertyKey.values()) {
            if (propertyKey.value().equals(text)) {
                return propertyKey.value();
            }
        }
        return null;
    }
}
