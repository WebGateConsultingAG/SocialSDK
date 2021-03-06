/*
 * © Copyright IBM Corp. 2013
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at:
 * 
 * http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or 
 * implied. See the License for the specific language governing 
 * permissions and limitations under the License.
 */
package com.ibm.sbt.test.java.connections.forums;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.ibm.sbt.automation.core.test.BaseAuthJavaServiceTest;

/**
 * @author mkataria
 * @date Nov 14, 2013
 */
public class ForumTopicsTags extends BaseAuthJavaServiceTest {

    @Test
    public void runTest() {
        boolean result = checkNoError("Social_Forums_Forum_Topics_Tags");
        assertTrue(getNoErrorMsg(), result);
    }

}
