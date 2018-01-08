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
 * Objects of this class represent WTLS public key certificates as specified by
 * PKCS#11 v2.20 Chap. 10.6.4.
 *
 * @author Florian Reimair
 * @version 1.0
 */
// CHECKSTYLE:SKIP
public class WTLSCertificate extends Certificate {

    /**
     * The subject attribute of this certificate.
     */
    protected ByteArrayAttribute subject;

    /**
     * The issuer attribute of this certificate.
     */
    protected ByteArrayAttribute issuer;

    /**
     * The value attribute of this certificate; i.e. BER-encoded certificate.
     */
    protected ByteArrayAttribute value;

    /**
     * This attribute gives the URL where the complete certificate can be
     * obtained.
     */
    protected CharArrayAttribute url;

    /**
     * SHA-1 hash of the subject public key.
     */
    protected ByteArrayAttribute hashOfSubjectPublicKey;

    /**
     * SHA-1 hash of the issuer public key.
     */
    protected ByteArrayAttribute hashOfIssuerPublicKey;

    /**
     * Default Constructor.
     *
     * @preconditions
     * @postconditions
     */
    public WTLSCertificate() {
        super();
        certificateType.setLongValue(CertificateType.WTLS);
    }

    /**
     * Called by getInstance to create an instance of a PKCS#11 WTLS public key
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
    protected WTLSCertificate(Session session, long objectHandle)
        throws TokenException {
        super(session, objectHandle);
        certificateType.setLongValue(CertificateType.WTLS);
    }

    /**
     * The getInstance method of the Certificate class uses this method to
     * create an instance of a PKCS#11 WTLS public key certificate.
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
        return new WTLSCertificate(session, objectHandle);
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
    protected static void putAttributesInTable(WTLSCertificate object) {
        Util.requireNonNull("object", object);
        object.attributeTable.put(Attribute.SUBJECT, object.subject);
        object.attributeTable.put(Attribute.ISSUER, object.issuer);
        object.attributeTable.put(Attribute.VALUE, object.value);
        object.attributeTable.put(Attribute.URL, object.url);
        object.attributeTable.put(Attribute.HASH_OF_SUBJECT_PUBLIC_KEY,
            object.hashOfSubjectPublicKey);
        object.attributeTable.put(Attribute.HASH_OF_ISSUER_PUBLIC_KEY,
            object.hashOfIssuerPublicKey);
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

        subject = new ByteArrayAttribute(Attribute.SUBJECT);
        issuer = new ByteArrayAttribute(Attribute.ISSUER);
        value = new ByteArrayAttribute(Attribute.VALUE);
        url = new CharArrayAttribute(Attribute.URL);
        hashOfSubjectPublicKey
            = new ByteArrayAttribute(Attribute.HASH_OF_SUBJECT_PUBLIC_KEY);
        hashOfIssuerPublicKey
            = new ByteArrayAttribute(Attribute.HASH_OF_ISSUER_PUBLIC_KEY);

        putAttributesInTable(this);
    }

    /**
     * Create a (deep) clone of this object.
     *
     * @return A clone of this object.
     * @preconditions
     * @postconditions (result <> null)
     *                 and (result instanceof X509PublicKeyCertificate)
     *                 and (result.equals(this))
     */
    @Override
    public java.lang.Object clone() {
        WTLSCertificate clone = (WTLSCertificate) super.clone();

        clone.subject = (ByteArrayAttribute) this.subject.clone();
        clone.issuer = (ByteArrayAttribute) this.issuer.clone();
        clone.value = (ByteArrayAttribute) this.value.clone();
        clone.url = (CharArrayAttribute) this.url.clone();
        clone.hashOfSubjectPublicKey
            = (ByteArrayAttribute) this.hashOfSubjectPublicKey.clone();
        clone.hashOfIssuerPublicKey
            = (ByteArrayAttribute) this.hashOfIssuerPublicKey.clone();

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

        if (!(otherObject instanceof WTLSCertificate)) {
            return false;
        }

        WTLSCertificate other = (WTLSCertificate) otherObject;
        return super.equals(other)
                && this.subject.equals(other.subject)
                && this.issuer.equals(other.issuer)
                && this.value.equals(other.value)
                && this.url.equals(other.url)
                && this.hashOfSubjectPublicKey.equals(
                        other.hashOfSubjectPublicKey)
                && this.hashOfIssuerPublicKey.equals(
                        other.hashOfIssuerPublicKey);
    }

    /**
     * Gets the subject attribute of this WTLS public key certificate.
     *
     * @return The subject attribute of this WTLS public key certificate.
     * @preconditions
     * @postconditions (result <> null)
     */
    public ByteArrayAttribute getSubject() {
        return subject;
    }

    /**
     * Gets the issuer attribute of this WTLS public key certificate.
     *
     * @return The issuer attribute of this WTLS public key certificate.
     * @preconditions
     * @postconditions (result <> null)
     */
    public ByteArrayAttribute getIssuer() {
        return issuer;
    }

    /**
     * Gets the value attribute of this WTLS public key certificate.
     *
     * @return The value attribute of this WTLS public key certificate.
     * @preconditions
     * @postconditions (result <> null)
     */
    public ByteArrayAttribute getValue() {
        return value;
    }

    /**
     * Get the URL attribute of this object.
     *
     * @return Contains the URL as a char array.
     * @preconditions
     * @postconditions (result <> null)
     */
    public CharArrayAttribute getUrl() {
        return url;
    }

    /**
     * Gets the hash of subject public key attribute of this WTLS public key
     * certificate.
     *
     * @return The hash of subject public key attribute of this WTLS public key
     *         certificate.
     * @preconditions
     * @postconditions (result <> null)
     */
    public ByteArrayAttribute getHashOfSubjectPublicKey() {
        return hashOfSubjectPublicKey;
    }

    /**
     * Gets the hash of issuer public key attribute of this WTLS public key
     * certificate.
     *
     * @return The hash of issuer public key attribute of this WTLS public key
     *         certificate.
     * @preconditions
     * @postconditions (result <> null)
     */
    public ByteArrayAttribute getHashOfIssuerPublicKey() {
        return hashOfIssuerPublicKey;
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
        return issuer.hashCode();
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
            subject, issuer, value });
        Object.getAttributeValues(session, objectHandle, new Attribute[] {
            url, hashOfSubjectPublicKey, hashOfIssuerPublicKey });
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
        buffer.append("Subject (DER, hex): ");
        buffer.append(subject.toString());

        buffer.append(Constants.NEWLINE_INDENT);
        buffer.append("Issuer (DER, hex): ");
        buffer.append(issuer.toString());

        buffer.append(Constants.NEWLINE_INDENT);
        buffer.append("Value (BER, hex): ");
        buffer.append(value.toString());

        buffer.append(Constants.NEWLINE_INDENT);
        buffer.append("URL: ");
        buffer.append(url.toString());

        buffer.append(Constants.NEWLINE_INDENT);
        buffer.append("Hash Of Subject Public Key: ");
        buffer.append(hashOfSubjectPublicKey.toString());

        buffer.append(Constants.NEWLINE_INDENT);
        buffer.append("Hash Of Issuer Public Key: ");
        buffer.append(hashOfIssuerPublicKey.toString());

        return buffer.toString();
    }

}
