package uz.likwer.cinema.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public class Utils {
    public static String prettyDateTime(LocalDateTime dateTime) {
        String format = "yyyy.MM.dd HH:mm";
        return dateTime.format(DateTimeFormatter.ofPattern(format));
    }

    public static void main(String[] args) {
        System.out.println((5+1)%5==3);
    }
    private static final Pattern UUID_PATTERN = Pattern.compile(
            "[0-9a-f]{8}-[0-9a-f]{4}-4[0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}",
            Pattern.CASE_INSENSITIVE
    );

    public static boolean isUUIDv4(String uuidString) {
        return UUID_PATTERN.matcher(uuidString).matches();
    }
}
