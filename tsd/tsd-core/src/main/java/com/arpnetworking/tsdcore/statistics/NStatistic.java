/**
 * Copyright 2014 Brandon Arp
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
package com.arpnetworking.tsdcore.statistics;

import com.arpnetworking.tsdcore.model.Quantity;

import java.util.List;

/**
 * Counts the entries.
 *
 * @author Brandon Arp (barp at groupon dot com)
 */
public class NStatistic extends BaseStatistic {

    /**
     * {@inheritDoc}
     */
    @Override
    public Double calculate(final List<Quantity> unorderedValues) {
        return Double.valueOf(unorderedValues.size());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return "n";
    }

}
