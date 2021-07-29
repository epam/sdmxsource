## SdmxSource

SdmxSource is based on the https://metadatatechnology.com/software/sdmxsource.php.

Initial implementation was taken from https://sdmxsource.metadatatechnology.com/nexus/content/repositories/releases version 1.5.6.6

Part of JSON serialization/deserialization functionality was ported from SdmxSource .NET implementation by Eurostat https://ec.europa.eu/eurostat/web/sdmx-infospace/sdmx-it-tools/sdmx-source

### Objectives:
- Get rid of dependency injection to make using of SdmxSource easier;
- Upgrade or get rid of Spring dependency;
- Add features necessary for https://gitlab.deltixhub.com/Deltix/Nursery/sdmx-prototype.
- Change default value of timeDimension.localRepresentation to be SDMX-compliant
