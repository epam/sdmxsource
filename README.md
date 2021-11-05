# SdmxSource

A fork of a wonderful [`SdmxSource`](https://metadatatechnology.com/software/sdmxsource.php) library by `Metadata Technology`.

The fork is based on latest (to date) public version of `SdmxSource` `1.5.6.6`. The motivation behind forking is to resolve several limitations of the original library that are important for its wider adoption as well as wider community contribution to the project.

## Key changes

* **Released on `GitHub`.** Previously the source code was only available as a `-sources` artifact within a `Maven` package. This provides a common playground for community contribution.

* **Published to `Maven Central`.** Previously only available from private `Maven` registry owned by `Metadata Technology` subject to downtimes.

* **Removed aggressive dependency injection and transitive dependencies to `Spring` framework.** Design of the original library was heavily using dependency injection mechanism. This design decision complicated using the library as a set of utility classes making integration with the library complex and invasive from the perspective of transitive dependencies to `Spring` packages. Library code was refactored to enable manual creation and initialization of objects. While the objects are no longer 'beans', their original naming which used `Beans` suffix was preserved.

## Other improvements

* `SDMX-JSON` serialization / deserialization for structural artefacts. Ported from .NET version of [`SdmxSource` by `Eurostat`](https://ec.europa.eu/eurostat/web/sdmx-infospace/sdmx-it-tools/sdmx-source).
* `SDMX-CSV` serialization / deserialization for data sets.
* Unit test coverage. Ported from .NET version of [`SdmxSource` by `Eurostat`](https://ec.europa.eu/eurostat/web/sdmx-infospace/sdmx-it-tools/sdmx-source).
* Bug fixes.

