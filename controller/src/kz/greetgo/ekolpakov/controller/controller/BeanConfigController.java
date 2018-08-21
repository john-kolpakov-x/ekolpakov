package kz.greetgo.ekolpakov.controller.controller;

import kz.greetgo.depinject.core.BeanConfig;
import kz.greetgo.depinject.core.BeanScanner;
import kz.greetgo.depinject.core.Include;
import kz.greetgo.ekolpakov.controller.templates.BeanConfigTemplates;
import kz.greetgo.ekolpakov.controller.views.BeanConfigViews;

@BeanConfig
@BeanScanner
@Include({BeanConfigViews.class, BeanConfigTemplates.class})
public class BeanConfigController {}
