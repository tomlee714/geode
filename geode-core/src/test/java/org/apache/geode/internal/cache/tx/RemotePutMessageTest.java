/*
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license
 * agreements. See the NOTICE file distributed with this work for additional information regarding
 * copyright ownership. The ASF licenses this file to You under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License. You may obtain a
 * copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.apache.geode.internal.cache.tx;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import org.apache.geode.CancelCriterion;
import org.apache.geode.cache.RegionDestroyedException;
import org.apache.geode.distributed.internal.DistributionManager;
import org.apache.geode.distributed.internal.InternalDistributedSystem;
import org.apache.geode.distributed.internal.membership.InternalDistributedMember;
import org.apache.geode.internal.cache.CacheDistributionAdvisor;
import org.apache.geode.internal.cache.DistributedRegion;
import org.apache.geode.internal.cache.EntryEventImpl;
import org.apache.geode.internal.cache.EventID;
import org.apache.geode.internal.cache.RemoteOperationException;

public class RemotePutMessageTest {
  @Test
  public void testDistributeNotFailWithRegionDestroyedException() throws RemoteOperationException {
    EntryEventImpl event = mock(EntryEventImpl.class);
    EventID eventID = mock(EventID.class);
    DistributedRegion region = mock(DistributedRegion.class);
    InternalDistributedSystem ids = mock(InternalDistributedSystem.class);
    DistributionManager dm = mock(DistributionManager.class);
    CancelCriterion cc = mock(CancelCriterion.class);
    CacheDistributionAdvisor advisor = mock(CacheDistributionAdvisor.class);
    InternalDistributedMember member = mock(InternalDistributedMember.class);
    Set<InternalDistributedMember> replicates = new HashSet<>(Arrays.asList(member));
    RemotePutMessage.RemotePutResponse response = mock(RemotePutMessage.RemotePutResponse.class);
    Object expectedOldValue = new Object();

    when(event.getRegion()).thenReturn(region);
    when(event.getEventId()).thenReturn(eventID);
    when(region.getCacheDistributionAdvisor()).thenReturn(advisor);
    when(advisor.adviseInitializedReplicates()).thenReturn(replicates);
    when(response.waitForResult()).thenThrow(new RegionDestroyedException("", ""));
    when(region.getSystem()).thenReturn(ids);
    when(region.getDistributionManager()).thenReturn(dm);
    when(ids.getDistributionManager()).thenReturn(dm);
    when(dm.getCancelCriterion()).thenReturn(cc);
    when(dm.putOutgoing(any())).thenReturn(null);

    RemotePutMessage.distribute(event, 1, false, false, expectedOldValue, false, false);
  }
}
