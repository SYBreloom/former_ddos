/*
 * Copyright © 2017 sy and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

/**
 * Created by 沈毅 on 2019/4/1.
 */
public class PacketInDO {
    private String dateStr;
    private String swName;
    private double packetLength;
    private String srcMac;
    private String dstMac;
    private String srcIp;
    private String dstIP;
    private int portSrc;
    private int portDst;
    private String ipProtocol;
    private String packetInReason;

    public PacketInDO(){

    }

    public PacketInDO(String dateStr, String swName, double packetLength, String srcMac, String dstMac, String srcIp, String dstIP,
                      int portSrc, int portDst, String ipProtocol, String packetInReason) {
        this.dateStr = dateStr;
        this.swName = swName;
        this.packetLength = packetLength;
        this.srcMac = srcMac;
        this.dstMac = dstMac;
        this.srcIp = srcIp;
        this.dstIP = dstIP;
        this.portSrc = portSrc;
        this.portDst = portDst;
        this.ipProtocol = ipProtocol;
        this.packetInReason = packetInReason;
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public String getSwName() {
        return swName;
    }

    public void setSwName(String swName) {
        this.swName = swName;
    }

    public double getPacketLength() {
        return packetLength;
    }

    public void setPacketLength(double packetLength) {
        this.packetLength = packetLength;
    }

    public String getSrcMac() {
        return srcMac;
    }

    public void setSrcMac(String srcMac) {
        this.srcMac = srcMac;
    }

    public String getDstMac() {
        return dstMac;
    }

    public void setDstMac(String dstMac) {
        this.dstMac = dstMac;
    }

    public String getSrcIp() {
        return srcIp;
    }

    public void setSrcIp(String srcIp) {
        this.srcIp = srcIp;
    }

    public String getDstIP() {
        return dstIP;
    }

    public void setDstIP(String dstIP) {
        this.dstIP = dstIP;
    }

    public int getPortSrc() {
        return portSrc;
    }

    public void setPortSrc(int portSrc) {
        this.portSrc = portSrc;
    }

    public int getPortDst() {
        return portDst;
    }

    public void setPortDst(int portDst) {
        this.portDst = portDst;
    }

    public String getIpProtocol() {
        return ipProtocol;
    }

    public void setIpProtocol(String ipProtocol) {
        this.ipProtocol = ipProtocol;
    }

    public String getPacketInReason() {
        return packetInReason;
    }

    public void setPacketInReason(String packetInReason) {
        this.packetInReason = packetInReason;
    }

    @Override
    public String toString() {
        return "PacketInDO{" +
                "dateStr='" + dateStr + '\'' +
                ", swName='" + swName + '\'' +
                ", packetLength=" + packetLength +
                ", srcMac='" + srcMac + '\'' +
                ", dstMac='" + dstMac + '\'' +
                ", srcIp='" + srcIp + '\'' +
                ", dstIP='" + dstIP + '\'' +
                ", portSrc=" + portSrc +
                ", portDst=" + portDst +
                ", ipProtocol='" + ipProtocol + '\'' +
                ", packetInReason='" + packetInReason + '\'' +
                '}';
    }
}
