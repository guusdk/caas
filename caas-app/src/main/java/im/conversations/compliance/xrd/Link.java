package im.conversations.compliance.xrd;

import java.net.URI;
import java.util.StringJoiner;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Link")
public class Link {

    @XmlAttribute private String rel;
    @XmlAttribute private URI href;

    private Link() {}

    public String getRel() {
        return rel;
    }

    public URI getHref() {
        return href;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Link.class.getSimpleName() + "[", "]")
                .add("rel='" + rel + "'")
                .add("href=" + href)
                .toString();
    }
}
