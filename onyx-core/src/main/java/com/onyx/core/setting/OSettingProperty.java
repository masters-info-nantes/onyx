package com.onyx.core.setting;

import com.onyx.platform.OService;
import com.onyx.platform.OServiceValue;

/**
 * Created by Hollevoet Yehudi on 3/19/15.
 */

@OService(name="setting")
public class OSettingProperty {
    @OServiceValue(name="name")
    public String name;

    @OServiceValue(name="settingClass")
    public Class settingClass;

    @OServiceValue(name="settingInterface")
    public Class settingInterface;
}
