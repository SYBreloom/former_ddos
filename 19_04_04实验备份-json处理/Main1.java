import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;


//作为先行版 已经作废 在sflowtoolJasonHandler里面对几个步骤进行了封装

/**
 * Created by 沈毅 on 2019/3/13.
 */
public class Main1 {
    public static void main(String args[]) {

        BufferedReader bufferedReader = null;


        boolean isEnd = false;
        try {
            File file = new File("F:\\近期文件\\sflowtooltmp.txt");
            bufferedReader = new BufferedReader(new FileReader(file));
            while (!isEnd){
                ArrayList<String> stringArrayList =new ArrayList<>();
                ArrayList<SflowToolJason> sflowToolJasons = new ArrayList<SflowToolJason>();
                for (int i=0;i<=4;i++){ //读最多5行数据 存入arrayList
                    String line = bufferedReader.readLine();
                    if (line!=null){
                        stringArrayList.add(line);
                        //System.out.println(line);
                        SflowToolJason sflowToolJason = new SflowToolJason(line);
                        System.out.println(sflowToolJason.generateCounterSampleDO("19").toString());
                        sflowToolJasons.add(sflowToolJason);

                    }
                    else {
                        isEnd = true;
                    }
                }
                //流采样的
                HashMap<String, Integer> srcIpList = new HashMap<>();
                HashMap<String, Integer> dstIpList = new HashMap<>();
                HashMap<String, Integer> srcPortList = new HashMap<>();
                HashMap<String, Integer> dstPortList = new HashMap<>();
                ArrayList<FlowSampleDO> flowSampleDOs = new ArrayList<>();
                ArrayList<CounterSampleDO> counterSampleDOS = new ArrayList<>();
                // 统计采样的字节、包等统计信息
                long ifInOctets = 0, ifInUcastPkts = 0, ifInMulticastPkts = 0 ,ifOutOctets = 0, ifOutUcastPkts = 0, ifOutMulticastPkts = 0;
                // 流采样的包长度
                int flowSamplePktLength=0;

                for(SflowToolJason sflowToolJason: sflowToolJasons){
                    // 1.counterSample
                    String inIndexName = "19";
                    counterSampleDOS.add(sflowToolJason.generateCounterSampleDO(inIndexName));

                    // 2. FlowSample
                    flowSampleDOs.addAll(sflowToolJason.generateFlowSampleDO());
                }

                int countSampleSize = counterSampleDOS.size();
                ifInOctets = (Long.parseLong(counterSampleDOS.get(countSampleSize-1).getIfInOctets())
                        - Long.parseLong(counterSampleDOS.get(0).getIfInOctets())) / countSampleSize;
                ifInUcastPkts = (Long.parseLong(counterSampleDOS.get(countSampleSize-1).getIfInUcastPkts())
                        - Long.parseLong(counterSampleDOS.get(0).getIfInUcastPkts())) / countSampleSize;
                ifInMulticastPkts = (Long.parseLong(counterSampleDOS.get(countSampleSize-1).getIfInMulticastPkts())
                        - Long.parseLong(counterSampleDOS.get(0).getIfInMulticastPkts())) / countSampleSize;
                ifOutOctets = (Long.parseLong(counterSampleDOS.get(countSampleSize-1).getIfOutOctets())
                        - Long.parseLong(counterSampleDOS.get(0).getIfOutOctets())) / countSampleSize;
                ifOutUcastPkts = (Long.parseLong(counterSampleDOS.get(countSampleSize-1).getIfOutUcastPkts())
                        - Long.parseLong(counterSampleDOS.get(0).getIfOutUcastPkts())) / countSampleSize;
                ifOutMulticastPkts = (Long.parseLong(counterSampleDOS.get(countSampleSize-1).getIfOutMulticastPkts())
                        - Long.parseLong(counterSampleDOS.get(0).getIfOutMulticastPkts())) / countSampleSize;

                //todo 测试计算的统计采样得到的平均值 测试完成后删除
                System.out.println(ifInOctets+" "+ifInUcastPkts+" "+ifInMulticastPkts+" "+ifOutOctets+" "+ifOutUcastPkts+" "+ifOutMulticastPkts);

                for(FlowSampleDO flowSampleDO:flowSampleDOs){
                    //遍历一遍 计算entropy
                    //todo 计算长度还有平均值

                    flowSamplePktLength += Integer.parseInt(flowSampleDO.getSampledPacketSize());
                    String srcIP = flowSampleDO.getSrcIP();
                    String dstIP = flowSampleDO.getDstIP();

                    String srcPort, dstPort;
                    if (flowSampleDO.getTCPSrcPort()==null){
                        srcPort = flowSampleDO.getUDPSrcPort();
                        dstPort = flowSampleDO.getUDPDstPort();
                    }
                    else {
                        srcPort = flowSampleDO.getTCPSrcPort();
                        dstPort = flowSampleDO.getTCPDstPort();
                    }

                    EntropyHelper.updateMap(srcIP, srcIpList);
                    EntropyHelper.updateMap(dstIP, dstIpList);
                    EntropyHelper.updateMap(srcPort, srcPortList);
                    EntropyHelper.updateMap(dstPort, dstPortList);
                }
                int avgFlowSamplePktLength = flowSamplePktLength/flowSampleDOs.size();
                double entropy = EntropyHelper.calcuteEntropy(srcIpList);
                System.out.println(srcIpList);   //todo 删掉
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



    }

}
