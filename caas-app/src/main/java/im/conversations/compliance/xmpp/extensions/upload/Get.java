package im.conversations.compliance.xmpp.extensions.upload;

import java.net.URL;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "get")
public class Get {

    @XmlAttribute private URL url;
}
