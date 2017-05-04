
# EventTime = ZonedDateTime × isTimeExplicit

**This project is now under development.**

`EventTime` is a simple, concrete and immutable Java class that is essentially a `java.time.ZonedDateTime` with a flag that indicates if the time part is explicit or not.

If `isTimeExplicit` flag is `false`, i.e. the time part is not given to a constructor, the time part is initialized to "00:00:00".

## Parsing and formatting

TBW. It supports ISO8601, with some extension.

## Comparison and equality

`compareTo` method first compares `java.time.Instance`s obtained from the `ZonedDateTime` parts of the two `EventTime` instances. Ties are broken by comparing the `isTimeExplicit` flags (flag `true` is bigger than `false`.)

`equals` method is consistent with `compareTo`. This means there are multiple `EventTime` instances with different time zones that equal to each other.


# License

Copyright 2017 Toshio Ito <debug.ito@gmail.com>

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
