/**
 * Created by 沈毅 on 2019/4/3.
 */
public class CounterSampleDO extends SampleDO {
    private String dateStr;
    private String ifIndex;
    private String ifInOctets;
    private String ifInUcastPkts;
    private String ifInMulticastPkts;
    private String ifOutOctets;
    private String ifOutUcastPkts;
    private String ifOutMulticastPkts;


    public CounterSampleDO() {
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public String getIfIndex() {
        return ifIndex;
    }

    public void setIfIndex(String ifIndex) {
        this.ifIndex = ifIndex;
    }

    public String getIfInOctets() {
        return ifInOctets;
    }

    public void setIfInOctets(String ifInOctets) {
        this.ifInOctets = ifInOctets;
    }

    public String getIfInUcastPkts() {
        return ifInUcastPkts;
    }

    public void setIfInUcastPkts(String ifInUcastPkts) {
        this.ifInUcastPkts = ifInUcastPkts;
    }

    public String getIfInMulticastPkts() {
        return ifInMulticastPkts;
    }

    public void setIfInMulticastPkts(String ifInMulticastPkts) {
        this.ifInMulticastPkts = ifInMulticastPkts;
    }

    public String getIfOutOctets() {
        return ifOutOctets;
    }

    public void setIfOutOctets(String ifOutOctets) {
        this.ifOutOctets = ifOutOctets;
    }

    public String getIfOutUcastPkts() {
        return ifOutUcastPkts;
    }

    public void setIfOutUcastPkts(String ifOutUcastPkts) {
        this.ifOutUcastPkts = ifOutUcastPkts;
    }

    public String getIfOutMulticastPkts() {
        return ifOutMulticastPkts;
    }

    public void setIfOutMulticastPkts(String ifOutMulticastPkts) {
        this.ifOutMulticastPkts = ifOutMulticastPkts;
    }

    @Override
    public String toString() {
        return "CounterSampleDO{" +
                "dateStr='" + dateStr + '\'' +
                ", ifIndex='" + ifIndex + '\'' +
                ", ifInOctets='" + ifInOctets + '\'' +
                ", ifInUcastPkts='" + ifInUcastPkts + '\'' +
                ", ifInMulticastPkts='" + ifInMulticastPkts + '\'' +
                ", ifOutOctets='" + ifOutOctets + '\'' +
                ", ifOutUcastPkts='" + ifOutUcastPkts + '\'' +
                ", ifOutMulticastPkts='" + ifOutMulticastPkts + '\'' +
                '}';
    }
}
