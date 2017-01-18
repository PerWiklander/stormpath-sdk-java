/*
* Copyright 2016 Stormpath, Inc.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.stormpath.sdk.factor.google;

import com.stormpath.sdk.challenge.google.GoogleAuthenticatorChallenge;
import com.stormpath.sdk.factor.Factor;

/**
 * A {@code GoogleAuthenticatorFactor} is a Factor that represents a two-step verification services using
 * the Time-based One-time Password Algorithm (TOTP) and HMAC-based One-time Password Algorithm (HOTP),
 * for authenticating users of mobile applications by Google.
 * <p/>
 * The TOTP algorithm determines the next valid code without the requirement for any communication between server and
 * TOTP client.
 * <p/>
 * As such, the challenge can be created with the code at the same time:
 * <p/>
 *
 * <pre>
 * GoogleAuthenticatorChallenge challenge = googleAuthenticatorFactor.createChallenge(code);
 * </pre>
 *
 * @since 1.1.0
 */
public interface GoogleAuthenticatorFactor<T extends GoogleAuthenticatorChallenge> extends Factor<T> {
    /**
     * Returns the account name. Defaults to account's username
     *
     * @return the account name.
     */
    String getAccountName();

    /**
     * Sets the the account name. Defaults to account's username.
     *
     * @param accountName the account name to be set.
     *
     * @return this instance for method chaining.
     */
    GoogleAuthenticatorFactor setAccountName(String accountName);

    /**
     * Returns the issuer. Defaults to 'Stormpath'.
     *
     * @return the issuer.
     */
    String getIssuer();

    /**
     * Sets the the issuer. Defaults to 'Stormpath'.
     *
     * @param issuer the account name to be set.
     *
     * @return this instance for method chaining.
     */
    GoogleAuthenticatorFactor setIssuer(String issuer);

    /**
     * Returns the secret generated by Stormpath.
     *
     * @return the secret.
     */
    String getSecret();

    /**
     * Returns the keyUri generated by Stormpath.
     *
     * @return the keyUri.
     */
    String getKeyUri();

    /**
     * Returns the base64QRImage generated by Stormpath.
     *
     * @return the base64QRImage.
     */
    String getBase64QrImage();

    /**
     *
     * @param code The code to validate the challenge that is created. With Google Authenticator, you can create a
     *             challenge and validate it all in one call.
     * @return the {@link GoogleAuthenticatorChallenge}
     */
    GoogleAuthenticatorChallenge createChallenge(String code);
}
