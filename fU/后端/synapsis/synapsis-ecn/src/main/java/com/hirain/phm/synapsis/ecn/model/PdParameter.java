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
 *       &lt;attribute name="callback" use="required" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *       &lt;attribute name="cycle" use="required" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *       &lt;attribute name="marshall" use="required" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *       &lt;attribute name="offset-address" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *       &lt;attribute name="redundant" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *       &lt;attribute name="timeout-value" use="required" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *       &lt;attribute name="validity-behavior" use="required" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "pd-parameter")
@Data
@EqualsAndHashCode(callSuper = false)
public class PdParameter extends SheetAndLine {

	@XmlAttribute(name = "callback")
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	@XmlSchemaType(name = "NCName")
	protected String callback;

	@XmlAttribute(name = "cycle")
	protected String cycle;

	@XmlAttribute(name = "marshall")
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	@XmlSchemaType(name = "NCName")
	protected String marshall;

	@XmlAttribute(name = "offset-address")
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	@XmlSchemaType(name = "NCName")
	protected String offsetAddress;

	@XmlAttribute(name = "redundant")
	protected String redundant;

	@XmlAttribute(name = "timeout-value")
	protected String timeOutValue;

	@XmlAttribute(name = "validity-behavior")
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	@XmlSchemaType(name = "NCName")
	protected String validityBehaviour;

}
