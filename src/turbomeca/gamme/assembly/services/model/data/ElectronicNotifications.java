/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3</a>, using an XML
 * Schema.
 * $Id$
 */

package turbomeca.gamme.assembly.services.model.data;

/**
 * Class ElectronicNotifications.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class ElectronicNotifications implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _electronicNotificationList.
     */
    private java.util.Vector<turbomeca.gamme.assembly.services.model.data.ElectronicNotification> _electronicNotificationList;


      //----------------/
     //- Constructors -/
    //----------------/

    public ElectronicNotifications() {
        super();
        this._electronicNotificationList = new java.util.Vector<turbomeca.gamme.assembly.services.model.data.ElectronicNotification>();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * 
     * 
     * @param vElectronicNotification
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addElectronicNotification(
            final turbomeca.gamme.assembly.services.model.data.ElectronicNotification vElectronicNotification)
    throws java.lang.IndexOutOfBoundsException {
        this._electronicNotificationList.addElement(vElectronicNotification);
    }

    /**
     * 
     * 
     * @param index
     * @param vElectronicNotification
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addElectronicNotification(
            final int index,
            final turbomeca.gamme.assembly.services.model.data.ElectronicNotification vElectronicNotification)
    throws java.lang.IndexOutOfBoundsException {
        this._electronicNotificationList.add(index, vElectronicNotification);
    }

    /**
     * Method enumerateElectronicNotification.
     * 
     * @return an Enumeration over all
     * turbomeca.gamme.assembly.services.model.data.ElectronicNotification
     * elements
     */
    public java.util.Enumeration<? extends turbomeca.gamme.assembly.services.model.data.ElectronicNotification> enumerateElectronicNotification(
    ) {
        return this._electronicNotificationList.elements();
    }

    /**
     * Method getElectronicNotification.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the
     * turbomeca.gamme.assembly.services.model.data.ElectronicNotification
     * at the given index
     */
    public turbomeca.gamme.assembly.services.model.data.ElectronicNotification getElectronicNotification(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._electronicNotificationList.size()) {
            throw new IndexOutOfBoundsException("getElectronicNotification: Index value '" + index + "' not in range [0.." + (this._electronicNotificationList.size() - 1) + "]");
        }

        return (turbomeca.gamme.assembly.services.model.data.ElectronicNotification) _electronicNotificationList.get(index);
    }

    /**
     * Method getElectronicNotification.Returns the contents of the
     * collection in an Array.  <p>Note:  Just in case the
     * collection contents are changing in another thread, we pass
     * a 0-length Array of the correct type into the API call. 
     * This way we <i>know</i> that the Array returned is of
     * exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public turbomeca.gamme.assembly.services.model.data.ElectronicNotification[] getElectronicNotification(
    ) {
        turbomeca.gamme.assembly.services.model.data.ElectronicNotification[] array = new turbomeca.gamme.assembly.services.model.data.ElectronicNotification[0];
        return (turbomeca.gamme.assembly.services.model.data.ElectronicNotification[]) this._electronicNotificationList.toArray(array);
    }

    /**
     * Method getElectronicNotificationAsReference.Returns a
     * reference to '_electronicNotificationList'. No type checking
     * is performed on any modifications to the Vector.
     * 
     * @return a reference to the Vector backing this class
     */
    public java.util.Vector<turbomeca.gamme.assembly.services.model.data.ElectronicNotification> getElectronicNotificationAsReference(
    ) {
        return this._electronicNotificationList;
    }

    /**
     * Method getElectronicNotificationCount.
     * 
     * @return the size of this collection
     */
    public int getElectronicNotificationCount(
    ) {
        return this._electronicNotificationList.size();
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
     */
    public void removeAllElectronicNotification(
    ) {
        this._electronicNotificationList.clear();
    }

    /**
     * Method removeElectronicNotification.
     * 
     * @param vElectronicNotification
     * @return true if the object was removed from the collection.
     */
    public boolean removeElectronicNotification(
            final turbomeca.gamme.assembly.services.model.data.ElectronicNotification vElectronicNotification) {
        boolean removed = _electronicNotificationList.remove(vElectronicNotification);
        return removed;
    }

    /**
     * Method removeElectronicNotificationAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public turbomeca.gamme.assembly.services.model.data.ElectronicNotification removeElectronicNotificationAt(
            final int index) {
        java.lang.Object obj = this._electronicNotificationList.remove(index);
        return (turbomeca.gamme.assembly.services.model.data.ElectronicNotification) obj;
    }

    /**
     * 
     * 
     * @param index
     * @param vElectronicNotification
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setElectronicNotification(
            final int index,
            final turbomeca.gamme.assembly.services.model.data.ElectronicNotification vElectronicNotification)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._electronicNotificationList.size()) {
            throw new IndexOutOfBoundsException("setElectronicNotification: Index value '" + index + "' not in range [0.." + (this._electronicNotificationList.size() - 1) + "]");
        }

        this._electronicNotificationList.set(index, vElectronicNotification);
    }

    /**
     * 
     * 
     * @param vElectronicNotificationArray
     */
    public void setElectronicNotification(
            final turbomeca.gamme.assembly.services.model.data.ElectronicNotification[] vElectronicNotificationArray) {
        //-- copy array
        _electronicNotificationList.clear();

        for (int i = 0; i < vElectronicNotificationArray.length; i++) {
                this._electronicNotificationList.add(vElectronicNotificationArray[i]);
        }
    }

    /**
     * Sets the value of '_electronicNotificationList' by copying
     * the given Vector. All elements will be checked for type
     * safety.
     * 
     * @param vElectronicNotificationList the Vector to copy.
     */
    public void setElectronicNotification(
            final java.util.Vector<turbomeca.gamme.assembly.services.model.data.ElectronicNotification> vElectronicNotificationList) {
        // copy vector
        this._electronicNotificationList.clear();

        this._electronicNotificationList.addAll(vElectronicNotificationList);
    }

    /**
     * Sets the value of '_electronicNotificationList' by setting
     * it to the given Vector. No type checking is performed.
     * @deprecated
     * 
     * @param electronicNotificationVector the Vector to set.
     */
    public void setElectronicNotificationAsReference(
            final java.util.Vector<turbomeca.gamme.assembly.services.model.data.ElectronicNotification> electronicNotificationVector) {
        this._electronicNotificationList = electronicNotificationVector;
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
     * turbomeca.gamme.assembly.services.model.data.ElectronicNotifications
     */
    public static turbomeca.gamme.assembly.services.model.data.ElectronicNotifications unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (turbomeca.gamme.assembly.services.model.data.ElectronicNotifications) org.exolab.castor.xml.Unmarshaller.unmarshal(turbomeca.gamme.assembly.services.model.data.ElectronicNotifications.class, reader);
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
