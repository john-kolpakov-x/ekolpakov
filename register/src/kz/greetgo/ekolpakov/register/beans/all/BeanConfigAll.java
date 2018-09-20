package kz.greetgo.ekolpakov.register.beans.all;

import kz.greetgo.depinject.core.BeanConfig;
import kz.greetgo.depinject.core.BeanScanner;
import kz.greetgo.depinject.core.Include;
import kz.greetgo.ekolpakov.register.impl.BeanConfigRegister;

@BeanConfig
@BeanScanner
@Include({BeanConfigRegister.class})
public class BeanConfigAll {}
