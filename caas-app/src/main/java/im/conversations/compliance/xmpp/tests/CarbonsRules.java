package im.conversations.compliance.xmpp.tests;

import im.conversations.compliance.annotations.ComplianceTest;
import java.util.Collections;
import java.util.List;
import rocks.xmpp.core.session.XmppClient;

@ComplianceTest(
        short_name = "xep0280_rules",
        full_name = "XEP-0280: Message Carbons - Recommended Rules",
        url = "https://xmpp.org/extensions/xep-0280.html#recommended-rules",
        description = "Modern rules for which messages should be carbon-copied",
        informational = true)
public class CarbonsRules extends AbstractDiscoTest {

    public CarbonsRules(XmppClient client) {
        super(client);
    }

    @Override
    List<String> getNamespaces() {
        return Collections.singletonList("urn:xmpp:carbons:rules:0");
    }

    @Override
    boolean checkOnServer() {
        return true;
    }
}
