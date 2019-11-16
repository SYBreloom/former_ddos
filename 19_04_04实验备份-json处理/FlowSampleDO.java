/**
 * Created by 沈毅 on 2019/4/3.
 */
public class FlowSampleDO extends SampleDO{
    private String dateStr;
    private String sampledPacketSize;
    private String srcMAC;
    private String dstMAC;
    private String srcIP;
    private String dstIP;
    private String UDPSrcPort;
    private String UDPDstPort;
    private String UDPBytes;
    private String TCPSrcPort;
    private String TCPDstPort;
    private String TCPBytes;

    public FlowSampleDO() {
    }

    public FlowSampleDO(String sampledPacketSize, String srcMAC, String dstMAC, String srcIP, String dstIP,
                        String UDPSrcPort, String UDPDstPort, String UDPBytes, String TCPSrcPort, String TCPDstPort,
                        String TCPBytes) {
        this.sampledPacketSize = sampledPacketSize;
        this.srcMAC = srcMAC;
        this.dstMAC = dstMAC;
        this.srcIP = srcIP;
        this.dstIP = dstIP;
        this.UDPSrcPort = UDPSrcPort;
        this.UDPDstPort = UDPDstPort;
        this.UDPBytes = UDPBytes;
        this.TCPSrcPort = TCPSrcPort;
        this.TCPDstPort = TCPDstPort;
        this.TCPBytes = TCPBytes;
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public String getSampledPacketSize() {
        return sampledPacketSize;
    }

    public void setSampledPacketSize(String sampledPacketSize) {
        this.sampledPacketSize = sampledPacketSize;
    }

    public String getSrcMAC() {
        return srcMAC;
    }

    public void setSrcMAC(String srcMAC) {
        this.srcMAC = srcMAC;
    }

    public String getDstMAC() {
        return dstMAC;
    }

    public void setDstMAC(String dstMAC) {
        this.dstMAC = dstMAC;
    }

    public String getSrcIP() {
        return srcIP;
    }

    public void setSrcIP(String srcIP) {
        this.srcIP = srcIP;
    }

    public String getDstIP() {
        return dstIP;
    }

    public void setDstIP(String dstIP) {
        this.dstIP = dstIP;
    }

    public String getUDPSrcPort() {
        return UDPSrcPort;
    }

    public void setUDPSrcPort(String UDPSrcPort) {
        this.UDPSrcPort = UDPSrcPort;
    }

    public String getUDPDstPort() {
        return UDPDstPort;
    }

    public void setUDPDstPort(String UDPDstPort) {
        this.UDPDstPort = UDPDstPort;
    }

    public String getUDPBytes() {
        return UDPBytes;
    }

    public void setUDPBytes(String UDPBytes) {
        this.UDPBytes = UDPBytes;
    }

    public String getTCPSrcPort() {
        return TCPSrcPort;
    }

    public void setTCPSrcPort(String TCPSrcPort) {
        this.TCPSrcPort = TCPSrcPort;
    }

    public String getTCPDstPort() {
        return TCPDstPort;
    }

    public void setTCPDstPort(String TCPDstPort) {
        this.TCPDstPort = TCPDstPort;
    }

    public String getTCPBytes() {
        return TCPBytes;
    }

    public void setTCPBytes(String TCPBytes) {
        this.TCPBytes = TCPBytes;
    }

    @Override
    public String toString() {
        return "FlowSampleDO{" +
                "dateStr='" + dateStr + '\'' +
                ", sampledPacketSize='" + sampledPacketSize + '\'' +
                ", srcMAC='" + srcMAC + '\'' +
                ", dstMAC='" + dstMAC + '\'' +
                ", srcIP='" + srcIP + '\'' +
                ", dstIP='" + dstIP + '\'' +
                ", UDPSrcPort='" + UDPSrcPort + '\'' +
                ", UDPDstPort='" + UDPDstPort + '\'' +
                ", UDPBytes='" + UDPBytes + '\'' +
                ", TCPSrcPort='" + TCPSrcPort + '\'' +
                ", TCPDstPort='" + TCPDstPort + '\'' +
                ", TCPBytes='" + TCPBytes + '\'' +
                '}';
    }
}
