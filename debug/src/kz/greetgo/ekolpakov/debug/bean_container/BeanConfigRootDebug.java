package kz.greetgo.ekolpakov.debug.bean_container;

import kz.greetgo.depinject.core.BeanConfig;
import kz.greetgo.depinject.core.Include;
import kz.greetgo.ekolpakov.controller.controller.BeanConfigController;
import kz.greetgo.ekolpakov.debug.beans.BeanConfigDebug;
import kz.greetgo.ekolpakov.register.impl.BeanConfigRegister;

@BeanConfig
@Include({BeanConfigController.class, BeanConfigDebug.class, BeanConfigRegister.class})
public class BeanConfigRootDebug {}
