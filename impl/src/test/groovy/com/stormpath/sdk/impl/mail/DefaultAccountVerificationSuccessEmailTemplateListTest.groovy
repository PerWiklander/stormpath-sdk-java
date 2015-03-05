/*
* Copyright 2015 Stormpath, Inc.
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
package com.stormpath.sdk.impl.mail

import com.stormpath.sdk.impl.ds.InternalDataStore
import com.stormpath.sdk.impl.resource.ArrayProperty
import com.stormpath.sdk.impl.resource.IntegerProperty
import com.stormpath.sdk.mail.AccountVerificationSuccessEmailTemplate
import com.stormpath.sdk.mail.AccountVerificationSuccessEmailTemplateList
import org.testng.annotations.Test

import static org.easymock.EasyMock.createStrictMock
import static org.testng.Assert.assertEquals
import static org.testng.Assert.assertTrue

/**
 * @since 1.0.RC4
 */
class DefaultAccountVerificationSuccessEmailTemplateListTest {

    @Test
    void testAll() {

        def internalDataStore = createStrictMock(InternalDataStore)

        AccountVerificationSuccessEmailTemplateList resourceWithDS = new DefaultAccountVerificationSuccessEmailTemplateList(internalDataStore)
        AccountVerificationSuccessEmailTemplateList resourceWithProps = new DefaultAccountVerificationSuccessEmailTemplateList(internalDataStore, [href: "https://api.stormpath.com/v1/accountCreationPolicies/35YM3OwioW9PVtfLOh6q1e/verificationEmailTemplates"])
        AccountVerificationSuccessEmailTemplateList resourceWithQueryString = new DefaultAccountVerificationSuccessEmailTemplateList(internalDataStore, [href: "https://api.stormpath.com/v1/accountCreationPolicies/35YM3OwioW9PVtfLOh6q1e/verificationEmailTemplates"], [qp: "test"])

        assertTrue(resourceWithDS instanceof DefaultAccountVerificationSuccessEmailTemplateList
                && resourceWithProps instanceof DefaultAccountVerificationSuccessEmailTemplateList
                && resourceWithQueryString instanceof DefaultAccountVerificationSuccessEmailTemplateList)

        assertEquals(resourceWithQueryString.getItemType(), AccountVerificationSuccessEmailTemplate)

        def propertyDescriptors = resourceWithProps.getPropertyDescriptors()

        assertEquals(propertyDescriptors.size(), 3)
        assertTrue(propertyDescriptors.get("items") instanceof ArrayProperty && propertyDescriptors.get("offset") instanceof IntegerProperty && propertyDescriptors.get("limit") instanceof IntegerProperty)
        assertEquals(propertyDescriptors.get("items").getType(), AccountVerificationSuccessEmailTemplate)
        assertEquals(((DefaultAccountVerificationSuccessEmailTemplateList)resourceWithDS).getItemType(), AccountVerificationSuccessEmailTemplate)
    }
}
