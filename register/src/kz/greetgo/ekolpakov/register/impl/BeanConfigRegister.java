package kz.greetgo.ekolpakov.register.impl;

import kz.greetgo.depinject.core.BeanConfig;
import kz.greetgo.depinject.core.BeanScanner;
import kz.greetgo.depinject.core.Include;
import kz.greetgo.ekolpakov.register.beans.all.BeanConfigAll;

@BeanConfig
@BeanScanner
@Include({BeanConfigAll.class})
public class BeanConfigRegister {}
