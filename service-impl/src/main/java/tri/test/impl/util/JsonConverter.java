package tri.test.impl.util;

import com.google.gson.Gson;

public class JsonConverter {
    private static final Gson gson = new Gson();

    private JsonConverter() {
    }

    public static String toJson(Object o) {
        return gson.toJson(o);
    }

    public static <T> T fromJson(String json, Class<T> classOfT) {
        return gson.fromJson(json, classOfT);
    }
}
