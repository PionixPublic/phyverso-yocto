FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append = " \
	file://overlayfs.cfg \
"

PACKAGE_ARCH = "${MACHINE_ARCH}"

