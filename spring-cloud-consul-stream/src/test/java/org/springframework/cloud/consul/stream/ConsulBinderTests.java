/*
 * Copyright 2013-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.cloud.consul.stream;

import org.springframework.cloud.stream.binder.Binder;
import org.springframework.cloud.stream.binder.PartitionCapableBinderTests;
import org.springframework.cloud.stream.binder.Spy;

import com.ecwid.consul.v1.ConsulClient;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Spencer Gibb
 */
public class ConsulBinderTests extends PartitionCapableBinderTests {

	private Binder binder;

	@Override
	protected boolean usesExplicitRouting() {
		return true;
	}

	@Override
	protected String getClassUnderTestName() {
		return ConsulMessageChannelBinder.class.getSimpleName();
	}

	@Override
	public Spy spyOn(String name) {
		return null;
	}

	@Override
	protected Binder getBinder() throws Exception {
		if (binder == null) {
			EventService eventService = new EventService(new ConsulStreamProperties(), new ConsulClient(), new ObjectMapper());
			binder = new ConsulTestBinder(eventService);
		}
		return binder;
	}

	@Override
	protected void binderBindUnbindLatency() throws InterruptedException {
		Thread.sleep(1000);
	}
}