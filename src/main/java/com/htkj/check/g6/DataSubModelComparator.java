package com.htkj.check.g6;

import java.util.Comparator;

public class DataSubModelComparator implements Comparator<DataSubModel> {
    @Override
    public int compare(DataSubModel d1, DataSubModel d2) {
        if (d1.time > d2.time) {
            return 1;
        } else if (d1.time == d2.time) {
            return 0;
        } else {
            return -1;
        }
    }
}
