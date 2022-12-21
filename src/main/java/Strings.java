public class Strings {

    public static String first(String... values) {
        for(String value: values) {
            if(value!=null && value.trim().length()>0) {
                return value;
            }
        }
        return null;
    }
}
