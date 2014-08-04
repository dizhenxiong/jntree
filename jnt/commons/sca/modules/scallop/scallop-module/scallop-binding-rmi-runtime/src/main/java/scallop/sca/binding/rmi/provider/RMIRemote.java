package scallop.sca.binding.rmi.provider;

import java.rmi.Remote;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 *
 * @author songkun1
 * @version 2.0
 */
public class RMIRemote implements Comparable<RMIRemote> {

    private String host;
    private String port;

    public RMIRemote(String host, String port) {
        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    /**
     * @see Object#equals(Object)
     */
    public boolean equals(Object object) {
        if (!(object instanceof RMIRemote)) {
            return false;
        }
        RMIRemote rhs = (RMIRemote) object;
        return new EqualsBuilder().append(this.port, rhs.port).append(this.host,
                rhs.host).isEquals();
    }

    /**
     * @see Object#hashCode()
     */
    public int hashCode() {
        return new HashCodeBuilder(2072921821, 1883966355).append(this.port).append(
                this.host).toHashCode();
    }

    @Override
    public int compareTo(RMIRemote o) {
        return new CompareToBuilder().append(this.port, o.port).append(this.host, o.host).toComparison();
    }
}
