package im.conversations.compliance.xmpp.tests;

import im.conversations.compliance.annotations.ComplianceTest;
import java.util.Arrays;
import java.util.List;
import rocks.xmpp.core.session.XmppClient;

@ComplianceTest(
        short_name = "xep0313extended",
        full_name = "XEP-0313: Message Archive Management (extended usage)",
        url = "https://xmpp.org/extensions/xep-0313.html",
        description =
                "Provides extended query support for MAM including before-id, after-id, and"
                        + " archive metadata.",
        informational = true)
public class MamExtended extends AbstractDiscoTest {

    public static final List<String> NAMESPACES = Arrays.asList("urn:xmpp:mam:2#extended");

    public MamExtended(XmppClient client) {
        super(client);
    }

    @Override
    List<String> getNamespaces() {
        return NAMESPACES;
    }

    @Override
    boolean checkOnServer() {
        return false;
    }
}
