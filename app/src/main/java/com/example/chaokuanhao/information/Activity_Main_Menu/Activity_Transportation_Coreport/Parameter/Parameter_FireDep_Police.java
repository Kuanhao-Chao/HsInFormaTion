package com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Transportation_Coreport.Parameter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chaokuanhao on 25/11/2017.
 */

public class Parameter_FireDep_Police {

    private List<Parameter_FireDep> fireDep = new ArrayList<Parameter_FireDep>();
    private List<Parameter_Police> police = new ArrayList<Parameter_Police>();

    public Parameter_FireDep_Police( List<Parameter_FireDep> a, List<Parameter_Police> b ){
        fireDep = a;
        police = b;
    }

    public List<Parameter_FireDep> getFireDep() {
        return fireDep;
    }

    public List<Parameter_Police> getPolice() {
        return police;
    }

    public void setFireDep(List<Parameter_FireDep> fireDep) {
        this.fireDep = fireDep;
    }

    public void setPolice(List<Parameter_Police> police) {
        this.police = police;
    }
}




