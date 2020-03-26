//
// ���ļ����� JavaTM Architecture for XML Binding (JAXB) ����ʵ�� v2.2.8-b130911.1802 ���ɵ�
// ����� <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// �����±���Դģʽʱ, �Դ��ļ��������޸Ķ�����ʧ��
// ����ʱ��: 2019.12.03 ʱ�� 05:42:01 PM CST 
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
 *         &lt;element ref="{}md-parameter"/>
 *         &lt;element ref="{}pd-parameter"/>
 *         &lt;element ref="{}source" maxOccurs="unbounded"/>
 *         &lt;element ref="{}destination" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *       &lt;attribute name="com-id" use="required" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *       &lt;attribute name="com-parameter-id" use="required" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *       &lt;attribute name="data-set-id" use="required" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "mdParameter", "pdParameter", "source", "destination" })
@XmlRootElement(name = "telegram")
@Data
@EqualsAndHashCode(callSuper = false)
public class Telegram extends SheetAndLine {

	@XmlElement(name = "md-parameter")
	protected MdParameter mdParameter;

	@XmlElement(name = "pd-parameter")
	protected PdParameter pdParameter;

	@XmlElement(required = true)
	protected List<Source> source;

	@XmlElement(required = true)
	protected List<Destination> destination;

	@XmlAttribute(name = "com-id")
	protected String comId;

	@XmlAttribute(name = "com-parameter-id")
	protected String comParameterId;

	@XmlAttribute(name = "data-set-id")
	protected String datasetId;

	@XmlAttribute(name = "name")
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	@XmlSchemaType(name = "NCName")
	protected String name;

}
