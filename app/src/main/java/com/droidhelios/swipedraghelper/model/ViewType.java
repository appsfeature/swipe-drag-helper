package com.droidhelios.swipedraghelper.model;

/**
 * @author Created by Abhijit Rao on 20-11-2019.
 */

public class ViewType {

    private int dataIndex;
    private int type;

    public ViewType(int dataIndex, int type) {
        this.dataIndex = dataIndex;
        this.type = type;
    }

    public int getDataIndex() {
        return dataIndex;
    }

    public int getType() {
        return type;
    }
}
