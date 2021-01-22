package com.ltthuong.app;

public class Object {
    public String key;
    public String name;

    public Object() {
    }
    public Object(String name, String key)
    {
        this.name = name;
        this.key = key;
    }
    public String getName( )
    {
        return name;
    }
    public String getKey( )
    {
        return key;
    }

    public void setName(String name )
    {
        this.name = name;
    }
    public void setKey (String key)
    {
        this.key = key;
    }
}

