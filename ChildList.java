package com.zorbando.harit.zorbandocontests;

/**
 * Created by harit on 3/17/2016.
 */
public class ChildList {
    public String header = null;

    public boolean state = false;
    public ChildList(String header,boolean state )
    {
        this.header = header;
        this.state = state;


    }
    public String getHeader() {
        return header;
    }
    public boolean getState(){
        return state;
    }
}
