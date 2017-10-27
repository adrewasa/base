package com.asa.base.data.xml.process.impl;


import com.asa.base.data.xml.XmlObjectFactory;
import com.asa.base.data.xml.node.XmlNode;
import com.asa.base.data.xml.process.XmlNodeProcess;
import com.asa.base.utils.data.xml.imp.node.XmlNodeUtils;

/**
 * @author andrew.asa
 * @create 2017-10-21
 **/
public abstract class AbstractXmlNodeProcess implements XmlNodeProcess {

    public <T> T translateNode(XmlNode node) {

        return (T) node;
    }

    public XmlObjectFactory getValueFactory(Class factoryClass) throws Exception {

        return (XmlObjectFactory) factoryClass.newInstance();
    }

    public XmlNode getInterfaceNode(Object o) throws Exception{

        return XmlNodeUtils.getXmlNodeByClass(o.getClass());
    }
}
