/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3</a>, using an XML
 * Schema.
 * $Id$
 */

package turbomeca.gamme.assembly.services.model.data;

/**
 * Class NotificationRequestType.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class NotificationRequestType implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _userMark.
     */
    private turbomeca.gamme.assembly.services.model.data.UserMark _userMark;

    /**
     * Field _ncr.
     */
    private java.lang.String _ncr;

    /**
     * Field _comment.
     */
    private java.lang.String _comment;

    /**
     * Field _optionnalComment.
     */
    private java.lang.String _optionnalComment;


      //----------------/
     //- Constructors -/
    //----------------/

    public NotificationRequestType() {
        super();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'comment'.
     * 
     * @return the value of field 'Comment'.
     */
    public java.lang.String getComment(
    ) {
        return this._comment;
    }

    /**
     * Returns the value of field 'ncr'.
     * 
     * @return the value of field 'Ncr'.
     */
    public java.lang.String getNcr(
    ) {
        return this._ncr;
    }

    /**
     * Returns the value of field 'optionnalComment'.
     * 
     * @return the value of field 'OptionnalComment'.
     */
    public java.lang.String getOptionnalComment(
    ) {
        return this._optionnalComment;
    }

    /**
     * Returns the value of field 'userMark'.
     * 
     * @return the value of field 'UserMark'.
     */
    public turbomeca.gamme.assembly.services.model.data.UserMark getUserMark(
    ) {
        return this._userMark;
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
     * Sets the value of field 'comment'.
     * 
     * @param comment the value of field 'comment'.
     */
    public void setComment(
            final java.lang.String comment) {
        this._comment = comment;
    }

    /**
     * Sets the value of field 'ncr'.
     * 
     * @param ncr the value of field 'ncr'.
     */
    public void setNcr(
            final java.lang.String ncr) {
        this._ncr = ncr;
    }

    /**
     * Sets the value of field 'optionnalComment'.
     * 
     * @param optionnalComment the value of field 'optionnalComment'
     */
    public void setOptionnalComment(
            final java.lang.String optionnalComment) {
        this._optionnalComment = optionnalComment;
    }

    /**
     * Sets the value of field 'userMark'.
     * 
     * @param userMark the value of field 'userMark'.
     */
    public void setUserMark(
            final turbomeca.gamme.assembly.services.model.data.UserMark userMark) {
        this._userMark = userMark;
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
     * turbomeca.gamme.assembly.services.model.data.NotificationRequestType
     */
    public static turbomeca.gamme.assembly.services.model.data.NotificationRequestType unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (turbomeca.gamme.assembly.services.model.data.NotificationRequestType) org.exolab.castor.xml.Unmarshaller.unmarshal(turbomeca.gamme.assembly.services.model.data.NotificationRequestType.class, reader);
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
