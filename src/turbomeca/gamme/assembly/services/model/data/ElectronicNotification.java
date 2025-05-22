/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3</a>, using an XML
 * Schema.
 * $Id$
 */

package turbomeca.gamme.assembly.services.model.data;

/**
 * Class ElectronicNotification.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class ElectronicNotification implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _id.
     */
    private java.lang.String _id;

    /**
     * Field _instance.
     */
    private java.lang.String _instance;

    /**
     * Field _alternative.
     */
    private java.lang.String _alternative;

    /**
     * Field _updated.
     */
    private boolean _updated = false;

    /**
     * keeps track of state for field: _updated
     */
    private boolean _has_updated;

    /**
     * Field _active.
     */
    private boolean _active;

    /**
     * keeps track of state for field: _active
     */
    private boolean _has_active;

    /**
     * Field _refId.
     */
    private java.lang.Object _refId;

    /**
     * Field _type.
     */
    private turbomeca.gamme.assembly.services.model.data.types.NotificationType _type;

    /**
     * Field _origin.
     */
    private turbomeca.gamme.assembly.services.model.data.types.NotificationOriginType _origin;

    /**
     * Field _statusNotification.
     */
    private turbomeca.gamme.assembly.services.model.data.types.StatusNotificationType _statusNotification;

    /**
     * Field _request.
     */
    private turbomeca.gamme.assembly.services.model.data.Request _request;

    /**
     * Field _response.
     */
    private turbomeca.gamme.assembly.services.model.data.Response _response;


      //----------------/
     //- Constructors -/
    //----------------/

    public ElectronicNotification() {
        super();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     */
    public void deleteActive(
    ) {
        this._has_active= false;
    }

    /**
     */
    public void deleteUpdated(
    ) {
        this._has_updated= false;
    }

    /**
     * Returns the value of field 'active'.
     * 
     * @return the value of field 'Active'.
     */
    public boolean getActive(
    ) {
        return this._active;
    }

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
     * Returns the value of field 'id'.
     * 
     * @return the value of field 'Id'.
     */
    public java.lang.String getId(
    ) {
        return this._id;
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
     * Returns the value of field 'origin'.
     * 
     * @return the value of field 'Origin'.
     */
    public turbomeca.gamme.assembly.services.model.data.types.NotificationOriginType getOrigin(
    ) {
        return this._origin;
    }

    /**
     * Returns the value of field 'refId'.
     * 
     * @return the value of field 'RefId'.
     */
    public java.lang.Object getRefId(
    ) {
        return this._refId;
    }

    /**
     * Returns the value of field 'request'.
     * 
     * @return the value of field 'Request'.
     */
    public turbomeca.gamme.assembly.services.model.data.Request getRequest(
    ) {
        return this._request;
    }

    /**
     * Returns the value of field 'response'.
     * 
     * @return the value of field 'Response'.
     */
    public turbomeca.gamme.assembly.services.model.data.Response getResponse(
    ) {
        return this._response;
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
     * Returns the value of field 'type'.
     * 
     * @return the value of field 'Type'.
     */
    public turbomeca.gamme.assembly.services.model.data.types.NotificationType getType(
    ) {
        return this._type;
    }

    /**
     * Returns the value of field 'updated'.
     * 
     * @return the value of field 'Updated'.
     */
    public boolean getUpdated(
    ) {
        return this._updated;
    }

    /**
     * Method hasActive.
     * 
     * @return true if at least one Active has been added
     */
    public boolean hasActive(
    ) {
        return this._has_active;
    }

    /**
     * Method hasUpdated.
     * 
     * @return true if at least one Updated has been added
     */
    public boolean hasUpdated(
    ) {
        return this._has_updated;
    }

    /**
     * Returns the value of field 'active'.
     * 
     * @return the value of field 'Active'.
     */
    public boolean isActive(
    ) {
        return this._active;
    }

    /**
     * Returns the value of field 'updated'.
     * 
     * @return the value of field 'Updated'.
     */
    public boolean isUpdated(
    ) {
        return this._updated;
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
     * Sets the value of field 'active'.
     * 
     * @param active the value of field 'active'.
     */
    public void setActive(
            final boolean active) {
        this._active = active;
        this._has_active = true;
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
     * Sets the value of field 'id'.
     * 
     * @param id the value of field 'id'.
     */
    public void setId(
            final java.lang.String id) {
        this._id = id;
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
     * Sets the value of field 'origin'.
     * 
     * @param origin the value of field 'origin'.
     */
    public void setOrigin(
            final turbomeca.gamme.assembly.services.model.data.types.NotificationOriginType origin) {
        this._origin = origin;
    }

    /**
     * Sets the value of field 'refId'.
     * 
     * @param refId the value of field 'refId'.
     */
    public void setRefId(
            final java.lang.Object refId) {
        this._refId = refId;
    }

    /**
     * Sets the value of field 'request'.
     * 
     * @param request the value of field 'request'.
     */
    public void setRequest(
            final turbomeca.gamme.assembly.services.model.data.Request request) {
        this._request = request;
    }

    /**
     * Sets the value of field 'response'.
     * 
     * @param response the value of field 'response'.
     */
    public void setResponse(
            final turbomeca.gamme.assembly.services.model.data.Response response) {
        this._response = response;
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
     * Sets the value of field 'type'.
     * 
     * @param type the value of field 'type'.
     */
    public void setType(
            final turbomeca.gamme.assembly.services.model.data.types.NotificationType type) {
        this._type = type;
    }

    /**
     * Sets the value of field 'updated'.
     * 
     * @param updated the value of field 'updated'.
     */
    public void setUpdated(
            final boolean updated) {
        this._updated = updated;
        this._has_updated = true;
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
     * turbomeca.gamme.assembly.services.model.data.ElectronicNotification
     */
    public static turbomeca.gamme.assembly.services.model.data.ElectronicNotification unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (turbomeca.gamme.assembly.services.model.data.ElectronicNotification) org.exolab.castor.xml.Unmarshaller.unmarshal(turbomeca.gamme.assembly.services.model.data.ElectronicNotification.class, reader);
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
