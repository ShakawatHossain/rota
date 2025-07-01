package accuratesoft.shakawat.rotadatacollection.models;

import java.util.ArrayList;

public class MMain {
    ArrayList<String> tab_code;
    ArrayList<String> pass;
    ArrayList<String> hos_name;

    public MMain(){
        tab_code = new ArrayList<String>();
        pass = new ArrayList<String>();
        hos_name = new ArrayList<String>();
        for(int i=1;i<10;i++){
            if (i==4){
                continue;
            }
            tab_code.add("tab0"+i);
            pass.add("iedcr0"+i);
        }
        hos_name.add("RMCH_01");
        hos_name.add("JIMCH_02");
        hos_name.add("JRRMCH_03");
        hos_name.add("BBMH_05");
        hos_name.add("JGH_06");
        hos_name.add("SBMCH_07");
        hos_name.add("RpMCH_08");
        hos_name.add("CMCH_09");
    }
    public String check_login(String tab,String password){
        for(int i=0;i<tab_code.size();i++){
            if (tab_code.get(i).contentEquals(tab) && pass.get(i).contentEquals(password)){
                return hos_name.get(i);
            }
        }
        return "";
    }
}
