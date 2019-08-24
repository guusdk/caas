package im.conversations.compliance.xmpp.tests;

import com.google.gson.GsonBuilder;
import im.conversations.compliance.annotations.ComplianceTest;
import im.conversations.compliance.xrd.ExtensibleResourceDescriptor;
import im.conversations.compliance.xrd.Link;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rocks.xmpp.core.session.XmppClient;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Arrays;
import java.util.List;


@ComplianceTest(
        short_name = "xep0156",
        informational = true,
        full_name = "XEP-0156: Discovering Alternative XMPP Connection Methods (HTTP)",
        url = "https://xmpp.org/extensions/xep-0156.html",
        description = "Allows web clients to discover connections methods"
)
public class AlternateConnections extends AbstractTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(AlternateConnections.class);

    private static final GsonBuilder GSON_BUILDER = new GsonBuilder();
    private static final List<String> rels = Arrays.asList("urn:xmpp:alt-connections:xbosh", "urn:xmpp:alt-connections:websocket");

    public AlternateConnections(XmppClient client) {
        super(client);
    }

    private static boolean discoverAndTestAltConnections(final String domain) {
        return discoverAndTestAltConnections(domain, false, true)
                || discoverAndTestAltConnections(domain, true, true)
                || discoverAndTestAltConnections(domain, false, false)
                || discoverAndTestAltConnections(domain, true, false);
    }

    private static boolean discoverAndTestAltConnections(final String domain, final boolean json, final boolean https) {
        try {
            final URL url = new URL(https ? "https" : "http", domain, "/.well-known/host-meta" + (json ? ".json" : ""));
            LOGGER.debug(String.format("checking on %s ",url.toString()));
            final URLConnection connection = url.openConnection();
            connection.setConnectTimeout(2000);
            connection.setReadTimeout(5000);
            if (!"*".equals(connection.getHeaderField("Access-Control-Allow-Origin"))) {
                return false;
            }
            try (final InputStream is = connection.getInputStream()) {
                return json ? testAltConnectionsFromJson(is) : testAltConnectionsFromXml(is);
            }
        } catch (Throwable throwable) {
            return false;
        }
    }

    private static boolean testAltConnectionsFromXml(InputStream is) throws Throwable {
        final JAXBContext jaxbContext = JAXBContext.newInstance(ExtensibleResourceDescriptor.class, Link.class);
        final Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        final ExtensibleResourceDescriptor xdr = (ExtensibleResourceDescriptor) unmarshaller.unmarshal(is);
        final List<Link> links = xdr.getLinks(rels);
        return checkConnections(links);
    }

    private static boolean testAltConnectionsFromJson(InputStream is) {
        final ExtensibleResourceDescriptor xdr = GSON_BUILDER.create().fromJson(new InputStreamReader(is), ExtensibleResourceDescriptor.class);
        final List<Link> links = xdr.getLinks(rels);
        return checkConnections(links);
    }

    private static boolean checkConnections(List<Link> links) {
        for (Link link : links) {
            if (!checkConnection(link.getHref())) {
                return false;
            }
        }
        return links.size() > 0;
    }

    private static boolean checkConnection(URI uri) {
        int port = uri.getPort();
        if (port < 0) {
            if (Arrays.asList("wss", "https").contains(uri.getScheme())) {
                port = 443;
            } else if (Arrays.asList("ws", "http").contains(uri.getScheme())) {
                port = 80;
            }
        }
        return checkConnection(uri.getHost(), port);
    }

    private static boolean checkConnection(String host, int port) {
        LOGGER.debug(String.format("checking connection to %s:%d", host, port));
        try (final Socket socket = new Socket()) {
            socket.setSoTimeout(2000);
            socket.connect(new InetSocketAddress(host, port));
            return socket.isConnected();
        } catch (Throwable t) {
            return false;
        }
    }

    @Override
    public boolean run() {
        return discoverAndTestAltConnections(client.getDomain().toString());
    }
}
