package com.codechallenge.employeeapi.validation.uuid;

import java.util.UUID;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
public final class UuidValidation {
    private UuidValidation() {
    }

    public static boolean isValidUUID(UUID id) {
        String uuidRegex = "^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$";
        Pattern pattern = Pattern.compile(uuidRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(id.toString());
        return matcher.matches();
    }
}
