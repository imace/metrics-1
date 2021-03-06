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
package com.arpnetworking.tsdcore.statistics;

import com.arpnetworking.test.TestBeanFactory;
import com.arpnetworking.tsdcore.model.Quantity;
import com.google.common.collect.Lists;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Tests the TPStatistic class.
 *
 * @author Brandon Arp (barp at groupon dot com)
 */
public class TPStatisticTest {

    @Test
    public void testName() {
        Assert.assertEquals("tp50", new TP50Statistic().getName());
        Assert.assertEquals("tp90", new TP90Statistic().getName());
        Assert.assertEquals("tp95", new TP95Statistic().getName());
        Assert.assertEquals("tp99", new TP99Statistic().getName());
        Assert.assertEquals("tp99.9", new TP99p9Statistic().getName());
        Assert.assertEquals("min", new TP0Statistic().getName());
        Assert.assertEquals("max", new TP100Statistic().getName());
    }

    @Test
    public void testTP0Stat() {
        final TPStatistic tp0 = new TP0Statistic();

        final List<Quantity> vals = TestBeanFactory.createSamples(ONE_TO_FIVE);
        final Double tp0stat = tp0.calculate(vals);
        Assert.assertThat(tp0stat, CoreMatchers.is(Double.valueOf(1d)));
    }

    @Test
    public void testTP100Stat() {
        final TPStatistic tp100 = new TP100Statistic();
        final List<Double> doubleVals = Lists.newArrayList(ONE_TO_FIVE);
        final List<Quantity> vals = TestBeanFactory.createSamples(doubleVals);
        final Double tpstat = tp100.calculate(vals);
        Assert.assertThat(tpstat, CoreMatchers.is(Double.valueOf(5d)));
    }

    @Test
    public void testTP99StatSmallSet() {
        final TPStatistic tp = new TP99Statistic();
        final List<Double> doubleVals = Lists.newArrayList(ONE_TO_FIVE);
        final List<Quantity> vals = TestBeanFactory.createSamples(doubleVals);
        final Double tpstat = tp.calculate(vals);
        Assert.assertThat(tpstat, CoreMatchers.is(Double.valueOf(5d)));
    }

    @Test
    public void testTP99Stat() {
        final TPStatistic tp = new TP99Statistic();
        final ArrayList<Double> vList = Lists.newArrayList();
        for (int x = 0; x < 100; ++x) {
            vList.add(Double.valueOf(x));
        }
        final List<Quantity> vals = TestBeanFactory.createSamples(vList);
        final Double tpstat = tp.calculate(vals);
        Assert.assertThat(tpstat, CoreMatchers.is(Double.valueOf(99d)));
    }

    @Test
    public void testTP50Stat() {
        final TPStatistic tp = new TP50Statistic();
        final ArrayList<Double> vList = Lists.newArrayList();
        for (int x = 0; x < 100; ++x) {
            vList.add(Double.valueOf(x));
        }
        final List<Quantity> vals = TestBeanFactory.createSamples(vList);
        final Double tpstat = tp.calculate(vals);
        Assert.assertThat(tpstat, CoreMatchers.is(Double.valueOf(50d)));
    }

    @Test
    public void testTP999Stat() {
        final TPStatistic tp = new TP99p9Statistic();
        final ArrayList<Double> vList = Lists.newArrayList();
        for (int x = 0; x < 10000; ++x) {
            vList.add(Double.valueOf(x));
        }
        final List<Quantity> vals = TestBeanFactory.createSamples(vList);
        final Double tpstat = tp.calculate(vals);
        Assert.assertThat(tpstat, CoreMatchers.is(Double.valueOf(9990d)));
    }

    @Test
    public void testEquality() {
        Assert.assertFalse(new TP0Statistic().equals(null));
        Assert.assertFalse(new TP0Statistic().equals("ABC"));
        Assert.assertTrue(new TP0Statistic().equals(new TP0Statistic()));

        Assert.assertFalse(new TP50Statistic().equals(null));
        Assert.assertFalse(new TP50Statistic().equals("ABC"));
        Assert.assertTrue(new TP50Statistic().equals(new TP50Statistic()));

        Assert.assertFalse(new TP90Statistic().equals(null));
        Assert.assertFalse(new TP90Statistic().equals("ABC"));
        Assert.assertTrue(new TP90Statistic().equals(new TP90Statistic()));

        Assert.assertFalse(new TP95Statistic().equals(null));
        Assert.assertFalse(new TP95Statistic().equals("ABC"));
        Assert.assertTrue(new TP95Statistic().equals(new TP95Statistic()));

        Assert.assertFalse(new TP99Statistic().equals(null));
        Assert.assertFalse(new TP99Statistic().equals("ABC"));
        Assert.assertTrue(new TP99Statistic().equals(new TP99Statistic()));

        Assert.assertFalse(new TP99p9Statistic().equals(null));
        Assert.assertFalse(new TP99p9Statistic().equals("ABC"));
        Assert.assertTrue(new TP99p9Statistic().equals(new TP99p9Statistic()));

        Assert.assertFalse(new TP100Statistic().equals(null));
        Assert.assertFalse(new TP100Statistic().equals("ABC"));
        Assert.assertTrue(new TP100Statistic().equals(new TP100Statistic()));
    }

    @Test
    public void testHashCode() {
        Assert.assertEquals(new TP0Statistic().hashCode(), new TP0Statistic().hashCode());
        Assert.assertEquals(new TP50Statistic().hashCode(), new TP50Statistic().hashCode());
        Assert.assertEquals(new TP90Statistic().hashCode(), new TP90Statistic().hashCode());
        Assert.assertEquals(new TP95Statistic().hashCode(), new TP95Statistic().hashCode());
        Assert.assertEquals(new TP99Statistic().hashCode(), new TP99Statistic().hashCode());
        Assert.assertEquals(new TP99p9Statistic().hashCode(), new TP99p9Statistic().hashCode());
        Assert.assertEquals(new TP100Statistic().hashCode(), new TP100Statistic().hashCode());
    }

    private static final List<Double> ONE_TO_FIVE = Lists.newArrayList(
            Double.valueOf(1d),
            Double.valueOf(2d),
            Double.valueOf(3d),
            Double.valueOf(4d),
            Double.valueOf(5d));
}
