
# EventTime = LocalDate | ZonedDateTime

**This project is now under development.**


`EventTime` is a simple, concrete Java class that represents either `java.time.LocalDate` or `java.time.ZonedDateTime`, i.e. it's a sum (or union) type of the two back-end types.

`EventTime` roughly models how we, humans, think about dates and times for upcoming events, like when you use a calendar app or manually hack iCalendar ([RFC 5545](https://tools.ietf.org/html/rfc5545)) files.

## Parsing and formatting

TBW. It supports ISO8601, with some extension.

## Equality and comparison

TBW.

# License

Copyright 2017 Toshio Ito <debug.ito@gmail.com>

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
