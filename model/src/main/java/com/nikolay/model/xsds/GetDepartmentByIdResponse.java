//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.05.12 at 01:32:09 PM MSK 
//


package com.nikolay.model.xsds;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="departmentResponse" type="{http://www.human-resources.com/definition}departmentResponse"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "departmentResponse"
})
@XmlRootElement(name = "getDepartmentByIdResponse")
public class GetDepartmentByIdResponse {

    @XmlElement(required = true)
    protected DepartmentResponse departmentResponse;

    /**
     * Gets the value of the departmentResponse property.
     * 
     * @return
     *     possible object is
     *     {@link DepartmentResponse }
     *     
     */
    public DepartmentResponse getDepartmentResponse() {
        return departmentResponse;
    }

    /**
     * Sets the value of the departmentResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link DepartmentResponse }
     *     
     */
    public void setDepartmentResponse(DepartmentResponse value) {
        this.departmentResponse = value;
    }

}
