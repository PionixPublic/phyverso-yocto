FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append:am62-phyverso-evcs = " \
	file://0001-u-boot-dts-switch-USB0-to-host-mode.patch \
	file://0002-add-flash_emmc-u-boot-script.patch \
"

SRC_URI:append:am62-phyverso-evcs-k3r5 = " \
	file://0001-u-boot-dts-switch-USB0-to-host-mode.patch \
	file://0002-add-flash_emmc-u-boot-script.patch \
"