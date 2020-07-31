# An example of a data oriented DSL

The problem consists on reading binary data from a buffer. This could be thought of as part of the solution
to the problem of implementing a parser of a binary protocol.

## Data representation considerations

We need to think of the variety of data that we want the reader to be able to handle. A good start could be
the primitive types. So we need to read integers of different sizes, as well as floating point numbers of
different precisions, etc. Also, since we are working at the binary level, we need to take into account
the endianness of the bytes.

## What would it look like?

```clojure
(def spec [{:type        :int32
            :big-endian? true}
           {:type        :float64
            :big-endian? true}])

;; The make-reader function is the interpreter of our DSL.
(def read (make-reader spec))

(def buffer [0x65 0x10 0xf3 0x29 0x40 0x00 0x00 0x00 0x00 0x00 0x00 0x00])

(read buffer) => [1695609641 2.0]
```

## Questions to improve the DSL

- What if I want to read a map with certain fields, or a record?
- Would it be a good idea to implement functions to compose different specs, or
  shall we not since this can be donde with the existing functions in Clojure?
- What about well defined sections of a buffer, like the ones in a TCP packet,
  is there a modificuation we could make to parse these better?
