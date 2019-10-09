package com.htkj.check.g6;

import java.util.List;

public class DataModel {
    public String vin;
    public String iccid;

    public boolean isLocate;
    public boolean isInTime;
    public boolean isSpeed;
    public boolean isTorque;
    public boolean isLevel;

    public boolean result;

    public List<DataSubModel> datas;
}
