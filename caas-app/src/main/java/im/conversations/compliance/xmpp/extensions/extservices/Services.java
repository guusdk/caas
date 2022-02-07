package im.conversations.compliance.xmpp.extensions.extservices;

import java.util.Collections;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "services")
@XmlAccessorType(XmlAccessType.FIELD)
public class Services {
    public static final String NAMESPACE = "urn:xmpp:extdisco:2";

    @XmlElement(name = "service")
    private List<Service> services;

    public List<Service> getServices() {
        return services == null ? Collections.emptyList() : services;
    }
}
