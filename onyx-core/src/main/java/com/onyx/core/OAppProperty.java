package com.onyx.core;

import com.onyx.platform.OService;
import com.onyx.platform.OServiceValue;

/**
 * Created by Maxime on 08/02/15.
 */
@OService(name = "application")
public class OAppProperty {

    @OServiceValue(name = "id")
    public String id;
    
    @OServiceValue(name = "name")
    public String name;
    
    @OServiceValue(name = "mainActivity")
    public Class mainActivity;
    
    
}
