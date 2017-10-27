package com.asa.base.data.xml.process.impl;


import com.asa.base.data.xml.XmlObjectFactory;
import com.asa.base.data.xml.node.XmlNode;
import com.asa.base.data.xml.node.XmlObjectNode;
import com.asa.base.data.xml.process.XmlNodeProcess;
import com.asa.base.data.xml.process.XmlStructureNodeFactory;
import com.asa.base.environmen.Environment;
import com.asa.base.utils.data.xml.imp.node.XmlNodeUtils;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.AttributesImpl;

import javax.xml.transform.sax.TransformerHandler;
import java.util.List;
import java.util.Stack;

/**
 * Created by andrew_asa on 2017/10/23.
 */
public class XmlObjectNodeProcess extends AbstractXmlNodeProcess {

    @Override
    public void createXmlNodeByClass(Class clazz, XmlNode root) throws Exception {

    }

    @Override
    public boolean shouldDealWith(XmlNode node) {

        return node.getClass().equals(XmlObjectNode.class);
    }

    @Override
    public String getName() {

        return null;
    }

    @Override
    public Object startElement(String uri, String localName, String qName, Attributes attributes, XmlNode node, Stack stack) throws Exception {

        XmlObjectNode objectNode = translateNode(node);
        Object o;
        if (objectNode.isInterface()) {
            // 如果是接口类型
            Class factoryClazz = objectNode.getValueFactory();
            XmlObjectFactory factory = getValueFactory(factoryClazz);
            o = factory.getObject(attributes);
            XmlNode cn = XmlNodeUtils.getXmlObjectNodeByClass(o.getClass());
            node.addAll(cn.getChilds());
        } else {
            o = objectNode.getClazz().newInstance();
        }
        if (o != null) {
            stack.push(o);
        }
        return o;
    }

    @Override
    public void characters(char[] ch, int start, int length, XmlNode node, Stack stack) {

    }

    @Override
    public Object endElement(Environment environment) {

        XmlNode node = environment.getElement(3);
        if (node.isInterface()) {
            node.clearChild();
        }
        return null;
    }

    @Override
    public void afterEndElement(Environment environment) {

    }

    @Override
    public void writeNode(Object o, XmlNode node, TransformerHandler handler, AttributesImpl attr) throws Exception {

        attr.clear();
        String tagName = node.getTagName();
        XmlObjectNode objectNode = translateNode(node);
        createAttr(o, objectNode, attr);
        handler.startElement("", "", tagName, attr);
        try {
            if (objectNode.isInterface()) {
                // 如果是接口类型需要重新获取对象的node
                XmlNode on = getInterfaceNode(o);
                List<XmlNode> childs = on.getChilds();
                for (XmlNode child : childs) {
                    XmlNodeProcess process = XmlStructureNodeFactory.getProcessByXmlNode(child);
                    process.writeNode(o, child, handler, attr);
                }
            }
        } finally {
            handler.endElement("", "", tagName);
        }
    }

    /**
     * 生成标签属性
     *
     * @param o
     * @param node
     * @param attr
     */
    private void createAttr(Object o, XmlObjectNode node, AttributesImpl attr) throws Exception {

        // 只有接口的时候才有这个必要
        if (node.isInterface()) {
            XmlObjectFactory factory = getValueFactory(node.getValueFactory());
            factory.createAttr(o, attr);
        }
    }

}
