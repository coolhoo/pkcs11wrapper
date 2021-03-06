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

package iaik.pkcs.pkcs11.params;

import java.util.Arrays;

import iaik.pkcs.pkcs11.Util;
import sun.security.pkcs11.wrapper.CK_SSL3_RANDOM_DATA;

/**
 * This class encapsulates parameters for the Mechanism.SSL3_MASTER_KEY_DERIVE
 * and Mechanism.SSL3_KEY_AND_MAC_DERIVE mechanisms.
 *
 * @author Karl Scheibelhofer
 * @version 1.0
 * @invariants (clientRandom <> null)
 *             and (serverRandom <> null)
 */
@SuppressWarnings("restriction")
// CHECKSTYLE:SKIP
public class SSL3RandomDataParams implements Params {

  /**
   * The client's random data.
   */
  protected byte[] clientRandom;

  /**
   * The server's random data.
   */
  protected byte[] serverRandom;

  /**
   * Create a new SSL3RandomDataParameters object with the given client and
   * server random.
   *
   * @param clientRandom
   *          The client's random data.
   * @param serverRandom
   *          The server's random data.
   * @preconditions (clientRandom <> null)
   *                and (serverRandom <> null)
   * @postconditions
   */
  public SSL3RandomDataParams(byte[] clientRandom, byte[] serverRandom) {
    this.clientRandom = Util.requireNonNull("clientRandom", clientRandom);
    this.serverRandom = Util.requireNonNull("serverRandom", serverRandom);
  }

  /**
   * Get this parameters object as a CK_SSL3_RANDOM_DATA object.
   *
   * @return This object as a CK_SSL3_RANDOM_DATA object.
   * @preconditions
   * @postconditions (result <> null)
   */
  @Override
  public Object getPKCS11ParamsObject() {
    return new CK_SSL3_RANDOM_DATA(clientRandom, serverRandom);
  }

  /**
   * Get the client's random data.
   *
   * @return The client's random data.
   * @preconditions
   * @postconditions (result <> null)
   */
  public byte[] getClientRandom() {
    return clientRandom;
  }

  /**
   * Get the server's random data.
   *
   * @return The server's random data.
   * @preconditions
   * @postconditions (result <> null)
   */
  public byte[] getServerRandom() {
    return serverRandom;
  }

  /**
   * Set the client's random data.
   *
   * @param clientRandom
   *          The client's random data.
   * @preconditions (clientRandom <> null)
   * @postconditions
   */
  public void setClientRandom(byte[] clientRandom) {
    this.clientRandom = Util.requireNonNull("clientRandom", clientRandom);
  }

  /**
   * Set the server's random data.
   *
   * @param serverRandom
   *          The server's random data.
   * @preconditions (serverRandom <> null)
   * @postconditions
   */
  public void setServerRandom(byte[] serverRandom) {
    this.serverRandom = Util.requireNonNull("serverRandom", serverRandom);
  }

  /**
   * Returns the string representation of this object. Do not parse data from
   * this string, it is for debugging only.
   *
   * @return A string representation of this object.
   */
  @Override
  public String toString() {
    return Util.concat("  Client Random (hex): ", Util.toHex(clientRandom),
        "\n  Server Random (hex): ", Util.toHex(serverRandom));
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
  public boolean equals(Object otherObject) {
    if (this == otherObject) {
      return true;
    } else if (!(otherObject instanceof SSL3RandomDataParams)) {
      return false;
    }

    SSL3RandomDataParams other = (SSL3RandomDataParams) otherObject;
    return Arrays.equals(this.clientRandom, other.clientRandom)
        && Arrays.equals(this.serverRandom, other.serverRandom);
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
    return Util.hashCode(clientRandom)
        ^ Util.hashCode(serverRandom);
  }

}
