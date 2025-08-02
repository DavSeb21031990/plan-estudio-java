package features;

public class PatternMatchingDemo {

    public static void main(String[] args) {
        //
    }

    public static String processObject(Object obj) {
        return switch (obj) {
            case String str -> "String: " + str;
            case Integer num -> "Integer: " + num;
            case Boolean bool -> "Boolean: " + bool;
            default -> "No encontró ninguna coincidencia";
        };
    }

    public static boolean isValidEmail(Object email) {
        return switch (email) {
            case String str when str.contains("@") && str.length() > 5 -> true;
            default -> false;
        };
    }

}
