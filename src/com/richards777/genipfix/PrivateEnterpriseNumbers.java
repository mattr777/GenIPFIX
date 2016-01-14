package com.richards777.genipfix;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PrivateEnterpriseNumbers {
    private static PrivateEnterpriseNumbers instance = null;
    private Map<Integer, String> nameMap = new HashMap<>();
    private Map<String, Integer> numberMap = new HashMap<>();

    public static synchronized PrivateEnterpriseNumbers get() {
        if (instance == null) {
            instance = new PrivateEnterpriseNumbers();
        }

        return instance;
    }

    private PrivateEnterpriseNumbers() {
        nameMap.put(110, "NETSCOUT (Network General)");
        numberMap.put("NETSCOUT (Network General)", 110);

        nameMap.put(141, "NETSCOUT (Frontier Software)");
        numberMap.put("NETSCOUT (Frontier Software)", 141);

        nameMap.put(1246, "Visual Networks");
        numberMap.put("Visual Networks", 1246);

        nameMap.put(9694, "Arbor Networks");
        numberMap.put("Arbor Networks", 9694);

        nameMap.put(37560, "NETSCOUT (Simena)");
        numberMap.put("NETSCOUT (Simena)", 37560);
    }

    public Set<String> getNames() {
        return numberMap.keySet();
    }

    public int getNumber(String enterpriseName) {
        return numberMap.get(enterpriseName);
    }

    public String getName(Integer enterpriseNumber) {
        return nameMap.get(enterpriseNumber);
    }
}
