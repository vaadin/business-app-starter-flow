package com.vaadin.starter.business.it;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;

import com.vaadin.testbench.Parameters;
import com.vaadin.testbench.ScreenshotOnFailureRule;
import com.vaadin.testbench.TestBench;
import com.vaadin.testbench.parallel.ParallelTest;

public abstract class AbstractIT extends ParallelTest {

    private static final int SERVER_PORT = 8080;
    private final String route;
    private final By rootSelector;

    public AbstractIT() {
        this("", By.tagName("body"));
    }

    protected AbstractIT(String route, By rootSelector) {
        this.route = route;
        this.rootSelector = rootSelector;
    }

    @Rule
    public ScreenshotOnFailureRule rule = new ScreenshotOnFailureRule(this,
            true);

    @Before
    public void setup() throws Exception {
        if (isUsingHub()) {
            super.setup();
        } else {
            setDriver(TestBench.createDriver(new ChromeDriver()));
        }
        getDriver().get(getURL(route));
    }

    /**
     * Returns whether we are using a test hub. This means that the starter
     * is running tests in Vaadin's CI environment, and uses TestBench to
     * connect to the testing hub.
     *
     * @return whether we are using a test hub
     */
    private static boolean isUsingHub() {
        return Boolean.TRUE.toString().equals(
                System.getProperty(USE_HUB_PROPERTY));
    }

    /**
     * Property set to true when running on a test hub.
     */
    private static final String USE_HUB_PROPERTY = "test.use.hub";

    /**
     * Returns deployment host name concatenated with route.
     *
     * @return URL to route
     */
    private static String getURL(String route) {
        return String.format("http://%s:%s/%s", getDeploymentHostname(),
                SERVER_PORT, route);
    }

    /**
     * If running on CI, get the host name from environment variable HOSTNAME
     *
     * @return the host name
     */
    private static String getDeploymentHostname() {
        return isUsingHub() ? System.getenv("HOSTNAME") : "localhost";
    }

    protected void assertNumbers(String expected, String actual) {
        // Remove any thousands and decimal separators before comparing
        Assert.assertEquals(stripSeparators(expected), stripSeparators(actual));
    }

    protected String getPort() {
        return "8080";
    }

    protected String getBaseURL() {
        return "http://" + getCurrentHostAddress() + ":" + getPort();
    }

    private static Optional<String> getHostAddress(
            NetworkInterface nwInterface) {
        Enumeration<InetAddress> addresses = nwInterface.getInetAddresses();
        while (addresses.hasMoreElements()) {
            InetAddress address = addresses.nextElement();
            if (address.isLoopbackAddress()) {
                continue;
            }
            if (address.isSiteLocalAddress()) {
                return Optional.of(address.getHostAddress());
            }
        }
        return Optional.empty();
    }

    protected String getCurrentHostAddress() {
        if (getRunOnHub(getClass()) == null
                && Parameters.getHubHostname() == null) {
            return "localhost";
        }
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface
                    .getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface nwInterface = interfaces.nextElement();
                if (!nwInterface.isUp() || nwInterface.isLoopback()
                        || nwInterface.isVirtual()) {
                    continue;
                }
                Optional<String> address = getHostAddress(nwInterface);
                if (address.isPresent()) {
                    return address.get();
                }
            }
        } catch (SocketException e) {
            throw new RuntimeException("Could not find the host name", e);
        }
        throw new RuntimeException(
                "No compatible (10.0.0.0/8, 172.16.0.0/12, 192.168.0.0/16) ip address found.");
    }

    private String stripSeparators(String string) {
        return string.replaceAll("[\\., ]", "").replace((char) 160, ' ');
    }

}
