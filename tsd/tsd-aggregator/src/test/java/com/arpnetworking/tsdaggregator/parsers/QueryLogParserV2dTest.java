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

package com.arpnetworking.tsdaggregator.parsers;

import com.arpnetworking.tsdaggregator.model.Metric;
import com.arpnetworking.tsdaggregator.model.Record;
import com.arpnetworking.tsdcore.model.Unit;
import com.arpnetworking.tsdcore.parsers.exceptions.ParsingException;
import com.google.common.io.Resources;

import org.hamcrest.Matchers;
import org.hamcrest.collection.IsMapContaining;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

/**
 * Tests for the 2d version of the query log format.
 *
 * @author Brandon Arp (barp at groupon dot com)
 */
public class QueryLogParserV2dTest {

    @Test
    public void testParse() throws ParsingException, IOException {
        final Record record = parseRecord("QueryLogParserV2dTest.testParse.json");
        Assert.assertNotNull(record);

        Assert.assertEquals(DateTime.parse("2014-03-24T12:15:41.010Z"), record.getTime());

        final Map<String, ? extends Metric> variables = record.getMetrics();
        Assert.assertThat(variables, Matchers.<String>hasKey("foo"));
        final Metric fooVar = variables.get("foo");
        Assert.assertEquals(4, fooVar.getValues().size());
        Assert.assertTrue(fooVar.getValues().get(0).getUnit().isPresent());
        Assert.assertEquals(Unit.MILLISECOND, fooVar.getValues().get(0).getUnit().get());

        Assert.assertThat(variables, Matchers.<String>hasKey("mem"));
        final Metric memVar = variables.get("mem");
        Assert.assertEquals(3, memVar.getValues().size());
        Assert.assertTrue(memVar.getValues().get(0).getUnit().isPresent());
        Assert.assertEquals(Unit.GIGABYTE, memVar.getValues().get(0).getUnit().get());

        Assert.assertThat(variables, Matchers.<String>hasKey("dbQueries"));
        final Metric queriesVar = variables.get("dbQueries");
        Assert.assertEquals(1, queriesVar.getValues().size());
        Assert.assertFalse(queriesVar.getValues().get(0).getUnit().isPresent());
    }

    @Test
    public void testAnnotations() throws ParsingException, IOException {
        final Record record = parseRecord("QueryLogParserV2dTest.testAnnotations.json");
        Assert.assertNotNull(record);

        Assert.assertEquals(2, record.getAnnotations().size());
        Assert.assertThat(record.getAnnotations(), IsMapContaining.hasEntry("method", "POST"));
        Assert.assertThat(record.getAnnotations(), IsMapContaining.hasEntry("request_id", "c5251254-8f7c-4c21-95da-270eb66e100b"));
    }

    @Test
    public void testNoValues() throws ParsingException, IOException {
        final Record record = parseRecord("QueryLogParserV2dTest.testNoValues.json");
        Assert.assertNotNull(record);

        final Map<String, ? extends Metric> variables = record.getMetrics();
        Assert.assertThat(variables, Matchers.<String>hasKey("foo"));
        final Metric fooVar = variables.get("foo");
        Assert.assertEquals(0, fooVar.getValues().size());
    }

    @Test(expected = ParsingException.class)
    public void testBadFinalTimestamp() throws ParsingException, IOException {
        parseRecord("QueryLogParserV2dTest.testBadFinalTimestamp.json");
    }

    @Test(expected = ParsingException.class)
    public void testMissingFinalTimestamp() throws ParsingException, IOException {
        parseRecord("QueryLogParserV2dTest.testMissingFinalTimestamp.json");
    }

    @Test(expected = ParsingException.class)
    public void testBadInitialTimestamps() throws ParsingException, IOException {
        parseRecord("QueryLogParserV2dTest.testBadInitialTimestamp.json");
    }

    @Test(expected = ParsingException.class)
    public void testNullAnnotations() throws ParsingException, IOException {
        parseRecord("QueryLogParserV2dTest.testNullAnnotations.json");
    }

    @Test(expected = ParsingException.class)
    public void testMissingAnnotations() throws ParsingException, IOException {
        parseRecord("QueryLogParserV2dTest.testMissingAnnotations.json");
    }

    @Test(expected = ParsingException.class)
    public void testMissingBothTimestamps() throws ParsingException, IOException {
        parseRecord("QueryLogParserV2dTest.testMissingBothTimestamps.json");
    }

    @Test(expected = ParsingException.class)
    public void testBadAnnotations() throws ParsingException, IOException {
        parseRecord("QueryLogParserV2dTest.testBadAnnotations.json");
    }

    @Test(expected = ParsingException.class)
    public void testBadElement() throws ParsingException, IOException {
        parseRecord("QueryLogParserV2dTest.testBadElement.json");
    }

    @Test(expected = ParsingException.class)
    public void testNullElement() throws ParsingException, IOException {
        parseRecord("QueryLogParserV2dTest.testNullElement.json");
    }

    @Test(expected = ParsingException.class)
    public void testNullValues() throws ParsingException, IOException {
        parseRecord("QueryLogParserV2dTest.testNullValue.json");
    }

    @Test(expected = ParsingException.class)
    public void parse2dBadValue() throws ParsingException, IOException {
        parseRecord("QueryLogParserV2dTest.testBadValue.json");
    }

    @Test(expected = ParsingException.class)
    public void testNullMetric() throws ParsingException, IOException {
        parseRecord("QueryLogParserV2dTest.testNullMetric.json");
    }

    private static Record parseRecord(final String fileName) throws ParsingException, IOException {
        return new QueryLogParser().parse(Resources.toByteArray(Resources.getResource(
                QueryLogParserV2dTest.class, fileName)));
    }
}
