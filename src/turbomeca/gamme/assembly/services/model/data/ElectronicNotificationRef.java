/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3</a>, using an XML
 * Schema.
 * $Id$
 */

package turbomeca.gamme.assembly.services.model.data;

/**
 * Class ElectronicNotificationRef.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class ElectronicNotificationRef implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _refId.
     */
    private java.lang.String _refId;

    /**
     * Field _instance.
     */
    private java.lang.String _instance;

    /**
     * Field _alternative.
     */
    private java.lang.String _alternative;

    /**
     * Field _statusNotification.
     */
    private turbomeca.gamme.assembly.services.model.data.types.StatusNotificationType _statusNotification;


      //----------------/
     //- Constructors -/
    //----------------/

    public ElectronicNotificationRef() {
        super();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'alternative'.
     * 
     * @return the value of field 'Alternative'.
     */
    public java.lang.String getAlternative(
    ) {
        return this._alternative;
    }

    /**
     * Returns the value of field 'instance'.
     * 
     * @return the value of field 'Instance'.
     */
    public java.lang.String getInstance(
    ) {
        return this._instance;
    }

    /**
     * Returns the value of field 'refId'.
     * 
     * @return the value of field 'RefId'.
     */
    public java.lang.String getRefId(
    ) {
        return this._refId;
    }

    /**
     * Returns the value of field 'statusNotification'.
     * 
     * @return the value of field 'StatusNotification'.
     */
    public turbomeca.gamme.assembly.services.model.data.types.StatusNotificationType getStatusNotification(
    ) {
        return this._statusNotification;
    }

    /**
     * Method isValid.
     * 
     * @return true if this object is valid according to the schema
     */
    public boolean isValid(
    ) {
        try {
            validate();
        } catch (org.exolab.castor.xml.ValidationException vex) {
            return false;
        }
        return true;
    }

    /**
     * 
     * 
     * @param out
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     */
    public void marshal(
            final java.io.Writer out)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        org.exolab.castor.xml.Marshaller.marshal(this, out);
    }

    /**
     * 
     * 
     * @param handler
     * @throws java.io.IOException if an IOException occurs during
     * marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     */
    public void marshal(
            final org.xml.sax.ContentHandler handler)
    throws java.io.IOException, org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        org.exolab.castor.xml.Marshaller.marshal(this, handler);
    }

    /**
     * Sets the value of field 'alternative'.
     * 
     * @param alternative the value of field 'alternative'.
     */
    public void setAlternative(
            final java.lang.String alternative) {
        this._alternative = alternative;
    }

    /**
     * Sets the value of field 'instance'.
     * 
     * @param instance the value of field 'instance'.
     */
    public void setInstance(
            final java.lang.String instance) {
        this._instance = instance;
    }

    /**
     * Sets the value of field 'refId'.
     * 
     * @param refId the value of field 'refId'.
     */
    public void setRefId(
            final java.lang.String refId) {
        this._refId = refId;
    }

    /**
     * Sets the value of field 'statusNotification'.
     * 
     * @param statusNotification the value of field
     * 'statusNotification'.
     */
    public void setStatusNotification(
            final turbomeca.gamme.assembly.services.model.data.types.StatusNotificationType statusNotification) {
        this._statusNotification = statusNotification;
    }

    /**
     * Method unmarshal.
     * 
     * @param reader
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     * @return the unmarshaled
     * turbomeca.gamme.assembly.services.model.data.ElectronicNotificationRef
     */
    public static turbomeca.gamme.assembly.services.model.data.ElectronicNotificationRef unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (turbomeca.gamme.assembly.services.model.data.ElectronicNotificationRef) org.exolab.castor.xml.Unmarshaller.unmarshal(turbomeca.gamme.assembly.services.model.data.ElectronicNotificationRef.class, reader);
    }

    /**
     * 
     * 
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     */
    public void validate(
    )
    throws org.exolab.castor.xml.ValidationException {
        org.exolab.castor.xml.Validator validator = new org.exolab.castor.xml.Validator();
        validator.validate(this);
    }

}
