/**
 * Copyright 2014 Groupon.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.arpnetworking.tsdaggregator.test;

import com.arpnetworking.tsdaggregator.model.DefaultMetric;
import com.arpnetworking.tsdaggregator.model.DefaultRecord;
import com.arpnetworking.tsdaggregator.model.Metric;
import com.arpnetworking.tsdaggregator.model.MetricType;
import com.arpnetworking.tsdaggregator.model.Record;
import com.arpnetworking.tsdcore.model.Quantity;
import com.arpnetworking.tsdcore.model.Unit;
import com.google.common.base.Optional;

import org.joda.time.DateTime;

import java.util.Collections;

/**
 * Creates reasonable random instances of common data types for testing. This is
 * strongly preferred over mocking data type classes as mocking should be
 * reserved for defining behavior and not data.
 *
 * @author Ville Koskela (vkoskela at groupon dot com)
 */
public final class TestBeanFactory {

    /**
     * Create a builder for pseudo-random <code>Record</code>.
     *
     * @return New builder for pseudo-random <code>Record</code>.
     */
    public static DefaultRecord.Builder createRecordBuilder() {
        return new DefaultRecord.Builder()
                .setMetrics(Collections.singletonMap(
                        "foo/bar",
                        createMetric()))
                .setTime(new DateTime())
                .setAnnotations(Collections.<String, String>emptyMap());
    }

    /**
     * Create a new reasonable pseudo-random <code>Record</code>.
     *
     * @return New reasonable pseudo-random <code>Record</code>.
     */
    public static Record createRecord() {
        return createRecordBuilder().build();
    }

    /**
     * Create a builder for pseudo-random <code>Metric</code>.
     *
     * @return New builder for pseudo-random <code>Metric</code>.
     */
    public static DefaultMetric.Builder createMetricBuilder() {
        return new DefaultMetric.Builder()
                .setType(MetricType.COUNTER)
                .setValues(Collections.singletonList(new Quantity(3.14f, Optional.<Unit>absent())));
    }

    /**
     * Create a new reasonable pseudo-random <code>Metric</code>.
     *
     * @return New reasonable pseudo-random <code>Metric</code>.
     */
    public static Metric createMetric() {
        return createMetricBuilder().build();
    }

    private TestBeanFactory() {}
}
