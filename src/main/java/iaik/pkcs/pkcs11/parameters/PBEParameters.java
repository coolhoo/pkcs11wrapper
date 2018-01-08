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

package iaik.pkcs.pkcs11.parameters;

import iaik.pkcs.pkcs11.TokenRuntimeException;
import iaik.pkcs.pkcs11.Util;
import iaik.pkcs.pkcs11.wrapper.Constants;
import iaik.pkcs.pkcs11.wrapper.Functions;
import sun.security.pkcs11.wrapper.CK_PBE_PARAMS;

/**
 * This class encapsulates parameters for the Mechanism.PBA_* and
 * Mechanism.PBA_SHA1_WITH_SHA1_HMAC mechanisms.
 *
 * @author Karl Scheibelhofer
 * @version 1.0
 * @invariants (initializationVector == null)
 *             or ((initializationVector <> null)
 *                 and (initializationVector.length == 8))
 *             and (password <> null)
 *             and (salt <> null)
 */
@SuppressWarnings("restriction")
// CHECKSTYLE:SKIP
public class PBEParameters implements Parameters {

    /**
     * The 8-byte initialization vector (IV), if an IV is required.
     */
    protected char[] iv;

    /**
     * The password to be used in the PBE key generation.
     */
    protected char[] password;

    /**
     * The salt to be used in the PBE key generation.
     */
    protected char[] salt;

    /**
     * The number of iterations required for the generation.
     */
    protected long iterations;

    /**
     * Create a new PBEDeriveParameters object with the given attributes.
     *
     * @param initializationVector
     *          The 8-byte initialization vector (IV), if an IV is required.
     * @param password
     *          The password to be used in the PBE key generation.
     * @param salt
     *          The salt to be used in the PBE key generation.
     * @param iterations
     *          The number of iterations required for the generation.
     * @preconditions (initializationVector == null)
     *                or ((initializationVector <> null)
     *                    and (initializationVector.length == 8))
     *                and (password <> null)
     *                and (salt <> null)
     * @postconditions
     */
    public PBEParameters(char[] iv,
            char[] password,
            char[] salt,
            long iterations) {
        if ((iv != null) && (iv.length != 8)) {
            throw new IllegalArgumentException(
                "Argument \"initializationVector\" must be null or must have"
                + " length 8, if it is not null.");
        }
        this.iv = iv;
        this.password = Util.requireNonNull("password", password);
        this.salt = Util.requireNonNull("salt", salt);
        this.iterations = iterations;
    }

    /**
     * Create a (deep) clone of this object.
     *
     * @return A clone of this object.
     * @preconditions
     * @postconditions (result <> null)
     *                 and (result instanceof PBEParameters)
     *                 and (result.equals(this))
     */
    @Override
    public java.lang.Object clone() {
        PBEParameters clone;

        try {
            clone = (PBEParameters) super.clone();

            clone.iv = (char[]) this.iv.clone();
            clone.password = (char[]) this.password.clone();
            clone.salt = (char[]) this.salt.clone();
        } catch (CloneNotSupportedException ex) {
            // this must not happen, because this class is cloneable
            throw new TokenRuntimeException(
                    "An unexpected clone exception occurred.", ex);
        }

        return clone;
    }

    /**
     * Get this parameters object as an object of the CK_PBE_PARAMS class.
     *
     * @return This object as a CK_PBE_PARAMS object.
     * @preconditions
     * @postconditions (result <> null)
     */
    @Override
    public Object getPKCS11ParamsObject() {
        CK_PBE_PARAMS params = new CK_PBE_PARAMS();

        params.pInitVector = iv;
        params.pPassword = password;
        params.pSalt = salt;
        params.ulIteration = iterations;

        return params;
    }

    /**
     * Get the 8-byte initialization vector (IV), if an IV is required.
     *
     * @return The 8-byte initialization vector (IV), if an IV is required.
     * @preconditions
     * @postconditions (result == null)
     *                 or ((result <> null)
     *                     and (result.length == 8))
     */
    public char[] getInitializationVector() {
        return iv;
    }

    /**
     * Get the password to be used in the PBE key generation.
     *
     * @return The password to be used in the PBE key generation.
     * @preconditions
     * @postconditions (result <> null)
     */
    public char[] getPassword() {
        return password;
    }

    /**
     * Get the salt to be used in the PBE key generation.
     *
     * @return The salt to be used in the PBE key generation.
     * @preconditions
     * @postconditions (result <> null)
     */
    public char[] getSalt() {
        return salt;
    }

    /**
     * Get the number of iterations required for the generation.
     *
     * @return The number of iterations required for the generation.
     * @preconditions
     * @postconditions
     */
    public long getIterations() {
        return iterations;
    }

    /**
     * Set the 8-byte initialization vector (IV), if an IV is required.
     *
     * @param initializationVector
     *          The 8-byte initialization vector (IV), if an IV is required.
     * @preconditions (initializationVector == null)
     *                or ((initializationVector <> null)
     *                    and (initializationVector.length == 8))
     * @postconditions
     */
    public void setInitializationVector(char[] iv) {
        if ((iv != null) && (iv.length != 8)) {
            throw new IllegalArgumentException(
                "Argument \"iv\" must be null or must have "
                + "length 8, if it is not null.");
        }
        this.iv = iv;
    }

    /**
     * Set the password to be used in the PBE key generation.
     *
     * @param password
     *          The password to be used in the PBE key generation.
     * @preconditions (password <> null)
     * @postconditions
     */
    public void setPassword(char[] password) {
        this.password = Util.requireNonNull("password", password);
    }

    /**
     * Set the salt to be used in the PBE key generation.
     *
     * @param salt
     *          The salt to be used in the PBE key generation.
     * @preconditions (salt <> null)
     * @postconditions
     */
    public void setSalt(char[] salt) {
        this.salt = Util.requireNonNull("salt", salt);;
    }

    /**
     * Set the number of iterations required for the generation.
     *
     * @param iterations
     *          The number of iterations required for the generation.
     * @preconditions
     * @postconditions
     */
    public void setIterations(long iterations) {
        this.iterations = iterations;
    }

    /**
     * Returns the string representation of this object. Do not parse data from
     * this string, it is for debugging only.
     *
     * @return A string representation of this object.
     */
    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();

        buffer.append(Constants.INDENT);
        buffer.append("Initialization Vector: ");
        buffer.append((iv != null) ? new String(iv) : null);

        buffer.append(Constants.NEWLINE_INDENT);
        buffer.append("Password: ");
        buffer.append((password != null) ? new String(password) : null);

        buffer.append(Constants.NEWLINE_INDENT);
        buffer.append("Salt: ");
        buffer.append((salt != null) ? new String(salt) : null);

        buffer.append(Constants.NEWLINE_INDENT);
        buffer.append("Iterations (dec): ");
        buffer.append(iterations);

        return buffer.toString();
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

        if (!(otherObject instanceof PBEParameters)) {
            return false;
        }

        PBEParameters other = (PBEParameters) otherObject;
        return Functions.equals(this.iv, other.iv)
               && Functions.equals(this.password, other.password)
               && Functions.equals(this.salt, other.salt)
               && (this.iterations == other.iterations);
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
        return Functions.hashCode(iv)
                ^ Functions.hashCode(password)
                ^ Functions.hashCode(salt)
                ^ ((int) iterations);
    }

}
