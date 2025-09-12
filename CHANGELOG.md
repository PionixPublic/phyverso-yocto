
# Change Log

## [2.0.0] - Unreleased, experimental

### Release files

- will be added when ready for release

### Added

- adds Winline driver
- WIC file output enabled by default

### Changed

- switch from kirkstone to scarthgap
- MCU FW updated to 2.0.0-rc2 and corresponding PhyVersoBSP EVerest module integrated (TODO: link finished PR once merged into everest-core/main)
- switch from meta-basecamp to meta-everest
- root user default password changed to "everest"
- removed motor locks from DC port configs

### Fixed

- functional security related fixes in MCU FW
- MSP_RST devicetree label off-by-one fixed

## [1.0.3] - Unreleased, experimental stage

### Release files

- will be added when ready for release / for now only given to specific customers directly

### Added

- TPM support activated

### Changed

- tpm2-openssl updated to v1.2
- adds dc_external_derate interface/feature

### Fixed

- fixes bug with basecamp being started before lumissil is ready which leads to NMK key setting always failing on first HLC session

## [1.0.2] - Unreleased, experimental stage

### Release files

- will be added when ready for release to public / for now only given to specific customers directly

### Added

- adds new InfyPower basecamp driver for InfyPower modules using V1.13 CAN protocol
- adds Podman

### Changed

- remove unused ac-kit image recipe

### Fixed

- ~~fixes bug with basecamp being started before lumissil is ready which leads to NMK key setting always failing on first HLC session~~
**was actually not fixed here yet**

## [1.0.1] - 2025-06-23

### Release files
- http://pionix-update.de/phyverso/1.0.1/20250623081614/phyverso-basecamp-bundle-am62-phyverso-evcs-20250623081614.raucb
- http://pionix-update.de/phyverso/1.0.1/20250623081614/phyverso-basecamp-image-am62-phyverso-evcs-20250623081614.rootfs.partup
- http://pionix-update.de/phyverso/1.0.1/20250623081614/phyverso-basecamp-image-am62-phyverso-evcs-20250623081614.rootfs.wic.bmap
- http://pionix-update.de/phyverso/1.0.1/20250623081614/phyverso-basecamp-image-am62-phyverso-evcs-20250623081614.rootfs.wic.xz
- SDK: can be build using portal, process documented in README

### Added

- adds generic template config which shows AC / DC configs for both ports and how to integrate RS485 devices
- adds configs for Phytec AC and DC evaluation kits
- adds provisioning scripts in production/comission for Phytec AC and DC evaluation kits
- adds wifi module support for cc33xx
- adds display support for dd0700mc01_lvds/dpi
- adds support for Isabellenhuette IEM-DCR
- adds flutter support and example display-app
- adds nano
- adds preconfigured network file for Isabellenhuette IEM-DCR to configure phyVerso eth0 to be in default network segment as IEM-DCR

### Changed

- BaseCamp version bumped to 1.0.1 + hotfixes (image naming is coincidence, basecamp image version does not follow BaseCamp version employed on a given image)
- root user default password changed from empty to "basecamp"

### Fixed
- fixes cc33xx missing firmware bug
- fixes can bitrate for Huawei power supply in Phytec DC evaluation kit
- fixes PMIC bug that lead to cc33xx not restarting properly on warm reboot
- all 5V tolerant inputs/outputs now properly muxed
- integrates lumissil firmware fix made by Pionix+Lumissil

---

## [1.0.0] - 2024-11-26

### Release files
- http://pionix-update.de/phyverso/1.0.0/phyverso-basecamp-bundle-am62-phyverso-evcs-20241126150139.raucb
- http://pionix-update.de/phyverso/1.0.0/phyverso-basecamp-image-am62-phyverso-evcs-20241126150139.rootfs.partup
- http://pionix-update.de/phyverso/1.0.0/phyverso-basecamp-image-am62-phyverso-evcs-20241126150139.rootfs.wic.bmap
- http://pionix-update.de/phyverso/1.0.0/phyverso-basecamp-image-am62-phyverso-evcs-20241126150139.rootfs.wic.xz
- SDK: http://pionix-update.de/phyverso/1.0.0/phytec-ampliphy-rauc-glibc-x86_64-phyverso-basecamp-image-aarch64-toolchain-BSP-Yocto-Ampliphy-AM62x-PD23.2.1-phyVERSO-EVCS.sh

### Added

- adds phyverso-yocto repo git hash to /etc/os-release to keep track of which exact build is flashed to targets
- adds phyverso-yocto repo tag if present to /etc/os-release

### Changed

- everest-framework patch for BaseCamp banner on boot as requested by PHYTEC
- everest-cmake patch to remove '-dirty' from GIT_VERSION output on manager boot

### Fixed
- fixes partup partitioning in meta-phyverso-evcs

---

## [alpha-5.0] - 2024-11-18
 
### Release files

Release files for alpha versions have been removed. Get in contact with Pionix if you still explicitly need an alpha image. Otherwise please consider migrating to 1.0.0 and upwards!
 
### Added

- adds soft-stop button functionality on IN_1/IN_2 inputs on X43 (externaly attached pull resistors needed on input pins!)
- adds Eastron SDM230 1phase powermeter to AC kit configuration

### Changed

- Basecamp version bumped to 1.0-rc2

### Fixed

---
 
## [alpha-4.0] - 2024-10-21
 
### Release files
Release files for alpha versions have been removed. Get in contact with Pionix if you still explicitly need an alpha image. Otherwise please consider migrating to 1.0.0 and upwards!
 
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

Release files for alpha versions have been removed. Get in contact with Pionix if you still explicitly need an alpha image. Otherwise please consider migrating to 1.0.0 and upwards!

### Added
 
### Changed
  
- changed default motor lock types on all configs to time-based HELLA locks
- changed AC configs to have 1phase/16A current limit
 
### Fixed
 
- due to changes in meta-basecamp the basecamp config files were not added to yocto image anymore, this got fixed here


---

## [alpha-3] - 2024-10-11

### Release files
Release files for alpha versions have been removed. Get in contact with Pionix if you still explicitly need an alpha image. Otherwise please consider migrating to 1.0.0 and upwards!
 
### Added

- MCU firmware added to yocto image
- MCU firmware gets flashed on boot if needed (version in MCU differs from the one supplied in the yocto image)


### Changed
- uses Basecamp 0.4 release


### Fixed
 
- bug fixed where Lumissil PLC was in STA mode -> no CCO present, therefore no HLC communication was possible