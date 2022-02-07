package im.conversations.compliance.xmpp.extensions.upload;

import java.net.URL;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "put")
public class Put {

    @XmlAttribute private URL url;

    public URL getUrl() {
        return url;
    }
}
