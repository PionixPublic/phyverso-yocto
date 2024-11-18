
# Change Log

## [alpha-5.0] - 2024-11-18
 
### Release files
- http://pionix-update.de/phyverso/alpha-5.0/phyverso-basecamp-bundle-am62-phyverso-evcs-20241118094011.raucb
- http://pionix-update.de/phyverso/alpha-5.0/phyverso-basecamp-image-am62-phyverso-evcs-20241118094011.rootfs.partup
- http://pionix-update.de/phyverso/alpha-5.0/phyverso-basecamp-image-am62-phyverso-evcs-20241118094011.rootfs.wic.bmap
- http://pionix-update.de/phyverso/alpha-5.0/phyverso-basecamp-image-am62-phyverso-evcs-20241118094011.rootfs.wic.xz
 
### Added

- adds soft-stop button functionality on IN_1/IN_2 inputs on X43 (externaly attached pull resistors needed on input pins!)
- adds Eastron SDM230 1phase powermeter to AC kit configuration

### Changed

- Basecamp version bumped to 1.0-rc2

### Fixed

---
 
## [alpha-4.0] - 2024-10-21
 
### Release files
- http://pionix-update.de/phyverso/alpha-4/phyverso-basecamp-bundle-am62-phyverso-evcs-20241021050740.raucb
- http://pionix-update.de/phyverso/alpha-4/phyverso-basecamp-image-am62-phyverso-evcs-20241021050740.rootfs.partup
- http://pionix-update.de/phyverso/alpha-4/phyverso-basecamp-image-am62-phyverso-evcs-20241021050740.rootfs.wic.bmap
- http://pionix-update.de/phyverso/alpha-4/phyverso-basecamp-image-am62-phyverso-evcs-20241021050740.rootfs.wic.xz
 
### Added

- added preliminary AC kit config with 1phase/16A configuration

### Changed

- yocto build now uses PHYTEC provided meta-phyverso-evcs and meta-lumissil-greenphy layers instead of meta-ksp0782
- MACHINE name adjusted
- AC configs have been changed to have motor lock feature configured

### Fixed

---

## [alpha-3.1] - 2024-10-16

### Release files

- https://pionix-update.de/phyverso/alpha-3.1/phyverso-basecamp-bundle-am62-phyverso-evcs-1-20241016182129.raucb
- https://pionix-update.de/phyverso/alpha-3.1/phyverso-basecamp-image-am62-phyverso-evcs-1-20241016182129.rootfs.partup
- https://pionix-update.de/phyverso/alpha-3.1/phyverso-basecamp-image-am62-phyverso-evcs-1-20241016182129.rootfs.wic.bmap
- https://pionix-update.de/phyverso/alpha-3.1/phyverso-basecamp-image-am62-phyverso-evcs-1-20241016182129.rootfs.wic.xz

### Added
 
### Changed
  
- changed default motor lock types on all configs to time-based HELLA locks
- changed AC configs to have 1phase/16A current limit
 
### Fixed
 
- due to changes in meta-basecamp the basecamp config files were not added to yocto image anymore, this got fixed here


---

## [alpha-3] - 2024-10-11

### Release files
- https://pionix-update.de/phyverso/alpha-3/phyverso-basecamp-bundle-am62-phyverso-evcs-1-20241011094736.raucb
- https://pionix-update.de/phyverso/alpha-3/phyverso-basecamp-image-am62-phyverso-evcs-1-20241011094736.rootfs.partup
- https://pionix-update.de/phyverso/alpha-3/phyverso-basecamp-image-am62-phyverso-evcs-1-20241011094736.rootfs.wic.bmap
- https://pionix-update.de/phyverso/alpha-3/phyverso-basecamp-image-am62-phyverso-evcs-1-20241011094736.rootfs.wic.xz
 
### Added

- MCU firmware added to yocto image
- MCU firmware gets flashed on boot if needed (version in MCU differs from the one supplied in the yocto image)


### Changed
- uses Basecamp 0.4 release


### Fixed
 
- bug fixed where Lumissil PLC was in STA mode -> no CCO present, therefore no HLC communication was possible