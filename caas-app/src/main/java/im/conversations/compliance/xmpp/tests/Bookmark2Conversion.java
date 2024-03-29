package im.conversations.compliance.xmpp.tests;

import im.conversations.compliance.annotations.ComplianceTest;
import java.util.Arrays;
import java.util.List;
import rocks.xmpp.core.session.XmppClient;

@ComplianceTest(
        short_name = "xep0402",
        full_name = "XEP-0402: PEP Native Bookmarks",
        url = "https://xmpp.org/extensions/xep-0402.html",
        description = "Converts between PrivateXML, legacy PEP, and native PEP bookmarks",
        informational = true)
public class Bookmark2Conversion extends AbstractDiscoTest {

    public Bookmark2Conversion(XmppClient client) {
        super(client);
    }

    @Override
    List<String> getNamespaces() {
        return Arrays.asList("urn:xmpp:bookmarks:1#compat", "urn:xmpp:bookmarks:1#compat-pep");
    }

    @Override
    boolean checkOnServer() {
        return false;
    }
}
