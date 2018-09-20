package kz.greetgo.ekolpakov.register.z_etc;

import kz.greetgo.depinject.core.BeanConfig;
import kz.greetgo.depinject.core.Include;
import kz.greetgo.ekolpakov.register.beans.all.BeanConfigAll;

@BeanConfig
@Include({BeanConfigAll.class})
public class BeanConfigOfRegisterTests {}
