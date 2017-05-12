
# EventTime = ZonedDateTime × isTimeExplicit

`EventTime` is a simple, concrete and immutable Java class that is essentially a `java.time.ZonedDateTime` with a flag that indicates if the time part is explicit or not.

If `isTimeExplicit` flag is `false`, i.e. the time part is not given to a constructor, the time part is initialized to "00:00:00".

The advantage of this class is:

- It models both date and date-time, which is often necessary when we think about events in our schedule.
- It is comparable, event though it is essentially either date or date-time.


## Parsing and formatting

`parse` method parses strings of ISO8601-like format, e.g. "2017-05-12", "2017-05-12+09:00", "2017-05-12T19:12:11+09:00". If the time zone is omitted, the system's default time zone is used.

It can also parse time zone IDs, e.g. "2017-05-12T19:12:11[Asia/Tokyo]". (This is not part of ISO8601, though.)

When formatting, it formats the date part only if the `isTimeExplicit` is `false`. If `isTimeExplicit` is `true`, it formats the time part, too.

For detail, see the [test spec](https://github.com/debug-ito/event-time-java/blob/master/src/test/java/com/github/debug_ito/event_time/test/EventTimeParserFormattersTest.java).


## Comparison and equality

`compareTo` method first compares `java.time.Instance`s obtained from the `ZonedDateTime` parts of the two `EventTime` instances. Ties are broken by comparing the `isTimeExplicit` flags (flag `true` is bigger than `false`.)

`equals` method is consistent with `compareTo`. This means there are multiple `EventTime` instances with different time zones that equal to each other.

For detail, see [test case](https://github.com/debug-ito/event-time-java/blob/master/src/test/java/com/github/debug_ito/event_time/test/EventTimeComparisonTest.java).

# License

Copyright 2017 Toshio Ito <debug.ito@gmail.com>

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
