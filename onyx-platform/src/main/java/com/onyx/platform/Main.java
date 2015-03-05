package com.onyx.platform;

/**
 * Created by Maxime on 05/02/15.
 */
public class Main {
    public static void main (String[] args) throws Exception{
        OPlatform p = new OPlatform();
        if(args.length>0) {
            for (String param : args) {
                if (param.equals("-menu"))
                    p.showMenu();
                else if (param.equals("-debug")) {
                    //to-do
                } else {
                    System.out.println("Invalid Parameters");
                }
                //Others param to do
            }
        }
        else
        {
            p.loadDefaultPlugins();
        }
    }
}
