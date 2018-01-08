// Copyright (c) 2002 Graz University of Technology. All rights reserved.
//
// Redistribution and use in source and binary forms, with or without
// modification, are permitted provided that the following conditions are met:
//
// 1. Redistributions of source code must retain the above copyright notice,
//    this list of conditions and the following disclaimer.
//
// 2. Redistributions in binary form must reproduce the above copyright notice,
//    this list of conditions and the following disclaimer in the documentation
//    and/or other materials provided with the distribution.
//
// 3. The end-user documentation included with the redistribution, if any, must
//    include the following acknowledgment:
//
//    "This product includes software developed by IAIK of Graz University of
//     Technology."
//
//    Alternately, this acknowledgment may appear in the software itself, if and
//    wherever such third-party acknowledgments normally appear.
//
// 4. The names "Graz University of Technology" and "IAIK of Graz University of
//    Technology" must not be used to endorse or promote products derived from
//    this software without prior written permission.
//
// 5. Products derived from this software may not be called "IAIK PKCS Wrapper",
//    nor may "IAIK" appear in their name, without prior written permission of
//    Graz University of Technology.
//
// THIS SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED
// WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
// WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
// PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE LICENSOR BE
// LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
// OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
// PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
// OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
// ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
// OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
// OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
// POSSIBILITY OF SUCH DAMAGE.

package iaik.pkcs.pkcs11.objects;

import iaik.pkcs.pkcs11.Session;
import iaik.pkcs.pkcs11.TokenException;
import iaik.pkcs.pkcs11.Util;
import iaik.pkcs.pkcs11.wrapper.Constants;

/**
 * Objects of this class represent X.509 attribute certificate as specified by
 * PKCS#11 v2.11.
 *
 * @author Karl Scheibelhofer
 * @version 1.0
 * @invariants (owner <> null)
 *             and (acIssuer <> null)
 *             and (serialNumber <> null)
 *             and (attrTypes <> null)
 *             and (value <> null)
 */
public class X509AttributeCertificate extends Certificate {

    /**
     * The owner attribute of this certificate.
     */
    protected ByteArrayAttribute owner;

    /**
     * The owner attribute of this certificate.
     */
    protected ByteArrayAttribute acIssuer;

    /**
     * The serial number attribute of this certificate.
     */
    protected ByteArrayAttribute serialNumber;

    /**
     * The attribute types attribute of this certificate.
     */
    protected ByteArrayAttribute attrTypes;

    /**
     * The value attribute of this certificate; i.e. BER-encoded certificate.
     */
    protected ByteArrayAttribute value;

    /**
     * Default Constructor.
     *
     * @preconditions
     * @postconditions
     */
    public X509AttributeCertificate() {
        super();
        certificateType.setLongValue(CertificateType.X_509_ATTRIBUTE);
    }

    /**
     * Called by getInstance to create an instance of a PKCS#11 X.509 attribute
     * certificate.
     *
     * @param session
     *          The session to use for reading attributes. This session must
     *          have the appropriate rights; i.e. it must be a user-session, if
     *          it is a private object.
     * @param objectHandle
     *          The object handle as given from the PKCS#111 module.
     * @exception TokenException
     *              If getting the attributes failed.
     * @preconditions (session <> null)
     * @postconditions
     */
    protected X509AttributeCertificate(Session session, long objectHandle)
        throws TokenException {
        super(session, objectHandle);
        certificateType.setLongValue(CertificateType.X_509_ATTRIBUTE);
    }

    /**
     * The getInstance method of the Certificate class uses this method to
     * create an instance of a PKCS#11 X.509 attribute certificate.
     *
     * @param session
     *          The session to use for reading attributes. This session must
     *          have the appropriate rights; i.e. it must be a user-session, if
     *          it is a private object.
     * @param objectHandle
     *          The object handle as given from the PKCS#111 module.
     * @return The object representing the PKCS#11 object.
     *         The returned object can be casted to the
     *         according sub-class.
     * @exception TokenException
     *              If getting the attributes failed.
     * @preconditions (session <> null)
     * @postconditions (result <> null)
     */
    public static Object getInstance(Session session, long objectHandle)
        throws TokenException {
        return new X509AttributeCertificate(session, objectHandle);
    }

    /**
     * Put all attributes of the given object into the attributes table of this
     * object. This method is only static to be able to access invoke the
     * implementation of this method for each class separately (see use in
     * clone()).
     *
     * @param object
     *          The object to handle.
     * @preconditions (object <> null)
     * @postconditions
     */
    protected static void putAttributesInTable(
            X509AttributeCertificate object) {
        Util.requireNonNull("object", object);
        object.attributeTable.put(Attribute.OWNER, object.owner);
        object.attributeTable.put(Attribute.AC_ISSUER, object.acIssuer);
        object.attributeTable.put(Attribute.SERIAL_NUMBER,
                object.serialNumber);
        object.attributeTable.put(Attribute.ATTR_TYPES, object.attrTypes);
        object.attributeTable.put(Attribute.VALUE, object.value);
    }

    /**
     * Allocates the attribute objects for this class and adds them to the
     * attribute table.
     *
     * @preconditions
     * @postconditions
     */
    @Override
    protected void allocateAttributes() {
        super.allocateAttributes();

        owner = new ByteArrayAttribute(Attribute.OWNER);
        acIssuer = new ByteArrayAttribute(Attribute.AC_ISSUER);
        serialNumber = new ByteArrayAttribute(Attribute.SERIAL_NUMBER);
        attrTypes = new ByteArrayAttribute(Attribute.ATTR_TYPES);
        value = new ByteArrayAttribute(Attribute.VALUE);

        putAttributesInTable(this);
    }

    /**
     * Create a (deep) clone of this object.
     *
     * @return A clone of this object.
     * @preconditions
     * @postconditions (result <> null)
     *                 and (result instanceof X509AttributeCertificate)
     *                 and (result.equals(this))
     */
    @Override
    public java.lang.Object clone() {
        X509AttributeCertificate clone
            = (X509AttributeCertificate) super.clone();

        clone.owner = (ByteArrayAttribute) this.owner.clone();
        clone.acIssuer = (ByteArrayAttribute) this.acIssuer.clone();
        clone.serialNumber = (ByteArrayAttribute) this.serialNumber.clone();
        clone.attrTypes = (ByteArrayAttribute) this.attrTypes.clone();
        clone.value = (ByteArrayAttribute) this.value.clone();

        // put all cloned attributes into the new table
        putAttributesInTable(clone);

        return clone;
    }

    /**
     * Compares all member variables of this object with the other object.
     * Returns only true, if all are equal in both objects.
     *
     * @param otherObject
     *          The other object to compare to.
     * @return True, if other is an instance of this class and all member
     *         variables of both objects are equal. False, otherwise.
     * @preconditions
     * @postconditions
     */
    @Override
    public boolean equals(java.lang.Object otherObject) {
        if (this == otherObject) {
            return true;
        }

        if (!(otherObject instanceof X509AttributeCertificate)) {
            return false;
        }

        X509AttributeCertificate other = (X509AttributeCertificate) otherObject;
        return super.equals(other)
                && this.owner.equals(other.owner)
                && this.acIssuer.equals(other.acIssuer)
                && this.serialNumber.equals(other.serialNumber)
                && this.attrTypes.equals(other.attrTypes)
                && this.value.equals(other.value);
    }

    /**
     * Gets the owner attribute of this X.509 attribute certificate.
     *
     * @return The owner attribute of this X.509 attribute certificate.
     * @preconditions
     * @postconditions (result <> null)
     */
    public ByteArrayAttribute getOwner() {
        return owner;
    }

    /**
     * Gets the attribute certificate issuer attribute of this X.509 attribute
     * certificate.
     *
     * @return The attribute certificate issuer attribute of this X.509
     *         attribute certificate.
     * @preconditions
     * @postconditions (result <> null)
     */
    public ByteArrayAttribute getAcIssuer() {
        return acIssuer;
    }

    /**
     * Gets the serial number attribute of this X.509 attribute certificate.
     *
     * @return The serial number attribute of this X.509 attribute certificate.
     * @preconditions
     * @postconditions (result <> null)
     */
    public ByteArrayAttribute getSerialNumber() {
        return serialNumber;
    }

    /**
     * Gets the attribute types attribute of this X.509 attribute certificate.
     *
     * @return The attribute types attribute of this X.509 attribute
     *         certificate.
     * @preconditions
     * @postconditions (result <> null)
     */
    public ByteArrayAttribute getAttrTypes() {
        return attrTypes;
    }

    /**
     * Gets the value attribute of this X.509 attribute certificate.
     *
     * @return The value attribute of this X.509 attribute certificate.
     * @preconditions
     * @postconditions (result <> null)
     */
    public ByteArrayAttribute getValue() {
        return value;
    }

    /**
     * The overriding of this method should ensure that the objects of this
     * class work correctly in a hashtable.
     *
     * @return The hash code of this object.
     * @preconditions
     * @postconditions
     */
    @Override
    public int hashCode() {
        return acIssuer.hashCode() ^ serialNumber.hashCode();
    }

    /**
     * Read the values of the attributes of this object from the token.
     *
     * @param session
     *          The session to use for reading attributes. This session must
     *          have the appropriate rights; i.e. it must be a user-session, if
     *          it is a private object.
     * @exception TokenException
     *              If getting the attributes failed.
     * @preconditions (session <> null)
     * @postconditions
     */
    @Override
    public void readAttributes(Session session)
        throws TokenException {
        super.readAttributes(session);

        Object.getAttributeValues(session, objectHandle, new Attribute[] {
            owner, acIssuer, serialNumber, attrTypes, value });
    }

    /**
     * Returns a string representation of the current object. The
     * output is only for debugging purposes and should not be used for other
     * purposes.
     *
     * @return A string presentation of this object for debugging output.
     * @preconditions
     * @postconditions (result <> null)
     */
    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder(256);

        buffer.append(super.toString());

        buffer.append(Constants.NEWLINE_INDENT);
        buffer.append("Owner (DER, hex): ");
        buffer.append(owner.toString());

        buffer.append(Constants.NEWLINE_INDENT);
        buffer.append("Attribute Certificate Issuer (DER, hex): ");
        buffer.append(acIssuer.toString());

        buffer.append(Constants.NEWLINE_INDENT);
        buffer.append("Serial Number (DER, hex): ");
        buffer.append(serialNumber.toString());

        buffer.append(Constants.NEWLINE_INDENT);
        buffer.append("Attribute Types (BER, hex): ");
        buffer.append(attrTypes.toString());

        buffer.append(Constants.NEWLINE_INDENT);
        buffer.append("Value (BER, hex): ");
        buffer.append(value.toString());

        return buffer.toString();
    }

}
