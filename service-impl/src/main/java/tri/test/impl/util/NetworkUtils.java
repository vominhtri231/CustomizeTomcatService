package tri.test.impl.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.Enumeration;

public class NetworkUtils {

    private NetworkUtils() {
    }

    public static String getNetworkAddress() {
        try {
            final Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
            for (NetworkInterface netInterface : Collections.list(nets)) {
                final Enumeration<InetAddress> inetAddresses = netInterface.getInetAddresses();
                for (InetAddress inetAddress : Collections.list(inetAddresses)) {
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.toString();
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        throw new IllegalStateException("Cannot get instance IP address");
    }
}
