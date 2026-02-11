package com.more.app.base.ui.product;

public class SwiftValidator {

    public static boolean bic(String bic) {
        return bic != null &&
               bic.matches("^[A-Z]{4}[A-Z]{2}[A-Z0-9]{2}([A-Z0-9]{3})?$");
    }

    public static boolean country(String code) {
        return code != null &&
               code.matches("^[A-Z]{2}$");
    }

    public static boolean swiftText(String text) {
        return text == null ||
               text.matches("^[A-Z0-9/\\-?:().,'+ ]*$");
    }
}
