//
// ���ļ����� JavaTM Architecture for XML Binding (JAXB) ����ʵ�� v2.2.8-b130911.1802 ���ɵ�
// ����� <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// �����±���Դģʽʱ, �Դ��ļ��������޸Ķ�����ʧ��
// ����ʱ��: 2019.12.03 ʱ�� 05:19:10 PM CST 
//

package com.hirain.phm.synapsis.ecn.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import lombok.Data;
import lombok.EqualsAndHashCode;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "deviceConfiguration", "busInterfaceList", "comParameterList", "dataSetList" })
@XmlRootElement(name = "device")
@Data
@EqualsAndHashCode(callSuper = false)
public class Device extends SheetAndLine {

	@XmlElement(name = "device-configuration")
	protected DeviceConfiguration deviceConfiguration;

	@XmlElement(name = "bus-interface-list")
	protected BusInterfaceList busInterfaceList;

	@XmlElement(name = "com-parameter-list")
	protected ComParameterList comParameterList;

	@XmlElement(name = "data-set-list")
	protected DataSetList dataSetList;

	@XmlAttribute(name = "host-name")
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	@XmlSchemaType(name = "NCName")
	protected String hostName;

	@XmlAttribute(name = "leader-name")
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	@XmlSchemaType(name = "NCName")
	protected String leaderName;

	@XmlAttribute(name = "type")
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	@XmlSchemaType(name = "NCName")
	protected String type;

}
