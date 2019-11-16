import java.util.HashMap;

/**
 * Created by 沈毅 on 2019/4/4.
 */
public class EntropyHelper {
    public static void updateMap(String members, HashMap<String, Integer> hashMap){
        if (!members.equals("")) {
            if (hashMap.containsKey(members)) {
                int oldValue = hashMap.get(members);
                hashMap.put(members, 1+ oldValue);
            } else {
                hashMap.put(members, 1);
            }
        }
    }

    /**
     *  根据公式计算熵值, 并归一化
     * @param hashMap 保存值和数量映射关系的映射表
     * @return 熵
     */
    public static double calcuteEntropy(HashMap<String, Integer> hashMap){

        int sum = 0;
        double entropy = 0;
        for(Integer value : hashMap.values()) {
            sum += value;
        }
        for(Integer value : hashMap.values()) {
            float temp =(float)value/sum;
            entropy -= temp*Math.log(temp);
        }

        return entropy / Math.log(sum);

    }
}
