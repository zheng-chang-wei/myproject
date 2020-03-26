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
 *       &lt;attribute name="confirm-timeout" use="required" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *       &lt;attribute name="connect-timeout" use="required" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *       &lt;attribute name="marshall" use="required" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *       &lt;attribute name="num-sessions" use="required" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *       &lt;attribute name="protocol" use="required" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *       &lt;attribute name="qos" use="required" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *       &lt;attribute name="reply-timeout" use="required" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *       &lt;attribute name="tcp-port" use="required" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *       &lt;attribute name="ttl" use="required" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *       &lt;attribute name="udp-port" use="required" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "md-com-parameter")
@Data
@EqualsAndHashCode(callSuper = false)
public class MdComParameter extends SheetAndLine {

	@XmlAttribute(name = "callback")
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	@XmlSchemaType(name = "NCName")
	protected String callback;

	@XmlAttribute(name = "confirm-timeout")
	protected String confirmTimeOut;

	@XmlAttribute(name = "connect-timeout")
	protected String connectTimeOut;

	@XmlAttribute(name = "marshall")
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	@XmlSchemaType(name = "NCName")
	protected String marshall;

	@XmlAttribute(name = "num-sessions")
	protected String numSessions;

	@XmlAttribute(name = "protocol")
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	@XmlSchemaType(name = "NCName")
	protected String protocol;

	@XmlAttribute(name = "qos")
	protected String qos;

	@XmlAttribute(name = "reply-timeout")
	protected String replyTimeOut;

	@XmlAttribute(name = "tcp-port")
	protected String tcpPort;

	@XmlAttribute(name = "ttl")
	protected String ttl;

	@XmlAttribute(name = "udp-port")
	protected String udpPort;

}
