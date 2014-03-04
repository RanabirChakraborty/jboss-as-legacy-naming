/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2013, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.legacy.jnp.infinispan;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import org.jboss.ha.framework.interfaces.ClusterNode;

/**
 *
 * @author <a href="mailto:ehugonne@redhat.com">Emmanuel Hugonnet</a> (c) 2013 Red Hat, inc.
 */
public class LegacyClusterNodeAdapter implements ClusterNode {

    private ClusterNodeProxy node;

    public LegacyClusterNodeAdapter(ClusterNodeProxy node) {
        this.node = node;
    }

    @Override
    public String getName() {
        return node.getName();
    }

    @Override
    public InetAddress getIpAddress() {
        return node.getIpAddress();
    }

    @Override
    public int getPort() {
        return node.getPort();
    }

    private String getId(ClusterNode node) {
        return getIpAddress().getHostAddress() + ":" + getPort();
    }

    @Override
    public int compareTo(Object o) {
        if ((o == null) || !(o instanceof ClusterNode)) {
            throw new ClassCastException("ClusterNode.compareTo(): comparison between different classes");
        }
        return getId(this).compareTo(getId((ClusterNode) o));
    }

    public static Vector convertToVector(List<ClusterNodeProxy> nodes) {
        Vector result = new Vector(nodes.size());
        for (ClusterNodeProxy node : nodes) {
            result.add(new LegacyClusterNodeAdapter(node));
        }
        return result;
    }

    public static ClusterNode[] convertToArray(List<ClusterNodeProxy> nodes) {
        List<ClusterNode> result = new ArrayList<ClusterNode>(nodes.size());
        for (ClusterNodeProxy node : nodes) {
            result.add(new LegacyClusterNodeAdapter(node));
        }
        return result.toArray(new ClusterNode[nodes.size()]);
    }
}
