//
// ���ļ����� JavaTM Architecture for XML Binding (JAXB) ����ʵ�� v2.2.8-b130911.1802 ���ɵ�
// ����� <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// �����±���Դģʽʱ, �Դ��ļ��������޸Ķ�����ʧ��
// ����ʱ��: 2019.12.03 ʱ�� 05:10:31 PM CST 
//

package com.hirain.phm.synapsis.ecn.model;

import java.util.List;

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

/**
 * <p>
 * anonymous complex type�� Java �ࡣ
 * <p>
 * ����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}trdp-process"/>
 *         &lt;element ref="{}pd-com-parameter"/>
 *         &lt;element ref="{}md-com-parameter"/>
 *         &lt;element ref="{}telegram" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *       &lt;attribute name="host-ip" use="required" type="{http://www.w3.org/2001/XMLSchema}NMTOKEN" />
 *       &lt;attribute name="leader-ip" use="required" type="{http://www.w3.org/2001/XMLSchema}NMTOKEN" />
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *       &lt;attribute name="network-id" use="required" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "trdpProcess", "pdComParameter", "mdComParameter", "telegram" })
@XmlRootElement(name = "bus-interface")
@Data
@EqualsAndHashCode(callSuper = false)
public class BusInterface extends SheetAndLine {

	@XmlElement(name = "trdp-process")
	protected TrdpProcess trdpProcess = new TrdpProcess();

	@XmlElement(name = "pd-com-parameter")
	protected PdComParameter pdComParameter = new PdComParameter();

	@XmlElement(name = "md-com-parameter")
	protected MdComParameter mdComParameter = new MdComParameter();

	@XmlElement()
	protected List<Telegram> telegram;

	@XmlAttribute(name = "host-ip")
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	@XmlSchemaType(name = "NMTOKEN")
	protected String hostIp;

	@XmlAttribute(name = "leader-ip")
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	@XmlSchemaType(name = "NMTOKEN")
	protected String leaderIp;

	@XmlAttribute(name = "name")
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	@XmlSchemaType(name = "NCName")
	protected String name;

	@XmlAttribute(name = "network-id")
	protected String networkId;

}
