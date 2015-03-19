package com.onyx.core;

import com.onyx.platform.OService;
import com.onyx.platform.OServiceValue;

/**
 * Created by wolkom on 3/19/15.
 */

@OService(name="network")
public class OSettingProperty {
    @OServiceValue(name="name")
    String name;
    @OServiceValue(name="mainPanel")
    Class panel;
}
