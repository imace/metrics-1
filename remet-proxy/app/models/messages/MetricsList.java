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

package models.messages;

import com.google.common.base.Objects;

import java.util.Map;
import java.util.Set;

/**
 * Message class to hold a tree of metrics.
 *
 * @author Brandon Arp (barp at groupon dot com)
 */
public final class MetricsList {
    /**
     * Public constructor.
     *
     * @param metrics Metrics map
     */
    public MetricsList(final Map<String, Map<String, Set<String>>> metrics) {
        _metrics = metrics;
    }

    public Map<String, Map<String, Set<String>>> getMetrics() {
        return _metrics;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("Metrics", _metrics)
                .toString();
    }

    private final Map<String, Map<String, Set<String>>> _metrics;
}
