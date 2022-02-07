package im.conversations.compliance.xmpp.tests;

import java.util.Map;
import rocks.xmpp.core.session.XmppClient;
import rocks.xmpp.core.stream.client.StreamFeaturesManager;
import rocks.xmpp.core.stream.model.StreamFeature;

public abstract class AbstractStreamFeatureTest extends AbstractTest {

    public AbstractStreamFeatureTest(XmppClient client) {
        super(client);
    }

    @Override
    public boolean run() {
        Map<Class<? extends StreamFeature>, StreamFeature> features =
                client.getManager(StreamFeaturesManager.class).getFeatures();
        return features.containsKey(getStreamFeature());
    }

    abstract Class<? extends StreamFeature> getStreamFeature();
}
