package com.onyx.core;

import com.onyx.platform.OService;
import com.onyx.platform.OServiceValue;

/**
 * Created by Maxime on 08/02/15.
 */
@OService(name = "application")
public class OApp {

    @OServiceValue
    Class mainActivity;
}
