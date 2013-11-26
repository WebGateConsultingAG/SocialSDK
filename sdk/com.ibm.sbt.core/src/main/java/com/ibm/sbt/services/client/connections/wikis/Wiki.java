/*
 * � Copyright IBM Corp. 2013
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

package com.ibm.sbt.services.client.connections.wikis;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.w3c.dom.Node;

import com.ibm.commons.util.StringUtil;
import com.ibm.commons.xml.NamespaceContext;
import com.ibm.commons.xml.xpath.XPathExpression;
import com.ibm.sbt.services.client.base.AtomEntity;
import com.ibm.sbt.services.client.base.AtomXPath;
import com.ibm.sbt.services.client.base.BaseService;
import com.ibm.sbt.services.client.base.ConnectionsConstants;
import com.ibm.sbt.services.client.base.datahandlers.XmlDataHandler;
import com.ibm.sbt.services.client.connections.activity.Member;
import com.ibm.sbt.services.client.connections.common.Person;

/**
 * @author Mario Duarte
 *
 */
public class Wiki extends AtomEntity {

	/**
	 * 
	 * @param service
	 * @param node
	 * @param namespaceCtx
	 * @param xpathExpression
	 */
	public Wiki(BaseService service, Node node, NamespaceContext namespaceCtx, 
			XPathExpression xpathExpression) {
		super(service, node, namespaceCtx, xpathExpression);
	}
	
	/**
	 * 
	 * @param service
	 * @param dataHandler
	 */
	public Wiki(BaseService service, XmlDataHandler dataHandler) {
		super(service, dataHandler);
	}
	
	/**
	 * Create empty wiki with no DataHandler
	 * @param service
	 */
	public Wiki(BaseService service) {
		super(service, null);
	}
	
	/**
	 * Unique identifier of a wiki.
	 * @return wikiUuid
	 */
	public String getUuid() {
		return getAsString(WikiXPath.uuid);
	}
	
	/**
	 * Label of a wiki.
	 * @return wikiUuid
	 */
	public String getLabel() {
		return getAsString(WikiXPath.label);
	}
	
	/**
	 * 
	 * @param label
	 */
	public void setLabel(String label) {
		setAsString(WikiXPath.label, label);
	}
	
	/**
	 * Community to which the wiki belongs to
	 * @return communityUuid
	 */
	public String getCommunityUuid() {
		return getAsString(WikiXPath.communityUuid);
	}
	
	/**
	 * 
	 * @return boolean indication whether or not this wiki is a community wiki.
	 */
	public boolean isCommunityWiki() {
		return getCommunityUuid() != null;
	}
	
	/**
	 * @return
	 */
	public Person getModifier() {
		return new Person(getService(), new XmlDataHandler((Node)this.getDataHandler().getData(), 
    			ConnectionsConstants.nameSpaceCtx, (XPathExpression)AtomXPath.modifier.getPath()));
	}
	
	/**
	 * Date of the wiki creation
	 * @return
	 */
	public Date getCreated() {
		return this.getAsDate(WikiXPath.created);
	}
	
	/**
	 * Date of the last modification
	 * @return
	 */
	public Date getModified() {
		return this.getAsDate(WikiXPath.modified);
	}
	
	/**
	 * List of users who can access the wiki. 
	 * @return
	 */
	public List<Member> getSharedWith() {
		throw new UnsupportedOperationException(); // FIXME
	}
	
	/**
	 * Set of permissions available for the wiki.
	 * @return
	 */
	public Set<String> getPermissions() {
		Set<String> permissions = null;
		String permissionsStr = getAsString(WikiXPath.permissions);
		if(!StringUtil.isEmpty(permissionsStr)) {
			permissions = new HashSet<String>();
			for(String p : permissionsStr.split(",")) {
				permissions.add(p.trim());
			}
		}
		return permissions;
	}
	
	/**
	 * Specify who can access the wiki. 
	 * @param members
	 */
	public void setSharedWith(List<Member> members) {
		throw new UnsupportedOperationException(); // FIXME
	}
}