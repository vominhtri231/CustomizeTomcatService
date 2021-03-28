package tri.test.config;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class SpiUtils {
    private SpiUtils() {
    }

    public static <T> T lookupServiceComponent(final String name, Class<T> serviceType) {
        try {
            final Context ctx = new InitialContext();
            final String fullName = "java:comp/env/" + name;
            return serviceType.cast(ctx.lookup(fullName));
        } catch (NamingException e1) {
            throw new IllegalStateException("Failed to lookup service " + name, e1);
        }
    }
}
