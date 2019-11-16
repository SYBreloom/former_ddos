import com.google.gson.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by 沈毅 on 2019/4/3.
 */
public class SflowToolJason {

    private JsonParser jsonParser;
    private String sflowString;
    private JsonObject sflowJson;   //最大的那个jsonObject
    private JsonArray samples;      //主体的采样信息

    private static final String ELEMENTS = "elements";
    private static final String SAMPLES = "samples";
    private static final String SAMPLE_TYPE = "sampleType";

    public SflowToolJason(){
        jsonParser = new JsonParser();
    }

    public SflowToolJason(String sflowString){
        this();
        this.sflowString = sflowString;
        this.sflowJson = getJsonObjectFromJsonString();
        this.samples = hasAndGetElementFromMember(sflowJson, SAMPLES).getAsJsonArray();
    }

    /**
     * 对于每一行文件，即每一个sflow包，这是一个对象。然后，对象里有很多sample，对sample提取我需要的数据,产生DO
     * @return
     */
    public ArrayList<SampleDO> generateDO(){
        String time = extractTime();
        ArrayList<SampleDO> sampleDOArrayList = new ArrayList<SampleDO>();

        for (JsonElement sample: samples){
            JsonObject sampleJsonObject = sample.getAsJsonObject();

            String sampleType = hasAndGetStringFromMember(sampleJsonObject, SAMPLE_TYPE);
            JsonArray elements = hasAndGetElementFromMember(sampleJsonObject,ELEMENTS).getAsJsonArray();

            if(sampleType.equals("COUNTERSSAMPLE")){
                if(elements.size()!=1)
                    System.err.println("FLOWSAMPLE elements数量不为1");
                JsonObject elementJsonObject = elements.get(0).getAsJsonObject(); //COUNTERSSAMPLE 统计采样这个elements是只有1个元素

                CounterSampleDO counterSampleDO = new CounterSampleDO();
                counterSampleDO.setDateStr(time);
                counterSampleDO.setIfIndex(hasAndGetStringFromMember(elementJsonObject,"ifIndex"));
                counterSampleDO.setIfInOctets(hasAndGetStringFromMember(elementJsonObject,"ifInOctets"));
                counterSampleDO.setIfInUcastPkts(hasAndGetStringFromMember(elementJsonObject,"ifInUcastPkts"));
                counterSampleDO.setIfInMulticastPkts(hasAndGetStringFromMember(elementJsonObject,"ifInMulticastPkts"));
                counterSampleDO.setIfOutOctets(hasAndGetStringFromMember(elementJsonObject,"ifOutOctets"));
                counterSampleDO.setIfOutUcastPkts(hasAndGetStringFromMember(elementJsonObject,"ifOutUcastPkts"));
                counterSampleDO.setIfOutMulticastPkts(hasAndGetStringFromMember(elementJsonObject,"ifOutMulticastPkts"));

                //t.println(counterSampleDO);
                sampleDOArrayList.add(counterSampleDO);

            }
            else if (sampleType.equals("FLOWSAMPLE")){
                if(elements.size()!=2)
                    System.err.println("FLOWSAMPLE elements数量不为2");
                JsonObject elementJsonObject = elements.get(1).getAsJsonObject(); //FLOWSAMPLE 流采样这个elements是只有2个元素

                FlowSampleDO flowSampleDO = new FlowSampleDO();
                flowSampleDO.setDateStr(time);
                flowSampleDO.setSampledPacketSize(hasAndGetStringFromMember(elementJsonObject,"sampledPacketSize"));
                flowSampleDO.setSrcMAC(hasAndGetStringFromMember(elementJsonObject,"srcMAC"));
                flowSampleDO.setDstMAC(hasAndGetStringFromMember(elementJsonObject,"dstMAC"));
                flowSampleDO.setSrcIP(hasAndGetStringFromMember(elementJsonObject,"srcIP"));
                flowSampleDO.setDstIP(hasAndGetStringFromMember(elementJsonObject,"dstIP"));
                flowSampleDO.setUDPSrcPort(hasAndGetStringFromMember(elementJsonObject,"UDPSrcPort"));
                flowSampleDO.setUDPDstPort(hasAndGetStringFromMember(elementJsonObject,"UDPDstPort"));
                flowSampleDO.setUDPBytes(hasAndGetStringFromMember(elementJsonObject,"UDPBytes"));
                flowSampleDO.setTCPSrcPort(hasAndGetStringFromMember(elementJsonObject, "TCPSrcPort"));
                flowSampleDO.setTCPDstPort(hasAndGetStringFromMember(elementJsonObject,"TCPDstPort"));
                flowSampleDO.setTCPBytes(hasAndGetStringFromMember(elementJsonObject,"TCPBytes"));

                sampleDOArrayList.add(flowSampleDO);
                // System.out.println(flowSampleDO);

            }

        }
        return sampleDOArrayList;

    }

    /**
     *
     * @param inIndexName 交换机端口名
     * @return 对应的couterSampleDo
     */
    public CounterSampleDO generateCounterSampleDO(String inIndexName){
        String time = extractTime();
        CounterSampleDO counterSampleDO = new CounterSampleDO();
        for (JsonElement sample: samples){
            JsonObject sampleJsonObject = sample.getAsJsonObject();

            String sampleType = hasAndGetStringFromMember(sampleJsonObject, SAMPLE_TYPE);
            JsonArray elements = hasAndGetElementFromMember(sampleJsonObject,ELEMENTS).getAsJsonArray();

            if(sampleType.equals("COUNTERSSAMPLE")){
                if(elements.size()!=1)
                    System.err.println("COUNTERSSAMPLE elements数量不为1");
                JsonObject elementJsonObject = elements.get(0).getAsJsonObject(); //COUNTERSSAMPLE 统计采样这个elements是只有1个元素

                if (inIndexName.equals(hasAndGetStringFromMember(elementJsonObject,"ifIndex"))){
                    counterSampleDO.setDateStr(time);
                    counterSampleDO.setIfIndex(hasAndGetStringFromMember(elementJsonObject,"ifIndex"));
                    counterSampleDO.setIfInOctets(hasAndGetStringFromMember(elementJsonObject,"ifInOctets"));
                    counterSampleDO.setIfInUcastPkts(hasAndGetStringFromMember(elementJsonObject,"ifInUcastPkts"));
                    counterSampleDO.setIfInMulticastPkts(hasAndGetStringFromMember(elementJsonObject,"ifInMulticastPkts"));
                    counterSampleDO.setIfOutOctets(hasAndGetStringFromMember(elementJsonObject,"ifOutOctets"));
                    counterSampleDO.setIfOutUcastPkts(hasAndGetStringFromMember(elementJsonObject,"ifOutUcastPkts"));
                    counterSampleDO.setIfOutMulticastPkts(hasAndGetStringFromMember(elementJsonObject,"ifOutMulticastPkts"));
                }
                return counterSampleDO;
            }
        }
        System.err.println("没有"+inIndexName+"对应的sample数据");
        return counterSampleDO;
    }

    public ArrayList<FlowSampleDO> generateFlowSampleDO(){
        String time = extractTime();
        ArrayList<FlowSampleDO> flowSampleDOArrayList = new ArrayList<FlowSampleDO>();

        for (JsonElement sample: samples) {
            JsonObject sampleJsonObject = sample.getAsJsonObject();

            String sampleType = hasAndGetStringFromMember(sampleJsonObject, SAMPLE_TYPE);
            JsonArray elements = hasAndGetElementFromMember(sampleJsonObject, ELEMENTS).getAsJsonArray();

            if (sampleType.equals("FLOWSAMPLE")) {
                if (elements.size() != 2)
                    System.err.println("FLOWSAMPLE elements数量不为2");
                JsonObject elementJsonObject = elements.get(1).getAsJsonObject(); //COUNTERSSAMPLE 统计采样这个elements是只有1个元素

                FlowSampleDO flowSampleDO = new FlowSampleDO();
                flowSampleDO.setDateStr(time);
                flowSampleDO.setSampledPacketSize(hasAndGetStringFromMember(elementJsonObject,"sampledPacketSize"));
                flowSampleDO.setSrcMAC(hasAndGetStringFromMember(elementJsonObject,"srcMAC"));
                flowSampleDO.setDstMAC(hasAndGetStringFromMember(elementJsonObject,"dstMAC"));
                flowSampleDO.setSrcIP(hasAndGetStringFromMember(elementJsonObject,"srcIP"));
                flowSampleDO.setDstIP(hasAndGetStringFromMember(elementJsonObject,"dstIP"));
                flowSampleDO.setUDPSrcPort(hasAndGetStringFromMember(elementJsonObject,"UDPSrcPort"));
                flowSampleDO.setUDPDstPort(hasAndGetStringFromMember(elementJsonObject,"UDPDstPort"));
                flowSampleDO.setUDPBytes(hasAndGetStringFromMember(elementJsonObject,"UDPBytes"));
                flowSampleDO.setTCPBytes(hasAndGetStringFromMember(elementJsonObject,"TCPBytes"));
                flowSampleDO.setTCPSrcPort(hasAndGetStringFromMember(elementJsonObject, "TCPSrcPort"));
                flowSampleDO.setTCPDstPort(hasAndGetStringFromMember(elementJsonObject,"TCPDstPort"));

                flowSampleDOArrayList.add(flowSampleDO);
                // System.out.println(flowSampleDO);
            }
        }
        return flowSampleDOArrayList;
    }

    /**
     *  从原始string得到jsonObject
     * @return jsonObject
     */
    public JsonObject getJsonObjectFromJsonString(){
        if(sflowString == null){
            System.err.println("sflowString null!");
            return null;
        }
        String correctJsonFormat = this.correctJsonFormat(sflowString);
        JsonObject jsonObject = jsonParser.parse(correctJsonFormat).getAsJsonObject();
        return jsonObject;
    }

//    public JsonElement getJsonElementFromJsonString(String originalSflowString){
//        String correctJsonFormat = this.correctJsonFormat(originalSflowString);
//        JsonElement jsonElement = jsonParser.parse(correctJsonFormat);
//        return jsonElement;
//    }

    public String getSflowString() {
        return sflowString;
    }

    public void setSflowString(String sflowString) {
        this.sflowString = sflowString;
    }

    public JsonArray getSamples() {
        return samples;
    }

    public void setSamples(JsonArray samples) {
        this.samples = samples;
    }

    /**
     *  由于sflowtool输出的json格式字符串格式有错误 需要添加几个逗号
     * @param originalString sflowtool -j直接输出的 原始的Json
     * @return correctedString 返回正常的json格式字符串
     */
    private String correctJsonFormat(String originalString){
        String tmp1 = originalString.replace("\"\"","\",\"");    //去掉 "" 中间缺少的逗号
        String tmp2 = tmp1.replace("}{","},{");         //去掉 }{ 中间缺少的逗号
        return tmp2;
    }

//    public JsonArray getElements(JsonObject sampleJsonObject){
//        return hasAndGetElementFromMember(sampleJsonObject,ELEMENTS).getAsJsonArray();
//    }

    private JsonElement hasAndGetElementFromMember(JsonObject jsonObject, String memberName){
        if(jsonObject.has(memberName))
            return jsonObject.get(memberName);
        else
            return null;
    }

    /**
     *
     * @param jsonObject 对应的JsonObject
     * @param memberName 属性名称
     * @return 如果为空返回"" 否则返回string
     */
    private String hasAndGetStringFromMember(JsonObject jsonObject, String memberName){
        if(jsonObject.has(memberName))
            return jsonObject.get(memberName).getAsString();
        else
            return "";
    }

    /**
     *
     * @param jsonObject 对应的JsonObject
     * @param memberName 属性名称
     * @return
     */
    private boolean hasMember(JsonObject jsonObject, String memberName){
        return jsonObject.has(memberName);
    }

    public double extractTimeDouble(){
        String localtime = hasAndGetElementFromMember(sflowJson,"localtime").getAsString();
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        Date date = null;
        try {
            date = simpleDateFormat2.parse(localtime);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
        return date.getTime();
    }

    private String extractTime(){
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
        String localtime = hasAndGetElementFromMember(sflowJson,"localtime").getAsString();
        Date date = null;
        try {
            date = simpleDateFormat2.parse(localtime);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        return simpleDateFormat.format(date);

    }


}
