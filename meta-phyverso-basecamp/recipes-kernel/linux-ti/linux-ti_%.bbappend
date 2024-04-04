FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append = " \
	file://overlayfs.cfg \
	file://usb_ch341.cfg \
"

PACKAGE_ARCH = "${MACHINE_ARCH}"

